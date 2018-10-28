package commands;

@SuppressWarnings("serial")
public class ECommandExecutionException extends Exception {
  private String reason, command;

  /**
   * This exception is thrown when an error occurs while executing a command.
   *
   * @param reason reason
   */
  public ECommandExecutionException(String reason, String command) {
    super();
    this.reason = reason;
    this.command = command;
  }

  public ECommandExecutionException(String reason) {
    super();
    this.reason = reason;
    this.command = "null";
  }

  /**
   * Gets the reason for the error.
   */
  @Override
  public String toString() {
    return reason;
  }

  public String command() {
    return command;
  }
}
