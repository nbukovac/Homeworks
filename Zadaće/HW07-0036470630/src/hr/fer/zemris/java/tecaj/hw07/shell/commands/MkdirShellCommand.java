package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass;

/**
 * Creates a new directory. One argument is required a that is the new directory
 * path. All missing parent directories will be also created.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class MkdirShellCommand extends AbstractShellCommand {

	/**
	 * Constructs a new {@link MkdirShellCommand} along with the command name
	 * and command description.
	 */
	public MkdirShellCommand() {
		super("mkdir");

		final List<String> description = new ArrayList<>();

		description.add("Creates a new directory.");
		description.add("One argument is required and that is a directory path.");
		description.add("All directories in the provided path are created if the didn't exist");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		final String[] splitArguments = ShellHelperClass.splitQoutableArguments(arguments);

		try {
			if (splitArguments.length != 1) {
				env.writeln("mkdir command requires one argument and that is directory path");
			} else {
				final File file = new File(splitArguments[0]);

				if (file.mkdirs()) {
					env.writeln("Directory structure is created");
				} else {
					env.writeln("Directory structure creation failed");
				}

			}

		} catch (final IOException e) {
			return ShellHelperClass.writeErrorCause(env, e);
		}
		return ShellStatus.CONTINUE;
	}

}
