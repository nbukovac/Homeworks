package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception thrown when a error occurs while {@link SmartScriptParser} parses
 * an provided text.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Serial version UID for this exception
	 */
	private static final long serialVersionUID = 3045549669007983361L;

	/**
	 * Constructs a new {@link SmartScriptParserException} with a generic
	 * message.
	 */
	public SmartScriptParserException() {
		super("An error has occured during the process of parsing your text. Please check "
				+ "if your text is parsable");
	}

	/**
	 * Constructs a new {@link SmartScriptParserException} with a specified
	 * message.
	 * 
	 * @param message
	 *            specific message
	 */
	public SmartScriptParserException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link SmartScriptParserException} with a specified
	 * cause.
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public SmartScriptParserException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@link SmartScriptParserException} with a specified
	 * cause and message.
	 * 
	 * @param cause
	 *            the cause of the exception
	 * @param message
	 *            specific message
	 */
	public SmartScriptParserException(final Throwable cause, final String message) {
		super(message, cause);
	}
}
