package hr.fer.zemris.java.tecaj.hw5.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.FormattedArrayList;
import hr.fer.zemris.java.tecaj.hw5.db.helper.PrintHelper;
import hr.fer.zemris.java.tecaj.hw5.db.parser.QueryParserException;

/**
 * Program that emulates a database and database querying on a set of data. It
 * is possible to perform either a <code>indexquery</code> or <code>query</code>
 * . <code>indexquery</code> is done in O(1) average complexity and only
 * provides a <code>jmbag</code> fetch operation while <code>query</code> is
 * done in O(n) because the whole database has to be queried to determine all
 * values for which the query returns true. <code>indexquery</code> and
 * <code>query</code> are case sensitive as well as the attributes
 * <code>jmbag</code>, <code>firstName</code> and <code>lastName</code>. Logical
 * operator <code>and</code> is case insensitive.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class StudentDB {

	/**
	 * Creates a new string without the <code>indexquery</code> or
	 * <code>query</code> keywords
	 * 
	 * @param query
	 *            user inputed query
	 * @param size
	 *            starting point for substring method
	 * @return fixed query
	 */
	private static String fixQuery(final String query, final int size) {
		return query.substring(size).trim();
	}

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		StudentDatabase database = null;
		try {
			final List<String> lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
			database = new StudentDatabase(lines);
		} catch (final IOException e) {
			System.err.println("Database file doesn't exist");
			System.exit(-1);
		}

		String query = "";
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		final FormattedArrayList students = new FormattedArrayList();
		IFilter filter = null;

		while (true) {
			System.out.print("> ");
			try {
				query = reader.readLine().trim();
			} catch (final IOException e) {
				System.err.println("An error occurred while reading user input");
			}

			try {
				students.clear();

				if (query.startsWith("indexquery")) {
					final IndexQueryFilter indexFilter = new IndexQueryFilter(fixQuery(query, "indexquery".length()));
					students.add(database.forJMBAG(indexFilter.getJmbag()));

				} else if (query.startsWith("query")) {
					filter = new QueryFilter(fixQuery(query, "query".length()));
					students.addAll(database.filter(filter));

				} else if (!query.toLowerCase().equals("exit")) {
					System.err.println("Only indexquery and query are legal commands");

				} else {
					break;
				}
			} catch (final QueryParserException e) {
				System.err.println(e.getMessage());
			}

			PrintHelper.printStudents(students);
		}

		System.out.println("Have a nice day!");
	}

}
