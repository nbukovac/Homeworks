package hr.fer.zemris.java.tecaj.hw5.db.getter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class that implements {@link IFieldValueGetter} and returns the
 * <code>firstName</code> member variable from a {@link StudentRecord}
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class FirstNameValueGetter implements IFieldValueGetter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(final StudentRecord record) {
		return record.getFirstName();
	}

}
