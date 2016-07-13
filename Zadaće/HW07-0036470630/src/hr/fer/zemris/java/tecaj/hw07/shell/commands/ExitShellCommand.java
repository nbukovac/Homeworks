package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Terminates the current {@link MyShell} session.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ExitShellCommand extends AbstractShellCommand {

	/**
	 * Constructs a new {@link ExitShellCommand} along with the command name and
	 * command description.
	 */
	public ExitShellCommand() {
		super("exit");

		final List<String> description = new ArrayList<>();
		description.add("Terminates the current shell session");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		return ShellStatus.TERMINATE;
	}

}
