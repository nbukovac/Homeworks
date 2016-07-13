package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class derived from {@link Oval} used to specify ellipses. Ellipses can be
 * drawn on {@link BWRaster} if their radiuses are &gt;= 1.
 * 
 * @see "https://en.wikipedia.org/wiki/Ellipse"
 * 
 * @author Nikolac Bukovac
 * @version 1.0
 */
public class Ellipse extends Oval {

	/**
	 * Size argument for {@link Ellipse}, defines the size of horizontal stretch
	 */
	private int horizontalRadius;

	/**
	 * Size argument for {@link Ellipse}, defines the size of vertical stretch
	 */
	private int verticalRadius;

	/**
	 * Constructs a new {@link Ellipse} with the provided arguments. An
	 * {@link IllegalArgumentException} is thrown if horizontal or vertical
	 * radius is &lt; 1.
	 * 
	 * @param x
	 *            x-coordinate of center
	 * @param y
	 *            y-coordinate of center
	 * @param horizontalRadius
	 *            the size of horizontal stretch
	 * @param verticalRadius
	 *            size of vertical stretch
	 * @throws IllegalArgumentException
	 *             if horizontal or vertical radius is &lt; 1
	 */
	public Ellipse(final int x, final int y, final int horizontalRadius, final int verticalRadius) {
		super(x, y);

		checkSizeArgument(horizontalRadius);
		checkSizeArgument(verticalRadius);

		this.horizontalRadius = horizontalRadius;
		this.verticalRadius = verticalRadius;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final int x, final int y) {
		return contains(x, y, horizontalRadius, verticalRadius);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(final BWRaster raster) {
		draw(raster, horizontalRadius, verticalRadius);
	}

	/**
	 * Returns the horizontal radius
	 * 
	 * @return the horizontalRadius
	 */
	public int getHorizontalRadius() {
		return horizontalRadius;
	}

	/**
	 * Returns the vertical radius
	 * 
	 * @return the verticalRadius
	 */
	public int getVerticalRadius() {
		return verticalRadius;
	}

	/**
	 * Sets the horizontal radius to the provided value. An
	 * {@link IllegalArgumentException} is thrown if argument is &lt; 1.
	 * 
	 * @param horizontalRadius
	 *            the horizontalRadius to set
	 * @throws IllegalArgumentException
	 *             if argument is &lt; 1.
	 */
	public void setHorizontalRadius(final int horizontalRadius) {
		checkSizeArgument(horizontalRadius);

		this.horizontalRadius = horizontalRadius;
	}

	/**
	 * Sets the vertical radius to the provided value. An
	 * {@link IllegalArgumentException} is thrown if argument is &lt; 1.
	 * 
	 * @param verticalRadius
	 *            the verticalRadius to set
	 * @throws IllegalArgumentException
	 *             if argument is &lt; 1.
	 */
	public void setVerticalRadius(final int verticalRadius) {
		checkSizeArgument(verticalRadius);

		this.verticalRadius = verticalRadius;
	}

}
