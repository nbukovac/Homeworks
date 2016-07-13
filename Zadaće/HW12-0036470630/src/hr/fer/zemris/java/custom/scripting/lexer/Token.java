package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents a parsed part of text. The text is parsed with the {@link Lexer}
 * class.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Token {

	/**
	 * {@link TokenType} of this {@link Token}
	 */
	private final TokenType type;

	/**
	 * Value parsed from text
	 */
	private final Object value;

	/**
	 * Constructs a new {@link Token} with the specified {@link TokenType} and
	 * value.
	 * 
	 * @param type
	 *            specific {@link TokenType}
	 * @param value
	 *            specific value
	 */
	public Token(final TokenType type, final Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns the {@link TokenType} of this {@link Token}.
	 * 
	 * @return {@link TokenType}
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Returns the value of this {@link Token}
	 * 
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}
}
