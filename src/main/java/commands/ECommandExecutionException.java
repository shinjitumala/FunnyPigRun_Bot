package commands;

@SuppressWarnings("serial")
public class ECommandExecutionException extends Exception {
	private String reason;

	/**
	 * This exception is thrown when an error occurs while executing a command.
	 * 
	 * @param reason reason
	 */
	public ECommandExecutionException(String reason) {
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
