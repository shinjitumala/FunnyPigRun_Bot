package commands;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import main.FPR;
import myutils.MyUtils;

/**
 * Interface for commands
 */
public abstract class ICommand {
  protected Role permission;

  public ICommand(Role permission) {
    this.permission = permission;
  }

  /**
   * Parses the creates message.
   *
   * @param event message event
   * @return returns true if command was successfully parsed. if command is not
   *         from this module, returns false. error while executing will be thrown
   *         as an exception with reason.
   */
  public boolean parse(MessageCreateEvent event) throws ECommandExecutionException {
    // Permission check.
    MyUtils.hasPermission(event.getMessage().getUserAuthor(), FPR.server(), permission);

    // Get
    String command[] = MyUtils.getSegments(event);

    return exec(command, event);
  }

  protected abstract boolean exec(String[] command, MessageCreateEvent event) throws ECommandExecutionException;
}
