package hr.fer.zemris.java.graphics.raster;

/**
 * Class that implements {@link BWRaster} and as such is also a black and white
 * raster image. Internal image is created with a boolean matrix and this way
 * pixels can only be turned off and turned on what is the primarily goal of
 * this class.
 * 
 * @author Nikola Bukovac
 *
 */
public class BWRasterMem implements BWRaster {

	/**
	 * {@link BWRasterMem} screen
	 */
	private boolean[][] image;

	/**
	 * Width of {@link BWRasterMem} screen
	 */
	private final int width;

	/**
	 * Height of {@link BWRasterMem} screen
	 */
	private final int height;

	/**
	 * Tracks if flip mode is activated
	 */
	private boolean flipMode;

	/**
	 * Constructs a new {@link BWRasterMem} with the specified width and height.
	 * An {@link IllegalArgumentException} is thrown if width or height is &lt;
	 * 1.
	 * 
	 * @param width
	 *            width of {@link BWRasterMem} screen
	 * @param height
	 *            height of {@link BWRasterMem} screen
	 * @throws IllegalArgumentException
	 *             if width or height is &lt; 1
	 */
	public BWRasterMem(final int width, final int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Height and width of Raster have to be >= 1");
		}

		image = new boolean[height][width];
		this.width = width;
		this.height = height;
	}

	/**
	 * Checks if given point is inside this {@link BWRasterMem}. An
	 * {@link IllegalArgumentException} is thrown if it doesn't belong.
	 * 
	 * @param x
	 *            x-coordinate of point
	 * @param y
	 *            y-coordinate of point
	 * @throws IllegalArgumentException
	 *             if given point doesn't belong to this {@link BWRasterMem}
	 */
	private void checkIfPointValid(final int x, final int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IllegalArgumentException(
					"The specified point doesn't belong to this Raster: (" + x + ", " + y + ")");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		image = new boolean[height][width];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disableFlipMode() {
		flipMode = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enableFlipMode() {
		flipMode = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTurned(final int x, final int y) {
		checkIfPointValid(x, y);

		return image[y][x];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void turnOff(final int x, final int y) {
		checkIfPointValid(x, y);

		image[y][x] = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void turnOn(final int x, final int y) {
		checkIfPointValid(x, y);

		if (flipMode) {
			image[y][x] = !image[y][x];
		} else {
			image[y][x] = true;
		}
	}
}
