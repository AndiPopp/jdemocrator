package de.inpiraten.jdemocrator.event;

public class IllegalEntryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5900587564625532440L;

	/**
	 * 
	 */
	public IllegalEntryException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IllegalEntryException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IllegalEntryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public IllegalEntryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public IllegalEntryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
