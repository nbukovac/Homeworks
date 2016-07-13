package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Class that represents student information for a certain course.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class StudentRecord {

	/**
	 * Unique identifier
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
	 * Points achieved at mid term
	 */
	private final double pointsMI;

	/**
	 * points achieved at final exam
	 */
	private final double pointsZI;

	/**
	 * Points achieved at laboratory
	 */
	private final double pointsLabs;

	/**
	 * Students grade
	 */
	private final int grade;

	/**
	 * Constructs a new {@link StudentRecord} with the provided values.
	 * 
	 * @param jMBAG
	 *            identifier
	 * @param lastName
	 *            last name
	 * @param firstName
	 *            first name
	 * @param pointsMI
	 *            points achieved at mid term
	 * @param pointsZI
	 *            points achieved at final exam
	 * @param pointsLabs
	 *            points achieved at laboratory
	 * @param grade
	 *            final grade
	 */
	public StudentRecord(final String jMBAG, final String lastName, final String firstName, final double pointsMI,
			final double pointsZI, final double pointsLabs, final int grade) {
		super();
		JMBAG = jMBAG;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pointsMI = pointsMI;
		this.pointsZI = pointsZI;
		this.pointsLabs = pointsLabs;
		this.grade = grade;
	}

	/**
	 * Returns the students first name
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the students grade
	 * 
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Returns the students JMBAG
	 * 
	 * @return the jMBAG
	 */
	public String getJMBAG() {
		return JMBAG;
	}

	/**
	 * Returns the students last name
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the students points achieved at laboratory
	 * 
	 * @return the pointsLabs
	 */
	public double getPointsLabs() {
		return pointsLabs;
	}

	/**
	 * Returns the students points achieved at mid term
	 * 
	 * @return the pointsMI
	 */
	public double getPointsMI() {
		return pointsMI;
	}

	/**
	 * Returns the students points achieved at final exam
	 * 
	 * @return the pointsZI
	 */
	public double getPointsZI() {
		return pointsZI;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return JMBAG;
	}

}
