package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.EndNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Parses a provided document using {@link Lexer} rules and returns the parsed
 * documents structure defined as a tree.
 * 
 * @see Lexer
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SmartScriptParser {

	/**
	 * Number of arguments in ForLoopNode
	 */
	private final static int FOR = 4;

	/**
	 * Enclosing {@link Node}, contains all other document {@link Node}s
	 */
	private final DocumentNode documentNode;

	/**
	 * Stack used for generating the document structure
	 */
	private final ObjectStack stack;

	/**
	 * Lexer that defines parsing rules
	 */
	private final Lexer lexer;

	/**
	 * Tokens found in a tag
	 */
	private final ArrayIndexedCollection tokens;

	/**
	 * Constructs a new {@link SmartScriptParser} that parses the provided
	 * document
	 * 
	 * @param docBody
	 *            document for parsing
	 */
	public SmartScriptParser(final String docBody) {
		lexer = new Lexer(docBody);
		documentNode = new DocumentNode();
		stack = new ObjectStack();
		tokens = new ArrayIndexedCollection();

		stack.push(documentNode);

		startParsing();
	}

	/**
	 * Returns a new {@link EchoNode}. Two types of {@link EchoNode}s can be
	 * constructed, first is when a tag starts with a '=' character, second is
	 * when a tag starts with a valid variable name.
	 * 
	 * @return new {@link EchoNode} with {@link Element}s enclosed in tag
	 */
	private Node constructEchoNode() {
		final int size = tokens.size();
		final Token firstToken = (Token) tokens.get(0);
		int firstArgument = 0;

		if (firstToken.getType() == TokenType.ECHO) {
			firstArgument = 1;
		}

		final Element[] elements = new Element[size - firstArgument];

		for (int i = firstArgument, count = 0; i < size; i++, count++) {
			elements[count] = getEchoElement(i);
		}

		return new EchoNode(elements);
	}

	/**
	 * Returns a new {@link ForLoopNode}. Two types of {@link ForLoopNode}s can
	 * be constructed, first is a for loop with four arguments, second is a for
	 * loop with three arguments.
	 * 
	 * @return new {@link ForLoopNode} with {@link Element}s enclosed in tag
	 */
	private Node constructForLoopNode() {
		final int size = tokens.size();

		if (size - 1 != FOR && size - 1 != FOR - 1) {
			throw new SmartScriptParserException("Invalid number of arguments in for loop tag");
		}

		final Element[] arguments = new Element[FOR];

		for (int i = 1; i < size; i++) {
			arguments[i - 1] = getForLoopElement(i);
		}

		if (!(arguments[0] instanceof ElementVariable)) {
			throw new SmartScriptParserException("First argument in for loop has to be a variable");
		}

		return new ForLoopNode((ElementVariable) arguments[0], arguments[1], arguments[2], arguments[3]);
	}

	/**
	 * Constructs a new {@link Node} based on the tokens in the
	 * <code>tokens</code> collection.
	 * 
	 * @return new {@link Node}
	 */
	private Node constructNode() {
		Node node = null;

		if (tokens.size() < 1) {
			throw new SmartScriptParserException("Unable to recreate tag");
		}

		final Token token = (Token) tokens.get(0);
		final TokenType tagType = token.getType();

		if (tagType == TokenType.ECHO || tagType == TokenType.VARIABLE) {
			node = constructEchoNode();
		} else if (tagType == TokenType.FOR) {
			node = constructForLoopNode();
		} else if (tagType == TokenType.END) {
			if (tokens.size() > 1) {
				throw new SmartScriptParserException("Invalid end tag");
			}

			node = new EndNode();
		}

		tokens.clear();

		return node;
	}

	/**
	 * Returns the constructed {@link DocumentNode} representing the documents
	 * tree structure
	 * 
	 * @return structured {@link DocumentNode}
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Returns a {@link Element} depending on the next token. If a invalid
	 * {@link TokenType} is in tag a {@link SmartScriptParserException} is
	 * thrown.
	 * 
	 * @param index
	 *            next {@link Token} to check
	 * @return {@link Element} according to next {@link Token}
	 */
	private Element getEchoElement(final int index) {
		final Token token = (Token) tokens.get(index);
		final String tokenValue = token.getValue().toString();

		switch (token.getType()) {
		case OPERATOR:
			return new ElementOperator(tokenValue);
		case STRING:
			return new ElementString(tokenValue);
		case VARIABLE:
			return new ElementVariable(tokenValue);
		case CONST_INT:
			return new ElementConstantInteger(Integer.parseInt(tokenValue));
		case CONST_DOUBLE:
			return new ElementConstantDouble(Double.parseDouble(tokenValue));
		case FUNCTION:
			return new ElementFunction(tokenValue);
		default:
			throw new SmartScriptParserException("Invalid token inside a echo tag");
		}
	}

	/**
	 * Returns a {@link Element} depending on the next token. If a invalid
	 * {@link TokenType} is in tag a {@link SmartScriptParserException} is
	 * thrown.
	 * 
	 * @param index
	 *            next {@link Token} to check
	 * @return {@link Element} according to next {@link Token}
	 */
	private Element getForLoopElement(final int index) {
		final Token token = (Token) tokens.get(index);
		final String tokenValue = token.getValue().toString();

		switch (token.getType()) {
		case STRING:
			return new ElementString(tokenValue);
		case VARIABLE:
			return new ElementVariable(tokenValue);
		case CONST_INT:
			return new ElementConstantInteger(Integer.parseInt(tokenValue));
		case CONST_DOUBLE:
			return new ElementConstantDouble(Double.parseDouble(tokenValue));
		default:
			throw new SmartScriptParserException("Invalid token inside a for loop tag");
		}
	}

	/**
	 * Returns the next {@link Node} found during the process of {@link Token}
	 * creation
	 * 
	 * @return new {@link Node}
	 */
	private Node nextNode() {
		Token token = lexer.nextToken();

		if (token == null) {
			throw new LexerException("End of file reached");
		}

		if (token.getType() == TokenType.TEXT) {
			return new TextNode(token.getValue().toString());
		} else {
			while (token.getType() != TokenType.END_TAG) {
				if (token.getType() != TokenType.START_TAG) {
					tokens.add(token);
				}

				token = lexer.nextToken();
			}
		}

		return constructNode();
	}

	/**
	 * Starts the parsing process and upon completion if the process was
	 * uninterrupted document structure will be constructed otherwise an
	 * {@link SmartScriptParserException} will be thrown during the parsing
	 * process. The exception is thrown if previously a {@link LexerException}
	 * or a {@link EmptyStackException} have occurred.
	 * 
	 * @see LexerException
	 * @see EmptyStackException
	 */
	private void startParsing() {
		LexerState state = LexerState.START;
		Node nextNode = null;

		// While EOF state isn't reached, a new Node is requested and added to
		// the tree structure.
		while (state != LexerState.EOF) {
			try {
				nextNode = nextNode();
			} catch (final LexerException e1) {
				throw new SmartScriptParserException(e1.getMessage());
			}

			try {
				if (nextNode instanceof EndNode) {
					stack.pop();
				} else {
					final Node topNode = (Node) stack.peek();
					topNode.addChildNode(nextNode);

					if (nextNode instanceof ForLoopNode) {
						stack.push(nextNode);
					}
				}
			} catch (final EmptyStackException e) {
				throw new SmartScriptParserException("This document has too many end tags");
			}

			state = lexer.getState();
		}

		if (stack.size() > 1) {
			throw new SmartScriptParserException("Too few end tags");
		}
	}

}
