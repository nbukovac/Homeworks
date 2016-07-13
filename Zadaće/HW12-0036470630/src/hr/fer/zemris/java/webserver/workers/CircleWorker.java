package hr.fer.zemris.java.webserver.workers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that implements {@link IWebWorker} and writes a image with a circle to
 * the {@link RequestContext}s output stream.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(final RequestContext context) {
		context.setMimeType("image/png");
		final BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

		final Graphics2D g2d = bim.createGraphics();
		g2d.fillOval(0, 0, 199, 199);
		g2d.dispose();

		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (final IOException e) {
			System.out.println(
					"Writing to the output stream has been interrupted because of an exception " + e.getMessage());
		}
	}

}
