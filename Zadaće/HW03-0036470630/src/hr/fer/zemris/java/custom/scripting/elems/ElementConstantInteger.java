package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@link Element} representing a constant integer value.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ElementConstantInteger extends Element {

	/**
	 * Constant integer value
	 */
	private final int value;

	/**
	 * Constructs a new {@link ElementConstantInteger} with the provided number
	 * value
	 * 
	 * @param value
	 *            number value
	 */
	public ElementConstantInteger(final int value) {
		this.value = value;
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
	public int getValue() {
		return value;
	}
}
