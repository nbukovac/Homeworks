package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Class that implements the {@link IntegerStorageObserver} and as a action it
 * prints out double of the current value of the stored value in the
 * {@link IntegerStorage}. This observer is removed from the list of active
 * observers when it is called the specified number of times.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of times this observer is notified of the value change
	 */
	private int callsLeft;

	/**
	 * Constructs a new {@link DoubleValue} observer with the specified number
	 * of calls.
	 * 
	 * @param n
	 *            number of times this observer is notified
	 */
	public DoubleValue(final int n) {
		super();
		callsLeft = n;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(final IntegerStorage storage) {
		if (callsLeft > 0) {
			System.out.println(String.format("Double value %d", storage.getValue() * 2));

			callsLeft--;
		} else {
			storage.removeObserver(this);
		}
	}

}
