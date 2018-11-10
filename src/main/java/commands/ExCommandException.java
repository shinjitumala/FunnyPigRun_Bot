package commands;

public class ExCommandException extends Exception {
  private static final long serialVersionUID = 480472982956019394L;

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
