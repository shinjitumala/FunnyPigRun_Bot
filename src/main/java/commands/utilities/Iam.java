package commands.utilities;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import commands.ACommand;
import commands.ExCommandException;
import commands.ICommand;
import commands.MainCommand;
import main.FPR;
import myutils.MyUtils;
import myutils.UTemplates;
import myutils.enums.ERoles;

@ACommand(
    command = "iam",
    permission = ERoles.DRIFTER,
    help = "Usage: `" + MainCommand.PREFIX + "iam <role tag>`\n"
        + "Will change your role to the specified one. \n"
        + "You can check the tag for a role using the `" + MainCommand.PREFIX
        + "rolelist` command.")
public class Iam implements ICommand {

  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    String tag;
    try {
      tag = scanner.next();
    } catch (NoSuchElementException e) {
      throw new ExCommandException("Insufficient arguments.");
    }

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
      throw new ExCommandException("Target user is missing.");
    }
  }
}
