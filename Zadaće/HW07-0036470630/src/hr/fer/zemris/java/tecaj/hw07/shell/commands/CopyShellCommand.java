package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass;

/**
 * Copies a file to the specified destination. Two arguments are required, first
 * is source file and the second is the destination file. If the destination is
 * a directory then the file will be copied to that directory. All missing
 * directories will be constructed.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class CopyShellCommand extends AbstractShellCommand {

	/**
	 * Buffer size for reading and writing from files
	 */
	private static int BUFFER_SIZE = 4096;

	/**
	 * Copies the source file to the destination file. Copying is done with a
	 * buffer. An {@link IOException} is thrown if anything happens as it is
	 * specified.
	 * 
	 * @param sourceFile
	 *            source file
	 * @param destinationFile
	 *            destination file
	 * @param env
	 *            working environment
	 * @throws IOException
	 *             if anything happens as it is specified by the
	 *             {@link IOException}
	 */
	private static void copyFiles(final File sourceFile, final File destinationFile, final Environment env)
			throws IOException {
		final FileInputStream inputStream = new FileInputStream(sourceFile);
		final FileOutputStream outputStream = new FileOutputStream(destinationFile);
		final byte[] buffer = new byte[BUFFER_SIZE];

		int numberOfReadBytes = inputStream.read(buffer);

		while (numberOfReadBytes != -1) {
			outputStream.write(buffer, 0, numberOfReadBytes);

			numberOfReadBytes = inputStream.read(buffer);
		}

		inputStream.close();
		outputStream.close();

		env.writeln(
				"File " + sourceFile.getAbsolutePath() + " has been copied to " + destinationFile.getAbsolutePath());
	}

	/**
	 * Constructs a new {@link CopyShellCommand} along with the command name and
	 * command description.
	 */
	public CopyShellCommand() {
		super("copy");

		final List<String> description = new ArrayList<>();

		description.add("Copies a file to the specified destination");
		description.add("Two arguments are required, the first one has to a file,");
		description.add("the second one can be either a directory or a file");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		final String[] splitArguments = ShellHelperClass.splitQoutableArguments(arguments);

		try {
			if (splitArguments.length == 2) {
				final File sourceFile = new File(splitArguments[0]);
				File destinationFile = new File(splitArguments[1]);

				if (sourceFile.isFile()) {
					if (destinationFile.exists() && destinationFile.isFile()) {
						env.writeln("File already exists. Do you want to overwrite it!(Y/N)");

						if (env.readline().trim().toLowerCase().equals("y")) {
							copyFiles(sourceFile, destinationFile, env);
						}

					} else if (destinationFile.isDirectory()) {
						destinationFile = new File(destinationFile, sourceFile.getName());
						copyFiles(sourceFile, destinationFile, env);

					} else {
						if (!destinationFile.exists()) {
							final String destination = destinationFile.getAbsolutePath();
							final File destinationDirectory = new File(
									destination.substring(0, destination.lastIndexOf(File.separatorChar)));

							if (!destinationDirectory.mkdirs()) {
								env.writeln("Couldn't create the destination directory.");
								return ShellStatus.CONTINUE;
							}
						}

						copyFiles(sourceFile, destinationFile, env);
					}

				} else {
					env.writeln("First argument has to be a file path.");
				}

			} else {
				env.writeln("Copy command requires 2 arguments.");
			}
		} catch (final IOException e) {
			return ShellHelperClass.writeErrorCause(env, e);
		}

		return ShellStatus.CONTINUE;
	}

}
