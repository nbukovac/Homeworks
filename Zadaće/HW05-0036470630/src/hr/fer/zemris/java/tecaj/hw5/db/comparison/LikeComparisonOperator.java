package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * Class that implements {@link IComparisonOperator} and performs regular
 * expression matching of the first string to the second, only one ".*" wildcard
 * is allowed, other wildcard are not allowed.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class LikeComparisonOperator implements IComparisonOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean satisfied(final String value1, final String value2) {
		return value1.matches(value2.replace("*", ".*"));

	}

}
