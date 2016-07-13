package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class which represents a part of a document whether it is text, for loop
 * or expressions. Used for document parsing to determine if a document is
 * valid.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Node {

	/** Collection designated for {@link Node} storage */
	private ArrayIndexedCollection childrenNodes;

	/**
	 * Adds a new child {@link Node} to this parent {@link Node}.
	 * 
	 * @param child
	 *            new child {@link Node}
	 */
	public void addChildNode(final Node child) {
		if (childrenNodes == null) {
			childrenNodes = new ArrayIndexedCollection();
		}

		childrenNodes.add(child);
	}

	/**
	 * Returns the child {@link Node} at the specified position from the
	 * internal collection. An {@link IndexOutOfBoundsException} is thrown if
	 * the <code>index</code> argument is &lt; 0 or &gt;= number of direct
	 * children
	 * 
	 * @param index
	 *            position of the child {@link Node}
	 * @return child {@link Node} from the specified position
	 */
	public Node getChild(final int index) {
		if (index < 0 || index >= numberOfChildren()) {
			throw new IndexOutOfBoundsException("Index is out bounds for this collection");
		}

		return (Node) childrenNodes.get(index);
	}

	/**
	 * Returns the number of direct children for this {@link Node}
	 * 
	 * @return number of direct children
	 */
	public int numberOfChildren() {
		return (childrenNodes == null) ? 0 : childrenNodes.size();
	}
}
