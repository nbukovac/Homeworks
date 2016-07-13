package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class that represents a visualization for a {@link BWRaster} image. Image is
 * represented as a String of characters that represent turned on and turned off
 * image pixels.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public abstract class AbstractRasterView implements RasterView {

	/**
	 * Defines newline character
	 */
	protected static final char NEWLINE_DELIMITER = '\n';

	/**
	 * Defines how a turned on pixel looks
	 */
	protected final char turnedOnChar;

	/**
	 * Defines how a turned off pixel looks
	 */
	protected final char turnedOffChar;

	/**
	 * Constructs a new {@link AbstractRasterView} with default
	 * <code>turnedOn</code> and <code>turnedOff</code> characters.
	 * <code>turnedOn</code> is set to '*' and <code>turnedOff</code> is set to
	 * '.'.
	 */
	public AbstractRasterView() {
		this('*', '.');
	}

	/**
	 * Constructs a new {@link AbstractRasterView} with specified
	 * <code>turnedOn</code> and <code>turnedOff</code> characters.
	 * 
	 * @param turnedOnChar
	 *            turned on pixel character
	 * @param turnedOffChar
	 *            turned off pixel character
	 */
	public AbstractRasterView(final char turnedOnChar, final char turnedOffChar) {
		this.turnedOnChar = turnedOnChar;
		this.turnedOffChar = turnedOffChar;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object produce(final BWRaster raster) {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0, height = raster.getHeight(); i < height; i++) {
			for (int j = 0, width = raster.getWidth(); j < width; j++) {
				if (raster.isTurned(j, i)) {
					sb.append(turnedOnChar);
				} else {
					sb.append(turnedOffChar);
				}
			}
			sb.append(NEWLINE_DELIMITER);
		}

		return sb.toString();
	}

}
