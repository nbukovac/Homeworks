package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.Iterator;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

/**
 * Program used to demonstrate the usage of {@link SimpleHashtable}.
 *
 */
public class Demonstration {

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		// create collection:
		final SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);

		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		final Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes:
																			// 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes:
																			// 4
		System.out.println(examMarks.toString());

		System.out.println();
		System.out.println(examMarks.containsValue(5));
		System.out.println(examMarks.containsKey("Ante"));
		System.out.println(examMarks.containsKey("Štefica"));
		System.out.println();

		for (final SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		System.out.println();

		for (final SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (final SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
			System.out.println();
		}

		System.out.println();

		examMarks.remove("Ivana");

		System.out.println(examMarks.toString());

		final Iterator<TableEntry<String, Integer>> iterator = examMarks.iterator();

		while (iterator.hasNext()) {
			final SimpleHashtable.TableEntry<String, Integer> pair = iterator.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			// examMarks.put("Ante", 4);
			iterator.remove();
		}

		System.out.println(examMarks.toString());
	}

}
