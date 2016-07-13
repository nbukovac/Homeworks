package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.FormattedArrayList;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.EqualComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.GreaterComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.GreaterEqualComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.LessComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.LessEqualComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.LikeComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.comparison.NotEqualComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.getter.FirstNameValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getter.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getter.JMBAGValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.getter.LastNameValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.parser.QueryParser;
import hr.fer.zemris.java.tecaj.hw5.db.parser.QueryParserException;

@SuppressWarnings("javadoc")
public class DatabaseTests {

	StudentDatabase database;

	public void fillDatabase() {
		try {
			final List<String> lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
			database = new StudentDatabase(lines);
		} catch (final IOException e) {
			System.err.println("Database.txt not found");
		}
	}

	@Test
	public void testComparisonOperators() {
		final String string1 = "Burek";
		final String string2 = "Krafna";
		final String string3 = "Burek";
		final String regex = "Bu*";

		IComparisonOperator operator = new EqualComparisonOperator();
		assertEquals(true, operator.satisfied(string1, string3));
		assertEquals(false, operator.satisfied(string1, string2));

		operator = new NotEqualComparisonOperator();
		assertEquals(true, operator.satisfied(string1, string2));
		assertEquals(false, operator.satisfied(string1, string3));

		operator = new GreaterComparisonOperator();
		assertEquals(false, operator.satisfied(string1, string3));
		assertEquals(false, operator.satisfied(string1, string2));
		assertEquals(true, operator.satisfied(string2, string1));

		operator = new GreaterEqualComparisonOperator();
		assertEquals(true, operator.satisfied(string1, string3));
		assertEquals(false, operator.satisfied(string1, string2));
		assertEquals(true, operator.satisfied(string2, string1));

		operator = new LessEqualComparisonOperator();
		assertEquals(true, operator.satisfied(string1, string3));
		assertEquals(true, operator.satisfied(string1, string2));
		assertEquals(false, operator.satisfied(string2, string1));

		operator = new LessComparisonOperator();
		assertEquals(false, operator.satisfied(string1, string3));
		assertEquals(true, operator.satisfied(string1, string2));
		assertEquals(false, operator.satisfied(string2, string1));

		operator = new LikeComparisonOperator();
		assertEquals(true, operator.satisfied(string1, string3));
		assertEquals(false, operator.satisfied(string1, string2));
		assertEquals(false, operator.satisfied(string2, string1));
		assertEquals(true, operator.satisfied(string1, regex));
	}

	@Test
	public void testDatabaseFilterLike() {
		fillDatabase();

		final QueryFilter filter = new QueryFilter("lastName    like  \"Šimunov*\"");

		final StudentRecord[] records = new StudentRecord[] { new StudentRecord("0000000055", null, null, 0),
				new StudentRecord("0000000056", null, null, 0) };

		final List<StudentRecord> queryRecords = database.filter(filter);

		assertEquals(true, queryRecords.get(0).equals(records[0]));
		assertEquals(true, queryRecords.get(1).equals(records[1]));

		database = null;
	}

	@Test
	public void testDatabaseFilterLikeAndLess() {
		fillDatabase();

		final QueryFilter filter = new QueryFilter("lastName    like  \"Ši*\" and firstName<\"Iz\"");

		final StudentRecord[] records = new StudentRecord[] { new StudentRecord("0000000055", null, null, 0),
				new StudentRecord("0000000057", null, null, 0) };

		final List<StudentRecord> queryRecords = database.filter(filter);

		assertEquals(true, queryRecords.get(0).equals(records[0]));
		assertEquals(true, queryRecords.get(1).equals(records[1]));

		database = null;
	}

	@Test
	public void testDatabaseForJMBAG() {
		fillDatabase();

		final StudentRecord record = database.forJMBAG("0000000005");
		final StudentRecord record2 = new StudentRecord("0000000005", null, null, 0);

		assertEquals(true, record.equals(record2));

		database = null;
	}

	@Test
	public void testFieldGetters() {
		final StudentRecord record = new StudentRecord("00", "Bukovac", "Nikola", 2);

		IFieldValueGetter getter = new LastNameValueGetter();
		assertEquals(true, getter.get(record).equals("Bukovac"));

		getter = new FirstNameValueGetter();
		assertEquals(true, getter.get(record).equals("Nikola"));

		getter = new JMBAGValueGetter();
		assertEquals(true, getter.get(record).equals("00"));

	}

