package hr.fer.zemris.web.servlet.definitions;

/**
 * Class that contains the calculated values of sine and cosine ready to be
 * shown in a HTML page.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class TrigPair {

	/** Calculated sine */
	private final Double sin;

	/** Calculated cosine */
	private final Double cos;

	/**
	 * Constructs a new {@link TrigPair} with the specified values.
	 * 
	 * @param sin
	 *            sine value
	 * @param cos
	 *            cosine value
	 */
	public TrigPair(final Double sin, final Double cos) {
		super();
		this.sin = sin;
		this.cos = cos;
	}

	/**
	 * Returns the cosine value.
	 * 
	 * @return cosine value
	 */
	public Double getCos() {
		return cos;
	}

	/**
	 * Returns the sine value.
	 * 
	 * @return sine value
	 */
	public Double getSin() {
		return sin;
	}

}
