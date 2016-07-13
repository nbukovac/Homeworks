package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@link Element} that represents a constant double value.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ElementConstantDouble extends Element {

	/**
	 * Constant double value
	 */
	private final double value;

	/**
	 * Constructs a new {@link ElementConstantDouble} with the provided number
	 * value
	 * 
	 * @param number
	 *            number value
	 */
	public ElementConstantDouble(final double number) {
		value = number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return value + "";
	}

	/**
	 * Returns the <code>value</code> member variable
	 * 
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
}
