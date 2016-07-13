package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface that specifies the necessary functions a {@link INodeVisitor}
 * should provide for successful processing of {@link Node} type objects.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface INodeVisitor {

	/**
	 * Processes a {@link DocumentNode} as it is specified by the concrete
	 * implementation of {@link INodeVisitor}.
	 * 
	 * @param node
	 *            {@link DocumentNode} that is going to be processed
	 */
	public void visitDocumentNode(DocumentNode node);

	/**
	 * Processes a {@link EchoNode} as it is specified by the concrete
	 * implementation of {@link INodeVisitor}.
	 * 
	 * @param node
	 *            {@link EchoNode} that is going to be processed
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Processes a {@link ForLoopNode} as it is specified by the concrete
	 * implementation of {@link INodeVisitor}.
	 * 
	 * @param node
	 *            {@link ForLoopNode} that is going to be processed
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Processes a {@link TextNode} as it is specified by the concrete
	 * implementation of {@link INodeVisitor}.
	 * 
	 * @param node
	 *            {@link TextNode} that is going to be processed
	 */
	public void visitTextNode(TextNode node);

}
