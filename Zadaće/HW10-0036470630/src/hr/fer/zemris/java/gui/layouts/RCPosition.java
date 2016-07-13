package hr.fer.zemris.java.gui.layouts;

/**
 * Class that specifies a table position that is determined by a row position
 * and a column position. It is primarily used to specify a position in
 * {@link CalcLayout}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class RCPosition {

	/**
	 * Row position
	 */
	private final int row;

	/**
	 * Column position
	 */
	private final int column;

	/**
	 * Constructs a new {@link RCPosition} with the specified {@code row} and
	 * {@code column} values. An {@link IllegalArgumentException} is thrown if
	 * either {@code row} or {@code column} is &lt; 0.
	 * 
	 * @param row
	 *            row position
	 * @param column
	 *            column position
	 * @throws IllegalArgumentException
	 *             if either {@code row} or {@code column} is &lt; 0.
	 */
	public RCPosition(final int row, final int column) {
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException("Both row and column have to be positive values");
		}

		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the column position.
	 * 
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns the row position
	 * 
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

}
