package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Class that implements {@link IntegerStorageObserver} and as action it counts
 * the number of value changes in {@link IntegerStorageChange}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Change counter
	 */
	private int changeCounter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(final IntegerStorageChange storage) {
		changeCounter++;

		System.out.println("Number of value changes since tracking: " + changeCounter);
	}

}
