package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass;

/**
 * Writes command descriptions to the associated {@link Environment}. If no
 * arguments are provided then all commands and their descriptions are written.
 * If one argument is provided and it matches a supported command then only that
 * command will be written.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class HelpShellCommand extends AbstractShellCommand {

	/**
	 * Constructs a new {@link HelpShellCommand} along with its command name and
	 * command description.
	 */
	public HelpShellCommand() {
		super("help");

		final List<String> description = new ArrayList<>();
		description.add("If no arguments are provided, lists all shell available commands.");
		description.add("If a command name was provided and it exists, only that command will be shown,");
		description.add("if it doesn't exist an error is shown to the user");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		final String[] splitArguments = ShellHelperClass.splitArguments(arguments);

		try {
			if (splitArguments.length > 1) {
				env.writeln("Invalid use of help. Help command accepts 0 or 1 arguments");

			} else if (splitArguments.length == 1 && !splitArguments[0].isEmpty()) {
				final List<ShellCommand> commands = new ArrayList<>();
				boolean commandFound = false;

				for (final ShellCommand comm : env.commands()) {
					if (comm.getCommandName().equals(splitArguments[0])) {
						commands.add(comm);
						ShellHelperClass.printCommandDescription(env, commands);
						commandFound = true;
						break;
					}
				}

				if (!commandFound) {
					env.writeln("The requested command isn't supported in this environment");
				}

			} else {
				ShellHelperClass.printCommandDescription(env, env.commands());
			}

		} catch (final IOException e) {
			return ShellHelperClass.writeErrorCause(env, e);
		}

		return ShellStatus.CONTINUE;
	}

}
