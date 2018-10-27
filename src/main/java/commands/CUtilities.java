package commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
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
          ping(event);
        break;
        case "rolelist":
          rolelist(event);
        break;
      }
    } else if ((command.length == 2) && command[1].equals("--help")) {
      switch (command[0]) {
        case "ping":
          event.getChannel().sendMessage(MyUtils.helpTemplate(command[0], Hping, permission));
        break;
      }
    }
    return false;
  }

  private final String Hping = "This command pings the bot in order to check if it is alive.";

  private void ping(MessageCreateEvent event) throws ECommandExecutionException {
    EmbedBuilder embed = MyUtils.embedTemplate("Pong!",
        "Don't worry " + event.getMessage().getUserAuthor().get().getMentionTag() + "!\n I'm alive and well :)",
        Color.BLUE);

    event.getChannel().sendMessage(embed);
  }

  private void rolelist(MessageCreateEvent event) {
    List<Role> tmp = FPR.server().getRoles();
    ArrayList<Role> roles = new ArrayList<>(tmp);
    roles.sort((r1, r2) -> {
      return r2.getPosition() - r1.getPosition();
    });

    StringBuilder sb = new StringBuilder();

    for (Role r : roles) {
      if (r.isMentionable() && !r.isEveryoneRole()) {
        sb.append(r.getMentionTag());
        sb.append("\n");
      }
    }

    EmbedBuilder embed = MyUtils.embedTemplate("Roles in funny.pig.gaming from top to bottom", sb.toString(),
        Color.GRAY);

    event.getChannel().sendMessage(embed);
  }

}
