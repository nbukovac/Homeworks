package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Class used for tokenization of a query. Valid query attributes are
 * <code>jmbag</code>, <code>firstName</code> and <code>lastName</code>, also
 * there is a valid logical operator <code>and</code>. For comparison we have 7
 * comparison operators. If a invalid attribute, logical operator, comparison
 * operator or anything else a {@link QueryLexerException} is thrown to stop
 * tokenization.
 * 
 * @see ComparisonType
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class QueryLexer {

	/**
	 * Data for parsing
	 */
	private final char[] data;

	/**
	 * Index of the next part of data specified for tokenization
	 */
	private int currentIndex;

	/**
	 * Last parsed token
	 */
	private QueryToken token;

	/**
	 * Constructs a new {@link QueryLexer} with the specified query.
	 * 
	 * @param query
	 *            query for tokenization
	 */
	public QueryLexer(final String query) {
		data = query.toCharArray();
	}

	/**
	 * Checks if <code>and</code> operator is next in line for tokenization. If
	 * <code>and</code> isn't the next {@link QueryToken} then an
	 * {@link QueryLexerException} is thrown, also if there isn't a query
	 * attribute after this and a {@link QueryLexerException} is again thrown
	 * 
	 * @throws QueryLexerException
	 *             if and isn't the next {@link QueryToken} or there isn't a
	 *             query attribute after this and
	 */
	private void checkAnd() {
		final String word = new String(data, currentIndex, 3);
		currentIndex += 3;

		if (currentIndex >= data.length) {
			throw new QueryLexerException("There isn't a query command after and");
		}

		if (word.toLowerCase().equals("and")) {
			token = new QueryToken(TokenType.AND);
		} else {
			throw new QueryLexerException("Illegal query command");
		}
	}

	/**
	 * Checks if <code>firstName</code> query type is next in line for
	 * tokenization. If not then an {@link QueryLexerException} is thrown.
	 * 
	 * @throws QueryLexerException
	 *             if firstName isn't the next {@link QueryToken}
	 */
	private void checkFirstName() {
		final String word = new String(data, currentIndex, 9);
		currentIndex += 9;

		if (word.equals("firstName")) {
			createNewQueryToken(TokenType.FIRST_NAME);
		} else {
			throw new QueryLexerException("Illegal query command");
		}
	}

	/**
	 * Checks if the next character is equal to '='.
	 * 
	 * @return true if next character is equal to '=', else false
	 */
	private boolean checkIfNextEqual() {
		return currentIndex + 1 < data.length && data[currentIndex + 1] == '=';
	}

	/**
	 * Checks if <code>JMBAG</code> query type is next in line for tokenization.
	 * If not then an {@link QueryLexerException} is thrown.
	 * 
	 * @throws QueryLexerException
	 *             if JMBAG isn't the next {@link QueryToken}
	 */
	private void checkJMBAG() {
		final String word = new String(data, currentIndex, 5);
		currentIndex += 5;

		if (word.equals("jmbag")) {
			createNewQueryToken(TokenType.JMBAG);
		} else {
			throw new QueryLexerException("Illegal query command");
		}
	}

	/**
	 * Checks if <code>lastName</code> query type is next in line for
	 * tokenization. If not then an {@link QueryLexerException} is thrown.
	 * 
	 * @throws QueryLexerException
	 *             if lastName isn't the next {@link QueryToken}
	 */
	private void checkLastName() {
		final String word = new String(data, currentIndex, 8);
		currentIndex += 8;

		if (word.equals("lastName")) {
			createNewQueryToken(TokenType.LAST_NAME);
		} else {
			throw new QueryLexerException("Illegal query command");
		}

	}

	/**
	 * Creates a new {@link QueryToken} with the specified {@link TokenType}.
	 * {@link ComparisonType} and token value are parsed next.
	 * 
	 * @param type
	 *            {@link TokenType} of the {@link QueryToken}
	 */
	private void createNewQueryToken(final TokenType type) {
		final ComparisonType comparison = getComparisonOperator();
		final String value = getTokenValue(comparison == ComparisonType.LIKE);

		token = new QueryToken(type, value, comparison);
	}

	/**
	 * Determines the {@link ComparisonType} of the comparison operator
	 * associated with the query.
	 * 
	 * @return comparison operator type
	 */
	private ComparisonType getComparisonOperator() {
		final char character = processWhitespace(data[currentIndex]);

		switch (character) {
		case '<':
			if (checkIfNextEqual()) {
				currentIndex += 2;
				return ComparisonType.LESS_EQUAL;
			} else {
				currentIndex++;
				return ComparisonType.LESS;
			}

		case '>':
			if (checkIfNextEqual()) {
				currentIndex += 2;
				return ComparisonType.GREATER_EQUAL;
			} else {
				currentIndex++;
				return ComparisonType.GREATER;
			}

		case '=':
			currentIndex++;
			return ComparisonType.EQUAL;

		case '!':
			if (checkIfNextEqual()) {
				currentIndex += 2;
				return ComparisonType.NOT_EQUAL;
			} else {
				throw new QueryLexerException("Illegal comparison operator");
			}

		default:
			final String word = new String(data, currentIndex, 4);
			currentIndex += 4;

			if (word.toLowerCase().equals("like")) {
				return ComparisonType.LIKE;
			} else {
				throw new QueryLexerException("Illegal comparison operator");
			}

		}
	}

	/**
	 * Gets token value from the query. If {@link ComparisonType} of
	 * {@link QueryToken} is <code>LIKE</code> then a wildcard symbol is
	 * allowed, but only one, if there are more a {@link QueryLexerException} is
	 * thrown. A {@link QueryLexerException} is also thrown if string literal
	 * isn't enclosed in quotes.
	 * 
	 * @param comparisonLike
	 *            determines if wildcards are important
	 * @return value of the string literal between quotes
	 */
	private String getTokenValue(final boolean comparisonLike) {
		final char character = processWhitespace(data[currentIndex]);

		if (character != '"') {
			throw new QueryLexerException("Query attribute has to enclosed in qoutes");
		}

		final StringBuilder sb = new StringBuilder();
		int specialCounter = 0;
		currentIndex++;

		for (final int end = data.length; currentIndex < end && data[currentIndex] != '"'; currentIndex++) {
			sb.append(data[currentIndex]);

			if (data[currentIndex] == '*') {
				specialCounter++;
			}
		}

		currentIndex++;

		final String returnString = sb.toString();

		if (specialCounter > 1 && comparisonLike) {
			throw new QueryLexerException("Too many wildcards in a string literal ->" + returnString);
		}

		return returnString;
	}

	/**
	 * Returns the next {@link QueryToken} found during the process of
	 * tokenizaning the provided query. A {@link QueryLexerException} is thrown
	 * if an invalid query is found.
	 * 
	 * @return next {@link QueryToken}
	 */
	public QueryToken nextToken() {
		if (currentIndex >= data.length) {
			token = new QueryToken(TokenType.END, null, null);
			return token;
		}

		char character = data[currentIndex];

		character = processWhitespace(character);

		if (character == 'a' || character == 'A') {
			if (token == null) {
				throw new QueryLexerException("Invalid syntax for a query.");
			}

			checkAnd();
			return token;
		}

		switch (character) {
		case 'j':
			checkJMBAG();
			break;
		case 'f':
			checkFirstName();
			break;
		case 'l':
			checkLastName();
			break;
		default:
			throw new QueryLexerException("Illegal query command");
		}

		return token;
	}

	/**
	 * Processes white space until a different character is found
	 * 
	 * @param character
	 *            start character for processing
	 * @return first non white space character
	 */
	private char processWhitespace(char character) {
		while (Character.isWhitespace(character)) {
			currentIndex++;
			character = data[currentIndex];
		}
		return character;
	}

}
