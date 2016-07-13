package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass;

/**
 * Changes the {@link Environment}s specialized characters({@code PROMPT},
 * {@code MULTILINE}, {@code MORELINES}) to the user defined values. If no
 * substitution symbol is provided then only the old symbol is shown.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SymbolShellCommand extends AbstractShellCommand {

	/**
	 * Formats the output message for the user.
	 * 
	 * @param newSymbol
	 *            new symbol
	 * @param env
	 *            working {@link Environment}
	 * @param attribute
	 *            symbol specified for change
	 * @param oldSymbol
	 *            old symbol
	 * @return formatted message
	 */
	private static String formatOutputString(final Character newSymbol, final Environment env, final String attribute,
			final Character oldSymbol) {
		String returnString = "Symbol for " + attribute;

		if (newSymbol == null) {
			returnString += " is '" + oldSymbol + "'";
		} else {
			returnString += " changed from '" + oldSymbol + "' to '" + newSymbol + "'";
		}

		return returnString;
	}

	/**
	 * Sets the appropriate {@code attribute} to the new symbol.
	 * 
	 * @param attribute
	 *            command attribute
	 * @param newSymbol
	 *            new symbol
	 * @param env
	 *            working {@link Environment}
	 * @return formatted message
	 */
	private static String getCommandOutputString(final String attribute, final Character newSymbol,
			final Environment env) {
		String returnString = null;

		if (attribute.toLowerCase().equals("prompt")) {
			returnString = formatOutputString(newSymbol, env, attribute, env.getPromptSymbol());

			if (newSymbol != null) {
				env.setPromptSymbol(newSymbol);
			}

		} else if (attribute.toLowerCase().equals("morelines")) {
			returnString = formatOutputString(newSymbol, env, attribute, env.getMorelinesSymbol());

			if (newSymbol != null) {
				env.setMorelinesSymbol(newSymbol);
			}

		} else if (attribute.toLowerCase().equals("multiline")) {
			returnString = formatOutputString(newSymbol, env, attribute, env.getMultilineSymbol());

			if (newSymbol != null) {
				env.setMultilineSymbol(newSymbol);
			}
		} else {
			returnString = attribute + " isn't supported for this command";
		}

		return returnString;
	}

	/**
	 * Constructs a new {@link SymbolShellCommand} along with the command name
	 * and command description.
	 */
	public SymbolShellCommand() {
		super("symbol");

		final List<String> description = new ArrayList<>();

		description.add("Symbol command is used to change the environments prompt, multiline and morelines symbols.");
		description.add("To change the prompt symbol, attribute PROMPT has to declared and a"
				+ " substitute character has to be provided");
		description.add("To change the multiline symbol, attribute MULTILINE has to declared and a"
				+ " substitute character has to be provided");
		description.add("To change the morelines symbol, attribute MORELINES has to declared and a"
				+ " substitute character has to be provided");
		description.add("If only the attribute is specified then the current symbol is shown");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		final String[] splitArguments = ShellHelperClass.splitArguments(arguments);

		try {
			if (splitArguments.length == 1) {
				env.writeln(getCommandOutputString(splitArguments[0], null, env));

			} else if (splitArguments.length == 2) {
				env.writeln(getCommandOutputString(splitArguments[0], splitArguments[1].charAt(0), env));
			} else {
				env.writeln("Symbol command expects one or two arguments");
			}

		} catch (final IOException e) {
			return ShellHelperClass.writeErrorCause(env, e);
		}

		return ShellStatus.CONTINUE;
	}

}
