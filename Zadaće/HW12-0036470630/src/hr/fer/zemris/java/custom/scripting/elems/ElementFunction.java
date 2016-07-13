package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@link Element} that represents a function. A function is
 * defined by a "@" preceding a function name.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ElementFunction extends Element {

	/**
	 * Function name
	 */
	private final String name;

	/**
	 * Constructs a new {@link ElementFunction} with the provided function name
	 * 
	 * @param name
	 *            function name
	 */
	public ElementFunction(final String name) {
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
	 * Returns the name of the function
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
