package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * GUI program that demonstrates usage of {@link BarChartComponent} and the
 * associated {@link BarChart} to show a graphical representation of the data
 * stored in the user provided file. User has to provide the path to the file
 * that contains valid data so that it can be parsed.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class BarChartDemo extends JFrame {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link BarChart} object with the required info
	 */
	private static BarChart barChart;

	/**
	 * Source file
	 */
	private static File fileSource;

	/**
	 * Extracts the info from the {@code sourceFile} required for a
	 * {@link BarChart} creation. If the provided file isn't formatted correctly
	 * or it doesn't exist an message dialog will be shown with the error.
	 * 
	 * @param sourceFile
	 *            source file
	 */
	private static void createBarChart(final String sourceFile) {
		try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
			final String xDescription = reader.readLine().trim();
			final String yDescription = reader.readLine().trim();
			final List<XYValue> values = new ArrayList<>();

			final String[] readValues = reader.readLine().trim().split("\\s+");

			for (int i = 0; i < readValues.length; i++) {
				final String[] split = readValues[i].split(",");

				if (split.length == 2) {
					try {
						final int x = Integer.parseInt(split[0]);
						final int y = Integer.parseInt(split[1]);
						values.add(new XYValue(x, y));
					} catch (final NumberFormatException e) {
						JOptionPane.showConfirmDialog(null, "The provided file isn't formatted validly");
						System.exit(-3);
					}
				}
			}

			Collections.sort(values, (x, y) -> x.getX() - y.getX());

			int minY = 0;
			int maxY = 0;
			int gapY = 0;

			try {
				minY = Integer.parseInt(reader.readLine().trim());
				maxY = Integer.parseInt(reader.readLine().trim());
				gapY = Integer.parseInt(reader.readLine().trim());
			} catch (final NumberFormatException e) {
				JOptionPane.showConfirmDialog(null, "The provided file isn't formatted validly");
				System.exit(-4);
			}

			barChart = new BarChart(values, xDescription, yDescription, minY, maxY, gapY);

		} catch (final IOException e) {
			JOptionPane.showConfirmDialog(null, "The provided file doesn't exist");
			System.exit(-2);
		}

	}

	/**
	 * Program entry point used to start the GUI.
	 * 
	 * @param args
	 *            file source
	 */
	public static void main(final String[] args) {
		if (args.length != 1) {
			JOptionPane.showMessageDialog(null,
					"You didn't provide a valid number of arguments. " + "Only 1 argument is required");
			System.exit(-1);
		}

		fileSource = new File(args[0]);
		createBarChart(args[0]);
		SwingUtilities.invokeLater(() -> {
			new BarChartDemo().setVisible(true);
		});
	}

	/**
	 * Constructs a new {@link BarChartDemo} and initializes the GUI.
	 */
	public BarChartDemo() {
		setTitle("BarChart demo");
		setSize(500, 500);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Initializes the GUI by setting the {@link BarChartComponent} with the
	 * constructed {@code barChart}.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		final BarChartComponent barChartComponent = new BarChartComponent(barChart);

		getContentPane().add(barChartComponent, BorderLayout.CENTER);
		getContentPane().add(new JLabel("Source file " + fileSource.getAbsolutePath(), SwingConstants.CENTER),
				BorderLayout.PAGE_START);

	}
}