	@Test
	public void testFormattedArrayList() {
		fillDatabase();

		final QueryFilter filter = new QueryFilter("lastName    like  \"Ši*\"");

		final List<StudentRecord> queryRecords = database.filter(filter);

		final FormattedArrayList formattedArrayList = new FormattedArrayList();
		formattedArrayList.addAll(queryRecords);

		// biggest is Šimunović
		assertEquals(9, formattedArrayList.getLastNameSize());

		// bigest is Hrvoje
		assertEquals(6, formattedArrayList.getFirstNameSize());

		formattedArrayList.clear();

		// both must be 0
		assertEquals(0, formattedArrayList.getLastNameSize());
		assertEquals(0, formattedArrayList.getFirstNameSize());

		database = null;
	}

	@Test(expected = QueryParserException.class)
	public void testIndexQueryFilterWithIllegalAttribute() {
		new IndexQueryFilter("lastName=\"B\"");
	}

	@Test(expected = QueryParserException.class)
	public void testIndexQueryFilterWithIllegalComparison() {
		new IndexQueryFilter("jmbag>\"B\"");
	}

	@Test(expected = QueryParserException.class)
	public void testIndexQueryFilterWithTooManyAttributes() {
		new IndexQueryFilter("jmbag=\"B\" and jmbag =\"00\"");
	}

