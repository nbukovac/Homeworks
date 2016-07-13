package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Thrown when an error occurs while a text is being processed by class
 * {@link Lexer}. An error can be either requesting a new {@link Token} when EOF
 * is reached or when a number can't be parsed as a {@link Long}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/**
	 * Serial version UID for this exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@link LexerException} with a generic message.
	 */
	public LexerException() {
		super("An error has occured during the process of parsing your text. Please check "
				+ "if your text is parsable");
	}

	/**
	 * Constructs a new {@link LexerException} with a specified message
	 * 
	 * @param message
	 *            specific message
	 */
	public LexerException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link LexerException} with a specified cause
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public LexerException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@link LexerException} with a specified cause and
	 * message
	 * 
	 * @param cause
	 *            the cause of the exception
	 * @param message
	 *            specific message
	 */
	public LexerException(final Throwable cause, final String message) {
		super(message, cause);
	}

}
