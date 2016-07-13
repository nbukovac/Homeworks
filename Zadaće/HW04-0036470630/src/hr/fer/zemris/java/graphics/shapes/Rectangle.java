package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class derived from {@link RightAngleParallelogram} used to specify rectangle
 * shapes. Rectangles can be drawn on {@link BWRaster} if their width and height
 * is &gt;= 1.
 * 
 * @see "https://en.wikipedia.org/wiki/Rectangle"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Rectangle extends RightAngleParallelogram {

	/**
	 * Width of {@link Rectangle}
	 */
	private int width;

	/**
	 * Height of {@link Rectangle}
	 */
	private int height;

	/**
	 * Constructs a new {@link Rectangle} with the provided top left corner
	 * point and size. An {@link IllegalArgumentException} is thrown if
	 * <code>width</code> or <code>height</code> is &lt; 1
	 * 
	 * @param x
	 *            x-coordinate of top left corner
	 * @param y
	 *            y-coordinate of top left corner
	 * @param width
	 *            width of rectangle
	 * @param height
	 *            height of rectangle
	 * @throws IllegalArgumentException
	 *             if <code>width</code> or <code>height</code> is &lt; 1
	 */
	public Rectangle(final int x, final int y, final int width, final int height) {
		super(x, y);

		checkSizeArgument(width);
		checkSizeArgument(height);

		this.width = width;
		this.height = height;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final int x, final int y) {
		return contains(x, y, width, height);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(final BWRaster raster) {
		draw(raster, width, height);
	}

	/**
	 * Returns height of {@link Rectangle}
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns width of {@link Rectangle}
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the height to the specified value. An
	 * {@link IllegalArgumentException} is thrown if argument is &lt; 1.
	 * 
	 * @param height
	 *            the height to set
	 * @throws IllegalArgumentException
	 *             if argument is &lt; 1
	 */
	public void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * Sets the width to the specified value. An
	 * {@link IllegalArgumentException} is thrown if argument is &lt; 1.
	 * 
	 * @param width
	 *            the width to set
	 * @throws IllegalArgumentException
	 *             if argument is &lt; 1
	 */
	public void setWidth(final int width) {
		this.width = width;
	}

}
