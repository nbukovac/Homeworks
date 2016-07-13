package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Program that demonstrates the usage of the Stream API.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class StreamDemo {

	/**
	 * Converts the provided {@code lines} to a list of {@link StudentRecord}s.
	 * 
	 * @param lines
	 *            lines to convert
	 * @return list of {@link StudentRecord}s
	 */
	private static List<StudentRecord> convert(final List<String> lines) {
		final List<StudentRecord> records = new ArrayList<>();

		for (final String s : lines) {
			if (s.trim().isEmpty()) {
				continue;
			}

			final String[] split = s.split("\\s+");
			final StudentRecord record = new StudentRecord(split[0], split[1], split[2], Double.parseDouble(split[3]),
					Double.parseDouble(split[4]), Double.parseDouble(split[5]), Integer.parseInt(split[6]));
			records.add(record);
		}

		return records;
	}

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		List<StudentRecord> records = null;

		try {
			final List<String> lines = Files.readAllLines(Paths.get("studenti.txt"), StandardCharsets.UTF_8);
			records = convert(lines);
		} catch (final IOException e) {
			System.err.println("An error occurred while reading from file");
		}

		// 1. MI + ZI + LAB > 25
		final long numberOfStudents = records.stream()
				.filter(r -> r.getPointsLabs() + r.getPointsMI() + r.getPointsZI() > 25).count();
		System.out.println("Number of students that have more than 25 points : " + numberOfStudents);
		System.out.println();

		// 2. grade == 5

		final long numberOf5s = records.stream().filter(r -> r.getGrade() == 5).count();
		System.out.println("Number of students that have grade 5 : " + numberOf5s);
		System.out.println();

		// 3. grade == 5 to list
		final List<StudentRecord> studentsWith5 = records.stream().filter(r -> r.getGrade() == 5)
				.collect(Collectors.toList());
		System.out.println("Students with grade 5 " + studentsWith5);
		System.out.println();

		// 4. same as 3. but sorted

		final List<StudentRecord> sortedStudents = studentsWith5
				.stream().sorted((r1, r2) -> (r1.getPointsLabs() + r1.getPointsMI()
						+ r1.getPointsZI()) > (r2.getPointsLabs() + r2.getPointsMI() + r2.getPointsZI()) ? -1 : 1)
				.collect(Collectors.toList());
		System.out.println("Sorted students with grade 5 " + sortedStudents);
		System.out.println();

		// 5. failed JMBAGs

		final List<String> failedJMBAGs = records.stream().filter(r -> r.getGrade() == 1).map(r -> r.getJMBAG())
				.collect(Collectors.toList());
		System.out.println("JMBAGs of failed students" + failedJMBAGs);
		System.out.println();

		// 6. create a map<grades, list of students with that grade>

		final Map<Integer, List<StudentRecord>> gradesMap = records.stream()
				.collect(Collectors.groupingBy(r -> r.getGrade(), Collectors.mapping(r -> r, Collectors.toList())));
		printGradesMap(gradesMap);

		// 7. create a map<grades, number of students with that grade>

		final Map<Integer, Integer> gradesCountMap = records.stream()
				.collect(Collectors.toMap(r -> r.getGrade(), r -> new Integer(1), (t, u) -> t + 1));
		System.out.println(gradesCountMap);
		System.out.println();

		// 8. create a map<passed, list of students>

		final Map<Boolean, List<StudentRecord>> passed = records.stream()
				.collect(Collectors.partitioningBy(r -> r.getGrade() != 1));
		printPassedMap(passed);
	}

	/**
	 * Prints the {@link Map} containing grades and students.
	 * 
	 * @param gradesMap
	 *            {@link Map} containing grades and students
	 */
	private static void printGradesMap(final Map<Integer, List<StudentRecord>> gradesMap) {
		for (final Map.Entry<Integer, List<StudentRecord>> entry : gradesMap.entrySet()) {
			System.out.println(entry);
		}

		System.out.println();
	}

	/**
	 * Prints the {@link Map} containing passed flags and students.
	 * 
	 * @param passed
	 *            {@link Map} containing passed flags and students
	 */
	private static void printPassedMap(final Map<Boolean, List<StudentRecord>> passed) {
		for (final Map.Entry<Boolean, List<StudentRecord>> entry : passed.entrySet()) {
			System.out.println(entry);
		}

		System.out.println();
	}

}
