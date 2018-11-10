package commands.admin;

import java.util.NoSuchElementException;
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
import myutils.UTemplates;
import myutils.enums.EFiles;
import myutils.enums.ERoles;

@ACommand(
    command = "addrole",
    permission = ERoles.PIGGIES,
    help = "Usage: `" + MainCommand.PREFIX + "addrole <tag> <id>`\n"
        + "Adds the role with <id> to be used as a nationality role with the tag <tag>.")
public class RoleAdd implements ICommand {
  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    String tag, id;
    try {
      tag = scanner.next();
      id = scanner.next();
    } catch (NoSuchElementException e) {
      throw new ExCommandException("Insufficient Arguments.");
    }

    Role role;
    try {

      long l = Long.parseLong(id);
      role = FPR.server().getRoleById(l).get();
    } catch (NoSuchElementException | NumberFormatException e) {
      FPR.log().error("CAdmin: addRole() > Could not find a role given ID!");
      throw new ExCommandException("Could not find a role with that ID.");
    }

    FPR.nationality().put(tag, role);

    if (!SRole.save()) {
      FPR
          .log()
            .error("CAdmin: roleAdd() > Error occured while writing to "
                + EFiles.NATIONALITY.toString() + ".");
      throw new ExCommandException("Error writing to file.");
    }

    EmbedBuilder embed = UTemplates
        .completeTemplate("The role " + role.getMentionTag() + " was added with the tag \"" + tag
            + "\".\n" + "You can now change your nationality to this role by using the `"
            + MainCommand.PREFIX + "iam " + tag + "` command.");
    event.getChannel().sendMessage(embed);

    return true;
  }

}
