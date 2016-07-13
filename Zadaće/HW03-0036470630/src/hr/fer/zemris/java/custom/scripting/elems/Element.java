package hr.fer.zemris.java.custom.scripting.elems;

import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * Base class which represents a generic element of a {@link Node} class
 * describing a part of a parsed {@link Node} whether it is a variable, string,
 * constant, function or operator.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Element {

	/**
	 * Returns a <code>String</code> representation of this {@link Element}.
	 * 
	 * @return <code>String</code> equivalent to {@link Element} value;
	 */
	public String asText() {
		return "";
	}

}
