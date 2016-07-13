package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Types of tokens that can be parsed while a text is processed using the
 * {@link Lexer} class.
 */
public enum TokenType {

	/** End of text */
	EOF,

	/** Word token */
	WORD,

	/** Number token */
	NUMBER,

	/** Symbol token */
	SYMBOL;
}
