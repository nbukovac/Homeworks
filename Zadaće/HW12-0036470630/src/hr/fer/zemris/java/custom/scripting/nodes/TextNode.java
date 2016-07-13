package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class derived from {@link Node} that represents text outside of tags.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class TextNode extends Node {

	/** This {@link TextNode}s text */
	private final String text;

	/**
	 * Constructs a new {@link TextNode} with the specified text
	 * 
	 * @param text
	 *            text for this {@link TextNode}
	 */
	public TextNode(final String text) {
		this.text = text;
	}

	@Override
	public void accept(final INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}

	/**
	 * Returns the <code>text</code> property of this {@link TextNode}
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
