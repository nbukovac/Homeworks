package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Abstract class derived from {@link GeometricShape} used to specify oval
 * shapes such as ellipse and circle. Shapes can be drawn on {@link BWRaster} if
 * their radius is &gt;= 1.
 * 
 * @see "https://en.wikipedia.org/wiki/Oval"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public abstract class Oval extends GeometricShape {

	/**
	 * X-coordinate of center
	 */
	protected int centerX;

	/**
	 * Y-coordinate of center
	 */
	protected int centerY;

	/**
	 * Constructs a new {@link Oval} with the specified center point.
	 * 
	 * @param x
	 *            x-coordinate of center
	 * @param y
	 *            y-coordinate of center
	 */
	public Oval(final int x, final int y) {
		centerX = x;
		centerY = y;
	}

	/**
	 * Checks if this {@link Oval} contains the given point specified by
	 * <code>x</code> and <code>y</code>.
	 * 
	 * @param x
	 *            x-coordinate of the given point
	 * @param y
	 *            y-coordinate of the given point
	 * @param horizontalRadius
	 *            horizontal radius of this {@link Oval}
	 * @param verticalRadius
	 *            vertical radius of this {@link Oval}
	 * @return true if the given point is contained inside this {@link Oval},
	 *         else false
	 */
	protected boolean contains(final int x, final int y, final int horizontalRadius, final int verticalRadius) {
		final double dx = x - centerX;
		final double dy = y - centerY;
		final double result = ((dx * dx) / (horizontalRadius * horizontalRadius))
				+ ((dy * dy) / (verticalRadius * verticalRadius));
		return result <= 1.0;
	}

	/**
	 * Draws a {@link Oval} on {@link BWRaster} but only the part that is
	 * visible on the {@link BWRaster}.
	 * 
	 * @param raster
	 *            {@link BWRaster} we are drawing this {@link Oval} on
	 * @param horizontalRadius
	 *            horizontal radius of this {@link Oval}
	 * @param verticalRadius
	 *            vertical radius of this {@link Oval}
	 */
	protected void draw(final BWRaster raster, final int horizontalRadius, final int verticalRadius) {
		final int startX = centerX - horizontalRadius < 0 ? 0 : centerX - horizontalRadius;
		final int startY = centerY - verticalRadius < 0 ? 0 : centerY - verticalRadius;
		final int endX = centerX + horizontalRadius < raster.getWidth() ? centerX + horizontalRadius
				: raster.getWidth();
		final int endY = centerY + verticalRadius < raster.getHeight() ? centerY + verticalRadius : raster.getHeight();

		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				if (contains(i, j, horizontalRadius, verticalRadius)) {
					raster.turnOn(i, j);
				}
			}
		}
	}

	/**
	 * Returns the x-coordinate of the center point
	 * 
	 * @return the centerX
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * Returns the y-coordinate of the center point
	 * 
	 * @return the centerY
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * Sets the x-coordinate of the center point
	 * 
	 * @param centerX
	 *            the centerX to set
	 */
	public void setCenterX(final int centerX) {
		this.centerX = centerX;
	}

	/**
	 * Sets the y-coordinate of the center point
	 * 
	 * @param centerY
	 *            the centerY to set
	 */
	public void setCenterY(final int centerY) {
		this.centerY = centerY;
	}

}
