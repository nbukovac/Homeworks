package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BinaryOperator;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that interprets the script code parsed from a document by the
 * {@link SmartScriptParser} and passes the execution result to the provided
 * {@link RequestContext} object. Interpretation is done with the locally
 * defined {@link INodeVisitor}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SmartScriptEngine {

	/**
	 * Constructed {@link DocumentNode} from a file
	 */
	private final DocumentNode documentNode;

	/**
	 * {@link RequestContext} used for header and content creation and writing
	 * to the output stream
	 */
	private final RequestContext requestContext;

	/**
	 * {@link ObjectMultistack} with all found variables that are used in the
	 * script
	 */
	private final ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * {@link INodeVisitor} used for traversing {@link Node}s and processing
	 * them as script commands
	 */
	private final INodeVisitor visitor = new INodeVisitor() {

		/**
		 * {@inheritDoc} <br>
		 * Calls every child {@link Node}s {@code accept} method to activate
		 * this {@link INodeVisitor}s processing.
		 */
		@Override
		public void visitDocumentNode(final DocumentNode node) {
			for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
				node.getChild(i).accept(this);
			}
		}

		/**
		 * {@inheritDoc} <br>
		 * Processes all functions and operations and writes the result to the
		 * {@link RequestContext} output stream.
		 */
		@Override
		public void visitEchoNode(final EchoNode node) {
			final Stack<ValueWrapper> tempStack = new Stack<>();

			for (final Element element : node.getElements()) {
				if (element instanceof ElementVariable) {
					tempStack.push(new ValueWrapper(multistack.peek(element.asText()).getValue()));
				} else if (element instanceof ElementFunction) {
					Functions.getFunction(element.asText()).apply(tempStack, requestContext);

				} else if (element instanceof ElementOperator) {
					tempStack.peek().increment(0.0);
					final double second = (double) tempStack.pop().getValue();
					tempStack.peek().increment(0.0);
					final double first = (double) tempStack.pop().getValue();
					final String operator = element.asText();
					final BinaryOperator<Double> function = BinaryOperations.getOperator(operator);

					tempStack.push(new ValueWrapper(function.apply(first, second)));
				} else {
					tempStack.push(new ValueWrapper(
							element.asText().replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t")));
				}
			}

			int position = tempStack.size() - 1;
			final List<Object> output = new ArrayList<>();
			for (int i = 0; i < tempStack.size(); i++) {
				output.add(null);
			}

			while (tempStack.size() > 0) {
				output.set(position, tempStack.pop().getValue());
				position--;
			}

			for (final Object object : output) {
				try {
					requestContext.write(object.toString());
				} catch (final IOException e) {
					System.err.println("Writing of EchoNode's content to output stream failed.");
					System.exit(-1);
				}
			}
		}

		/**
		 * {@inheritDoc} <br>
		 * Mimics a for loop and as such iterates through the set variables and
		 * processes the child {@link Node}s.
		 */
		@Override
		public void visitForLoopNode(final ForLoopNode node) {
			final String variable = node.getVariable().asText();
			double initial = Double.parseDouble(node.getStartExpression().asText());
			final double end = Double.parseDouble(node.getEndExpression().asText());
			final double step = node.getStepExpression() == null ? 1
					: Double.parseDouble(node.getStepExpression().asText());

			multistack.push(variable, new ValueWrapper(initial));

			for (; initial <= end; initial += step) {
				for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
					node.getChild(i).accept(this);
				}

				final ValueWrapper value = multistack.pop(variable);
				multistack.push(variable, new ValueWrapper((Double) value.getValue() + step));
			}

			multistack.pop(variable);
		}

		/**
		 * {@inheritDoc} <br>
		 * Writes the content of the {@link TextNode} to the
		 * {@link RequestContext} output stream.
		 */
		@Override
		public void visitTextNode(final TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (final IOException e) {
				System.err.println("Writing of TextNode's content to output stream failed.");
				System.exit(-1);
			}
		}

	};

	/**
	 * Constructs a new {@link SmartScriptEngine} with the specified
	 * {@link DocumentNode} and {@link RequestContext}.
	 * 
	 * @param documentNode
	 *            constructed {@link DocumentNode} from a file
	 * @param requestContext
	 *            {@link RequestContext} used for header and content creation
	 *            and writing to the output stream
	 */
	public SmartScriptEngine(final DocumentNode documentNode, final RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Starts the script execution.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
