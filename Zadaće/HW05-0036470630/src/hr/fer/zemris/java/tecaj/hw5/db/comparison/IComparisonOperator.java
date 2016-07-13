package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * Performs comparison operations specified by a concrete class implementing
 * this interface.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IComparisonOperator {

	/**
	 * Checks if two strings are in a relationship specified by the class.
	 * 
	 * @param value1
	 *            string to check if the relationship is valid
	 * @param value2
	 *            control string specified by the user
	 * @return true if the relationship is achieved, else false
	 */
	public boolean satisfied(String value1, String value2);

}
