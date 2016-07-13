package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Thrown to indicate that the <code>Stack</code> like collection has no
 * elements while an illegal operation is called on an empty stack.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {

	/** Serial version */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@link EmptyStackException} with a generic message
	 */
	public EmptyStackException() {
		super("Stack operation failed because the stack is empty");
	}

	/**
	 * Constructs an {@link EmptyStackException} with a specified message
	 * 
	 * @param messagge
	 *            specific message
	 */
	public EmptyStackException(final String messagge) {
		super(messagge);
	}
}
