package commands.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import commands.ACommand;
import commands.ExCommandException;
import commands.ICommand;
import main.FPR;
import myutils.UTemplates;
import myutils.enums.ERoles;

@ACommand(
    command = "rolelist",
    permission = ERoles.DRIFTER,
    help = "Lists all the roles in funny.pig.run Gaming form top to bottom with tags if available.\n"
        + "Use this to check the permission hierarchy. Or to check a role's tag.")
public class RoleList implements ICommand {
  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
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
