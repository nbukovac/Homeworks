package hr.fer.zemris.java.tecaj.hw5.db.getter;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Returns a field from a {@link StudentRecord} that is specified in a concrete
 * class that implements this interface.
 * 
 * @see StudentRecord
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IFieldValueGetter {

	/**
	 * Returns the field of a {@link StudentRecord} specified by the class
	 * 
	 * @param record
	 *            student data
	 * @return field of a {@link StudentRecord} specified by the class
	 */
	public String get(StudentRecord record);
}
