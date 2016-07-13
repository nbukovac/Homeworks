package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass;

/**
 * Writes a formatted representation of the specified directory to the
 * associated {@link Environment}. File characteristics are separated into four
 * columns.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class LsShellCommand extends AbstractShellCommand {

	/**
	 * Creates the first column for {@code ls} command output. First column
	 * consists of file descriptors such as is a file a directory, is it
	 * readable, is it writable and is it executable.
	 * 
	 * @param child
	 *            file to check
	 * @return first column of output
	 */
	private static String createFirstColumn(final File child) {
		final StringBuilder firstColumn = new StringBuilder();

		if (child.isDirectory()) {
			firstColumn.append('d');
		} else {
			firstColumn.append('-');
		}

		if (child.canRead()) {
			firstColumn.append('r');
		} else {
			firstColumn.append('-');
		}

		if (child.canWrite()) {
			firstColumn.append('w');
		} else {
			firstColumn.append('-');
		}

		if (child.canExecute()) {
			firstColumn.append('x');
		} else {
			firstColumn.append('-');
		}

		return firstColumn.toString();
	}

	/**
	 * Lists through a directory and for each {@link File} writes its
	 * statistics.
	 * 
	 * @param file
	 *            parent directory
	 * @param env
	 *            working environment
	 * @return output string for environment writing
	 * @throws IOException
	 *             if anything happens as it is specified by the
	 *             {@link IOException}
	 */
	private static String doDirectoryListing(final File file, final Environment env) throws IOException {
		final File[] childrenFiles = file.listFiles();
		final StringBuilder sb = new StringBuilder();

		for (final File child : childrenFiles) {
			sb.append(createFirstColumn(child));
			sb.append(' ');

			sb.append(String.format("%10d", child.length()));
			sb.append(' ');

			sb.append(getCreationDate(child));
			sb.append(' ');

			sb.append(child.getName());
			sb.append("\r\n");
		}

		return sb.toString();
	}

	/**
	 * Returns the files creation date in the following format
	 * "yyyy-MM-dd HH:mm:ss".
	 * 
	 * @param file
	 *            file to get creation date for
	 * @return file creation date as a {@link String}
	 * @throws IOException
	 *             if anything happens as it is specified by the
	 *             {@link IOException}
	 */
	private static String getCreationDate(final File file) throws IOException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Path path = Paths.get(file.getAbsolutePath());

		final BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		final BasicFileAttributes attributes = faView.readAttributes();
		final FileTime fileTime = attributes.creationTime();

		return sdf.format(new Date(fileTime.toMillis()));
	}

	/**
	 * Constructs a new {@link LsShellCommand} along with the command name and
	 * command description.
	 */
	public LsShellCommand() {
		super("ls");

		final List<String> description = new ArrayList<>();

		description.add("Lists all files and directories in the provided directory");
		description.add("Only one argument is needed and it has to be a directory path");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		final String[] splitArguments = ShellHelperClass.splitQoutableArguments(arguments);

		try {
			if (splitArguments.length != 1) {
				env.writeln("Invalid number of arguments for ls command");

			} else {
				final File file = new File(splitArguments[0]);

				if (file.isDirectory()) {
					env.writeln(doDirectoryListing(file, env));
				} else {
					env.writeln("Ls command has to given an directory path");
				}
			}
		} catch (final IOException e) {
			return ShellHelperClass.writeErrorCause(env, e);
		}

		return ShellStatus.CONTINUE;
	}

}
