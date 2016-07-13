package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * Visual representation of a {@link BWRaster} image used to better display
 * {@link BWRaster} image to the user.
 * 
 * @see BWRaster
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface RasterView {

	/**
	 * Creates the visualization of a {@link BWRaster} image.
	 * 
	 * @param raster
	 *            {@link BWRaster} with already set image
	 * @return end result of {@link BWRaster} image visualization
	 */
	public Object produce(BWRaster raster);
}