	@Test
	public void testIndexQueryGetJMBAG() {
		final IndexQueryFilter filter = new IndexQueryFilter("jmbag=\"0000000007\"");

		assertEquals(true, filter.getJmbag().equals("0000000007"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testQueryFilterWithNull() {
		new QueryFilter(null);
	}

	@Test
	public void testQueryParser1() {
		final QueryParser parser = new QueryParser("lastName      >\"Bu\"");

		final Query query1 = parser.getQueries();

		assertEquals(1, query1.getQueries().size());

		final Query inQuery1 = query1.getQueries().get(0);

		assertEquals(true, inQuery1.getQueryType() instanceof LastNameValueGetter);
		assertEquals(true, inQuery1.getValue().equals("Bu"));
		assertEquals(true, inQuery1.getComparisonOperator() instanceof GreaterComparisonOperator);

	}

	@Test
	public void testQueryParser2() {
		// test with redundant whitespace
		final QueryParser parser = new QueryParser("firstName  <=       \"Bu\"            ");

		final Query query1 = parser.getQueries();

		assertEquals(1, query1.getQueries().size());

		final Query inQuery1 = query1.getQueries().get(0);

		assertEquals(true, inQuery1.getQueryType() instanceof FirstNameValueGetter);
		assertEquals(true, inQuery1.getValue().equals("Bu"));
		assertEquals(true, inQuery1.getComparisonOperator() instanceof LessEqualComparisonOperator);

	}

	@Test
	public void testQueryParser3() {
		// test with redundant whitespace
		final QueryParser parser = new QueryParser("       jmbag  like       \"Bu\"            ");

		final Query query1 = parser.getQueries();

		assertEquals(1, query1.getQueries().size());

		final Query inQuery1 = query1.getQueries().get(0);

		assertEquals(true, inQuery1.getQueryType() instanceof JMBAGValueGetter);
		assertEquals(true, inQuery1.getValue().equals("Bu"));
		assertEquals(true, inQuery1.getComparisonOperator() instanceof LikeComparisonOperator);

	}

	@Test
	public void testQueryParserComplex() {
		final QueryParser parser = new QueryParser(
				"       jmbag  like       \"Bu\"         and firstName <=   \"Kr\"  ");

		final Query query1 = parser.getQueries();

		assertEquals(2, query1.getQueries().size());

		final Query inQuery1 = query1.getQueries().get(0);

		assertEquals(true, inQuery1.getQueryType() instanceof JMBAGValueGetter);
		assertEquals(true, inQuery1.getValue().equals("Bu"));
		assertEquals(true, inQuery1.getComparisonOperator() instanceof LikeComparisonOperator);

		final Query inQuery2 = query1.getQueries().get(1);

		assertEquals(true, inQuery2.getQueryType() instanceof FirstNameValueGetter);
		assertEquals(true, inQuery2.getValue().equals("Kr"));
		assertEquals(true, inQuery2.getComparisonOperator() instanceof LessEqualComparisonOperator);

	}

	@Test
	public void testQueryParserComplex2() {
		final QueryParser parser = new QueryParser(
				"       jmbag  like       \"Bu\"         and jmbag =   \"Kristina\"  ");

		final Query query = parser.getQueries();

		assertEquals(2, query.getQueries().size());

		final Query inQuery1 = query.getQueries().get(0);

		assertEquals(true, inQuery1.getQueryType() instanceof JMBAGValueGetter);
		assertEquals(true, inQuery1.getValue().equals("Bu"));
		assertEquals(true, inQuery1.getComparisonOperator() instanceof LikeComparisonOperator);

		final Query inQuery2 = query.getQueries().get(1);

		assertEquals(true, inQuery2.getQueryType() instanceof JMBAGValueGetter);
		assertEquals(true, inQuery2.getValue().equals("Kristina"));
		assertEquals(true, inQuery2.getComparisonOperator() instanceof EqualComparisonOperator);

	}

	@Test
	public void testQueryParserComplex3() {
		final QueryParser parser = new QueryParser(
				"  lastName           !=  \"Burek\"     and       firstName =   \"Krna\"   and jmbag>=\"0002\"  ");

		final Query query = parser.getQueries();

		assertEquals(3, query.getQueries().size());

		final Query inQuery1 = query.getQueries().get(0);

		assertEquals(true, inQuery1.getQueryType() instanceof LastNameValueGetter);
		assertEquals(true, inQuery1.getValue().equals("Burek"));
		assertEquals(true, inQuery1.getComparisonOperator() instanceof NotEqualComparisonOperator);

		final Query inQuery2 = query.getQueries().get(1);

		assertEquals(true, inQuery2.getQueryType() instanceof FirstNameValueGetter);
		assertEquals(true, inQuery2.getValue().equals("Krna"));
		assertEquals(true, inQuery2.getComparisonOperator() instanceof EqualComparisonOperator);

		final Query inQuery3 = query.getQueries().get(2);

		assertEquals(true, inQuery3.getQueryType() instanceof JMBAGValueGetter);
		assertEquals(true, inQuery3.getValue().equals("0002"));
		assertEquals(true, inQuery3.getComparisonOperator() instanceof GreaterEqualComparisonOperator);

	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserDoubleAnd() {
		new QueryParser("firstName<\"Ni\" and and  firstName<\"Ni\"");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserFirstAnd() {
		new QueryParser("and firstName<\"Ni\" ");
	}

	@Test
	public void testQueryParserForAndSplit() {
		final QueryParser parser = new QueryParser("lastName=\"Andrea\" and firstName= \"\"");

		final Query query = parser.getQueries();

		assertEquals(2, query.getQueries().size());

		final Query inQuery1 = query.getQueries().get(0);

		assertEquals(true, inQuery1.getQueryType() instanceof LastNameValueGetter);
		assertEquals(true, inQuery1.getValue().equals("Andrea"));
		assertEquals(true, inQuery1.getComparisonOperator() instanceof EqualComparisonOperator);

		final Query inQuery2 = query.getQueries().get(1);

		assertEquals(true, inQuery2.getQueryType() instanceof FirstNameValueGetter);
		assertEquals(true, inQuery2.getValue().equals(""));
		assertEquals(true, inQuery2.getComparisonOperator() instanceof EqualComparisonOperator);
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserIllegalAttribute() {
		new QueryParser("  lastname   !=  \"Burek\"     and       ime =   \"Krna\"   and jmbag>=\"0002\"  ");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserIllegalComparisonOperator() {
		new QueryParser("  lastname ==  \"Burek\"     and       firstName =   \"Krna\"   and jmbag>=\"0002\"  ");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserIllegalFirstName() {
		new QueryParser("  lastName       !=  \"Burek\"     and       fiRstName =   \"Krna\"   and jmbag>=\"0002\"  ");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserIllegalJMBAG() {
		new QueryParser("  lastName      !=  \"Burek\"     and    firstName =   \"Krna\"   and jmbAg>=\"0002\"  ");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserIllegalLastName() {
		new QueryParser(
				"  lastname           !=  \"Burek\"     and       firstName =   \"Krna\"   and jmbag>=\"0002\"  ");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserIllegalLikeComparison() {
		new QueryParser("  lastname lik  \"Burek\"     and       firstName =   \"Krna\"   and jmbag>=\"0002\"  ");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserIllegalNotEqual() {
		new QueryParser("  lastname !!  \"Burek\"     and       firstName =   \"Krna\"   and jmbag>=\"0002\"  ");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserNoQueryAfterAnd() {
		new QueryParser("firstName<\"Ni\" and");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserNull() {
		new QueryParser(null);
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserTooManyWildCards() {
		new QueryParser("  lastName  like  \"Bur*ek*\"   ");
	}

	@Test(expected = QueryParserException.class)
	public void testQueryParserWrongAnd() {
		new QueryParser("firstName<\"Ni\" akd f");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStudentDatabaseWithNull() {
		// throws
		database = new StudentDatabase(null);
	}

	@Test
	public void testStudentRecordHashCode() {
		final StudentRecord record1 = new StudentRecord("Burek", null, null, 0);
		final StudentRecord record2 = new StudentRecord("Burek", null, null, 0);

		assertEquals(true, record1.hashCode() == record2.hashCode());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testStudentRecordWithNull() {
		// throws
		new StudentRecord(null, null, "Nikola", 6);
	}

}
