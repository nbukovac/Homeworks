package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Class derived from {@link AbstractRasterView} representing a String
 * representation of a {@link BWRaster} image.
 * 
 * @see AbstractRasterView
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class StringRasterView extends AbstractRasterView {

	/**
	 * Constructs a new {@link StringRasterView} with default
	 * <code>turnedOn</code> and <code>turnedOff</code> characters.
	 * <code>turnedOn</code> is set to '*' and <code>turnedOff</code> is set to
	 * '.'.
	 */
	public StringRasterView() {
		super();
	}

	/**
	 * Constructs a new {@link StringRasterView} with specified
	 * <code>turnedOn</code> and <code>turnedOff</code> characters.
	 * 
	 * @param turnedOnChar
	 *            turned on pixel character
	 * @param turnedOffChar
	 *            turned off pixel character
	 */
	public StringRasterView(final char turnedOnChar, final char turnedOffChar) {
		super(turnedOnChar, turnedOffChar);
	}

}
