package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Parsing machine which creates {@link Token}s of 4 types({@link TokenType}).
 * Empty spaces are ignored, numbers can be escaped so they are used as words,
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Lexer {

	/**
	 * Input data specified for processing
	 */
	private final char[] data;

	/**
	 * Last parsed {@link Token}
	 */
	private Token token;

	/**
	 * Current position in the data array
	 */
	private int currentIndex;

	/**
	 * State the machine is working in.
	 * 
	 * @see hr.fer.zemris.java.tecaj.hw3.prob1.LexerState
	 */
	private LexerState state;

	/**
	 * Constructs a new {@link Lexer} with specified text for processing. Lexer
	 * state is by default BASIC. An {@link IllegalArgumentException} is thrown
	 * if the <code>text</code> argument is null.
	 * 
	 * @param text
	 *            input text for processing
	 * @throws IllegalArgumentException
	 *             if the text is a null reference
	 */
	public Lexer(final String text) {
		if (text == null) {
			throw new IllegalArgumentException("A null string can't be processed so please watch out.");
		}

		data = text.trim().toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
	}

	/**
	 * Creates a new {@link Token} with EOF {@link TokenType}
	 * 
	 * @return new {@link Token} with EOF {@link TokenType}
	 */
	private Token createNewEOF() {
		token = new Token(TokenType.EOF, null);
		return token;
	}

	/**
	 * Creates a new {@link Token} with WORD {@link TokenType} with the
	 * specified value
	 * 
	 * @param nextTokenString
	 *            specified value for the new {@link Token}
	 * @param currentTokenType
	 *            WORD {@link TokenType}
	 * @return new {@link Token} with WORD {@link TokenType}
	 */
	private boolean createNewWord(final String nextTokenString, final TokenType currentTokenType) {
		token = new Token(currentTokenType, nextTokenString);
		return true;
	}

	/**
	 * Final processing procedure to check if a NUMBER or WORD {@link Token} can
	 * be parsed
	 * 
	 * @param nextTokenString
	 *            specified value for the new {@link Token}
	 * @param currentTokenType
	 *            {@link TokenType} of the new {@link Token}
	 */
	private void finalProcess(final String nextTokenString, final TokenType currentTokenType) {
		if (currentIndex == data.length) {
			switch (currentTokenType) {
			case NUMBER:
				tryParseNumber(nextTokenString, currentTokenType);
				break;
			case WORD:
				token = new Token(currentTokenType, nextTokenString);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Returns the last parsed {@link Token} without processing the next one.
	 * 
	 * @return last processed {@link Token}
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Processes the next {@link Token} from the <code>data</code> array. If an
	 * error occurs a {@link LexerException} is thrown. Exception is thrown if a
	 * invalid escape sequence is used such as "\\a" or "\\=", a number is too
	 * big for Long parsing, next {@link Token} is asked for after a
	 * {@link Token} with {@link TokenType} <code>EOF</code> was parsed.
	 * 
	 * @return next parsed {@link Token}
	 * @throws LexerException
	 *             if any of the mentioned situations occur
	 */
	public Token nextToken() {
		final int dataSize = data.length;

		if (state == LexerState.EXTENDED && dataSize > 0) {
			return processExtendedState();
		}

		if (dataSize == 0 && getToken() == null) {
			return createNewEOF();

		} else if (dataSize == currentIndex && getToken().getType() != TokenType.EOF) {
			return createNewEOF();

		} else if (getToken() != null && getToken().getType() == TokenType.EOF) {
			throw new LexerException("You tried to get a new token even the last one was EOF.");
		}

		String nextTokenString = (data[currentIndex] + "").trim();
		boolean done = false;
		TokenType currentTokenType = TokenType.EOF;

		if (Character.isDigit(data[currentIndex])) {
			currentTokenType = TokenType.NUMBER;
		} else if (Character.isLetter(data[currentIndex])) {
			currentTokenType = TokenType.WORD;
		} else if (!Character.isWhitespace(data[currentIndex]) && data[currentIndex] != '\\') {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return token;
		}

		if (currentTokenType != TokenType.EOF) {
			currentIndex++;
		}

		for (; currentIndex < dataSize && !done; currentIndex++) {
			if (Character.isWhitespace(data[currentIndex])) {
				done = processWhitespace(dataSize, nextTokenString, done, currentTokenType);

			} else if (Character.isLetter(data[currentIndex])) {
				switch (currentTokenType) {
				case NUMBER:
					tryParseNumber(nextTokenString, currentTokenType);
					done = true;
					currentIndex--;
					break;
				case WORD:
					nextTokenString += data[currentIndex];
					break;
				case EOF:
					nextTokenString += data[currentIndex];
					currentTokenType = TokenType.WORD;
					break;
				default:
					break;
				}

			} else if (Character.isDigit(data[currentIndex])) {
				switch (currentTokenType) {
				case NUMBER:
					nextTokenString += data[currentIndex];
					break;
				case WORD:
					done = createNewWord(nextTokenString, currentTokenType);
					currentIndex--;
					break;
				case EOF:
					nextTokenString += data[currentIndex];
					currentTokenType = TokenType.NUMBER;
					break;
				default:
					break;
				}

			} else if (data[currentIndex] == '\\') {
				if (nextTokenString.equals("\\")) {
					nextTokenString = "";
				}

				if (currentIndex + 1 < dataSize
						&& (Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\')) {
					switch (currentTokenType) {
					case EOF:
						nextTokenString += data[currentIndex + 1];
						currentTokenType = TokenType.WORD;
						currentIndex++;
						break;
					case NUMBER:
						tryParseNumber(nextTokenString, currentTokenType);
						done = true;
						currentIndex--;
						break;
					case WORD:
						nextTokenString += data[currentIndex + 1];
						currentIndex++;
						break;
					default:
						break;
					}

				} else {
					throw new LexerException("Wrong escape sequence");
				}

			} else {
				done = processFoundSymbol(nextTokenString, done, currentTokenType);
			}

		}

		finalProcess(nextTokenString, currentTokenType);

		return token;
	}

	/**
	 * Processes the input text while the {@link Lexer} is in EXTENDED state.
	 * 
	 * @return next parsed {@link Token}
	 */
	private Token processExtendedState() {
		final int size = data.length;
		String nextStringToken = "";

		while (currentIndex < size && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}

		while (currentIndex < size && (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '\\')) {
			nextStringToken += data[currentIndex];
			currentIndex++;
		}

		if (!nextStringToken.isEmpty()) {
			token = new Token(TokenType.WORD, nextStringToken);
		} else {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
		}

		return token;
	}

	/**
	 * Processes the text when a symbol is found.
	 * 
	 * @param nextTokenString
	 *            value the for the {@link Token}
	 * @param done
	 *            completion indicator
	 * @param currentTokenType
	 *            {@link TokenType} for the {@link Token}
	 * @return new parsed SYMBOL {@link Token}
	 */
	private boolean processFoundSymbol(final String nextTokenString, boolean done, final TokenType currentTokenType) {
		switch (currentTokenType) {
		case NUMBER:
			tryParseNumber(nextTokenString, currentTokenType);
			done = true;
			currentIndex--;
			break;
		case WORD:
			token = new Token(currentTokenType, nextTokenString);
			currentIndex--;
			done = true;
			break;
		case EOF:
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			done = true;
			break;
		default:
			break;
		}
		return done;
	}

	/**
	 * Processes whitespace and removes every whitespace until a valid character
	 * is found.
	 * 
	 * @param dataSize
	 *            size of the <code>data</code> array
	 * @param nextTokenString
	 *            value for the next {@link Token}
	 * @param done
	 *            completion indicator
	 * @param currentTokenType
	 *            type for the next {@link Token}
	 * @return true to indicate a new {@link Token} has been parsed
	 */
	private boolean processWhitespace(final int dataSize, final String nextTokenString, boolean done,
			final TokenType currentTokenType) {
		switch (currentTokenType) {
		case NUMBER:
			tryParseNumber(nextTokenString, currentTokenType);
			done = true;
			break;
		case WORD:
			done = createNewWord(nextTokenString, currentTokenType);
			break;
		default:
			break;
		}

		while (currentIndex + 1 < dataSize && Character.isWhitespace(data[currentIndex + 1])) {
			currentIndex++;
		}

		return done;
	}

	/**
	 * Sets the state of the {@link Lexer}. Two states are valid, BASIC and
	 * EXTENDED.
	 * 
	 * @see hr.fer.zemris.java.tecaj.hw3.prob1.LexerState
	 * @param state
	 *            new state for this {@link Lexer}
	 */
	public void setState(final LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException(
					"Lexer state can not be set to null. Only BASIC and Extended are valid states");
		}

		this.state = state;
	}

	/**
	 * Tries to parse a number if successful creates a new NUMBER {@link Token}
	 * if not an {@link NumberFormatException} is thrown.
	 * 
	 * @param nextTokenString
	 *            value for the Number {@link Token}
	 * @param currentTokenType
	 *            Number {@link TokenType}
	 */
	private void tryParseNumber(final String nextTokenString, final TokenType currentTokenType) {
		try {
			token = new Token(currentTokenType, Long.parseLong(nextTokenString));
		} catch (final NumberFormatException e) {
			throw new LexerException("Number is too big to parse");
		}
	}

}
