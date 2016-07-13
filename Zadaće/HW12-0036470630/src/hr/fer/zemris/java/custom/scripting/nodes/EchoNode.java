package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class derived from {@link Node} that represents a {$=$} tag. Used to write
 * expressions which are then classified as correct {@link Element} objects
 * whether the expression consists of functions, variables, strings, operators
 * or numbers.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class EchoNode extends Node {

	/** {@link Element}s parsed from a {@link EchoNode} */
	private final Element[] elements;

	/**
	 * Constructs a new {@link EchoNode} with the specified argument
	 * 
	 * @param elements
	 *            {@link Element}s parsed from a {@link EchoNode}
	 */
	public EchoNode(final Element[] elements) {
		this.elements = elements;
	}

	@Override
	public void accept(final INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

	/**
	 * Returns {@link Element} objects contained in this {@link EchoNode}.
	 * 
	 * @return array of {@link Element}
	 */
	public Element[] getElements() {
		return elements;
	}
}
