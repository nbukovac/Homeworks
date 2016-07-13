package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * Class that emulates a database containing information about students. It is
 * possible to filter student data with different {@link IFilter}s that specify
 * a way to filter student data.
 * 
 * @see IFilter
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class StudentDatabase {

	/**
	 * Storage of {@link StudentRecord}s implemented as a hash table
	 */
	private final SimpleHashtable<String, StudentRecord> hashTable;

	/**
	 * Storage of {@link StudentRecord}s implemented as a array list used for
	 * order preserving
	 */
	private final List<StudentRecord> arrayList;

	/**
	 * Constructs a new {@link StudentDatabase} and fills it with the provided
	 * data accordingly. An {@link IllegalArgumentException} is thrown if
	 * <code>list</code> is null
	 * 
	 * @param list
	 *            data for database
	 * @throws IllegalArgumentException
	 *             if list is null
	 */
	public StudentDatabase(final List<String> list) {
		if (list == null) {
			throw new IllegalArgumentException("Null reference is sent instead of a list of strings");
		}

		hashTable = new SimpleHashtable<>();
		arrayList = new ArrayList<>();

		fillHashTable(list);
	}

	/**
	 * Checks if student data is valid. Data is valid if <code>JMBAG</code>,
	 * <code>lastName</code> and <code>firstName</code> are not null and
	 * <code>finalGrade</code> has to be a parsable integer value between 1 and
	 * 5.
	 * 
	 * @param data
	 *            student data to check
	 * @return true if data is valid, else false
	 */
	private boolean checkIfDataValid(final String[] data) {
		boolean parsableInteger = true;
		int number = 0;

		try {
			number = Integer.parseInt(data[3]);
		} catch (final NumberFormatException e) {
			parsableInteger = false;
		}

		return parsableInteger && data[0] != null && data[1] != null && data[2] != null && number > 0 && number <= 5;
	}

	/**
	 * Fills the internal collection with valid student data.
	 * 
	 * @param list
	 *            data ready for parsing
	 */
	private void fillHashTable(final List<String> list) {
		for (final String s : list) {
			final String[] split = s.split("\t");

			if (split.length != 4 || !checkIfDataValid(split)) {
				continue;
			}

			final StudentRecord record = new StudentRecord(split[0], split[1], split[2], Integer.parseInt(split[3]));

			hashTable.put(split[0], record);
			arrayList.add(record);
		}
	}

	/**
	 * Filters the internal database collection using a specified
	 * {@link IFilter}.
	 * 
	 * @param filter
	 *            {@link IFilter} used to filter data
	 * @return filtered {@link StudentRecord}s
	 */
	public List<StudentRecord> filter(final IFilter filter) {
		final List<StudentRecord> records = new ArrayList<>();

		for (final StudentRecord record : arrayList) {

			if (filter.accepts(record)) {
				records.add(record);
			}
		}

		return records;
	}

	/**
	 * Returns the {@link StudentRecord} specified by the user provided
	 * <code>JMBAG</code> argument. Average complexity of this operation is
	 * O(1).
	 * 
	 * @param JMBAG
	 *            students key
	 * @return {@link StudentRecord} specified by the provided JMBAG
	 */
	public StudentRecord forJMBAG(final String JMBAG) {
		return hashTable.get(JMBAG);
	}

}
