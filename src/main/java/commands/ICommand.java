package commands;

import java.util.Scanner;

import org.javacord.api.event.message.MessageCreateEvent;

/**
 * Abstract class for commands
 */
public interface ICommand {
  /**
   * Runs the command.
   *
   * @param event message event
   * @return returns true if command was successfully parsed. if command is not
   *         from this module, returns false. error while executing will be thrown
   *         as an exception with reason.
   */
  public boolean run(MessageCreateEvent event, Scanner scanner) throws ExCommandException;
}
