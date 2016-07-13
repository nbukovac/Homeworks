package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@link Element} that represents a mathematical operation.
 * Defined operation are +, -, /, *, ^.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ElementOperator extends Element {

	/**
	 * Operation symbol
	 */
	private final String symbol;

	/**
	 * Constructs a new {@link ElementOperator} with the provided mathematical
	 * operation symbol
	 * 
	 * @param symbol
	 *            operation symbol
	 */
	public ElementOperator(final String symbol) {
		this.symbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Returns the operation symbol
	 * 
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

}
