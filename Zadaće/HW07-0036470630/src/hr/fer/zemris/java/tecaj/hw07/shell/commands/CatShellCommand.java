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
 * Writes a files content to the associated {@link Environment}. If one argument
 * is provided then the file is written in the JVM default charset. If two
 * arguments are provided then the file is written in the provided charset
 * argument if it is supported.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class CatShellCommand extends AbstractShellCommand {

	/**
	 * Constructs a new {@link CatShellCommand} along with the command name and
	 * command description.
	 */
	public CatShellCommand() {
		super("cat");

		final List<String> description = new ArrayList<>();

		description.add("Cat command prints out the contents of a file.");
		description.add("At least one argument is required and that is the file name.");
		description.add("If a second argument is specified then it has to be the desired charset.");
		description.add("If the charset isn't specified, then the default one is used");
		description.add("If the file or the charset do not exist, then the command doesn't write anything");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		final String[] splitArguments = ShellHelperClass.splitQoutableArguments(arguments);

		if (splitArguments.length > 0) {
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
			InputStreamReader inputStreamReader = new InputStreamReader(fileInput, Charset.defaultCharset());

			if (splitArguments.length == 2) {
				if (Charset.isSupported(splitArguments[1])) {
					inputStreamReader = new InputStreamReader(fileInput, splitArguments[1]);
				} else {
					env.writeln("Unsupported charset. Try charsets to find a supported charset");
					return ShellStatus.CONTINUE;
				}

			} else if (splitArguments.length != 1) {
				env.writeln("Invalid number of arguments for cat command");
				return ShellStatus.CONTINUE;
			}

			BufferedReaderInfo readLine = ShellHelperClass.readFromFile(inputStreamReader);

			while (readLine.getReadChars() > 0) {
				env.write(ShellHelperClass.charArrayToString(readLine.getData(), readLine.getReadChars()));
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
