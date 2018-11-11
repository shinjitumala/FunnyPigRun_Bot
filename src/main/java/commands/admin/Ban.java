package commands.admin;

import java.util.Scanner;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import commands.ACommand;
import commands.ExCommandException;
import commands.ICommand;
import commands.MainCommand;
import main.FPR;
import myutils.MyUtils;
import myutils.UParse;
import myutils.enums.ERoles;

@ACommand(
    command = "ban",
    permission = ERoles.PIGGIES,
    help = "Usage: `" + MainCommand.PREFIX + "ban <mention tag> <reason> <delete message days>`\n"
        + "Bans user from the server. ")
public class Ban implements ICommand {
  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    String id = UParse.parseUser(scanner);
    String reason = UParse.parseString(scanner);
    int days = UParse.parseInt(scanner);

    User user = MyUtils.wait(FPR.api().getUserById(id));
    MyUtils.wait(FPR.server().banUser(user, days, reason));
    return true;
  }
}
