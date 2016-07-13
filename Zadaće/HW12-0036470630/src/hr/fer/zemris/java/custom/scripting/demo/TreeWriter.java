package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program used to demonstrate the usage of the Visitor design pattern. The
 * Visitor design pattern is used to traverse over a object that is designated
 * with the Composite design pattern and write its content to the standard
 * output. This program requires one argument and that is the file path to a
 * document that can be parsed with the {@link SmartScriptParser}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class TreeWriter {

	/**
	 * Class that implements {@link INodeVisitor} and writes every found
	 * {@link Node}s content as it is specified by this classes methods. Name of
	 * each method specifies for which {@link Node} type it is used.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * Returns a string in correct format. Format depends on the passed
		 * <code>element</code> argument, if the <code>element</code> argument
		 * is a instance of {@link ElementString} then the output is enclosed
		 * with &quot;.
		 * 
		 * @param element
		 *            {@link Element}
		 * @return String in correct format
		 */
		private static String forLoopString(final Element element) {
			if (element instanceof ElementString) {
				return "\"" + element.asText() + "\" ";
			} else {
				return element.asText() + " ";
			}
		}

		/**
		 * {@inheritDoc}<br>
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
		 * {@inheritDoc}<br>
		 * Writes all {@link Element}s as they were most likely represented
		 * before the parsing procedure.
		 */
		@Override
		public void visitEchoNode(final EchoNode node) {
			final StringBuilder output = new StringBuilder();
			output.append("{$= ");

			for (final Element element : node.getElements()) {
				if (element instanceof ElementString) {
					output.append("\"" + element.asText() + "\" ");
				} else {
					if (element instanceof ElementFunction) {
						output.append('@');
					}

					output.append(element.asText() + " ");
				}
			}

			output.append("$}");
			System.out.print(output.toString());
		}

		/**
		 * {@inheritDoc}<br>
		 * Writes all set member variables of the {@link ForLoopNode} and then
		 * calls every child {@link Node}s {@code accept} method to activate
		 * this {@link INodeVisitor}s processing.
		 */
		@Override
		public void visitForLoopNode(final ForLoopNode node) {
			final StringBuilder output = new StringBuilder();
			if (node.getStepExpression() != null) {
				output.append("{$FOR " + node.getVariable().asText() + " ");

				output.append(forLoopString(node.getStartExpression()) + forLoopString(node.getEndExpression())
						+ forLoopString(node.getStepExpression()) + "$}");
			} else {
				output.append("{$FOR " + node.getVariable().asText() + " ");

				output.append(forLoopString(node.getStartExpression()) + forLoopString(node.getEndExpression()) + "$}");
			}

			System.out.print(output.toString());

			for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
				node.getChild(i).accept(this);
			}

			System.out.print("{END}");
		}

		/**
		 * {@inheritDoc}<br>
		 * Writes the {@link TextNode}s content.
		 */
		@Override
		public void visitTextNode(final TextNode node) {
			System.out.print(node.getText());
		}

	}

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            document file path
	 */
	public static void main(final String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments.");
			System.exit(-1);
		}

		String docBody = "";

		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			System.err.println("The provided file path is invalid");
		}

		SmartScriptParser parser = null;

		try {
			parser = new SmartScriptParser(docBody);
		} catch (final SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (final Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		final WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
	}

}
