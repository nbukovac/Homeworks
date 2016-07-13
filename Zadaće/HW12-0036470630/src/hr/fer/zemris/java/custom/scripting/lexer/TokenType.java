package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Types of tokens that can be parsed while a text is processed using the
 * {@link Lexer} class.
 */
public enum TokenType {

	/** Mathematical operator */
	OPERATOR,

	/** Tag opening */
	START_TAG,

	/** Tag closing */
	END_TAG,

	/** Variable */
	VARIABLE,

	/** String in tags */
	STRING,

	/** Constant integer */
	CONST_INT,

	/** Constant double */
	CONST_DOUBLE,

	/** Text outside tags */
	TEXT,

	/** Start of echo tag */
	ECHO,

	/** Start of for loop tag */
	FOR,

	/** Function */
	FUNCTION,

	/** End tag */
	END;
}
