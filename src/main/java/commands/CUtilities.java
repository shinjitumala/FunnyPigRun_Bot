package commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import main.FPR;
import myutils.MyUtils;
import myutils.UTemplates;
import myutils.enums.ERoles;

public class CUtilities extends AbstractCommand {
  @ACommand(
      command = "iam",
      permission = ERoles.DRIFTER,
      help = "Usage: `" + "<FPR>" + "iam <role tag>`\n"
          + "Will change your role to the specified one. \n"
          + "You can check the tag for a role using the `" + "<FPR>" + "rolelist` command.")
  protected boolean iam(MessageCreateEvent event, Scanner scanner)
      throws ExCommandException, NoSuchElementException, IllegalStateException {
    String tag = scanner.next();

    Role nationality = MyUtils.findNationality(tag);
    if (nationality == null) {
      FPR.log().error("CUtilites: iam() > Cannot find a role with tag \"" + tag + "\".");
      throw new ExCommandException("Cannot find a role with that tag.");
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
        throw new ExCommandException("Error while managing roles.");
      }

      EmbedBuilder embed = UTemplates
          .completeTemplate(user.get().getMentionTag() + ", your role has been changed to "
              + nationality.getMentionTag() + ".");
      event.getChannel().sendMessage(embed);
      return true;
    } else {
      return false;
    }
  }

  @ACommand(
      command = "ping",
      permission = ERoles.DRIFTER,
      help = "Pings the bot in order to check if it is alive.")
  protected boolean ping(MessageCreateEvent event) throws ExCommandException {
    EmbedBuilder embed = UTemplates
        .embedTemplate("Pong!",
            "Don't worry " + event.getMessage().getUserAuthor().get().getMentionTag()
                + "!\n I'm alive and well :)",
            Color.BLUE);

    event.getChannel().sendMessage(embed);
    return true;
  }

  @ACommand(
      command = "rolelist",
      permission = ERoles.DRIFTER,
      help = "Lists all the roles in funny.pig.run Gaming form top to bottom with tags if available.\n"
          + "Use this to check the permission hierarchy. Or to check a role's tag.")
  protected boolean rolelist(MessageCreateEvent event) {
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

    EmbedBuilder embed = UTemplates
        .embedTemplate("Roles in funny.pig.gaming from top to bottom with tags if available",
            sb.toString(), Color.GRAY);

    event.getChannel().sendMessage(embed);
    return true;
  }

}
