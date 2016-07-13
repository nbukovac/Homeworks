package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Abstract class derived from {@link GeometricShape} used to specify special
 * cases of parallelograms and that is when all four angles are 90°, rectangles
 * and squares. Shapes can be drawn on {@link BWRaster} if their size arguments
 * are &gt;= 1.
 * 
 * @see "https://en.wikipedia.org/wiki/Parallelogram"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public abstract class RightAngleParallelogram extends GeometricShape {

	/**
	 * X-coordinate of top left corner
	 */
	protected int topLeftX;

	/**
	 * Y-coordinate of top left corner
	 */
	protected int topLeftY;

	/**
	 * Constructs a new {@link RightAngleParallelogram} with the specified left
	 * top corner.
	 * 
	 * @param x
	 *            x-coordinate of top left corner
	 * @param y
	 *            y-coordinate of top left corner
	 */
	public RightAngleParallelogram(final int x, final int y) {
		topLeftX = x;
		topLeftY = y;
	}

	/**
	 * Checks if this {@link RightAngleParallelogram} contains the given point
	 * specified by <code>x</code> and <code>y</code>.
	 * 
	 * @param x
	 *            x-coordinate of the given point
	 * @param y
	 *            y-coordinate of the given point
	 * @param width
	 *            width of this {@link RightAngleParallelogram}
	 * @param height
	 *            height of this {@link RightAngleParallelogram}
	 * @return if the given point is contained inside this
	 *         {@link RightAngleParallelogram}, else false
	 */
	protected boolean contains(final int x, final int y, final int width, final int height) {
		if (x >= topLeftX && x < topLeftX + width && y >= topLeftY && y < topLeftY + height) {
			return true;
		}

		return false;
	}

	/**
	 * Draws a {@link RightAngleParallelogram} on {@link BWRaster} but only the
	 * part that is visible on the {@link BWRaster}.
	 * 
	 * @param raster
	 *            {@link BWRaster} we are drawing this
	 *            {@link RightAngleParallelogram} on
	 * @param width
	 *            width of {@link RightAngleParallelogram}
	 * @param height
	 *            height of {@link RightAngleParallelogram}
	 */
	protected void draw(final BWRaster raster, final int width, final int height) {
		final int startX = topLeftX < 0 ? 0 : topLeftX;
		final int startY = topLeftY < 0 ? 0 : topLeftY;
		final int endX = topLeftX + width < raster.getWidth() ? topLeftX + width : raster.getWidth();
		final int endY = topLeftY + height < raster.getHeight() ? topLeftY + height : raster.getHeight();

		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				raster.turnOn(i, j);
			}
		}

	}

	/**
	 * Returns the x-coordinate of the top left corner
	 * 
	 * @return the topLeftX
	 */
	public int getTopLeftX() {
		return topLeftX;
	}

	/**
	 * Returns the y-coordinate of the top left corner
	 * 
	 * @return the topLeftY
	 */
	public int getTopLeftY() {
		return topLeftY;
	}

	/**
	 * Sets the x-coordinate of the top left corner
	 * 
	 * @param topLeftX
	 *            the topLeftX to set
	 */
	public void setTopLeftX(final int topLeftX) {
		this.topLeftX = topLeftX;
	}

	/**
	 * Sets the y-coordinate of the top left corner
	 * 
	 * @param topLeftY
	 *            the topLeftY to set
	 */
	public void setTopLeftY(final int topLeftY) {
		this.topLeftY = topLeftY;
	}

}
