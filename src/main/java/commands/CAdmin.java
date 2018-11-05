package commands;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import data.SRole;
import main.FPR;
import myutils.UFiles;
import myutils.UTemplates;
import myutils.enums.EFiles;
import myutils.enums.ERoles;

public class CAdmin extends AbstractCommand {
  @ACommand(
      command = "addrole",
      permission = ERoles.PIGGIES,
      help = "Usage: `addrole <tag> <id>`\n"
          + "Adds the role with <id> to be used as a nationality role with the tag <tag>.")
  public static boolean roleAdd(MessageCreateEvent event, Scanner scanner)
      throws ExCommandException, NoSuchElementException, IllegalStateException {
    String tag = scanner.next();
    String id = scanner.next();

    Role role;
    try {

      long l = Long.parseLong(id);
      role = FPR.server().getRoleById(l).get();
    } catch (NoSuchElementException | NumberFormatException e) {
      FPR.log().error("CAdmin: addRole() > Could not find a role given ID!");
      throw new ExCommandException("Could not find a role with that ID.");
    }

    FPR.nationality().put(tag, role);

    ArrayList<SRole> list = new ArrayList<>();
    for (String key : FPR.nationality().keySet()) {
      list.add(new SRole(FPR.nationality().get(key), key));
    }

    if (!UFiles.writeObject(list, EFiles.NATIONALITY.toString())) {
      FPR
          .log()
            .error("CAdmin: roleAdd() > Error occured while writing to "
                + EFiles.NATIONALITY.toString() + ".");
      throw new ExCommandException("Error writing to file");
    }

    EmbedBuilder embed = UTemplates
        .completeTemplate("The role " + role.getMentionTag() + " was added with the tag \"" + tag
            + "\".\n" + "You can now change your nationality to this role by using the `"
            + MainCommand.prefix() + "iam " + tag + "` command.");
    event.getChannel().sendMessage(embed);

    return true;
  }

}
