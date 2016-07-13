package hr.fer.zemris.java.tecaj.hw5.db.comparison;

import java.text.Collator;
import java.util.Locale;

/**
 * Class that implements {@link IComparisonOperator} and performs greater than
 * or equal, lexicographic comparison between two <code>Strings</code>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class GreaterEqualComparisonOperator implements IComparisonOperator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean satisfied(final String value1, final String value2) {
		final Collator collator = Collator.getInstance(new Locale("hr", "HR"));
		return collator.compare(value1, value2) >= 0;
	}

}
