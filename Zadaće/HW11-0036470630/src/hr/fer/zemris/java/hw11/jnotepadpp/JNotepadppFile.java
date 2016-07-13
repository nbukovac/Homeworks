package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.component.StatusBar;
import hr.fer.zemris.java.hw11.jnotepadpp.util.Statistics;

/**
 * Class that represents a file that is editable in {@link JNotepadpp}. A file
 * is defined by its editor and its file path.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class JNotepadppFile {

	/** {@link JTabbedPane} contained in the main window */
	private static JTabbedPane tabbedPane;

	/** {@link StatusBar} contained in the main window */
	private static StatusBar statusBar;

	/**
	 * Sets the {@code statusBar} to the specified argument.
	 * 
	 * @param statusBar
	 *            statusBar in main window
	 */
	public static void setStatusBar(final StatusBar statusBar) {
		JNotepadppFile.statusBar = statusBar;
	}

	/**
	 * Sets the {@code tabbedPane} to the specified argument.
	 * 
	 * @param tabbedPane
	 *            tabbedPane in main window
	 */
	public static void setTabbedPane(final JTabbedPane tabbedPane) {
		JNotepadppFile.tabbedPane = tabbedPane;
	}

	/** File editor */
	private JTextArea editor;

	/** File path */
	private Path filePath;

	/** Tracks if the file has changed after saving or opening */
	private boolean changed;

	/** File statistics */
	private final Statistics statistics;

	/** {@link CaretListener} used for tracking caret changes */
	private final CaretListener caretListener;

	/**
	 * Constructs a new {@link JNotepadppFile} with the default values.
	 */
	public JNotepadppFile() {
		this(new JTextArea(), null);
	}

	/**
	 * Constructs a new {@link JNotepadppFile} with the specified values.
	 * 
	 * @param editor
	 *            document editor
	 * @param filePath
	 *            file path
	 */
	public JNotepadppFile(final JTextArea editor, final Path filePath) {
		super();
		this.editor = editor;
		this.filePath = filePath;
		statistics = new Statistics();
		statistics.updateStatistics(editor);

		editor.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(final KeyEvent e) {
				changed = true;
				statistics.updateStatistics(editor);
				final int tabIndex = tabbedPane.getSelectedIndex();
				tabbedPane.setIconAt(tabIndex, JNotepadpp.UNSAVED);
			}
		});

		caretListener = new CaretListener() {

			@Override
			public void caretUpdate(final CaretEvent e) {
				final int selected = editor.getSelectedText() != null ? editor.getSelectedText().length() : 0;
				statusBar.updateStatusbar(editor.getText().length(), getCaretLineNumber(), getCaretColumn(), selected);
			}
		};

		editor.addCaretListener(caretListener);
	}

	/**
	 * Constructs a new {@link JNotepadppFile} with the default value for editor
	 * and specified for file path.
	 * 
	 * @param filePath
	 *            file path
	 */
	public JNotepadppFile(final Path filePath) {
		this(new JTextArea(), filePath);
	}

	/**
	 * Returns the column where the caret is currently positioned.
	 * 
	 * @return caret column position
	 */
	private int getCaretColumn() {
		final int line = getCaretLineNumber() - 1;
		int column = 0;

		try {
			column = editor.getCaretPosition() - editor.getLineStartOffset(line);
		} catch (final BadLocationException ignorable) {
		}

		return column;
	}

	/**
	 * Returns the line where the caret is currently positioned.
	 * 
	 * @return caret line number
	 */
	private int getCaretLineNumber() {
		int line = 0;

		try {
			line = editor.getLineOfOffset(editor.getCaretPosition()) + 1;
		} catch (final BadLocationException ignorable) {
		}

		return line;
	}

	/**
	 * @return the editor
	 */
	public JTextArea getEditor() {
		return editor;
	}

	/**
	 * @return the filePath
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * @return the statistics
	 */
	public Statistics getStatistics() {
		return statistics;
	}

	/**
	 * Returns the value of {@code changed}.
	 * 
	 * @return changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Sets the value of {@code changed}.
	 * 
	 * @param changed
	 *            new value
	 */
	public void setChanged(final boolean changed) {
		this.changed = changed;
	}

	/**
	 * @param editor
	 *            the editor to set
	 */
	public void setEditor(final JTextArea editor) {
		this.editor = editor;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(final Path filePath) {
		this.filePath = filePath;
	}

	/**
	 * Calls a update on the {@code caretListener}.
	 */
	public void updateCaret() {
		caretListener.caretUpdate(null);
	}

}
