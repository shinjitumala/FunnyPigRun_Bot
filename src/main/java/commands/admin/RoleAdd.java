package commands.admin;

import java.util.Scanner;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import commands.ACommand;
import commands.ExCommandException;
import commands.ICommand;
import commands.MainCommand;
import data.SRole;
import main.FPR;
import myutils.MyUtils;
import myutils.UParse;
import myutils.UTemplates;
import myutils.enums.ERoles;

@ACommand(
    command = "addrole",
    permission = ERoles.PIGGIES,
    help = "Usage: `" + MainCommand.PREFIX + "addrole <tag> <mention tag>`\n"
        + "Adds the mentioned role to be used as a nationality role with the tag <tag>.")
public class RoleAdd implements ICommand {
  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    String tag = UParse.parseString(scanner);
    String id = UParse.parseRole(scanner);

    Role role = MyUtils.get(FPR.server().getRoleById(id));
    SRole.nationality.put(tag, role);
    SRole.save();
    EmbedBuilder embed = UTemplates
        .completeTemplate("The role " + role.getMentionTag() + " was added with the tag \"" + tag
            + "\".\n" + "You can now change your nationality to this role by using the `"
            + MainCommand.PREFIX + "iam " + tag + "` command.");
    event.getChannel().sendMessage(embed);
    return true;
  }

}
