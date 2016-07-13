package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Interface that specifies what every command has to provide to ensure proper
 * functioning. Every command interacts with an {@link Environment} to ensure
 * interactivity. Every command provides a unique funcionality.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface ShellCommand {

	/**
	 * Executes a series of actions determined by the command. {@code env} is a
	 * {@link Environment} used to interact with the user. Every command
	 * specifies its way how it handles arguments.
	 * 
	 * @param env
	 *            {@link Environment}
	 * @param arguments
	 *            command arguments
	 * @return {@link ShellStatus} based on execution success and command
	 *         definition
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the command description.
	 * 
	 * @return command description
	 */
	List<String> getCommandDescription();

	/**
	 * Returns the command name
	 * 
	 * @return command name
	 */
	String getCommandName();
}
