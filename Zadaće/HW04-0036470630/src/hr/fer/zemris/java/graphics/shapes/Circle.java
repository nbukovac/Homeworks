package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class derived from {@link Oval} used to specify circles. Circles can be drawn
 * on {@link BWRaster} if their radius is &gt;= 1.
 * 
 * @see "https://en.wikipedia.org/wiki/Circle"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Circle extends Oval {

	/**
	 * Size argument for {@link Circle}, defines the size of the {@link Circle}
	 */
	private int radius;

	/**
	 * Constructs a new {@link Circle} with the provided arguments. An
	 * {@link IllegalArgumentException} is thrown if radius is &lt; 1.
	 * 
	 * @param x
	 *            x-coordinate of center
	 * @param y
	 *            y-coordinate of center
	 * @param radius
	 *            size of {@link Circle}
	 * @throws IllegalArgumentException
	 *             if radius is &lt; 1
	 */
	public Circle(final int x, final int y, final int radius) {
		super(x, y);

		checkSizeArgument(radius);

		this.radius = radius;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final int x, final int y) {
		return contains(x, y, radius, radius);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(final BWRaster raster) {
		draw(raster, radius, radius);
	}

	/**
	 * Returns the radius
	 * 
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets the radius to the specified value. An
	 * {@link IllegalArgumentException} is thrown if argument is &lt; 1.
	 * 
	 * @param radius
	 *            the radius to set
	 * @throws IllegalArgumentException
	 *             if argument is &lt; 1.
	 */
	public void setRadius(final int radius) {
		checkSizeArgument(radius);

		this.radius = radius;
	}

}
