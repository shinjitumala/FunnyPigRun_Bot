package commands.admin;

import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;

import commands.ACommand;
import commands.ExCommandException;
import commands.ICommand;
import commands.MainCommand;
import myutils.UParse;
import myutils.enums.ERoles;

@ACommand(
    command = "unban",
    permission = ERoles.PIGGIES,
    help = "Usage `" + MainCommand.PREFIX + "unban <mention tag>`\n"
        + "Unbans user from the server. ")
public class UnBan implements ICommand {

  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    String id = UParse.parseUser(scanner);

    return true;
  }

}
