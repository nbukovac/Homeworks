package hr.fer.zemris.java.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * Program which draws {@link GeometricShape}s on {@link BWRaster} and then
 * using a {@link RasterView} display the raster representation to the user.
 * User has to enter one or two console arguments to specify {@link BWRaster}
 * width and height.
 * 
 * @see GeometricShape
 * @see BWRaster
 * @see RasterView
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Demo {

	/**
	 * Adds a new {@link Circle} to <code>array</code> to the specified
	 * position. {@link Circle} arguments are positioned in
	 * <code>splitted</code> array.
	 * 
	 * @see Circle
	 * 
	 * @param array
	 *            containing object array
	 * @param position
	 *            next position in array
	 * @param splitted
	 *            array with arguments
	 */
	private static void addNewCircle(final Object[] array, final int position, final String[] splitted) {
		final int x = Integer.parseInt(splitted[1]);
		final int y = Integer.parseInt(splitted[2]);
		final int radius = Integer.parseInt(splitted[3]);

		array[position] = new Circle(x, y, radius);

	}

	/**
	 * Adds a new {@link Ellipse} to <code>array</code> to the specified
	 * position. {@link Ellipse} arguments are positioned in
	 * <code>splitted</code> array.
	 * 
	 * @see Ellipse
	 * 
	 * @param array
	 *            containing object array
	 * @param position
	 *            next position in array
	 * @param splitted
	 *            array with arguments
	 */
	private static void addNewEllipse(final Object[] array, final int position, final String[] splitted) {
		final int x = Integer.parseInt(splitted[1]);
		final int y = Integer.parseInt(splitted[2]);
		final int horizontal = Integer.parseInt(splitted[3]);
		final int vertical = Integer.parseInt(splitted[4]);

		array[position] = new Ellipse(x, y, horizontal, vertical);
	}

	/**
	 * Adds a new {@link Rectangle} to <code>array</code> to the specified
	 * position. {@link Rectangle} arguments are positioned in
	 * <code>splitted</code> array.
	 * 
	 * @see Rectangle
	 * 
	 * @param array
	 *            containing object array
	 * @param position
	 *            next position in array
	 * @param splitted
	 *            array with arguments
	 */
	private static void addNewRectangle(final Object[] array, final int position, final String[] splitted) {
		final int x = Integer.parseInt(splitted[1]);
		final int y = Integer.parseInt(splitted[2]);
		final int width = Integer.parseInt(splitted[3]);
		final int height = Integer.parseInt(splitted[4]);

		array[position] = new Rectangle(x, y, width, height);
	}

	/**
	 * Adds a new {@link Square} to <code>array</code> to the specified
	 * position. {@link Square} arguments are positioned in
	 * <code>splitted</code> array.
	 * 
	 * @see Square
	 * 
	 * @param array
	 *            containing object array
	 * @param position
	 *            next position in array
	 * @param splitted
	 *            array with arguments
	 */
	private static void addNewSquare(final Object[] array, final int position, final String[] splitted) {
		final int x = Integer.parseInt(splitted[1]);
		final int y = Integer.parseInt(splitted[2]);
		final int size = Integer.parseInt(splitted[3]);

		array[position] = new Square(x, y, size);
	}

	/**
	 * Checks the number of console arguments and sets raster accordingly. If
	 * there is only one argument <code>raster</code> width and height is set to
	 * this number value. If there are two arguments <code>raster</code> width
	 * is set to the first argument and width to second. For any other number of
	 * arguments close the program.
	 * 
	 * @see BWRaster
	 * 
	 * @param args
	 *            console arguments
	 * @param raster
	 *            {@link BWRaster} for setting
	 * @return set {@link BWRaster}
	 */
	private static BWRaster consoleArgumentCheck(final String[] args, BWRaster raster) {
		if (args.length == 1) {
			try {
				final int number = Integer.parseInt(args[0]);
				raster = new BWRasterMem(number, number);
			} catch (final IllegalArgumentException e) {
				System.err.println("The provided argument isn't a valid integer");
				System.exit(1);

			}

		} else if (args.length == 2) {
			try {
				final int width = Integer.parseInt(args[0]);
				final int height = Integer.parseInt(args[1]);
				raster = new BWRasterMem(width, height);
			} catch (final IllegalArgumentException e) {
				System.err.println("The provided argument isn't a valid integer");
				System.exit(2);

			}
		} else {
			System.err.println("Invalid number of arguments. Enter 1 or 2 integer arguments");
			System.exit(3);

		}

		return raster;
	}

	/**
	 * Draws {@link GeometricShape}s from <code>array</code> on
	 * <code>raster</code>.
	 * 
	 * @see BWRaster
	 * 
	 * @param raster
	 *            {@link BWRaster} used to draw on
	 * @param array
	 *            containing object array
	 * @return {@link BWRaster} with drawn shapes
	 */
	private static BWRaster drawOnRaster(final BWRaster raster, final Object[] array) {
		boolean flipEnabled = false;

		for (final Object o : array) {
			if (o == null) {
				flipEnabled = !flipEnabled;

				if (flipEnabled) {
					raster.enableFlipMode();
				} else {
					raster.disableFlipMode();
				}
			} else {
				final GeometricShape shape = (GeometricShape) o;
				shape.draw(raster);

			}
		}

		return raster;
	}

	/**
	 * Entry point of the program. User has to enter one or two console
	 * arguments to specify {@link BWRaster} width and height. If there is only
	 * one argument <code>raster</code> width and height is set to this number
	 * value. If there are two arguments <code>raster</code> width is set to the
	 * first argument and width to second. For any other number of arguments
	 * close the program.
	 * 
	 * @param args
	 *            console arguments
	 */
	public static void main(final String[] args) {
		BWRaster raster = null;

		raster = consoleArgumentCheck(args, raster);

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int numberOfElements = 0;

		try {
			System.out.println("Please enter number of shapes you want to add");
			final String line = reader.readLine();
			numberOfElements = Integer.parseInt(line.trim());
		} catch (IOException | NumberFormatException e) {
			System.err.println("Number of commands was expected.");
			System.exit(4);

		}

		final Object[] array = new Object[numberOfElements];
		int position = 0;

		System.out.println("Enter shapes:");

		while (true) {
			try {
				final String line = reader.readLine();

				if (line.trim().isEmpty()) {
					break;
				}

				final String[] splitted = line.split("\\s+");

				if (position < numberOfElements) {
					if (splitted[0].toLowerCase().equals("flip")) {
						array[position] = null;

					} else if (splitted[0].toLowerCase().equals("square")) {
						addNewSquare(array, position, splitted);

					} else if (splitted[0].toLowerCase().equals("rectangle")) {
						addNewRectangle(array, position, splitted);

					} else if (splitted[0].toLowerCase().equals("ellipse")) {
						addNewEllipse(array, position, splitted);

					} else if (splitted[0].toLowerCase().equals("circle")) {
						addNewCircle(array, position, splitted);

					} else {
						System.err.println("Not supported shape.");
						break;
					}
				}
			} catch (final IOException | NumberFormatException e) {
				System.err.println("An error occurred during the process of reading user input");
				System.exit(5);

			}

			position++;

		}

		raster = drawOnRaster(raster, array);

		final RasterView view = new SimpleRasterView();

		view.produce(raster);
	}
}
