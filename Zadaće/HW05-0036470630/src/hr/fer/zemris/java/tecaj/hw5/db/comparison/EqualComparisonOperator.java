package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * Class that implements {@link IComparisonOperator} and performs equal to,
 * lexicographic comparison between two <code>Strings</code>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class EqualComparisonOperator implements IComparisonOperator {

	@Override
	public boolean satisfied(final String value1, final String value2) {
		return value1.equals(value2);
	}

}
