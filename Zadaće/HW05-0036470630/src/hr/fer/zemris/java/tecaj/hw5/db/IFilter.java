package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Performs filtering of {@link StudentRecord}s defined by {@link Query} class.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IFilter {

	/**
	 * Checks if the provided {@link StudentRecord} passes all the queries
	 * performed on it. If it does then <code>true</code> is returned because
	 * the record is accepted, else is return <code>false</code>.
	 * 
	 * @param record
	 *            {@link StudentRecord} to check
	 * @return true if {@link StudentRecord} is accepted, else false
	 */
	public boolean accepts(StudentRecord record);
}
