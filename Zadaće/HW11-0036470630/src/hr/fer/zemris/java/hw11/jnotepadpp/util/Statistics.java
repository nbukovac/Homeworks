package hr.fer.zemris.java.hw11.jnotepadpp.util;

import javax.swing.JTextArea;

/**
 * Class used for document statistics tracking. Statistics that are being
 * tracked are number of lines in the current document, number of all characters
 * and number of non blank characters in the opened document.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Statistics {

	/** Number of lines in document */
	private int numberOfLines;

	/** Number of characters in document */
	private int numberOfCharacters;

	/** Number of non blank characters in document */
	private int numberOfNonBlankCharacters;

	@Override
	public String toString() {
		return "Your document has " + numberOfLines + " lines, " + numberOfCharacters + " characters and "
				+ numberOfNonBlankCharacters + " non blank characters";
	}

	/**
	 * Updates the statistics that are being kept for the opened document.
	 * 
	 * @param editor
	 *            opened document
	 */
	public void updateStatistics(final JTextArea editor) {
		numberOfLines = editor.getLineCount();

		final String text = editor.getText();
		numberOfCharacters = text.length();
		numberOfNonBlankCharacters = text.replaceAll("\\s+", "").length();
	}

}
