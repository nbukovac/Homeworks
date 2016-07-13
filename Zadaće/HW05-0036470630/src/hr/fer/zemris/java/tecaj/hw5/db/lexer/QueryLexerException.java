package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Thrown when a error occurs during query tokenization performed by
 * {@link QueryLexer}.
 * 
 * @see QueryLexer
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class QueryLexerException extends RuntimeException {

	/**
	 * Serial version UID for this exception
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@link QueryLexerException} with a generic message.
	 */
	public QueryLexerException() {
		super("An error has occured during the process of parsing your text. Please check "
				+ "if your text is parsable");
	}

	/**
	 * Constructs a new {@link QueryLexerException} with a specified message
	 * 
	 * @param message
	 *            specific message
	 */
	public QueryLexerException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link QueryLexerException} with a specified cause
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public QueryLexerException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@link QueryLexerException} with a specified cause and
	 * message
	 * 
	 * @param cause
	 *            the cause of the exception
	 * @param message
	 *            specific message
	 */
	public QueryLexerException(final Throwable cause, final String message) {
		super(message, cause);
	}
}
