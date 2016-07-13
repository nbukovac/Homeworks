package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class derived from {@link RightAngleParallelogram} used to specify square
 * shapes. Squares can be drawn on {@link BWRaster} if their size is &gt;= 1.
 * 
 * @see "https://en.wikipedia.org/wiki/Square"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class Square extends RightAngleParallelogram {

	/**
	 * Size of {@link Square}
	 */
	private int size;

	/**
	 * Constructs a new {@link Square} with the provided top left corner point
	 * and size. An {@link IllegalArgumentException} is thrown if
	 * <code>size</code> is &lt; 1
	 * 
	 * @param x
	 *            x-coordinate of top left corner
	 * @param y
	 *            y-coordinate of top left corner
	 * @param size
	 *            size of {@link Square}
	 * @throws IllegalArgumentException
	 *             if <code>size</code> is &lt; 1
	 */
	public Square(final int x, final int y, final int size) {
		super(x, y);

		checkSizeArgument(size);

		this.size = size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final int x, final int y) {
		return contains(x, y, size, size);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw(final BWRaster raster) {
		draw(raster, size, size);
	}

	/**
	 * Returns the size of {@link Square}
	 * 
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size to the specified value. An {@link IllegalArgumentException}
	 * is thrown if argument is &lt; 1.
	 * 
	 * @param size
	 *            the size to set
	 * @throws IllegalArgumentException
	 *             if argument is &lt; 1
	 */
	public void setSize(final int size) {
		this.size = size;
	}

}
