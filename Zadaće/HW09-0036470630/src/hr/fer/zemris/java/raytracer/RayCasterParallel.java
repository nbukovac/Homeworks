package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program used to demonstrate simple rendering of 3D graphical objects using a
 * ray-tracing algorithm to determine the colors of pixels. The whole process is
 * parallelized for better performance.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class RayCasterParallel {

	/**
	 * Class that extends {@link RecursiveAction} and enables recursive job
	 * creation specified for color calculation.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	private static class CalculationWork extends RecursiveAction {

		/** Serial version id */
		private static final long serialVersionUID = -8499925384202306318L;

		/** Threshold to stop dividing work by y */
		private static final int THRESHOLD = 25;

		/** Start y position */
		private final int startY;

		/** End y position */
		private int endY;

		/** Offset */
		private int offset;

		/** Height */
		private final int height;

		/** Width */
		private final int width;

		/** Y axis */
		private final Point3D yAxis;

		/** X axis */
		private final Point3D xAxis;

		/** Top left corner */
		private final Point3D screenCorner;

		/** Red color values */
		short[] red;

		/** Green color values */
		short[] green;

		/** Blue color values */
		short[] blue;

		/** {@link Scene} object */
		Scene scene;

		/** Horizontal */
		double horizontal;

		/** Vertical */
		double vertical;

		/** Eye */
		Point3D eye;

		/** RGB color values */
		short[] rgb;

		/**
		 * Constructs a new {@link CalculationWork} to create a new job.
		 * 
		 * @param startY
		 *            start y value
		 * @param endY
		 *            end y value
		 * @param offset
		 *            offset in array
		 * @param height
		 *            height
		 * @param width
		 *            width
		 * @param yAxis
		 *            {@link Point3D} y axis
		 * @param xAxis
		 *            {@link Point3D} y axis
		 * @param screenCorner
		 *            top left corner
		 * @param red
		 *            red color values
		 * @param green
		 *            green color values
		 * @param blue
		 *            blue color values
		 * @param scene
		 *            {@link Scene} object in which all {@link GraphicalObject}s
		 *            are contained
		 * @param horizontal
		 *            horizontal
		 * @param vertical
		 *            vertical
		 * @param eye
		 *            eye point
		 * @param rgb
		 *            RGB color values
		 */
		public CalculationWork(final int startY, final int endY, final int offset, final int height, final int width,
				final Point3D yAxis, final Point3D xAxis, final Point3D screenCorner, final short[] red,
				final short[] green, final short[] blue, final Scene scene, final double horizontal,
				final double vertical, final Point3D eye, final short[] rgb) {
			super();
			this.startY = startY;
			this.endY = endY;
			this.offset = offset;
			this.height = height;
			this.width = width;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.screenCorner = screenCorner;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.scene = scene;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.eye = eye;
			this.rgb = rgb;
		}

		@Override
		protected void compute() {
			if ((endY - startY) <= THRESHOLD) {
				computeDirect();
				return;

			} else {
				invokeAll(
						new CalculationWork(startY, startY + (endY - startY) / 2, offset, height, width, yAxis, xAxis,
								screenCorner, red, green, blue, scene, horizontal, vertical, eye, rgb),
						new CalculationWork(startY + (endY - startY) / 2 + 1, endY, offset, height, width, yAxis, xAxis,
								screenCorner, red, green, blue, scene, horizontal, vertical, eye, rgb));
			}

		}

		/**
		 * Calculates the pixel coloring.
		 */
		private void computeDirect() {
			if (startY > 0) {
				offset = (startY - 1) * width;
			} else {
				offset = 0;
			}

			endY = endY == height ? endY - 1 : endY;

			for (int y = startY; y <= endY; y++) {
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
		}

	}

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

				final int offset = 0;

				final ForkJoinPool pool = new ForkJoinPool();

				final CalculationWork work = new CalculationWork(0, height, offset, height, width, yAxis, xAxis,
						screenCorner, red, green, blue, scene, horizontal, vertical, eye, rgb);
				pool.invoke(work);

				pool.shutdown();

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
