package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Abstract class which represents geometric shapes such as circles, rectangles,
 * squares, etc. Shapes can be drawn on {@link BWRaster} if their size is &gt;=
 * 1.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public abstract class GeometricShape {

	/**
	 * Checks arguments which define {@link GeometricShape} size such as
	 * radiuses and side sizes. An {@link IllegalArgumentException} is thrown if
	 * <code>x</code> is &lt; 1 because a {@link GeometricShape} then can't be
	 * drawn on {@link BWRaster}.
	 * 
	 * @param x
	 *            argument to check
	 * @throws IllegalArgumentException
	 *             if <code>x</code> is &lt; 1.
	 */
	protected static void checkSizeArgument(final int x) {
		if (x < 1) {
			throw new IllegalArgumentException("Size argument can't be < 1");
		}
	}

	/**
	 * Checks if a specified set of coordinates is a point inside a
	 * {@link GeometricShape}.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @return true if point is inside of {@link GeometricShape}, else false;
	 */
	public abstract boolean contains(int x, int y);

	/**
	 * Draws a {@link GeometricShape} on {@link BWRaster} using the most
	 * efficient method for a given {@link GeometricShape} but only the part
	 * that is visible on the {@link BWRaster}..
	 * 
	 * @param raster
	 *            {@link BWRaster} we are drawing this {@link GeometricShape} on
	 */
	public void draw(final BWRaster raster) {
		for (int i = 0, width = raster.getWidth(); i < width; i++) {
			for (int j = 0, height = raster.getHeight(); j < height; j++) {
				if (contains(i, j)) {
					raster.turnOn(i, j);
				}
			}
		}
	}
}
