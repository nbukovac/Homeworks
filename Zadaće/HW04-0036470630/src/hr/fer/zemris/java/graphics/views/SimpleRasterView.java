package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class derived from {@link AbstractRasterView} representing a String
 * representation of a {@link BWRaster} image.
 * 
 * @see BWRaster
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SimpleRasterView extends AbstractRasterView {

	/**
	 * Constructs a new {@link SimpleRasterView} with default
	 * <code>turnedOn</code> and <code>turnedOff</code> characters.
	 * <code>turnedOn</code> is set to '*' and <code>turnedOff</code> is set to
	 * '.'.
	 */
	public SimpleRasterView() {
		super();
	}

	/**
	 * Constructs a new {@link SimpleRasterView} with specified
	 * <code>turnedOn</code> and <code>turnedOff</code> characters.
	 * 
	 * @param turnedOnChar
	 *            turned on pixel character
	 * @param turnedOffChar
	 *            turned off pixel character
	 */
	public SimpleRasterView(final char turnedOnChar, final char turnedOffChar) {
		super(turnedOnChar, turnedOffChar);
	}

	/**
	 * Creates the visualization of a {@link BWRaster} image but instead of
	 * returning value this method prints out the visualization to the standard
	 * output.
	 * 
	 * @param raster
	 *            {@link BWRaster} with already set image
	 * @return null
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

		System.out.println(sb.toString());

		return null;
	}

}
