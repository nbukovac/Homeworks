package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.Collections;
import java.util.List;

/**
 * Abstract class that implements {@link ShellCommand}. Concrete classes which
 * extend this class are shell commands.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public abstract class AbstractShellCommand implements ShellCommand {

	/**
	 * Command name
	 */
	private final String commandName;

	/**
	 * Command description
	 */
	private List<String> commandDescription;

	/**
	 * Constructs a {@link AbstractShellCommand} with the provided command name.
	 * 
	 * @param commandName
	 *            command name
	 */
	protected AbstractShellCommand(final String commandName) {
		this.commandName = commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	/**
	 * Sets the {@code commandDesctiption} to the provided
	 * {@code commandDescription} as an unmodifiable list.
	 * 
	 * @param commandDescription
	 *            command description
	 */
	protected void setCommandDescription(final List<String> commandDescription) {
		this.commandDescription = Collections.unmodifiableList(commandDescription);
	}

}
