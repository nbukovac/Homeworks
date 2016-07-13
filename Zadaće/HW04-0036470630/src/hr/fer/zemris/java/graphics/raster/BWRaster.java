package hr.fer.zemris.java.graphics.raster;

/**
 * Raster image that is represented as turned on and turned off pixels across a
 * specified field. It is possible to turn on and off pixels to change the image
 * accordingly.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface BWRaster {

	/**
	 * Sets {@link BWRaster}s internal field to turned off state
	 */
	public void clear();

	/**
	 * Disables {@link BWRaster} flip mode and now method turnOn operates in
	 * normal mode
	 */
	public void disableFlipMode();

	/**
	 * Enables {@link BWRaster}s flip mode and now method turnOn operates in
	 * special mode
	 */
	public void enableFlipMode();

	/**
	 * Returns {@link BWRaster} screen height
	 * 
	 * @return {@link BWRaster} screen height
	 */
	public int getHeight();

	/**
	 * Returns {@link BWRaster} screen width
	 * 
	 * @return {@link BWRaster} screen width
	 */
	public int getWidth();

	/**
	 * Checks if specified pixel defined by <code>x</code> and <code>y</code> on
	 * {@link BWRaster} screen is turned on. An {@link IllegalArgumentException}
	 * is thrown if pixel doesn't belong to this {@link BWRaster}.
	 * 
	 * @param x
	 *            x-coordinate of pixel
	 * @param y
	 *            y-coordinate of pixel
	 * @return true if it is turned on, else false
	 * @throws IllegalArgumentException
	 *             if pixel doesn't belong to this {@link BWRaster}
	 */
	public boolean isTurned(int x, int y);

	/**
	 * Turns off specified pixel defined by <code>x</code> and <code>y</code> on
	 * {@link BWRaster} screen. An {@link IllegalArgumentException} is thrown if
	 * pixel doesn't belong to this {@link BWRaster}.
	 * 
	 * @param x
	 *            x-coordinate of pixel
	 * @param y
	 *            y-coordinate of pixel
	 * @throws IllegalArgumentException
	 *             if pixel doesn't belong to this {@link BWRaster}
	 */
	public void turnOff(int x, int y);

	/**
	 * Turns on specified pixel defined by <code>x</code> and <code>y</code> on
	 * {@link BWRaster} screen. An {@link IllegalArgumentException} is thrown if
	 * pixel doesn't belong to this {@link BWRaster}.
	 * 
	 * @param x
	 *            x-coordinate of pixel
	 * @param y
	 *            y-coordinate of pixel
	 * @throws IllegalArgumentException
	 *             if pixel doesn't belong to this {@link BWRaster}
	 */
	public void turnOn(int x, int y);
}
