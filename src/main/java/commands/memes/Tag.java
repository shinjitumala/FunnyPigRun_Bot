package commands.memes;

import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;

import commands.ACommand;
import commands.ExCommandException;
import commands.ICommand;
import commands.MainCommand;
import myutils.enums.ERoles;

@ACommand(
    command = "tag",
    permission = ERoles.DRIFTER,
    help = "Usage: `" + MainCommand.PREFIX + "tag <tag> <message>`\n"
        + "Adds the message <message> with tag <tag> to the dictionary." + "You can")
public class Tag implements ICommand {
  @Override
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException {
    // TODO Auto-generated method stub
    return false;
  }

}
