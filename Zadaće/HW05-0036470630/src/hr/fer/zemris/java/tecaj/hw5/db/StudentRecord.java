package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Class that represents a student and his data in a database. Every student has
 * a unique distinguisher(JMBAG), first and last name, and a grade in a course.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class StudentRecord {

	/**
	 * Students unique identifier
	 */
	private final String JMBAG;

	/**
	 * Students last name
	 */
	private final String lastName;

	/**
	 * Students first name
	 */
	private final String firstName;

	/**
	 * Students final grade
	 */
	private final int finalGrade;

	/**
	 * Constructs a new {@link StudentRecord} with the provided student data. An
	 * {@link IllegalArgumentException} is thrown if <code>JMBAG</code> is null.
	 * 
	 * @param JMBAG
	 *            students unique identifier
	 * @param lastName
	 *            students last name
	 * @param firstName
	 *            students first name
	 * @param finalGrade
	 *            students final grade
	 * @throws IllegalArgumentException
	 *             if JMBAG is null
	 */
	public StudentRecord(final String JMBAG, final String lastName, final String firstName, final int finalGrade) {
		if (JMBAG == null) {
			throw new IllegalArgumentException("JMBAG can't be null");
		}

		this.JMBAG = JMBAG;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StudentRecord)) {
			return false;
		}
		final StudentRecord other = (StudentRecord) obj;
		if (JMBAG == null) {
			if (other.JMBAG != null) {
				return false;
			}
		} else if (!JMBAG.equals(other.JMBAG)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the <code>finalGrade</code> a student has achieved in a course
	 * 
	 * @return the finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Returns students first name
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns students unique identifier
	 * 
	 * @return the JMBAG
	 */
	public String getJMBAG() {
		return JMBAG;
	}

	/**
	 * Returns students last name
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((JMBAG == null) ? 0 : JMBAG.hashCode());
		return result;
	}

}
