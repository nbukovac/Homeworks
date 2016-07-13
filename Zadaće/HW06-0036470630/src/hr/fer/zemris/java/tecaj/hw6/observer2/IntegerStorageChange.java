package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Class that tracks changes in {@link IntegerStorage}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class IntegerStorageChange {

	/**
	 * {@link IntegerStorage} that contains a int value
	 */
	private final IntegerStorage integerStorage;

	/**
	 * Value stored before the change
	 */
	private final int valueBeforeChange;

	/**
	 * New value stored in {@code integerStorage}
	 */
	private final int newValue;

	/**
	 * Constructs a new {@link IntegerStorageChange} with the specified values.
	 * 
	 * @param integerStorage
	 *            reference to the {@link IntegerStorage} used to track changes
	 * @param valueBeforeChange
	 *            value before value change
	 * @param newValue
	 *            new value
	 */
	public IntegerStorageChange(final IntegerStorage integerStorage, final int valueBeforeChange, final int newValue) {
		super();
		this.integerStorage = integerStorage;
		this.valueBeforeChange = valueBeforeChange;
		this.newValue = newValue;
	}

	/**
	 * Return the {@link IntegerStorage}
	 * 
	 * @return the integerStorage
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}

	/**
	 * Returns the new value stored in {@code integerStorage}
	 * 
	 * @return the newValue
	 */
	public int getNewValue() {
		return newValue;
	}

	/**
	 * Returns the value stored in {@code integerStorage} before value change
	 * 
	 * @return the valueBeforeChange
	 */
	public int getValueBeforeChange() {
		return valueBeforeChange;
	}

}
