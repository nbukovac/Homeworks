package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass;

/**
 * Writes all available charsets on this JVM. No arguments are required and
 * allowed.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class CharsetShellCommand extends AbstractShellCommand {

	/**
	 * Constructs a new {@link CharsetShellCommand} along with the command name
	 * and command description.
	 */
	public CharsetShellCommand() {
		super("charsets");

		final List<String> description = new ArrayList<>();
		description.add("List all available charsets on this JVM");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		if (arguments.length() > 0) {
			try {
				env.writeln("Charsets command does not take any arguments");
			} catch (final IOException e) {
				return ShellHelperClass.writeErrorCause(env, e);
			}
		}

		final Map<String, Charset> charsets = Charset.availableCharsets();

		try {
			for (final String charsetName : charsets.keySet()) {
				env.writeln(charsetName);
			}

		} catch (final IOException e) {
			return ShellHelperClass.writeErrorCause(env, e);
		}

		return ShellStatus.CONTINUE;
	}

}
