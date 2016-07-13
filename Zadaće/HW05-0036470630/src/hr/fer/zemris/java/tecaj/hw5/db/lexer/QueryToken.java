package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Represents a parsed part of a query, this is done with {@link QueryLexer}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class QueryToken {

	/**
	 * Type of {@link QueryToken}
	 */
	private final TokenType type;

	/**
	 * Value of {@link QueryToken}
	 */
	private final Object value;

	/**
	 * Type of comparison
	 */
	private final ComparisonType comparisonType;

	/**
	 * Constructs a new {@link QueryToken}, where only {@link TokenType} matters
	 * and other members are initialized to null.
	 * 
	 * @param type
	 *            {@link TokenType}
	 */
	public QueryToken(final TokenType type) {
		this(type, null, null);
	}

	/**
	 * Constructs a new {@link QueryToken} with the provided arguments.
	 * 
	 * @param type
	 *            {@link TokenType}
	 * @param value
	 *            token value
	 * @param comparison
	 *            comparison type
	 */
	public QueryToken(final TokenType type, final Object value, final ComparisonType comparison) {
		this.type = type;
		this.value = value;
		this.comparisonType = comparison;
	}

	/**
	 * Returns the {@link ComparisonType} of this {@link QueryToken}.
	 * 
	 * @return the comparisonType
	 */
	public ComparisonType getComparisonType() {
		return comparisonType;
	}

	/**
	 * Returns the {@link TokenType} of this {@link QueryToken}.
	 * 
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Returns the value of this {@link QueryToken}.
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

}
