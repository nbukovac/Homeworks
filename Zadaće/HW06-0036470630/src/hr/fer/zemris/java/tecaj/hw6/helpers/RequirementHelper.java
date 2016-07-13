package hr.fer.zemris.java.tecaj.hw6.helpers;

/**
 * Helper class that is used to check if certain requirements are met so that it
 * is okay to progress further.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class RequirementHelper {

	/**
	 * Checks if {@code argument} is null, if it is an
	 * {@link IllegalArgumentException} is thrown with the specified
	 * {@code message}.
	 * 
	 * @param argument
	 *            argument to check
	 * @param message
	 *            message to display
	 * @throws IllegalArgumentException
	 *             if argument is null
	 */
	public static void checkArgumentNull(final Object argument, final String message) {
		if (argument == null) {
			throw new IllegalArgumentException(message);
		}
	}

}
