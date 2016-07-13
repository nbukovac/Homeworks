package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@link Element} that represents a variable. Valid variable
 * names are abc_32, A32, a_ and other. Invalid variable names are _abc, 32_sa
 * and other. A good variable name matches to the following regular expression
 * "[A-Za-z][A-Za-z_0-9]*"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ElementVariable extends Element {

	/**
	 * Variable name
	 */
	private final String name;

	/**
	 * Constructs a new {@link ElementVariable} with the provided name
	 * 
	 * @param name
	 *            variable name
	 */
	public ElementVariable(final String name) {
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String asText() {
		return name;
	}

	/**
	 * Returns the variable name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
