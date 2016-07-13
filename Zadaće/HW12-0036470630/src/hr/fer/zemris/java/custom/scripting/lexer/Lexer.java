package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * Parses text according to set rules. Tags are opened with "{$" and closed with
 * "$}", each type of tags has it own rules, see {@link ForLoopNode} and
 * {@link EchoNode} for details. Valid escape sequences are "\n", "\r", "\t",
 * "\{" and "\\" and that is only in text, valid operations are +, -, /, *, ^.
 * Whitespace inside tags is ignored.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Lexer {

	/**
	 * Text specified for lexic reconstruction
	 */
	private final char[] data;

	/**
	 * Determines the number of processed characters and the index of the
	 * following
	 */
	private int currentIndex;

	/**
	 * State the {@link Lexer} currently is
	 */
	private LexerState state;

	/**
	 * Last found {@link Token}, used to group text into smaller semantic parts
	 */
	private Token token;

	/**
	 * Constructs a new {@link Lexer} with the provided text for parsing
	 * 
	 * @param text
	 *            text for parsing
	 */
	public Lexer(final String text) {
		data = text.toCharArray();
		state = LexerState.START;
	}

	/**
	 * Checks if a 'e' character is a end tag, part of text or a variable name.
	 * Appropriate {@link Token} is created.
	 */
	private void checkCharacterE() {
		TokenType lastTokenType = null;

		if (token != null) {
			lastTokenType = token.getType();
		}

		if (lastTokenType == null || (lastTokenType != null && lastTokenType == TokenType.END_TAG)) {
			token = new Token(TokenType.TEXT, getTextForToken(currentIndex));
			return;
		} else if (lastTokenType != null && lastTokenType == TokenType.START_TAG) {

			if (currentIndex + 2 < data.length) {
				final String s = "" + data[currentIndex] + data[currentIndex + 1] + data[currentIndex + 2];

				if (s.toLowerCase().equals("end")) {
					token = new Token(TokenType.END, "");
					currentIndex += 3;
					return;
				}
			}

		}

		token = new Token(TokenType.VARIABLE, getVariableName());

	}

	/**
	 * Checks if a closing tag is reached or a part of text. A
	 * {@link LexerException} is thrown if a tag is closed wrong.
	 */
	private void checkClosingTag() {
		if (token == null || token.getType() == TokenType.END_TAG) {
			token = new Token(TokenType.TEXT, getTextForToken(currentIndex));
		} else {
			currentIndex++;
			if (currentIndex < data.length && data[currentIndex] == '}') {
				token = new Token(TokenType.END_TAG, "");
			} else {
				throw new LexerException("Wrong tag closing");
			}

			currentIndex++;
		}

	}

	/**
	 * Checks if a '=' character is a echo tag or a part of text. Appropriate
	 * {@link Token} is created.
	 */
	private void checkEcho() {
		if (token == null || token.getType() != TokenType.START_TAG) {
			token = new Token(TokenType.TEXT, getTextForToken(currentIndex));
		} else {
			token = new Token(TokenType.ECHO, "=");
		}

		currentIndex++;
	}

	/**
	 * Sets <code>state</code> to <code>LexerState.EOF</code> if end of file is
	 * reached
	 */
	private void checkEndOfFile() {
		if (currentIndex >= data.length) {
			state = LexerState.EOF;
		}
	}

	/**
	 * Checks if a <code>String</code> contains an invalid escape sequence.
	 * Valid escape sequences are "\n", "\r", "\t", "\{", "\\" and "\"" and that
	 * is only in text.
	 * 
	 * @param c
	 *            character to check
	 */
	private void checkEscapeSequence(final char c) {
		if (!(c == 'n' || c == '\\' || c == '"' || c == 't' || c == 'r' || c == 'r')) {
			throw new LexerException("Invalid escape sequence");
		}
	}

	/**
	 * Checks if a 'f' character is a part of text, for loop tag or variable
	 * name. Appropriate {@link Token} is created.
	 */
	private void checkFor() {
		TokenType lastTokenType = null;

		if (token != null) {
			lastTokenType = token.getType();
		}

		if (lastTokenType == null || (lastTokenType != null && lastTokenType == TokenType.END_TAG)) {
			token = new Token(TokenType.TEXT, getTextForToken(currentIndex));
			return;
		} else if (lastTokenType != null && lastTokenType == TokenType.START_TAG) {

			if (currentIndex + 2 < data.length) {
				final String s = "" + data[currentIndex] + data[currentIndex + 1] + data[currentIndex + 2];

				if (s.toLowerCase().equals("for")) {
					token = new Token(TokenType.FOR, "");
					currentIndex += 3;
					return;
				}
			}

			token = new Token(TokenType.VARIABLE, getVariableName());
		}

	}

	/**
	 * Checks if a '@' is a function or a part of text. If it is a function, a
	 * new {@link Token} with {@link TokenType} <code>FUNCTION</code> is
	 * created. If it is a part of text, a new {@link Token} with
	 * {@link TokenType} <code>TEXT</code> is created. A {@link LexerException}
	 * is thrown if '@' is after a {@link Token} with {@link TokenType}
	 * <code>START_TAG</code>
	 */
	private void checkFunction() {
		if (token == null || token.getType() == TokenType.END_TAG) {
			token = new Token(TokenType.TEXT, getTextForToken(currentIndex));
		} else if (token.getType() == TokenType.START_TAG) {
			throw new LexerException("Functions can't be at the begining of a tag");
		} else {
			currentIndex++;
			getFunction();
		}

	}

	/**
	 * Checks if a minus is a negative number, operator or a part of text. If it
	 * is a negative number, a new {@link Token} with proper {@link TokenType}
	 * is created. If it is a operator, a new {@link Token} with
	 * {@link TokenType} <code>OPERATOR</code> is created. If it is a part of
	 * text, a new {@link Token} with {@link TokenType} <code>TEXT</code> is
	 * created.
	 */
	private void checkMinus() {
		if (currentIndex + 1 < data.length) {
			if (Character.isDigit(data[currentIndex + 1])) {
				currentIndex++;
				getNumber("-");
			} else if (token != null && token.getType() != TokenType.END_TAG
					&& token.getType() != TokenType.START_TAG) {
				token = new Token(TokenType.OPERATOR, "-");
			} else if (token == null || token.getType() == TokenType.END_TAG) {
				token = new Token(TokenType.TEXT, getTextForToken(currentIndex));
			}
		}
	}

	/**
	 * Checks if a tag is properly opened. If it is a new {@link Token} with
	 * {@link TokenType} <code>START_TAG</code> is created. If not a new
	 * {@link Token} with {@link TokenType} <code>TEXT</code> is created.
	 */
	private void checkOpeningTag() {
		currentIndex++;

		if (data[currentIndex] == '$') {
			if (token == null || token.getType() == TokenType.END_TAG || token.getType() == TokenType.TEXT) {
				token = new Token(TokenType.START_TAG, "");

			} else {
				throw new LexerException("Tag opening inside a tag isn't valid");

			}

		} else {
			if (token == null || token.getType() == TokenType.END_TAG) {
				token = new Token(TokenType.TEXT, getTextForToken(currentIndex - 2));
			} else {
				throw new LexerException("Invalid charachter sequence in tag");
			}
		}

		currentIndex++;
	}

	/**
	 * Checks if the provided <code>String</code> is a valid mathematical
	 * operation. Valid operations are +, -, /, *, ^.
	 * 
	 * @param c
	 *            character to check
	 * @return true if <code>s</code> is a valid operation, else false
	 */
	private boolean checkOperator(final char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}

	/**
	 * Checks if a found &quot; is inside a tag or not. If it is a new
	 * {@link Token} with {@link TokenType} <code>STRING</code> is created.
	 */
	private void checkQuotations() {
		currentIndex++;
		if (token != null && token.getType() != TokenType.END_TAG && token.getType() != TokenType.START_TAG) {
			token = new Token(TokenType.STRING, getTokenString());
		}
	}

	/**
	 * Creates a new {@link Token} with {@link TokenType} <code>FUNCTION</code>
	 * with longest possible valid function name.
	 */
	private void getFunction() {
		token = new Token(TokenType.FUNCTION, getVariableName());
	}

	/**
	 * Creates a new {@link Token} with {@link TokenType} <code>CONST_INT</code>
	 * or <code>CONST_DOUBLE</code> depending on the found number
	 * 
	 * @param sign
	 *            determines if a number is positive or negative
	 */
	private void getNumber(final String sign) {
		final StringBuilder s = new StringBuilder();
		final int size = data.length;

		s.append(sign);

		while (currentIndex < size) {
			if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
				s.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}

		try {
			final int number = Integer.parseInt(s.toString());
			token = new Token(TokenType.CONST_INT, number);
		} catch (final NumberFormatException e) {
			final double doubleNumber = Double.parseDouble(s.toString());
			token = new Token(TokenType.CONST_DOUBLE, doubleNumber);
		}
	}

	/**
	 * Returns the state the {@link Lexer} is working in
	 * 
	 * @return {@link Lexer} state
	 */
	public LexerState getState() {
		return state;
	}

	/**
	 * Returns a string between two tags.
	 * 
	 * @param index
	 *            starting point for string creation
	 * @return String between two tags
	 */
	private String getTextForToken(final int index) {
		final StringBuilder s = new StringBuilder();

		for (int i = index, size = data.length; i < size - 1; i++) {
			if (data[i] == '{' && data[i + 1] == '$') {
				break;
			}

			s.append(data[i]);

			if (data[i] == '\\' && (data[i + 1] == '{' || data[i + 1] == '\\')) {
				i++;
				s.append(data[i]);
			} else if (data[i] == '\\') {
				checkEscapeSequence(data[i + 1]);
			}

			if (i == size - 2) {
				s.append(data[i + 1]);
			}
		}

		currentIndex += s.length();

		return s.toString();
	}

	/**
	 * Returns a string between two not escaped &quot; characters. An
	 * {@link LexerException} is thrown if a invalid escape sequence is found.
	 * 
	 * @return String between two not escaped &quot; characters
	 */
	private String getTokenString() {
		final StringBuilder s = new StringBuilder();

		for (int i = currentIndex, size = data.length; i < size - 1; i++) {
			if (data[i] == '"') {
				break;
			}

			s.append(data[i]);

			if (data[i] == '\\' && (data[i + 1] == '"' || data[i + 1] == '\\')) {
				i++;
				s.append(data[i]);
			} else if (data[i] == '\\') {
				checkEscapeSequence(data[i + 1]);
			}

			if (i == size - 2) {
				s.append(data[i + 1]);
			}
		}

		currentIndex += s.length() + 1;

		return s.toString();
	}

	/**
	 * Returns the longest possible valid variable name depending on the text.
	 * Valid variable names match to the following regular expression
	 * "[[:letter:]][[:letter:][:digit:]_]*"
	 * 
	 * @return the longest possible valid variable name
	 */
	private String getVariableName() {
		char c = data[currentIndex];

		if (!Character.isLetter(c)) {
			throw new LexerException("Invalid variable name");
		}

		final StringBuilder s = new StringBuilder();
		s.append(c);
		final int size = data.length;

		while (currentIndex < size) {
			currentIndex++;
			c = data[currentIndex];

			if (!variableNameChecker(c)) {
				break;
			}

			s.append(c);
		}

		return s.toString();
	}

	/**
	 * Returns next found {@link Token} unless a {@link LexerException} is
	 * thrown.
	 * 
	 * @see LexerException
	 * 
	 * @return next found {@link Node}
	 */
	public Token nextToken() {
		if (state == LexerState.EOF) {
			throw new LexerException("You can't generate a new Token after EOF");
		}

		char character = data[currentIndex];
		final boolean whitespaceValid = token == null || token.getType() == TokenType.END_TAG ? true : false;

		while (!whitespaceValid && Character.isWhitespace(character)) {
			currentIndex++;
			character = data[currentIndex];
		}

		character = Character.toLowerCase(character);

		switch (character) {
		case '{':
			checkOpeningTag();
			break;
		case 'e':
			checkCharacterE();
			break;
		case '=':
			checkEcho();
			break;
		case '$':
			checkClosingTag();
			break;
		case '"':
			checkQuotations();
			break;
		case 'f':
			checkFor();
			break;
		case '@':
			checkFunction();
			break;
		case '-':
			checkMinus();
			break;
		default:
			if (token != null && token.getType() != TokenType.END_TAG && Character.isDigit(character)) {
				getNumber("");
				break;
			}
			if (token != null && token.getType() != TokenType.END_TAG && token.getType() != TokenType.START_TAG
					&& checkOperator(character)) {
				token = new Token(TokenType.OPERATOR, data[currentIndex]);
				currentIndex++;
				break;
			}

			if (token != null && token.getType() != TokenType.END_TAG) {
				final String name = getVariableName();
				token = new Token(TokenType.VARIABLE, name);
				break;
			}

			token = new Token(TokenType.TEXT, getTextForToken(currentIndex));
			break;
		}

		checkEndOfFile();

		return token;
	}

	/**
	 * Checks if the next character is valid for a variable name.
	 * 
	 * @param c
	 *            character to check
	 * @return true if valid, else false
	 */
	private boolean variableNameChecker(final char c) {
		return Character.isLetter(c) || Character.isDigit(c) || c == '_';
	}
}
