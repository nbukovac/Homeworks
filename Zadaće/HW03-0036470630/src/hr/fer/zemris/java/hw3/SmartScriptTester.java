package hr.fer.zemris.java.hw3;

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
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program used to demonstrate usage of {@link SmartScriptParser} class.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SmartScriptTester {

	/**
	 * Creates a tree structure of {@link Node}s for better visualization of the
	 * parsing process.
	 * 
	 * @param documentBody
	 *            string representing the current structure of the tree
	 * @param document
	 *            parsed tree structure
	 * @param tab
	 *            used to indent child {@link Node}s
	 * @return string representing tree structure
	 */
	private static String createNodeStructure(String documentBody, final Node document, final String tab) {
		if (document instanceof DocumentNode) {
			documentBody += tab + "DocumentNode\n";
		} else if (document instanceof ForLoopNode) {
			documentBody += tab + "ForLoopNode\n";

		} else if (document instanceof EchoNode) {
			documentBody += tab + "EchoNode\n";

		} else {
			documentBody += tab + "TextNode\n";
		}

		for (int i = 0, size = document.numberOfChildren(); i < size; i++) {
			documentBody = createNodeStructure(documentBody, document.getChild(i), tab + "\t");
		}

		return documentBody;
	}

	/**
	 * Creates the original document as it likely was before parsing.
	 * 
	 * @param documentBody
	 *            string representing the current structure of the tree
	 * @param document
	 *            parsed tree structure
	 * @return string representing tree structure
	 */
	private static String createOriginalDocumentBody(String documentBody, final Node document) {
		if (document instanceof ForLoopNode) {
			final ForLoopNode tmp = (ForLoopNode) document;

			if (tmp.getStepExpression() != null) {
				documentBody += "{$FOR " + tmp.getVariable().asText() + " ";

				documentBody += forLoopString(tmp.getStartExpression()) + forLoopString(tmp.getEndExpression())
						+ forLoopString(tmp.getStepExpression()) + "$}";
			} else {
				documentBody += "{$FOR " + tmp.getVariable().asText() + " ";

				documentBody += forLoopString(tmp.getStartExpression()) + forLoopString(tmp.getEndExpression()) + "$}";
			}

		} else if (document instanceof EchoNode) {
			final EchoNode tmp = (EchoNode) document;
			documentBody += "{$= ";

			for (final Element element : tmp.getElements()) {
				if (element instanceof ElementString) {
					documentBody += "\"" + element.asText() + "\" ";
				} else {
					if (element instanceof ElementFunction) {
						documentBody += "@";
					}

					documentBody += element.asText() + " ";
				}
			}

			documentBody += "$}";

		} else if (document instanceof TextNode) {
			final TextNode tmp = (TextNode) document;
			documentBody += tmp.getText();
		}

		for (int i = 0, size = document.numberOfChildren(); i < size; i++) {
			documentBody = createOriginalDocumentBody(documentBody, document.getChild(i));
		}

		if (document instanceof ForLoopNode) {
			documentBody += "{$END$}";
		}

		return documentBody;
	}

	/**
	 * Returns a string in correct format. Format depends on the passed
	 * <code>element</code> argument, if the <code>element</code> argument is a
	 * instance of {@link ElementString} then the output is enclosed with
	 * &quot;.
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
	 * Main method used to test the {@link SmartScriptParser}. Requires path to
	 * the text document specified for parsing
	 * 
	 * @param args
	 *            path to file
	 */
	public static void main(final String[] args) {
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

		final DocumentNode document = parser.getDocumentNode();
		System.out.println(createNodeStructure("", document, ""));

		final String originalDocumentBody = createOriginalDocumentBody("", document);
		System.out.println(originalDocumentBody);
		System.out.println();

		final SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		final DocumentNode document2 = parser2.getDocumentNode();
		final String originalDocumentBody2 = createOriginalDocumentBody("", document2);

		System.out.println(originalDocumentBody2);
		System.out.println();
		System.out.println(originalDocumentBody.equals(originalDocumentBody2));

	}

}
