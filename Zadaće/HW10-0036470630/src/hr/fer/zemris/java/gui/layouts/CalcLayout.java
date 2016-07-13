package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class that implements {@link LayoutManager2} and thus represents a layout for
 * swing applications. This layout is laid out as a calculator and has a grid of
 * 5 rows and 7 columns with a special area that span across 5 table fields.
 * <br/>
 * It is possible to specify a position either using a {@link RCPosition} object
 * or using a formatted {@link String}.Valid row positions are [1, {@code ROWS}
 * ], valid column positions are [1, {@code COLUMNS}], there is a
 * {@link Component} with a special size and it is located at position
 * {@code 1,1}, because of it positions {@code (1,2), (1,3), (1,4), (1,5)} are
 * also invalid.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Interface used to implement a strategy {@link Component} size getting.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	private interface SizeGetter {
		/**
		 * Returns the {@link Component} size specified with a {@link Dimension}
		 * object.
		 * 
		 * @param component
		 *            {@link Component} object
		 * @return size specified with a {@link Dimension}
		 */
		Dimension getSize(Component component);
	}

	/**
	 * Number of rows specified by {@link CalcLayout}
	 */
	private static final int ROWS = 5;

	/**
	 * Number of columns specified by {@link CalcLayout}
	 */
	private static final int COLUMNS = 7;

	/**
	 * Gap between two {@link Component}s
	 */
	private final int gap;

	/**
	 * {@link Map} of {@link Component}s and their positions defined with a
	 * {@link String}
	 */
	private final Map<Component, String> components;

	/**
	 * Constructs a new {@link CalcLayout} with a predefined {@code gap} that is
	 * equal to 0.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructs a new {@link CalcLayout} with a specified {@code gap}.
	 * 
	 * @param gap
	 *            gap between two {@link Component}s
	 */
	public CalcLayout(final int gap) {
		this.gap = gap;
		components = new HashMap<>();
	}

	@Override
	public void addLayoutComponent(final Component comp, final Object constraints) {
		if (constraints instanceof String) {
			addLayoutComponent((String) constraints, comp);
			return;
		}

		if (!(constraints instanceof RCPosition)) {
			throw new IllegalArgumentException("This layout manager obtains component positions using a RCPosition"
					+ " object, so please provide one");
		}

		final RCPosition position = (RCPosition) constraints;

		if (!checkCoordinates(position.getRow(), position.getColumn())) {
			throw new IllegalArgumentException("The provided coordinates are not int he valid range. Check the"
					+ " documentation for more information");
		}

		final String positionValue = position.getRow() + "," + position.getColumn();

		final String existing = components.get(comp);
		if (existing != null) {
			throw new IllegalArgumentException("There is already a component at the specified position");
		} else {
			components.put(comp, positionValue);
		}

	}

	@Override
	public void addLayoutComponent(final String name, final Component comp) {
		final String existing = components.get(comp);
		if (existing != null) {
			throw new IllegalArgumentException("There is already a component at the specified position");
		}

		final String[] coordinates = name.split(",");

		if (coordinates.length != 2) {
			throw new IllegalArgumentException("Invalid number of coordinates provided by the argument name");
		} else {
			try {
				final int row = Integer.parseInt(coordinates[0]);
				final int column = Integer.parseInt(coordinates[1]);

				if (!checkCoordinates(row, column)) {
					throw new IllegalArgumentException("The provided coordinates are not int he valid range. Check the"
							+ " documentation for more information");
				}

				components.put(comp, name);

			} catch (final NumberFormatException e) {
				throw new IllegalArgumentException("Coordinates provided as a string aren't valid integers");
			}
		}

	}

	/**
	 * Calculates the {@link Dimension} defined by the {@link SizeGetter}.
	 * 
	 * @param parent
	 *            parent {@link Container}
	 * @param sizeGetter
	 *            {@link SizeGetter} that defines the {@link Dimension}
	 *            calculation
	 * @return calculated {@link Dimension}
	 */
	private Dimension calculateDimension(final Container parent, final SizeGetter sizeGetter) {
		final Dimension dimension = new Dimension(0, 0);

		for (int i = 0, n = parent.getComponentCount(); i < n; i++) {
			final Component component = parent.getComponent(i);
			final Dimension componentDimension = sizeGetter.getSize(component);

			if (componentDimension != null) {
				dimension.width = Math.max(dimension.width, componentDimension.width);
				dimension.height = Math.max(dimension.height, componentDimension.height);
			}
		}

		dimension.width = COLUMNS * dimension.width + (COLUMNS - 1) * gap;
		dimension.height = ROWS * dimension.height + (ROWS - 1) * gap;

		final Insets parentInsets = parent.getInsets();
		dimension.width += parentInsets.left + parentInsets.right;
		dimension.height += parentInsets.bottom + parentInsets.top;

		return dimension;
	}

	/**
	 * Checks if the provided {@code row} and {@code column} positions are valid
	 * for this {@link CalcLayout}. Valid row positions are [1, {@code ROWS}],
	 * valid column positions are [1, {@code COLUMNS}], there is a
	 * {@link Component} with a special size and it is located at position
	 * {@code 1,1}, because of it positions {@code (1,2), (1,3), (1,4), (1,5)}
	 * are also invalid.
	 * 
	 * @param row
	 *            row position
	 * @param column
	 *            column position
	 * @return true if position is valid, else false
	 */
	private boolean checkCoordinates(final int row, final int column) {
		boolean validCoordinates = true;

		if (row < 1 || row > ROWS || column < 1 || column > COLUMNS) {
			validCoordinates = false;
		}

		if (row == 1 && (column >= 2 && column <= 5)) {
			validCoordinates = false;
		}

		return validCoordinates;
	}

	@Override
	public float getLayoutAlignmentX(final Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(final Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void invalidateLayout(final Container target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void layoutContainer(final Container parent) {
		// parent.setPreferredSize(preferredLayoutSize(parent));

		final Insets parentInsets = parent.getInsets();
		final int width = parent.getWidth() - parentInsets.left - parentInsets.right - (COLUMNS - 1) * gap;
		final int height = parent.getHeight() - parentInsets.top - parentInsets.bottom - (ROWS - 1) * gap;

		final int elementWidth = Math.max(width / COLUMNS, 0);
		final int elementHeight = Math.max(height / ROWS, 0);

		for (final Entry<Component, String> componentEntry : components.entrySet()) {
			final Component component = componentEntry.getKey();
			final String[] position = componentEntry.getValue().split(",");

			final int row = Integer.parseInt(position[0]);
			final int column = Integer.parseInt(position[1]);

			if (row != 1 || column != 1) {
				component.setBounds(parentInsets.left + (column - 1) * (elementWidth + gap),
						parentInsets.top + (row - 1) * (elementHeight + gap), elementWidth, elementHeight);
			} else {
				component.setBounds(parentInsets.left, parentInsets.top,
						elementWidth * (COLUMNS - 2) + (COLUMNS - 3) * gap, elementHeight);
			}
		}
	}

	@Override
	public Dimension maximumLayoutSize(final Container target) {
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		return calculateDimension(parent, Component::getMinimumSize);
	}

	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		return calculateDimension(parent, Component::getPreferredSize);
	}

	@Override
	public void removeLayoutComponent(final Component comp) {
		components.remove(comp);
	}
}
