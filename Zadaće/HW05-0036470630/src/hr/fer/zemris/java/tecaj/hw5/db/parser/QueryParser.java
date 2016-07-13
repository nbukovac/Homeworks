package hr.fer.zemris.java.tecaj.hw5.db.parser;

import hr.fer.zemris.java.tecaj.hw5.db.Query;
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
import hr.fer.zemris.java.tecaj.hw5.db.lexer.ComparisonType;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryLexer;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryLexerException;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryToken;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.TokenType;

/**
 * Class used for query parsing and creation of {@link Query} that is used to
 * collect all queries that the user wants to do.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class QueryParser {

	/**
	 * {@link QueryLexer} used for {@link QueryToken} creation
	 */
	private final QueryLexer lexer;

	/**
	 * Containing {@link Query} object for all queries
	 */
	private final Query queries;

	/**
	 * Constructs a new {@link QueryParser} that immediately starts parsing the
	 * provided <code>query</code>. An {@link IllegalArgumentException} is
	 * thrown if <code>query</code> is null.
	 * 
	 * @param query
	 *            user defined query
	 * @throws IllegalArgumentException
	 *             if <code>query</code> is null
	 */
	public QueryParser(final String query) {
		if (query == null) {
			throw new QueryParserException("This query cannot be parsed");
		}

		lexer = new QueryLexer(query.trim());
		queries = new Query();

		startParsing();
	}

	/**
	 * Adds a new {@link Query} to the containing <code>queries</code>.
	 * 
	 * @param token
	 *            {@link QueryToken} gained from {@link QueryLexer}
	 * @param type
	 *            type of query
	 * @param operator
	 *            {@link IComparisonOperator}
	 */
	private void addNewQuery(final QueryToken token, final IFieldValueGetter type, final IComparisonOperator operator) {
		queries.addQuery(new Query(type, token.getValue().toString(), operator));
	}

	/**
	 * Determines what {@link IComparisonOperator} is defined by the parsed
	 * {@link ComparisonType}.
	 * 
	 * @param type
	 *            type of operator
	 * @return proper {@link IComparisonOperator}
	 */
	private IComparisonOperator determineOperator(final ComparisonType type) {
		IComparisonOperator operator = null;

		switch (type) {
		case EQUAL:
			operator = new EqualComparisonOperator();
			break;
		case GREATER:
			operator = new GreaterComparisonOperator();
			break;
		case GREATER_EQUAL:
			operator = new GreaterEqualComparisonOperator();
			break;
		case LESS:
			operator = new LessComparisonOperator();
			break;
		case LESS_EQUAL:
			operator = new LessEqualComparisonOperator();
			break;
		case LIKE:
			operator = new LikeComparisonOperator();
			break;
		case NOT_EQUAL:
			operator = new NotEqualComparisonOperator();
			break;
		default:
			break;

		}

		return operator;
	}

	/**
	 * Returns the parsed {@link Query} with all of the queries.
	 * 
	 * @return the queries
	 */
	public Query getQueries() {
		return queries;
	}

	/**
	 * Parses a user defined query and creates the {@link Query} with the parsed
	 * queries. An {@link QueryParserException} is thrown if no <code>and</code>
	 * is provided between two queries or a {@link QueryLexerException} was
	 * thrown.
	 * 
	 * @see QueryLexerException
	 * 
	 * @throws QueryParserException
	 *             if no <code>and</code> is provided between two queries or a
	 *             {@link QueryLexerException} was thrown.
	 */
	private void startParsing() {
		QueryToken token = null;

		try {
			token = lexer.nextToken();
		} catch (final QueryLexerException e) {
			throw new QueryParserException(e.getMessage());
		}

		boolean needAnd = false;

		while (token.getType() != TokenType.END) {
			if (needAnd) {
				if (token.getType() != TokenType.AND) {
					throw new QueryParserException("And has to be between two query commands");

				}

				needAnd = false;
			} else {
				if (token.getType() == TokenType.AND) {
					throw new QueryParserException("And operator in wrong place");
				}

				final IComparisonOperator operator = determineOperator(token.getComparisonType());

				switch (token.getType()) {
				case FIRST_NAME:
					addNewQuery(token, new FirstNameValueGetter(), operator);
					break;
				case JMBAG:
					addNewQuery(token, new JMBAGValueGetter(), operator);
					break;
				case LAST_NAME:
					addNewQuery(token, new LastNameValueGetter(), operator);
					break;
				default:
					break;
				}

				needAnd = true;
			}

			try {
				token = lexer.nextToken();
			} catch (final QueryLexerException e) {
				throw new QueryParserException(e.getMessage());
			}
		}
	}

}
