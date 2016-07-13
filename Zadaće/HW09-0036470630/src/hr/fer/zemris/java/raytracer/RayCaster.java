package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program used to demonstrate simple rendering of 3D graphical objects using a
 * ray-tracing algorithm to determine the colors of pixels.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class RayCaster {

	/**
	 * Returns a new {@link IRayTracerProducer} object used to take snapshots of
	 * the ray-tracing process.
	 * 
	 * @return new {@link IRayTracerProducer} object
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			private Point3D zAxis;
			private Point3D yAxis;
			private Point3D xAxis;
			private Point3D screenCorner;

			private Point3D getScreenCorner(final Point3D view, final double horizontal, final double vertical) {
				final Point3D top = yAxis.scalarMultiply(vertical / 2.0);
				final Point3D left = view.sub(xAxis.scalarMultiply(horizontal / 2.0));
				return top.add(left);
			}

			private Point3D getYAxis(final Point3D viewUp) {
				final Point3D normalizedViewUp = viewUp.normalize();
				return normalizedViewUp.sub(zAxis.scalarMultiply(normalizedViewUp.scalarProduct(zAxis))).normalize();
			}

			@Override
			public void produce(final Point3D eye, final Point3D view, final Point3D viewUp, final double horizontal,
					final double vertical, final int width, final int height, final long requestNo,
					final IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");

				final short[] red = new short[width * height];
				final short[] green = new short[width * height];
				final short[] blue = new short[width * height];

				zAxis = view.sub(eye).normalize();
				yAxis = getYAxis(viewUp);
				xAxis = zAxis.vectorProduct(yAxis).normalize();
				screenCorner = getScreenCorner(view, horizontal, vertical);

				final Scene scene = RayTracerViewer.createPredefinedScene();
				final short[] rgb = new short[3];

				int offset = 0;

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						final Point3D moveX = xAxis.scalarMultiply(x * horizontal / (width - 1));
						final Point3D moveY = yAxis.scalarMultiply(y * vertical / (height - 1));
						final Point3D screenPoint = screenCorner.add(moveX).sub(moveY);

						final Ray ray = Ray.fromPoints(eye, screenPoint);

						RayCasterUtil.tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}

	/**
	 * Program entry point
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

}
