package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.comparison.EqualComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.getter.JMBAGValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.parser.QueryParserException;

/**
 * Class derived from {@link QueryFilter} that allows only one type of query
 * performed on a database and that is <code>indexquery</code>, a command used
 * to make call an index and get specific information. Valid query is
 * "jmbag="x"" where x stands for a desired string.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class IndexQueryFilter extends QueryFilter {

	/**
	 * Constructs a new {@link IndexQueryFilter} with the specified query. An
	 * {@link QueryParserException} is thrown if the provided <code>query</code>
	 * specifies an illegal database query for this kind of query.
	 * 
	 * @param query
	 *            specified query
	 * @throws QueryParserException
	 *             if the provided <code>query</code> specifies an illegal
	 *             database query for this kind of query
	 */
	public IndexQueryFilter(final String query) {
		super(query);

		checkIfQueryValid();

	}

	/**
	 * Checks if the provided <code>query</code> is a valid query. An
	 * {@link QueryParserException} is thrown if it isn't.
	 * 
	 * @throws QueryParserException
	 *             if query isn't valid.
	 */
	private void checkIfQueryValid() {
		final List<Query> queryList = queries.getQueries();

		if (queryList.size() > 1 || !(queryList.get(0).getComparisonOperator() instanceof EqualComparisonOperator)
				|| !(queryList.get(0).getQueryType() instanceof JMBAGValueGetter)) {
			throw new QueryParserException("Invalid index query command");
		}
	}

	/**
	 * Returns the JMBAG value
	 * 
	 * @return JMBAG
	 */
	public String getJmbag() {
		return queries.getQueries().get(0).getValue();
	}

}
