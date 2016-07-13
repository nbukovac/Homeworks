package hr.fer.zemris.java.tecaj.hw07.shell;

/**
 * Enumeration used to indicate if a {@link Environment} is going to be
 * terminated or is it going to continue working. {@code CONTINUE} indicates
 * that the {@link Environment} continues with it operations, while
 * {@code TERMINATE} terminates the ongoing {@link Environment} operations.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public enum ShellStatus {

	/**
	 * Continue with {@link Environment} operations
	 */
	CONTINUE,

	/**
	 * Terminate the {@link Environment} operations
	 */
	TERMINATE
}
