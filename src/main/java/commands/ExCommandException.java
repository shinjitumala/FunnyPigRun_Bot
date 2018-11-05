package commands;

@SuppressWarnings("serial")
public class ExCommandException extends Exception {
  private String reason;

  public ExCommandException(String reason) {
    super();
    this.reason = reason;
  }

  /**
   * Gets the reason for the error.
   */
  @Override
  public String toString() {
    return reason;
  }
}
