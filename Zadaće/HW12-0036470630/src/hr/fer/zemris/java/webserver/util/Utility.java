package hr.fer.zemris.java.webserver.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Helper class that contains utility methods used for checking certain
 * preconditions that have to met and other methods.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Utility {

	/**
	 * Checks if the header is created in the {@link RequestContext} and if it
	 * is throws a new {@link RuntimeException}.
	 * 
	 * @param header
	 *            flag that determines if the header is generated
	 */
	public static void checkIfHeaderGenerated(final boolean header) {
		if (header) {
			throw new RuntimeException("Header is already generated");
		}
	}

	/**
	 * Checks if {@code object} is equal to null and if it is throws a new
	 * {@link IllegalArgumentException} with the specified message.
	 * 
	 * @param object
	 *            object to check
	 * @param message
	 *            exception message
	 */
	public static void checkIfNull(final Object object, final String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks if a nullable object is equal to {@code null}.
	 * 
	 * @param object
	 *            object to check
	 * @return true if {@code object} is null, else false
	 */
	public static boolean checkIfNullableEmpty(final Object object) {
		return object == null;
	}

	/**
	 * Read a file from disk specified by the {@code filePath} argument and
	 * creates a {@link String} with the whole file content.
	 * 
	 * @param filePath
	 *            documents file path
	 * @return {@link String} with the whole file content
	 */
	public static String readFromDisk(final String filePath) {
		String script = "";

		try {
			script = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			System.err.println("The provided file path is invalid");
		}

		return script;
	}
}
