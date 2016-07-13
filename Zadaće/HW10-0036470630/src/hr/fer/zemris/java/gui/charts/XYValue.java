package hr.fer.zemris.java.gui.charts;

/**
 * Class that stores x and y graph values for {@link BarChart} creation.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class XYValue {

	/**
	 * X value
	 */
	private final int x;

	/**
	 * Y value
	 */
	private final int y;

	/**
	 * Constructs a new {@link XYValue} with the provided {@code x} and
	 * {@code y} values.
	 * 
	 * @param x
	 *            x value
	 * @param y
	 *            y value
	 */
	public XYValue(final int x, final int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x value.
	 * 
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y value.
	 * 
	 * @return the y
	 */
	public int getY() {
		return y;
	}

}
