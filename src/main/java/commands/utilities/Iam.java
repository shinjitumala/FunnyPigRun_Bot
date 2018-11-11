package commands.utilities;

import java.util.List;
import java.util.Scanner;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
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
    command = "iam",
    permission = ERoles.DRIFTER,
    help = "Usage: `" + MainCommand.PREFIX + "iam <role tag>`\n"
        + "Will change your role to the specified one. \n"
        + "You can check the tag for a role using the `" + MainCommand.PREFIX
        + "rolelist` command.")
public class Iam implements ICommand {

  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    String tag = UParse.parseString(scanner);

    Role nationality = MyUtils.findNationality(tag);
    User user = MyUtils.get(event.getMessage().getUserAuthor());
    List<Role> roles = user.getRoles(FPR.server());
    for (String key : SRole.nationality.keySet()) {
      Role n = SRole.nationality.get(key);
      for (Role r : roles) {
        if (r.equals(n)) {
          MyUtils.wait(user.removeRole(r));
        }
      }
    }
    MyUtils.wait(user.addRole(nationality));

    EmbedBuilder embed = UTemplates
        .completeTemplate(user.getMentionTag() + ", your role has been changed to "
            + nationality.getMentionTag() + ".");
    event.getChannel().sendMessage(embed);
    return true;
  }
}
