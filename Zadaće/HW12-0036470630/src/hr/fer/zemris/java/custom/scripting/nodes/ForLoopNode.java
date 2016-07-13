package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class derived from {@link Node} that represents a {$FOR$} tag. Basically
 * represents a for loop whether it is a ordinary for loop or a foreach loop.
 * <code>variable</code>, <code>startExpression</code> and
 * <code>endExpression</code> are obligatory while <code>stepExpression</code>
 * is only needed if a ordinary for loop is constructed.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ForLoopNode extends Node {

	/**
	 * For loop variable
	 */
	private final ElementVariable variable;

	/**
	 * For loop start expression
	 */
	private final Element startExpression;

	/**
	 * For loop end expression
	 */
	private final Element endExpression;

	/**
	 * For loop step expression
	 */
	private final Element stepExpression;

	/**
	 * Constructs a new {@link ForLoopNode} with the specified arguments
	 * 
	 * @param variable
	 *            for loop variable
	 * @param startExpression
	 *            for loop initialization
	 * @param endExpression
	 *            for loop end expression
	 * @param stepExpression
	 *            for loop step expression
	 */
	public ForLoopNode(final ElementVariable variable, final Element startExpression, final Element endExpression,
			final Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	@Override
	public void accept(final INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

	/**
	 * Returns this {@link ForLoopNode}s end expression as an appropriate
	 * {@link Element}.
	 * 
	 * @return the endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns this {@link ForLoopNode}s start expression as an appropriate
	 * {@link Element}.
	 * 
	 * @return the startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns this {@link ForLoopNode}s step expression as an appropriate
	 * {@link Element}.
	 * 
	 * @return the stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	/**
	 * Returns this {@link ForLoopNode}s variable as an appropriate
	 * {@link ElementVariable}.
	 * 
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

}
