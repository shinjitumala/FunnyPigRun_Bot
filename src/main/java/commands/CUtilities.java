package commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import main.FPR;
import myutils.MyUtils;
import myutils.ServerRole;

public class CUtilities extends ICommand {
  public CUtilities() {
    super(FPR.getRole(ServerRole.DRIFTER.toString()));
  }

  @Override
  protected boolean exec(String[] command, MessageCreateEvent event) throws ECommandExecutionException {
    if (command.length == 1) {
      switch (command[0]) {
        case "ping":
          return ping(event);
        case "rolelist":
          return rolelist(event);
      }
    } else if ((command.length == 2) && command[1].equals("--help")) {
      switch (command[0]) {
        case "ping":
          event.getChannel().sendMessage(MyUtils.helpTemplate(command[0], Hping, permission));
          return true;
        case "rolelist":
          event.getChannel().sendMessage(MyUtils.helpTemplate(command[0], Hrolelist, permission));
          return true;
        case "iam":
          event.getChannel().sendMessage(MyUtils.helpTemplate(command[0], Hiam, permission));
      }
    } else if (command.length == 2) {
      switch (command[0]) {
        case "iam":
          return iam(event, command[1]);
      }
    }
    return false;
  }

  // --------------------------------
  private final String Hiam = "Usage: `" + MainCommand.prefix() + "iam <role tag>`\n"
      + "Will change your role to the specified one. \n" + "You can check the tag for a role using the `"
      + MainCommand.prefix() + "rolelist` command.";

  private boolean iam(MessageCreateEvent event, String tag) throws ECommandExecutionException {
    Role nationality = MyUtils.findNationality(tag);
    if (nationality == null) {
      FPR.log().error("CUtilites: iam() > Cannot find a role with tag \"" + tag + "\".");
      throw new ECommandExecutionException("Cannot find a role with that tag.", "iam");
    }

    Optional<User> user = event.getMessage().getUserAuthor();
    if (user.isPresent()) {
      try {
        List<Role> roles = user.get().getRoles(FPR.server());
        for (String key : FPR.nationality().keySet()) {
          Role n = FPR.nationality().get(key);
          for (Role r : roles) {
            if (r.equals(n)) {
              user.get().removeRole(r).get();
            }
          }
        }

        user.get().addRole(nationality).get();
      } catch (InterruptedException | ExecutionException e) {
        FPR.log().error("CUtilites: iam() > Error while managing roles.");
        throw new ECommandExecutionException("Error while managing roles.", "iam");
      }

      EmbedBuilder embed = MyUtils
          .completeTemplate(
              user.get().getMentionTag() + ", your role has been changed to " + nationality.getMentionTag() + ".");
      event.getChannel().sendMessage(embed);
    } else {
      return false;
    }

    return false;
  }

  // --------------------------------
  private final String Hping = "Pings the bot in order to check if it is alive.";

  private boolean ping(MessageCreateEvent event) throws ECommandExecutionException {
    EmbedBuilder embed = MyUtils
        .embedTemplate("Pong!",
            "Don't worry " + event.getMessage().getUserAuthor().get().getMentionTag() + "!\n I'm alive and well :)",
            Color.BLUE);

    event.getChannel().sendMessage(embed);
    return true;
  }

  // --------------------------------
  private final String Hrolelist = "Lists all the roles in funny.pig.run Gaming form top to bottom with tags if available.\n"
      + "Use this to check the permission hierarchy. Or to check a role's tag.";

  private boolean rolelist(MessageCreateEvent event) {
    List<Role> tmp = FPR.server().getRoles();
    ArrayList<Role> roles = new ArrayList<>(tmp);
    roles.sort((r1, r2) -> {
      return r2.getPosition() - r1.getPosition();
    });

    StringBuilder sb = new StringBuilder();

    for (Role r : roles) {
      if (r.isMentionable() && !r.isEveryoneRole()) {
        sb.append(r.getMentionTag());
        for (String key : FPR.nationality().keySet()) {
          if (FPR.nationality().get(key).equals(r)) {
            sb.append(": " + key);
          }
        }
        sb.append("\n");
      }
    }

    EmbedBuilder embed = MyUtils
        .embedTemplate("Roles in funny.pig.gaming from top to bottom with tags if available", sb.toString(),
            Color.GRAY);

    event.getChannel().sendMessage(embed);
    return true;
  }

}
