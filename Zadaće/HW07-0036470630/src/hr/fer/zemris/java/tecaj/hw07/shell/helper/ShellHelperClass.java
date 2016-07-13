package hr.fer.zemris.java.tecaj.hw07.shell.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * Helper class with frequently used methods for {@link ShellCommand}s
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ShellHelperClass {

	/**
	 * Class used to collect buffered input data.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	public static class BufferedReaderInfo {

		/**
		 * Buffered data
		 */
		private final char[] data;

		/**
		 * Number of read characters
		 */
		private final int readChars;

		/**
		 * Position in the {@code data} array
		 */
		private int position;

		/**
		 * Constructs a new {@link BufferedReaderInfo} with the collected
		 * information.
		 * 
		 * @param data
		 *            buffered array
		 * @param readChars
		 *            number of read characters
		 */
		public BufferedReaderInfo(final char[] data, final int readChars) {
			super();
			this.data = data;
			this.readChars = readChars;
		}

		/**
		 * @return the data
		 */
		public char[] getData() {
			return data;
		}

		/**
		 * @return the position
		 */
		public int getPosition() {
			return position;
		}

		/**
		 * @return the readChars
		 */
		public int getReadChars() {
			return readChars;
		}

		/**
		 * @param position
		 *            the position to set
		 */
		public void setPosition(final int position) {
			this.position = position;
		}

	}

	/**
	 * Buffer size for reading from a file or writing to a file
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Gets a string representation of a character array.
	 * 
	 * @param charArray
	 *            input char array
	 * @param readChars
	 *            number of read characters
	 * @return string representation of the character array
	 */
	public static String charArrayToString(final char[] charArray, final int readChars) {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < readChars; i++) {
			sb.append(charArray[i]);
		}

		return sb.toString();
	}

	/**
	 * Checks if the provided {@link File} is a directory or a file. If it a
	 * directory null is return, else either {@link ShellStatus} option
	 * depending on the success of writing to {@link Environment}.
	 * 
	 * @param splitArguments
	 *            file name
	 * @param env
	 *            working {@link Environment}
	 * @return appropriate {@link ShellStatus}
	 */
	public static ShellStatus checkIfFile(final String[] splitArguments, final Environment env) {
		final File file = new File(splitArguments[0]);

		if (!file.isFile()) {
			try {
				env.writeln("First argument has to be a file");

			} catch (final IOException e) {
				return ShellHelperClass.writeErrorCause(env, e);
			}

			return ShellStatus.CONTINUE;
		}

		return null;
	}

	/**
	 * Writes the command descriptions to the associated {@link Environment}.
	 * 
	 * @param env
	 *            working {@link Environment}
	 * @param commands
	 *            all commands supported by the provided {@link Environment}
	 * @throws IOException
	 *             if anything specified by the exception happens
	 */
	public static void printCommandDescription(final Environment env, final Iterable<ShellCommand> commands)
			throws IOException {

		for (final ShellCommand command : commands) {

			env.writeln(command.getCommandName());
			final List<String> description = command.getCommandDescription();

			for (final String line : description) {
				env.writeln("\t" + line);
			}

			env.writeln("");
		}
	}

	/**
	 * Reads a part of the file a returns a new {@link BufferedReaderInfo} with
	 * the collected data.
	 * 
	 * @param inputStreamReader
	 *            {@link InputStream} used to read from the file
	 * @return new {@link BufferedReaderInfo} with the collected data
	 * @throws IOException
	 *             if anything specified by the exception happens
	 */
	public static BufferedReaderInfo readFromFile(final InputStreamReader inputStreamReader) throws IOException {
		final char[] buffer = new char[BUFFER_SIZE];
		final Reader reader = new BufferedReader(inputStreamReader);

		final int readChars = reader.read(buffer);

		return new BufferedReaderInfo(buffer, readChars);
	}

	/**
	 * Splits the argument by whitespace.
	 * 
	 * @param arguments
	 *            command arguments
	 * @return split arguments
	 */
	public static String[] splitArguments(final String arguments) {
		final String argument = arguments.trim();
		return argument.split("\\s+");
	}

	/**
	 * Splits the arguments by quote signs.
	 * 
	 * @param arguments
	 *            command arguments
	 * @return split arguments
	 */
	public static String[] splitQoutableArguments(final String arguments) {
		if (!arguments.contains("\"")) {
			return splitArguments(arguments);
		}

		final String[] split = arguments.trim().split("\"");
		int notEmptyStrings = 0;

		for (final String string : split) {
			if (!string.trim().isEmpty()) {
				notEmptyStrings++;
			}
		}

		final String[] returnString = new String[notEmptyStrings];
		int i = 0;

		for (final String string : split) {
			if (!string.trim().isEmpty()) {
				returnString[i] = string.trim();
				i++;
			}
		}

		return returnString;
	}

	/**
	 * Tries to write the error cause to the associated {@link Environment} if
	 * it is possible. If it is a {@code ShellStatus.CONTINUE} is returned, else
	 * {@code ShellStatus.TERMINATE}.
	 * 
	 * @param env
	 *            working {@link Environment}
	 * @param e
	 *            caught {@link Exception}
	 * @return if the {@link Environment} works a {@code ShellStatus.CONTINUE}
	 *         is returned, else {@code ShellStatus.TERMINATE}
	 */
	public static ShellStatus writeErrorCause(final Environment env, final Exception e) {
		try {
			env.writeln("Unable to resolve the following IOException " + e.getMessage());
		} catch (final IOException ignorable) {
			return ShellStatus.TERMINATE;
		}

		return ShellStatus.CONTINUE;
	}
}
