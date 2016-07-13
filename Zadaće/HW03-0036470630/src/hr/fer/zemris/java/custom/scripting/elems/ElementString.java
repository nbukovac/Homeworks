package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@link Element} that represents a string.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ElementString extends Element {

	/**
	 * Strings value
	 */
	private final String value;

	/**
	 * Constructs a new {@link ElementString} with the provided string value
	 * 
	 * @param value
	 *            string value
	 */
	public ElementString(final String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return value;
	}

	/**
	 * Returns the string
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
