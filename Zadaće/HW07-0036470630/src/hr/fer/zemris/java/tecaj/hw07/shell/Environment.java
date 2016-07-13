package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * Interface that specifies what every {@link Environment} has to provide to the
 * user to ensure proper functionality. {@link Environment} offers writing and
 * reading user input and performing certain operations such as getting and
 * setting the specific symbols such as {@code MORELINES}, {@code MULTILINE} and
 * {@code PROMPT}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface Environment {

	/**
	 * Returns an {@link Iterable} object with all commands in an
	 * {@link Environment}.
	 * 
	 * @return {@link Iterable} object
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Returns a map with command names as keys and {@link ShellCommand}s as
	 * values.
	 * 
	 * @return a map with command names as keys and {@link ShellCommand}s as
	 *         values
	 */
	Map<String, ShellCommand> getCommands();

	/**
	 * Returns the currently set {@code MORELINES} symbol, required for multiple
	 * line user input.
	 * 
	 * @return currently set {@code MORELINES} symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Returns the currently set {@code MULTILINE} symbol, required to specify a
	 * multiple line prompt.
	 * 
	 * @return currently set {@code MULTILINE} symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Returns the currently set {@code PROMPT} symbol, required to specify
	 * normal prompt.
	 * 
	 * @return currently set {@code PROMPT} symbol
	 */
	Character getPromptSymbol();

	/**
	 * Reads a line from the {@link Environment}s standard input.
	 * 
	 * @return read line
	 * @throws IOException
	 *             if anything specified by the {@link IOException} happens
	 */
	String readline() throws IOException;

	/**
	 * Sets the {@code MORELINES} symbol to the provided {@code symbol}.
	 * 
	 * @param symbol
	 *            new {@code MORELINES} symbol
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Sets the {@code MULTILINE} symbol to the provided {@code symbol}.
	 * 
	 * @param symbol
	 *            new {@code MULTILINE} symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Sets the {@code PROMPT} symbol to the provided {@code symbol}.
	 * 
	 * @param symbol
	 *            new {@code PROMPT} symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Writes the provided {@code text} with no newline character to the defined
	 * {@link Environment}s output. An {@link IOException} is thrown if anything
	 * defined by it happens.
	 * 
	 * @param text
	 *            text to write
	 * @throws IOException
	 *             if anything defined by {@link IOException} happens
	 */
	void write(String text) throws IOException;

	/**
	 * Writes the provided {@code text} with a newline character to the defined
	 * {@link Environment}s output. An {@link IOException} is thrown if anything
	 * defined by it happens.
	 * 
	 * @param text
	 *            text to write
	 * @throws IOException
	 *             if anything defined by {@link IOException} happens
	 */
	void writeln(String text) throws IOException;
}
