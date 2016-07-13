package hr.fer.zemris.java.hw11.jnotepadpp.component;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that extends {@link JPanel} and has 3 components. One {@link JLabel}
 * with file size, one {@link JLabel} with caret position in file and a
 * {@link ClockLabel}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class StatusBar extends JPanel {

	/** Serial version uid */
	private static final long serialVersionUID = -600067144325765737L;

	/** Length {@link JLabel} */
	private final JLabel lengthLabel;

	/** Caret {@link JLabel} */
	private final JLabel caretLabel;

	/**
	 * Constructs a new {@link StatusBar}.
	 */
	public StatusBar() {
		setLayout(new GridLayout(1, 3));

		lengthLabel = new JLabel("Length : 0");
		caretLabel = new JLabel("Ln: 0 Col: 0 Sel: 0");

		add(lengthLabel);
		add(caretLabel);
		add(new ClockLabel());
	}

	/**
	 * Updates the status bar.
	 * 
	 * @param size
	 *            file size
	 * @param line
	 *            line in file
	 * @param column
	 *            column
	 * @param selection
	 *            selected text
	 */
	public void updateStatusbar(final int size, final int line, final int column, final int selection) {
		lengthLabel.setText("Length : " + size);
		caretLabel.setText("Ln: " + line + " Col: " + column + " Sel: " + selection);
	}

}
