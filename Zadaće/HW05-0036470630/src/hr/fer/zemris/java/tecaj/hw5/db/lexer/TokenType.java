package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Types of possible query operations.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public enum TokenType {

	/**
	 * Specifies AND logical operation
	 */
	AND,

	/**
	 * JMBAG query operation
	 */
	JMBAG,

	/**
	 * First name query operation
	 */
	FIRST_NAME,

	/**
	 * Last name query operation
	 */
	LAST_NAME,

	/**
	 * End of query
	 */
	END
}
