package hr.fer.zemris.java.fractals.complex;

/**
 * Helper class used to check if a specified value is {@code null} even though
 * it shouldn't be.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ComplexUtil {

	/**
	 * Default message if a value is null
	 */
	private static final String DEFAULT_MESSAGE = "Complex number can't be null reference. Unable to do requested operation";

	/**
	 * Checks if the specified {@code complex} argument is {@code null} or not.
	 * If it is the default message is shown.
	 * 
	 * @param complex
	 *            argument to check
	 */
	public static void checkIfComplexNull(final Object complex) {
		checkIfComplexNull(complex, DEFAULT_MESSAGE);
	}

	/**
	 * Checks if the specified {@code complex} argument is {@code null} or not.
	 * If it is the specified message is shown.
	 * 
	 * @param complex
	 *            argument to check
	 * @param message
	 *            specified message
	 */
	public static void checkIfComplexNull(final Object complex, final String message) {
		if (complex == null) {
			throw new IllegalArgumentException(message);
		}
	}

}
