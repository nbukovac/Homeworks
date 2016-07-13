package hr.fer.zemris.java.tecaj.hw5.db.parser;

import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryLexerException;

/**
 * Exception thrown when a error occurs while {@link QueryParser} parses a
 * provided text. Errors are if an <code>and</code> operator wasn't between two
 * queries, if an {@link QueryLexerException} was caught.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class QueryParserException extends RuntimeException {

	/**
	 * Serial version UID for this exception
	 */
	private static final long serialVersionUID = 3045549669007983361L;

	/**
	 * Constructs a new {@link QueryParserException} with a generic message.
	 */
	public QueryParserException() {
		super("An error has occured during the process of parsing your text. Please check "
				+ "if your text is parsable");
	}

	/**
	 * Constructs a new {@link QueryParserException} with a specified message.
	 * 
	 * @param message
	 *            specific message
	 */
	public QueryParserException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link QueryParserException} with a specified cause.
	 * 
	 * @param cause
	 *            the cause of the exception
	 */
	public QueryParserException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@link QueryParserException} with a specified cause and
	 * message.
	 * 
	 * @param cause
	 *            the cause of the exception
	 * @param message
	 *            specific message
	 */
	public QueryParserException(final Throwable cause, final String message) {
		super(message, cause);
	}
}
