package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * Thrown when an error occurs while a text is being processed by class
 * {@link Lexer}. An error can be either requesting a new {@link Node} when EOF
 * is reached, an illegal tag, wrong escape sequence, invalid {@link Element}
 * inside a tag, not properly closed tag.
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
