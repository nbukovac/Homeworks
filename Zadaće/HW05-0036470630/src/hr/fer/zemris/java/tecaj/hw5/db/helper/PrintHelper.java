package hr.fer.zemris.java.tecaj.hw5.db.helper;

import hr.fer.zemris.java.tecaj.hw5.collections.FormattedArrayList;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Helper class used to print out {@link StudentRecord}s matching the used
 * defined query.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class PrintHelper {

	/**
	 * Prints the equal signs for the frame line with the specified arguments
	 * 
	 * @param limit
	 *            number of equal sings
	 * @param sb
	 *            {@link StringBuilder}
	 * @return updated {@link StringBuilder}
	 */
	private static StringBuilder printEquals(final int limit, final StringBuilder sb) {
		sb.append("+");

		for (int i = 0; i < limit; i++) {
			sb.append("=");
		}

		return sb;
	}

	/**
	 * Prints the top or bottom frame line with appropriate sizes for each
	 * field.
	 * 
	 * @param jmbagSize
	 *            size of jmbag field
	 * @param lastNameSize
	 *            size of last name field
	 * @param firstNameSize
	 *            size of first name field
	 * @param gradeSize
	 *            size of grade field
	 */
	private static void printFrameLine(final int jmbagSize, final int lastNameSize, final int firstNameSize,
			final int gradeSize) {
		StringBuilder sb = new StringBuilder();

		sb = printEquals(jmbagSize, sb);
		sb = printEquals(lastNameSize, sb);
		sb = printEquals(firstNameSize, sb);
		sb = printEquals(gradeSize, sb);

		sb.append("+");

		System.out.println(sb.toString());
	}

	/**
	 * Prints all found {@link StudentRecord}s in a formatted matter.
	 * 
	 * @param students
	 *            queried {@link StudentRecord}s
	 */
	public static void printStudents(final FormattedArrayList students) {
		final int lastNameSize = students.getLastNameSize();
		final int firstNameSize = students.getFirstNameSize();

		if (students.size() > 0) {
			printFrameLine(FormattedArrayList.jmbagSize, lastNameSize + 2, firstNameSize + 2,
					FormattedArrayList.gradeSize);
			final StringBuilder sb = new StringBuilder();
			for (final StudentRecord record : students) {
				final String lastNameFormat = "%-" + lastNameSize + "s";
				final String firstNameFormat = "%-" + firstNameSize + "s";

				sb.append("| ").append(record.getJMBAG()).append(" | ")
						.append(String.format(lastNameFormat, record.getLastName())).append(" | ")
						.append(String.format(firstNameFormat, record.getFirstName())).append(" | ")
						.append(record.getFinalGrade()).append(" |").append("\n");
			}
			System.out.print(sb.toString());
			printFrameLine(FormattedArrayList.jmbagSize, lastNameSize + 2, firstNameSize + 2,
					FormattedArrayList.gradeSize);
		}

		System.out.println("Records selected: " + students.size());
		System.out.println();

	}
}
