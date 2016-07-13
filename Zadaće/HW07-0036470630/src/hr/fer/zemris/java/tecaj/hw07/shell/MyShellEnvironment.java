package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexDumpShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeShellCommand;

/**
 * Implements {@link Environment} and provides a environment that reads and
 * writes to the standard output/input. Supported commands are
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
 * @author Nikola Bukovac
 * @version 1.0
 */
public class MyShellEnvironment implements Environment {

	/**
	 * Prompt symbol
	 */
	private Character promptSymbol = '>';

	/**
	 * Multiline symbol
	 */
	private Character multilineSymbol = '|';

	/**
	 * Morelines symbol
	 */
	private Character morelinesSymbol = '\\';

	/**
	 * Map of commands
	 */
	private final Map<String, ShellCommand> commands;

	/**
	 * {@link BufferedReader} used for reading and writing the user input
	 */
	private final BufferedReader reader;

	/**
	 * Constructs a new {@link MyShellEnvironment} with all available commands
	 */
	public MyShellEnvironment() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		commands = new HashMap<>();

		commands.put("help", new HelpShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("charsets", new CharsetShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("hexdump", new HexDumpShellCommand());
	}

	@Override
	public Iterable<ShellCommand> commands() {
		return commands.values();
	}

	@Override
	public Map<String, ShellCommand> getCommands() {
		return commands;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public String readline() throws IOException {
		return reader.readLine().trim();
	}

	@Override
	public void setMorelinesSymbol(final Character symbol) {
		morelinesSymbol = symbol;
	}

	@Override
	public void setMultilineSymbol(final Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public void setPromptSymbol(final Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public void write(final String text) throws IOException {
		System.out.print(text);
	}

	@Override
	public void writeln(final String text) throws IOException {
		System.out.println(text);
	}

}
