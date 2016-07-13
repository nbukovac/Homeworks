package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass.BufferedReaderInfo;

/**
 * Writes a files content to the associated {@link Environment} in a formatted
 * manner. First is written the file offset in as a hex value, then 16 bytes
 * also as a hex value and at the end the original text with but every character
 * greater than 127 or less than 32 is written as '.'. Command requires only one
 * argument and that is the file path of the desired file.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class HexDumpShellCommand extends AbstractShellCommand {

	/**
	 * Number of the output line
	 */
	private static int outputLine = 1;

	/**
	 * Number of values in a half of the hex value table row
	 */
	private static final int HEX_HALF = 8;

	/**
	 * Gets a line containing all the required parts in one.
	 * 
	 * @param readLine
	 *            next buffered part to process
	 * @return formatted line, ready for writing
	 */
	private static String createOutputLine(final BufferedReaderInfo readLine) {
		final StringBuilder sb = new StringBuilder();

		sb.append(getHexNumeration(outputLine - 1, HEX_HALF));
		sb.append(':');

		sb.append(getHalfHexOutput(readLine));
		sb.append(" |");
		sb.append(getHalfHexOutput(readLine));
		sb.append(" | ");

		sb.append(getRightColumnOutput(readLine));

		return sb.toString();
	}

	/**
	 * Gets a half of the hex value table row in a formatted manner. Every
	 * symbol that exceeds {@code 127} is transformed to {@code '.'} as
	 * specified.
	 * 
	 * @param readLine
	 *            next buffered part to process
	 * @return formatted {@link String}
	 */
	private static String getHalfHexOutput(final BufferedReaderInfo readLine) {
		final StringBuilder sb = new StringBuilder();
		int limit = readLine.getPosition() + HEX_HALF < readLine.getReadChars() ? readLine.getPosition() + HEX_HALF
				: readLine.getReadChars();

		for (int i = readLine.getPosition(); i < limit; i++) {
			sb.append(' ');
			if (readLine.getData()[i] <= 127) {
				sb.append(String.format("%02x", (int) readLine.getData()[i]));
			} else {
				sb.append(String.format("%02x", (int) '.'));
			}
		}

		if (limit % HEX_HALF != 0) {
			limit %= HEX_HALF;
		} else {
			limit = HEX_HALF;
		}

		readLine.setPosition(readLine.getPosition() + limit);

		return String.format("%-24s", sb.toString());
	}

	/**
	 * Returns a hex value of {@code number}.
	 * 
	 * @param number
	 *            number to transform to hex
	 * @param formatSpace
	 *            number of spaces to format
	 * @return formatted hex value
	 */
	private static String getHexNumeration(final long number, final int formatSpace) {
		return String.format("%0" + formatSpace + "x", number);
	}

	/**
	 * Returns the last column of the output specified by the
	 * {@link HexDumpShellCommand} and that is the file written in the default
	 * charset.
	 * 
	 * @param readLine
	 *            next buffered part to process
	 * @return formatted {@link String}
	 */
	private static String getRightColumnOutput(final BufferedReaderInfo readLine) {
		boolean end = false;

		if (readLine.getReadChars() <= readLine.getPosition()) {
			end = true;
		}

		final StringBuilder sb = new StringBuilder();
		int limit = readLine.getPosition() % (2 * HEX_HALF) + (2 * HEX_HALF) * outputLine;

		if (end) {
			limit = readLine.getPosition();
		}

		int i = readLine.getPosition() - limit == 0 ? readLine.getPosition() - limit + (2 * HEX_HALF) * (outputLine - 1)
				: 0;
		final char[] readData = readLine.getData();

		for (; i < limit; i++) {
			if (readData[i] < 32 || readData[i] > 127) {
				sb.append('.');
			} else {
				sb.append(readData[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * Constructs a new {@link HexDumpShellCommand} along with the command name
	 * and command description.
	 */
	public HexDumpShellCommand() {
		super("hexdump");

		final List<String> description = new ArrayList<>();

		description.add("Produces a hex-output of a provided file based on the JVM default charset");
		description.add("One argument is required for this command to function.");
		description.add("Argument has to be a file path");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		final String[] splitArguments = ShellHelperClass.splitQoutableArguments(arguments);

		if (splitArguments.length == 1) {
			final ShellStatus status = ShellHelperClass.checkIfFile(splitArguments, env);

			if (status != null) {
				return status;
			}
		} else {
			try {
				env.writeln("Cat command expects one argument");
				return ShellStatus.CONTINUE;
			} catch (final IOException e) {
				return ShellHelperClass.writeErrorCause(env, e);
			}
		}

		try (final FileInputStream fileInput = new FileInputStream(splitArguments[0])) {
			final InputStreamReader inputStreamReader = new InputStreamReader(fileInput, Charset.defaultCharset());

			BufferedReaderInfo readLine = ShellHelperClass.readFromFile(inputStreamReader);

			while (readLine.getReadChars() > 0) {
				while (readLine.getReadChars() > readLine.getPosition()) {
					env.writeln(createOutputLine(readLine));
					outputLine++;
				}
				readLine = ShellHelperClass.readFromFile(inputStreamReader);
			}

			inputStreamReader.close();
			env.writeln("");

		} catch (final IOException e) {
			return ShellHelperClass.writeErrorCause(env, e);
		}

		return ShellStatus.CONTINUE;
	}
}
