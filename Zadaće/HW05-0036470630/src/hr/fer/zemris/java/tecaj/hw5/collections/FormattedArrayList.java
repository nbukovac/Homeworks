package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class derived from {@link ArrayList} specific for storage of
 * {@link StudentRecord}s and printing them in specific matter.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class FormattedArrayList extends ArrayList<StudentRecord> {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 4015101510703723652L;

	/**
	 * Size of the printed jmbag database field
	 */
	public static final int jmbagSize = 12;

	/**
	 * Size of the printed grade database field
	 */
	public static final int gradeSize = 3;

	/**
	 * Size of the printed last name database field
	 */
	private int lastNameSize;

	/**
	 * Size of the printed first name database field
	 */
	private int firstNameSize;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(final StudentRecord record) {
		final boolean returnValue = super.add(record);
		final int newLastNameSize = record.getLastName().length();
		final int newFirstNameSize = record.getFirstName().length();

		if (newFirstNameSize > firstNameSize) {
			firstNameSize = newFirstNameSize;
		}

		if (newLastNameSize > lastNameSize) {
			lastNameSize = newLastNameSize;
		}

		return returnValue;
	}

	/**
	 * Adds all elements from a <code>List&lt;StudentRecord&gt;</code> to this
	 * collection with setting <code>lastNameSize</code> and
	 * <code>firstNameSize</code> to the maximal respective size.
	 * 
	 * @param students
	 *            list of students
	 * @return true if all elements are added successfully, else false
	 */
	public boolean addAll(final List<StudentRecord> students) {
		if (students == null) {
			throw new IllegalArgumentException("Argument can't be a null reference");
		}

		int added = 0;

		for (final StudentRecord record : students) {
			if (add(record)) {
				added++;
			}
		}

		return added == students.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		super.clear();

		firstNameSize = 0;
		lastNameSize = 0;
	}

	/**
	 * Returns the size of the largest name
	 * 
	 * @return the firstNameSize
	 */
	public int getFirstNameSize() {
		return firstNameSize;
	}

	/**
	 * Returns the size of the largest last name
	 * 
	 * @return the lastNameSize
	 */
	public int getLastNameSize() {
		return lastNameSize;
	}

}
