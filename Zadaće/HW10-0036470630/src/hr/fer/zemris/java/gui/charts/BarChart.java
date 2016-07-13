package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class that contains information for bar chart drawing. This information is
 * used by {@link BarChartComponent} to create a graphical representation of the
 * data using a bar chart.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class BarChart {

	/**
	 * {@link XYValue}s containing bar size information
	 */
	private final List<XYValue> values;

	/**
	 * X axis description
	 */
	private final String xDescription;

	/**
	 * Y axis description
	 */
	private final String yDescription;

	/**
	 * Minimum y axis value
	 */
	private final int minimumY;

	/**
	 * Maximum y axis value
	 */
	private final int maximumY;

	/**
	 * Y axis gap between values
	 */
	private final int yGap;

	/**
	 * Constructs a new {@link BarChart} with specified arguments.
	 * 
	 * @param values
	 *            {@link XYValue}s containing bar size information
	 * @param xDescription
	 *            x axis description
	 * @param yDescription
	 *            Y axis description
	 * @param minimumY
	 *            Minimum y axis value
	 * @param maximumY
	 *            Maximum y axis value
	 * @param yGap
	 *            Y axis gap between values
	 */
	public BarChart(final List<XYValue> values, final String xDescription, final String yDescription,
			final int minimumY, final int maximumY, final int yGap) {
		super();
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minimumY = minimumY;
		this.maximumY = maximumY;
		this.yGap = yGap;
	}

	/**
	 * Returns the maximum y axis value.
	 * 
	 * @return the maximumY
	 */
	public int getMaximumY() {
		return maximumY;
	}

	/**
	 * Returns the minimum y axis value.
	 * 
	 * @return the minimumY
	 */
	public int getMinimumY() {
		return minimumY;
	}

	/**
	 * Returns the {@link XYValue}s containing bar information.
	 * 
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Returns the x axis description.
	 * 
	 * @return the xDescription
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Returns the y axis description.
	 * 
	 * @return the yDescription
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Returns the gap between 2 values on the y axis.
	 * 
	 * @return the yGap
	 */
	public int getyGap() {
		return yGap;
	}

}
