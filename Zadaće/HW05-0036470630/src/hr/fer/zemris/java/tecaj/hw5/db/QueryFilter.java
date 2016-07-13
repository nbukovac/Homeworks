package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.parser.QueryParser;

/**
 * Class that implements {@link IFilter} and therefore checks if a
 * {@link StudentRecord} passes all the queries performed on it. It is possible
 * to have multiple queries performed on a single {@link StudentRecord}.
 * 
 * @author Nikola Bukovac
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * Queries that are going to be performed on a {@link StudentRecord}
	 */
	protected final Query queries;

	/**
	 * Constructs a new {@link QueryFilter} and parses the provided
	 * <code>query</code> to get the {@link Query} defined by the user. An
	 * {@link IllegalArgumentException} is thrown if <code>query</code> is null.
	 * 
	 * @param query
	 *            user defined query
	 * @throws IllegalArgumentException
	 *             if <code>query</code> is null
	 */
	public QueryFilter(final String query) {
		if (query == null) {
			throw new IllegalArgumentException("Query can't be null");
		}

		final QueryParser parser = new QueryParser(query);
		queries = parser.getQueries();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean accepts(final StudentRecord record) {
		boolean accept = true;

		for (final Query query : queries.getQueries()) {
			final boolean queryAccept = !query.getComparisonOperator().satisfied(query.getQueryType().get(record),
					query.getValue());
			if (queryAccept) {
				accept = false;
				break;
			}
		}

		return accept;
	}

}
