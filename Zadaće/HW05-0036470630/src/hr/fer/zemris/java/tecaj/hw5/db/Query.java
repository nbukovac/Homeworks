package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.db.comparison.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.getter.IFieldValueGetter;

/**
 * Query performed on {@link StudentDatabase} consisting of query type,
 * comparison operator and value to compare to. It is possible to have multiple
 * queries performed at once.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Query {

	/**
	 * Type of query that is performed
	 */
	private final IFieldValueGetter queryType;

	/**
	 * Value of the query that is performed
	 */
	private final String value;

	/**
	 * Type of comparison that is performed
	 */
	private final IComparisonOperator comparisonOperator;

	/**
	 * Queries that are going to be performed
	 */
	private List<Query> queries;

	/**
	 * Constructs a new {@link Query} that will enclose all queries performed
	 */
	public Query() {
		this(null, null, null);
		queries = new ArrayList<>();
	}

	/**
	 * Constructs a new {@link Query} with the provided arguments;
	 * 
	 * @param type
	 *            type of query
	 * @param value
	 *            query value
	 * @param operator
	 *            type of comparison
	 */
	public Query(final IFieldValueGetter type, final String value, final IComparisonOperator operator) {
		this.queryType = type;
		this.value = value;
		comparisonOperator = operator;
		queries = null;
	}

	/**
	 * Adds a new {@link Query} to the enclosing {@link Query}. An
	 * {@link IllegalArgumentException} is thrown if the provided argument is
	 * null.
	 * 
	 * @param query
	 *            new {@link Query}
	 * @throws IllegalArgumentException
	 *             if the provided argument is null
	 */
	public void addQuery(final Query query) {
		if (query == null) {
			throw new IllegalArgumentException("Queries can't be null references");
		}

		queries.add(query);
	}

	/**
	 * Returns the {@link IComparisonOperator}
	 * 
	 * @return the comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Returns the queries
	 * 
	 * @return the queries
	 */
	public List<Query> getQueries() {
		return queries;
	}

	/**
	 * Returns the type of query
	 * 
	 * @return the queryType
	 */
	public IFieldValueGetter getQueryType() {
		return queryType;
	}

	/**
	 * Returns the query value
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
