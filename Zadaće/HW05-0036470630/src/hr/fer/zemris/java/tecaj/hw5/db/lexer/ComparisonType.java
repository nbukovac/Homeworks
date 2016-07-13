package hr.fer.zemris.java.tecaj.hw5.db.lexer;

/**
 * Enumeration used to specify types of comparison operators
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public enum ComparisonType {

	/**
	 * Greater than comparison
	 */
	GREATER,

	/**
	 * Less than comparison
	 */
	LESS,

	/**
	 * Greater than or equal comparison
	 */
	GREATER_EQUAL,

	/**
	 * Less than or equal comparison
	 */
	LESS_EQUAL,

	/**
	 * Equal comparison
	 */
	EQUAL,

	/**
	 * Not equal comparison
	 */
	NOT_EQUAL,

	/**
	 * Regular expression comparison
	 */
	LIKE
}
