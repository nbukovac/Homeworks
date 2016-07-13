package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * Program that emulates a bash shell with a limited subset of similar commands
 * as found in the before mentioned shell. The next commands are supported by
 * this version of shell.
 * 
 * <pre>
 *   <ul>
 *     <li>help</li>
 *     <li>symbol</li>
 *     <li>cat</li>
 *     <li>copy</li>
 *     <li>hexdump</li>
 *     <li>ls</li>
 *     <li>tree</li>
 *     <li>exit</li>
 *     <li>charsets</li>
 *     <li>mkdir</li>
 *    </ul>
 * </pre>
 * 
 * For more information use the help command.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class MyShell {

	/**
	 * Program entry point.
	 * 
	 * @param args
	 *            not used
	 * @throws IOException
	 *             if anything specified by the {@link IOException} happens
	 */
	public static void main(final String[] args) throws IOException {
		final Environment shell = new MyShellEnvironment();
		ShellStatus shellStatus = ShellStatus.CONTINUE;
		final Map<String, ShellCommand> shellCommands = shell.getCommands();

		shell.writeln("Welcome to MyShell v1.0");

		while (shellStatus != ShellStatus.TERMINATE) {
			shell.write(shell.getPromptSymbol() + " ");
			String input = shell.readline();
			String tmpInput = input;
			boolean firstMultiline = true;

			while (tmpInput.endsWith(shell.getMorelinesSymbol() + "")) {
				if (firstMultiline) {
					input = input.substring(0, input.length() - 1);
					firstMultiline = false;
				}

				shell.write(shell.getMultilineSymbol() + " ");
				tmpInput = shell.readline();

				if (tmpInput.endsWith(shell.getMorelinesSymbol() + "") && tmpInput.length() > 1) {
					input += " " + tmpInput.substring(0, tmpInput.length() - 1);
				} else {
					input += " " + tmpInput;
				}
			}

			final String[] split = input.split("\\s+", 2);
			final String commandName = split[0];
			String arguments = "";

			if (split.length == 2) {
				arguments = split[1];
			}

			final ShellCommand command = shellCommands.get(commandName);

			if (command == null) {
				shell.writeln(commandName + " isn't supported in this shell. Try help.");
			} else {
				shellStatus = command.executeCommand(shell, arguments);
			}

		}

	}

}
