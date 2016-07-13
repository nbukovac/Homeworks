package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Enumeration that defines the states a {@link Lexer} is allowed to be. Valid
 * states are BASIC and EXTENDED.
 */
public enum LexerState {

	/** Default state for {@link Lexer}, defines ordinary functionality */
	BASIC,

	/** Special state for {@link Lexer}, defines special functionality */
	EXTENDED;
}
