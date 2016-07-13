package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Defines states the lexer can be in.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public enum LexerState {

	/** End of file is reached */
	EOF,

	/** Start states which lasts until EOF is reached */
	START;
}
