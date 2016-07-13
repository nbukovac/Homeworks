package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.helper.ShellHelperClass;

/**
 * Writes the directory structure as a tree to the associated
 * {@link Environment}. One argument is required and that is the directory path
 * to the parent directory.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class TreeShellCommand extends AbstractShellCommand {

	/**
	 * Writes the file name as it is specified.
	 *
	 */
	private static class Ispis implements FileVisitor<Path> {

		/**
		 * Depth level
		 */
		private int level;

		/**
		 * Writes the appropriate tree structure node.
		 * 
		 * @param file
		 *            next {@link Path} to write
		 */
		private void ispis(final Path file) {
			if (level == 0) {
				System.out.println(file.normalize().toAbsolutePath());
			} else {
				System.out.printf("%" + (level * 2) + "s %s\r\n", " ", file.getFileName());
			}
		}

		@Override
		public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
			ispis(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
			ispis(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * Constructs a new {@link TreeShellCommand} along with the command name and
	 * command description.
	 */
	public TreeShellCommand() {
		super("tree");

		final List<String> description = new ArrayList<>();

		description.add("Prints out a tree structure of directories and files.");
		description.add("One argument is required and that is a directory path");

		setCommandDescription(description);
	}

	@Override
	public ShellStatus executeCommand(final Environment env, final String arguments) {
		final String[] splitArguments = ShellHelperClass.splitArguments(arguments);

		try {
			if (splitArguments.length != 1) {
				env.writeln("Tree command requires one argument");
			} else {
				final Path home = Paths.get(splitArguments[0]);

				if (!Files.isDirectory(home)) {
					env.writeln("Tree command requires a path directory.");
				} else {
					Files.walkFileTree(home, new Ispis());
				}

			}
		} catch (final IOException e) {
			return ShellHelperClass.writeErrorCause(env, e);
		}

		return ShellStatus.CONTINUE;
	}

}
