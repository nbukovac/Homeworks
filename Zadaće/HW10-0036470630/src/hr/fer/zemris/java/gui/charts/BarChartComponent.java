package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Class that extends {@link JComponent} and represents a bar chart.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class BarChartComponent extends JComponent {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Gap between the edges and between certain components
	 */
	private static final int GAP = 10;

	/**
	 * {@link BarChart} with the required information
	 */
	private final BarChart barChart;

	/**
	 * {@link Rectangle} containing the top left corner and dimensions where
	 * this {@link Component} is positioned
	 */
	private Rectangle componentRectangle;

	/**
	 * Constructs a new {@link BarChartComponent} with the specified
	 * {@link BarChart} provided as {@code barChart}.
	 * 
	 * @param barChart
	 *            {@link BarChart} with the required information
	 */
	public BarChartComponent(final BarChart barChart) {
		super();
		this.barChart = barChart;
	}

	/**
	 * Draws the axis lines upon the provided {@link Graphics} object.
	 * 
	 * @param g
	 *            {@link Graphics} object we are drawing upon
	 * @param fontMetrics
	 *            {@link FontMetrics} object containing information about the
	 *            current {@link Font}
	 * @param yAxisX
	 *            x coordinate of the y axis
	 * @param yAxisBottomY
	 *            y coordinate of the bottom of the y axis
	 */
	private void drawAxisLines(final Graphics g, final FontMetrics fontMetrics, final int yAxisX,
			final int yAxisBottomY) {
		final Color currentColor = g.getColor();

		g.setColor(Color.GRAY);
		g.drawLine(yAxisX, yAxisBottomY, yAxisX, componentRectangle.y + GAP);
		g.drawLine(yAxisX, yAxisBottomY, componentRectangle.width - GAP, yAxisBottomY);

		g.setColor(currentColor);
	}

	/**
	 * Draws the bars specified by the {@link XYValue}s contained in the
	 * {@code barChart} upon the provided {@link Graphics} object.
	 * 
	 * @param g
	 *            {@link Graphics} object we are drawing upon
	 * @param yAxisX
	 *            x coordinate of the y axis
	 * @param yAxisBottomY
	 *            y coordinate of the bottom of the y axis
	 * @param yAxisSize
	 *            size of the y axis
	 * @param numberOfYLabels
	 *            number of labels on the y axis
	 * @param xAxisLabelSize
	 *            size of 1 label on the x axis
	 */
	private void drawBars(final Graphics g, final int yAxisX, final int yAxisBottomY, final int yAxisSize,
			final int numberOfYLabels, final int xAxisLabelSize) {
		final List<XYValue> barChartValues = barChart.getValues();
		int i = 0;

		for (final XYValue value : barChartValues) {
			final int rectangleXPosition = yAxisX + i * xAxisLabelSize;
			final int rectangleYPosition = yAxisBottomY
					- (yAxisSize / numberOfYLabels) * (value.getY() / barChart.getyGap());
			final int rectangleWidth = xAxisLabelSize;
			final int rectangleHeight = yAxisBottomY - rectangleYPosition;

			g.setColor(Color.RED);
			g.fillRect(rectangleXPosition, rectangleYPosition, rectangleWidth, rectangleHeight);

			if (i < barChartValues.size() - 1) {
				g.setColor(Color.WHITE);
				g.drawLine(rectangleXPosition + rectangleWidth - 1, yAxisBottomY - 1,
						rectangleXPosition + rectangleWidth - 1, rectangleYPosition);
			}
			i++;
		}
	}

	/**
	 * Draws the labels on the x axis upon the provided {@link Graphics} object.
	 * 
	 * @param g
	 *            {@link Graphics} object we are drawing upon
	 * @param fontMetrics
	 *            {@link FontMetrics} object containing information about the
	 *            current {@link Font}
	 * @param yAxisX
	 *            x coordinate of the y axis
	 * @param yAxisBottomY
	 *            y coordinate of the bottom of the y axis
	 * @param currentColor
	 *            current {@link Color}
	 * @param numberOfXLabels
	 *            number of labels on the x axis
	 * @param xAxisLabelSize
	 *            size of a label on the x axis
	 */
	private void drawXAxisLabels(final Graphics g, final FontMetrics fontMetrics, final int yAxisX,
			final int yAxisBottomY, final Color currentColor, final int numberOfXLabels, final int xAxisLabelSize) {
		for (int i = 0; i < numberOfXLabels; i++) {
			final int xLabelPosition = yAxisX + xAxisLabelSize * i;

			if (i > 0) {
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(xLabelPosition, yAxisBottomY, xLabelPosition, componentRectangle.y + GAP);
			}

			g.setColor(currentColor);
			g.drawString(barChart.getValues().get(i).getX() + "", xLabelPosition + xAxisLabelSize / 2,
					yAxisBottomY + GAP + fontMetrics.getAscent() / 2);
		}
	}

	/**
	 * Draws the labels on the y axis upon the provided {@link Graphics} object.
	 * 
	 * @param g
	 *            {@link Graphics} object we are drawing upon
	 * @param fontMetrics
	 *            {@link FontMetrics} object containing information about the
	 *            current {@link Font}
	 * @param yAxisX
	 *            x coordinate of the y axis
	 * @param yAxisBottomY
	 *            y coordinate of the bottom of the y axis
	 * @param yAxisSize
	 *            size of the y axis
	 * @param numberOfYLabels
	 *            number of labels on the y axis
	 * @param currentColor
	 *            current {@link Color}
	 */
	private void drawYAxisLabels(final Graphics g, final FontMetrics fontMetrics, final int yAxisX,
			final int yAxisBottomY, final int yAxisSize, final int numberOfYLabels, final Color currentColor) {
		for (int i = 0, labelValue = barChart.getMinimumY(); i <= numberOfYLabels; i++) {
			final int yLabelPosition = yAxisBottomY - (yAxisSize / numberOfYLabels) * i + fontMetrics.getAscent() / 2;
			final String label = String.format("%" + getWidestLabelStringSize() + "d", labelValue);
			g.drawString(label, yAxisX - fontMetrics.stringWidth(label), yLabelPosition);

			final int yLinePosition = yLabelPosition - fontMetrics.getAscent() / 2;
			if (i > 0) {
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(yAxisX, yLinePosition, componentRectangle.width - GAP, yLinePosition);
			}

			g.setColor(currentColor);
			labelValue += barChart.getyGap();
		}
	}

	/**
	 * Returns the number of digits that the biggest y label has.
	 * 
	 * @return number of digits of the widest label
	 */
	private int getWidestLabelStringSize() {
		final List<XYValue> barChartValues = barChart.getValues();
		int maximum = 0;

		for (final XYValue xyValue : barChartValues) {
			maximum = Math.max(maximum, xyValue.getY());
		}

		return Integer.valueOf(maximum).toString().length();
	}

	/**
	 * Returns the number of pixels that the maximum y label is occupying.
	 * 
	 * @param fontMetrics
	 *            {@link FontMetrics} object
	 * @return number of pixels
	 */
	private int getWidestYLabel(final FontMetrics fontMetrics) {
		return fontMetrics.stringWidth(barChart.getMaximumY() + "");
	}

	@Override
	protected void paintComponent(final Graphics g) {
		final Insets insets = getInsets();
		final Dimension dimension = getSize();
		setOpaque(true);
		setBackground(Color.WHITE);

		componentRectangle = new Rectangle(insets.left, insets.top, dimension.width - insets.left - insets.right,
				dimension.height - insets.top - insets.bottom);

		final FontMetrics fontMetrics = g.getFontMetrics();
		final int yDescriptionWidth = fontMetrics.getAscent();

		final int yAxisX = componentRectangle.x + 2 * GAP + yDescriptionWidth + getWidestYLabel(fontMetrics);
		final int yAxisBottomY = componentRectangle.y + componentRectangle.height - 2 * GAP
				- 2 * fontMetrics.getAscent();
		drawAxisLines(g, fontMetrics, yAxisX, yAxisBottomY);

		setAxisDescriptions(g, yAxisX);

		final int yAxisSize = yAxisBottomY - GAP;
		final int numberOfYLabels = (barChart.getMaximumY() - barChart.getMinimumY()) / barChart.getyGap();

		final Color currentColor = g.getColor();

		drawYAxisLabels(g, fontMetrics, yAxisX, yAxisBottomY, yAxisSize, numberOfYLabels, currentColor);

		final int xAxisSize = componentRectangle.width - GAP - yAxisX;
		final int numberOfXLabels = barChart.getValues().size();
		final int xAxisLabelSize = xAxisSize / numberOfXLabels;

		drawXAxisLabels(g, fontMetrics, yAxisX, yAxisBottomY, currentColor, numberOfXLabels, xAxisLabelSize);

		drawBars(g, yAxisX, yAxisBottomY, yAxisSize, numberOfYLabels, xAxisLabelSize);

		g.setColor(Color.GRAY);

		g.drawPolygon(new int[] { yAxisX - 4, yAxisX, yAxisX + 4 },
				new int[] { componentRectangle.y + GAP, componentRectangle.y + 4, componentRectangle.y + GAP }, 3);
		g.drawPolygon(
				new int[] { componentRectangle.width - GAP, componentRectangle.width - 4,
						componentRectangle.width - GAP },
				new int[] { yAxisBottomY - 4, yAxisBottomY, yAxisBottomY + 4 }, 3);

	}

	/**
	 * Sets the descriptions for both axis that are contained in
	 * {@code barChart}.
	 * 
	 * @param g
	 *            {@link Graphics} object we are drawing upon
	 * @param yAxisX
	 *            x coordinate of the y axis
	 */
	private void setAxisDescriptions(final Graphics g, final int yAxisX) {
		final Graphics2D g2d = (Graphics2D) g;
		final AffineTransform defaultAt = g2d.getTransform();
		g2d.setTransform(AffineTransform.getQuadrantRotateInstance(3));

		g2d.drawString(barChart.getyDescription(), -(componentRectangle.height - componentRectangle.y - GAP) / 2,
				componentRectangle.x + 2 * GAP);

		g2d.setTransform(defaultAt);

		g.drawString(barChart.getxDescription(), (componentRectangle.width - componentRectangle.x - yAxisX) / 2,
				componentRectangle.height - GAP);
	}

}
