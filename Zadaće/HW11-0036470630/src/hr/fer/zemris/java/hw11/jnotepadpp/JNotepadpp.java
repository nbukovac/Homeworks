package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.component.StatusBar;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.KeyConstants;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJFileChooser;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.swing.LocalizableAction;

/**
 * GUI program that enables the user to edit files and a lot more. The user can
 * open, save and close files as he sees fit, also there are editing tools such
 * as cut, copy and paste. It is also possible to sort lines of text, toggle
 * character cases and get file statistics.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class JNotepadpp extends JFrame {

	/** Serial version UID */
	private static final long serialVersionUID = 6715912844504765348L;

	/** Default frame title */
	private static final String DEFAULT_TITLE = "JNotepad++";

	/** Saved document {@link ImageIcon} */
	public static final ImageIcon SAVED = createImageIcon("icons/saved.png");

	/** Unsaved document {@link ImageIcon} */
	public static final ImageIcon UNSAVED = createImageIcon("icons/unsaved.png");

	/** Title for new document */
	private static final String NEW_DOCUMENT_TITLE = "Untitled";

	/** Tag for {@code Croatian} language */
	private static final String CROATIAN = "hr";
	/** Tag for {@code English} language */
	private static final String ENGLISH = "en";
	/** Tag for {@code German} language */
	private static final String GERMAN = "de";

	/**
	 * Creates a {@link ImageIcon} from a picture stored at the defined
	 * location.
	 * 
	 * @param iconPath
	 *            image location
	 * @return new {@link ImageIcon}
	 */
	private static ImageIcon createImageIcon(final String iconPath) {
		final InputStream inputStream = JNotepadpp.class.getResourceAsStream(iconPath);
		if (inputStream == null) {
			JOptionPane.showMessageDialog(null, "Can't create the requested icon", "Icon creation error",
					JOptionPane.ERROR_MESSAGE);
		}

		byte[] iconData = null;

		try {
			final int iconSize = inputStream.available();
			iconData = new byte[iconSize];
			inputStream.read(iconData, 0, iconSize);
		} catch (final IOException ignorable) {
		}

		return new ImageIcon(iconData);
	}

	/**
	 * Program entry point used to start the GUI.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadpp().setVisible(true);
		});
	}

	/**
	 * {@link List} of opened documents represented as {@link JNotepadppFile}
	 * objects
	 */
	private final List<JNotepadppFile> openedFiles;

	/** {@link JTabbedPane} in main window */
	private final JTabbedPane tabbedPane;

	/** {@link Collator} object used for line sorting in the document */
	private Collator collator;

	/**
	 * Instance of {@link ILocalizationProvider} used for localization changes
	 */
	private final ILocalizationProvider localizationProvider = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);

	/**
	 * {@link Action} used to change the current language to {@code Croatian}
	 */
	private final Action changeLanguageToHR = new LocalizableAction(KeyConstants.HR, KeyConstants.HR_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = 3473674984190789181L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage(CROATIAN);
			setCollator(CROATIAN);
		}
	};

	/** {@link Action} used to change the current language to {@code German} */
	private final Action changeLanguageToDE = new LocalizableAction(KeyConstants.DE, KeyConstants.DE_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = -7564398817640126860L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage(GERMAN);
			setCollator(GERMAN);
		}
	};

	/** {@link Action} used to change the current language to {@code English} */
	private final Action changeLanguageToEN = new LocalizableAction(KeyConstants.EN, KeyConstants.EN_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = 7511172850030706419L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage(ENGLISH);
			setCollator(ENGLISH);
		}
	};

	/** {@link Action} used to create a new blank document */
	private final Action createBlankDocument = new LocalizableAction(KeyConstants.NEW_FILE,
			KeyConstants.NEW_FILE_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = 3473674984190789181L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final JNotepadppFile newFile = new JNotepadppFile();
			tabbedPane.add(NEW_DOCUMENT_TITLE, new JScrollPane(newFile.getEditor()));
			openedFiles.add(newFile);
		}
	};

	/** {@link Action} used to save a document */
	private final Action saveDocument = new LocalizableAction(KeyConstants.SAVE_FILE,
			KeyConstants.SAVE_FILE_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = -7564398817640126860L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final int tabIndex = tabbedPane.getSelectedIndex();
			Path filePath = null;

			if (openedFiles.get(tabIndex).getFilePath() != null) {
				filePath = openedFiles.get(tabIndex).getFilePath();
			} else {
				final JFileChooser fileChooser = new LJFileChooser(KeyConstants.SAVE_FILE_CHOOSER,
						localizationProvider);
				fileChooser.setDialogTitle("Save document");
				if (fileChooser.showSaveDialog(JNotepadpp.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepadpp.this,
							localizationProvider.getString(KeyConstants.SAVE_WARNING_MESSAGE),
							localizationProvider.getString(KeyConstants.SAVE_WARNING_TITLE),
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				filePath = fileChooser.getSelectedFile().toPath();
			}

			final byte[] data = openedFiles.get(tabIndex).getEditor().getText().getBytes(StandardCharsets.UTF_8);

			try {
				Files.write(filePath, data);
			} catch (final IOException e) {
				JOptionPane.showMessageDialog(JNotepadpp.this,
						localizationProvider.getString(KeyConstants.SAVE_ERROR_MESSAGE),
						localizationProvider.getString(KeyConstants.SAVE_ERROR_TITLE), JOptionPane.ERROR);
				return;
			}

			openedFiles.get(tabIndex).setChanged(false);
			tabbedPane.setIconAt(tabIndex, JNotepadpp.SAVED);

			tabbedPane.setToolTipTextAt(tabIndex, filePath.toAbsolutePath().toString());
			tabbedPane.setTitleAt(tabIndex, filePath.getFileName().toString());
			openedFiles.get(tabIndex).setFilePath(filePath);

			JOptionPane.showMessageDialog(JNotepadpp.this,
					localizationProvider.getString(KeyConstants.SAVE_SUCCESS_MESSAGE),
					localizationProvider.getString(KeyConstants.SAVE_SUCCESS_TITLE), JOptionPane.INFORMATION_MESSAGE);

		}
	};

	/** {@link Action} used to save a document as defined by the user */
	private final Action saveAsDocument = new LocalizableAction(KeyConstants.SAVE_AS, KeyConstants.SAVE_AS_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = 7511172850030706419L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final int tabIndex = tabbedPane.getSelectedIndex();
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save document");

			if (fileChooser.showSaveDialog(JNotepadpp.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadpp.this,
						localizationProvider.getString(KeyConstants.SAVE_WARNING_MESSAGE),
						localizationProvider.getString(KeyConstants.SAVE_WARNING_TITLE), JOptionPane.WARNING_MESSAGE);
				return;
			}

			final Path filePath = fileChooser.getSelectedFile().toPath();
			openedFiles.get(tabIndex).setFilePath(filePath);
			saveDocument.actionPerformed(arg0);

		}
	};

	/** {@link Action} used to open a document */
	private final Action openDocument = new LocalizableAction(KeyConstants.OPEN_FILE,
			KeyConstants.OPEN_FILE_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = -3934462477497463397L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Choose a file to open");

			if (fileChooser.showOpenDialog(JNotepadpp.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			final Path filePath = fileChooser.getSelectedFile().toPath();

			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadpp.this,
						localizationProvider.getString(KeyConstants.OPEN_ERROR_MESSAGE),
						localizationProvider.getString(KeyConstants.OPEN_ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
				return;
			}

			byte[] data = null;

			try {
				data = Files.readAllBytes(filePath);
			} catch (final IOException e) {
				JOptionPane.showMessageDialog(JNotepadpp.this,
						localizationProvider.getString(KeyConstants.OPEN_ERROR_MESSAGE),
						localizationProvider.getString(KeyConstants.OPEN_ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
				return;
			}

			final String text = new String(data, StandardCharsets.UTF_8);
			final JTextArea editor = new JTextArea();
			editor.setText(text);

			openedFiles.add(new JNotepadppFile(editor, filePath));

			tabbedPane.addTab(filePath.getFileName().toString(), JNotepadpp.SAVED, new JScrollPane(editor));
			tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
			tabbedPane.setToolTipTextAt(tabbedPane.getTabCount() - 1, filePath.toAbsolutePath().toString());
		}
	};

	/** {@link Action} used to close a opened document */
	private final Action closeDocument = new LocalizableAction(KeyConstants.CLOSE_FILE,
			KeyConstants.CLOSE_FILE_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = -3934462477497463397L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final int index = tabbedPane.getSelectedIndex();

			if (openedFiles.get(index).isChanged()) {
				saveDocument.actionPerformed(null);
			}

			tabbedPane.remove(index);
			openedFiles.remove(index);
		}
	};

	/** {@link Action} used to cut the selected text in the selected document */
	private final Action cutSelectedText = new LocalizableAction(KeyConstants.CUT, KeyConstants.CUT_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = -4251073337600579806L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final Action action = new DefaultEditorKit.CutAction();
			action.actionPerformed(arg0);
		}
	};

	/**
	 * {@link Action} used to copy the selected text in the selected document
	 */
	private final Action copySelectedText = new LocalizableAction(KeyConstants.COPY, KeyConstants.COPY_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = 6033358899405660460L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final Action action = new DefaultEditorKit.CopyAction();
			action.actionPerformed(arg0);
		}
	};

	/**
	 * {@link Action} used to paste the selected text in the selected document
	 */
	private final Action pasteSelectedtext = new LocalizableAction(KeyConstants.PASTE, KeyConstants.PASTE_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = -6771855756219933130L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final Action action = new DefaultEditorKit.PasteAction();
			action.actionPerformed(arg0);
		}
	};

	/** {@link Action} used to get statistics for the selected document */
	private final Action statisticsAction = new LocalizableAction(KeyConstants.STATISTICS,
			KeyConstants.STATISTICS_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = 7778800070036592077L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			final int tabIndex = tabbedPane.getSelectedIndex();
			final String message = openedFiles.get(tabIndex).getStatistics().toString();
			JOptionPane.showMessageDialog(JNotepadpp.this, message, "Statistical info",
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/** {@link Action} used to close the program */
	private final Action exitAction = new LocalizableAction(KeyConstants.CLOSE_APP, KeyConstants.CLOSE_APP_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = 6033358899405660460L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			for (final JNotepadppFile openedFile : openedFiles) {
				if (openedFile.isChanged()) {
					final String documentName = openedFile.getFilePath() == null ? "Untitled"
							: openedFile.getFilePath().getFileName().toString();
					if (JOptionPane.showConfirmDialog(JNotepadpp.this,
							localizationProvider.getString(KeyConstants.EXIT_SAVE)
									+ documentName) == JOptionPane.YES_OPTION) {
						saveDocument.actionPerformed(null);
					}
				}
			}

			dispose();
		}
	};

	/**
	 * {@link Action} used to change the case of the selected text to upper case
	 * in the selected document
	 */
	private final Action toUpperCase = new LocalizableAction(KeyConstants.TO_UPPER_CASE,
			KeyConstants.TO_UPPER_CASE_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = 4922986426898916411L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			changeSelectedText(String::toUpperCase);
		}
	};

	/**
	 * {@link Action} used to change the case of the selected text to lower case
	 * in the selected document
	 */
	private final Action toLowerCase = new LocalizableAction(KeyConstants.TO_LOWER_CASE,
			KeyConstants.TO_LOWER_CASE_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = 523289745800772207L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			changeSelectedText(String::toLowerCase);
		}
	};

	/**
	 * {@link Action} used to invert the case of the selected text in the
	 * selected document
	 */
	private final Action invertCase = new LocalizableAction(KeyConstants.INVERT_CASE,
			KeyConstants.INVERT_CASE_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = 2987492349342314893L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			changeSelectedText(this::invertCase);
		}

		private String invertCase(final String text) {
			final char[] characters = text.toCharArray();

			for (int i = 0; i < characters.length; i++) {
				if (Character.isLowerCase(characters[i])) {
					characters[i] = Character.toUpperCase(characters[i]);
				} else if (Character.isUpperCase(characters[i])) {
					characters[i] = Character.toLowerCase(characters[i]);
				}
			}

			return new String(characters);
		}
	};

	/**
	 * {@link Action} used to sort all selected lines ascendingly in the
	 * selected document
	 */
	private final Action ascendingSort = new LocalizableAction(KeyConstants.ASCENDING_SORT,
			KeyConstants.ASCENDING_SORT_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			changeSelectedTextByLines(line -> {
				line.sort(collator);
				return line;
			});
		}

	};

	/**
	 * {@link Action} used to sort all selected lines descendingly in the
	 * selected document
	 */
	private final Action descendingSort = new LocalizableAction(KeyConstants.DESCENDING_SORT,
			KeyConstants.DECENDING_SORT_DESCRIPTION, localizationProvider) {

		private static final long serialVersionUID = 2L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			changeSelectedTextByLines(line -> {
				line.sort(collator.reversed());
				return line;
			});
		}

	};

	/**
	 * {@link Action} used to distinct all selected lines in the selected
	 * document
	 */
	private final Action unique = new LocalizableAction(KeyConstants.UNIQUE, KeyConstants.UNIQUE_DESCRIPTION,
			localizationProvider) {

		private static final long serialVersionUID = 2193042092498794657L;

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			changeSelectedTextByLines(lines -> lines.stream().distinct().collect(Collectors.toList()));
		}

	};

	/**
	 * Constructs a new {@link JNotepadpp} and sets all required GUI elements.
	 */
	public JNotepadpp() {
		openedFiles = new ArrayList<>();
		tabbedPane = new JTabbedPane();
		JNotepadppFile.setTabbedPane(tabbedPane);

		setSize(700, 500);
		setTitle(DEFAULT_TITLE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				final int decision = JOptionPane.showConfirmDialog(JNotepadpp.this,
						localizationProvider.getString(KeyConstants.EXIT_APP_MESSAGE),
						localizationProvider.getString(KeyConstants.EXIT_APP_TITLE), JOptionPane.YES_NO_OPTION);
				if (decision != JOptionPane.YES_OPTION) {
					return;
				}

				dispose();
			}
		});

		setCollator(ENGLISH);
		initGUI();
	}

	/**
	 * Changes the selected text as it is defined by the argument
	 * {@code transform}.
	 * 
	 * @param transform
	 *            text transformation
	 */
	private void changeSelectedText(final Function<String, String> transform) {
		final int tabIndex = tabbedPane.getSelectedIndex();
		final JTextArea currentEditor = openedFiles.get(tabIndex).getEditor();
		final Document document = currentEditor.getDocument();

		final int length = currentEditor.getSelectedText() != null ? currentEditor.getSelectedText().length() : 0;

		if (length == 0) {
			return;
		}

		final int offset = currentEditor.getSelectionStart();

		try {
			String text = document.getText(offset, length);
			text = transform.apply(text);
			document.remove(offset, length);
			document.insertString(offset, text, null);
		} catch (final BadLocationException ignorable) {
		}
	}

	/**
	 * Changes the selected texts lines as it is defined by the {@code modify}
	 * argument.
	 * 
	 * @param modify
	 *            modifies the selected text lines
	 */
	private void changeSelectedTextByLines(final Function<List<String>, List<String>> modify) {
		final int tabIndex = tabbedPane.getSelectedIndex();
		final JTextArea editor = openedFiles.get(tabIndex).getEditor();

		try {
			final int startLine = editor.getLineOfOffset(editor.getSelectionStart());
			final int endLine = editor.getLineOfOffset(editor.getSelectionEnd());
			final int startOffset = editor.getLineStartOffset(startLine);
			final int endOffset = editor.getLineEndOffset(endLine);

			editor.setSelectionStart(startOffset);
			editor.setSelectionEnd(endOffset);

			final String[] lines = editor.getSelectedText().split("\\n");

			final List<String> outputLines = modify.apply(Arrays.asList(lines));

			final Document document = editor.getDocument();
			document.remove(startOffset, endOffset - startOffset);

			final StringBuilder sb = new StringBuilder();

			for (final String string : outputLines) {
				sb.append(string).append('\n');
			}

			document.insertString(startOffset, sb.toString(), null);

		} catch (final BadLocationException ignorable) {
		}
	}

	/**
	 * For every {@link Action} defines how it can be called from the keyboard.
	 */
	private void createActions() {

		setupAction(createBlankDocument, KeyStroke.getKeyStroke("control N"), KeyEvent.VK_N);
		setupAction(saveDocument, KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S);
		setupAction(openDocument, KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O);
		setupAction(closeDocument, KeyStroke.getKeyStroke("control W"), KeyEvent.VK_W);
		setupAction(exitAction, KeyStroke.getKeyStroke("F4"), KeyEvent.VK_F4);
		setupAction(saveAsDocument, KeyStroke.getKeyStroke("control alt S"), KeyEvent.VK_A);

		setupAction(cutSelectedText, KeyStroke.getKeyStroke("control X"), KeyEvent.VK_X);
		setupAction(copySelectedText, KeyStroke.getKeyStroke("control C"), KeyEvent.VK_C);
		setupAction(pasteSelectedtext, KeyStroke.getKeyStroke("control P"), KeyEvent.VK_P);

		setupAction(toUpperCase, KeyStroke.getKeyStroke("control U"), KeyEvent.VK_U);
		setupAction(toLowerCase, KeyStroke.getKeyStroke("control L"), KeyEvent.VK_L);
		setupAction(invertCase, KeyStroke.getKeyStroke("control I"), KeyEvent.VK_I);
		setupAction(ascendingSort, KeyStroke.getKeyStroke("control A"), KeyEvent.VK_A);
		setupAction(descendingSort, KeyStroke.getKeyStroke("control D"), KeyEvent.VK_D);
		setupAction(unique, KeyStroke.getKeyStroke("control T"), KeyEvent.VK_T);

		setupAction(changeLanguageToDE, KeyStroke.getKeyStroke("D"), KeyEvent.VK_D);
		setupAction(changeLanguageToEN, KeyStroke.getKeyStroke("E"), KeyEvent.VK_E);
		setupAction(changeLanguageToHR, KeyStroke.getKeyStroke("H"), KeyEvent.VK_H);

		setupAction(statisticsAction, KeyStroke.getKeyStroke("F2"), KeyEvent.VK_F2);
	}

	/**
	 * Creates all of the program menus.
	 */
	private void createMenus() {
		final JMenuBar menuBar = new JMenuBar();

		final JMenu fileMenu = new LJMenu(KeyConstants.FILE_MENU, localizationProvider);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(createBlankDocument));
		fileMenu.add(new JMenuItem(openDocument));
		fileMenu.add(new JMenuItem(closeDocument));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveDocument));
		fileMenu.add(new JMenuItem(saveAsDocument));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		final JMenu editMenu = new LJMenu(KeyConstants.EDIT_MENU, localizationProvider);
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(cutSelectedText));
		editMenu.add(new JMenuItem(copySelectedText));
		editMenu.add(new JMenuItem(pasteSelectedtext));

		final JMenu toolsMenu = new LJMenu(KeyConstants.TOOLS_MENU, localizationProvider);
		menuBar.add(toolsMenu);

		toolsMenu.add(new JMenuItem(toUpperCase));
		toolsMenu.add(new JMenuItem(toLowerCase));
		toolsMenu.add(new JMenuItem(invertCase));
		toolsMenu.addSeparator();

		final JMenu sortMenu = new LJMenu(KeyConstants.SORT_MENU, localizationProvider);
		toolsMenu.add(sortMenu);

		sortMenu.add(new JMenuItem(ascendingSort));
		sortMenu.add(new JMenuItem(descendingSort));

		toolsMenu.addSeparator();
		toolsMenu.add(new JMenuItem(unique));

		final JMenu languageMenu = new LJMenu(KeyConstants.LANGUAGES_MENU, localizationProvider);
		menuBar.add(languageMenu);

		languageMenu.add(new JMenuItem(changeLanguageToDE));
		languageMenu.add(new JMenuItem(changeLanguageToHR));
		languageMenu.add(new JMenuItem(changeLanguageToEN));

		final JMenu infoMenu = new JMenu("Info");
		menuBar.add(infoMenu);

		infoMenu.add(new JMenuItem(statisticsAction));

		setJMenuBar(menuBar);
	}

	/**
	 * Create the program toolbar.
	 */
	private void createToolbar() {
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);

		toolBar.add(new JButton(createBlankDocument));
		toolBar.add(new JButton(openDocument));
		toolBar.add(new JButton(saveDocument));
		toolBar.add(new JButton(saveAsDocument));
		toolBar.add(new JButton(closeDocument));

		toolBar.addSeparator();

		toolBar.add(new JButton(cutSelectedText));
		toolBar.add(new JButton(copySelectedText));
		toolBar.add(new JButton(pasteSelectedtext));

		toolBar.addSeparator();

		toolBar.add(new JButton(statisticsAction));

		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		final StatusBar statusBar = new StatusBar();
		JNotepadppFile.setStatusBar(statusBar);
		contentPane.add(statusBar, BorderLayout.PAGE_END);

		openedFiles.add(new JNotepadppFile());
		tabbedPane.addTab("Untitled", SAVED, new JScrollPane(openedFiles.get(0).getEditor()));
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent e) {
				final int tabIndex = tabbedPane.getSelectedIndex();
				final Path filePath = openedFiles.get(tabIndex).getFilePath();

				if (filePath == null) {
					JNotepadpp.this.setTitle(DEFAULT_TITLE);
				} else {
					JNotepadpp.this.setTitle(filePath.toAbsolutePath().toString() + " - " + DEFAULT_TITLE);
				}

				openedFiles.get(tabIndex).updateCaret();

			}
		});

		createActions();
		createMenus();
		createToolbar();
	}

	/**
	 * Sets the {@code collator} to a new {@link Locale} according to the
	 * current program language.
	 * 
	 * @param language
	 *            current program language
	 */
	private void setCollator(final String language) {
		final Locale locale = Locale.forLanguageTag(language);
		collator = Collator.getInstance(locale);
	}

	/**
	 * Helper method used to sets the specified {@code action}s accelerator and
	 * mnemonic key.
	 * 
	 * @param action
	 *            {@link Action} for setup
	 * @param accelerator
	 *            accelerator key
	 * @param mnemonic
	 *            mnemonic key
	 */
	private void setupAction(final Action action, final KeyStroke accelerator, final int mnemonic) {
		action.putValue(Action.ACCELERATOR_KEY, accelerator);
		action.putValue(Action.MNEMONIC_KEY, mnemonic);
	}

}
