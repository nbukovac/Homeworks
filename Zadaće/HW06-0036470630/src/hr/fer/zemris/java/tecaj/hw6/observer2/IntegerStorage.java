package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw6.helpers.RequirementHelper;

/**
 * Stores a {@link Integer} value and a list of {@link IntegerStorageObserver}s.
 * On value change every observer that listens for changes performs its defined
 * actions.
 * 
 * @see "https://en.wikipedia.org/wiki/Observer_pattern"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class IntegerStorage {

	/**
	 * Stored value
	 */
	private int value;

	/**
	 * Observers listening for value change
	 */
	private final List<IntegerStorageObserver> observers;

	/**
	 * Observers specified for removal from currently active observers
	 */
	private final List<IntegerStorageObserver> removal;

	/**
	 * Constructs a new {@link IntegerStorage} with the specified value.
	 * 
	 * @param value
	 *            initial value
	 */
	public IntegerStorage(final int value) {
		super();
		this.value = value;
		observers = new ArrayList<>();
		removal = new ArrayList<>();
	}

	/**
	 * Adds a {@link IntegerStorageObserver} to the list of active observers. An
	 * {@link IllegalArgumentException} is thrown if a null value is passed as
	 * {@code observer} argument.
	 * 
	 * @param observer
	 *            {@link IntegerStorageObserver} to add to the active observer
	 *            list
	 * @throws IllegalArgumentException
	 *             if observer is null
	 */
	public void addObserver(final IntegerStorageObserver observer) {
		RequirementHelper.checkArgumentNull(observer,
				"Null reference can't be added to a list of IntegerStorageObservers");

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes all observers from the active observer list.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Returns stored value.
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Removes all observers specified for removal from the active observer list
	 */
	private void removeFromObservers() {
		for (final IntegerStorageObserver observer : removal) {
			observers.remove(observer);
		}

		removal.clear();
	}

	/**
	 * Specifies a {@link IntegerStorageObserver} for future removal from the
	 * active observer list.
	 * 
	 * @param observer
	 *            {@link IntegerStorageObserver} specified for removal
	 */
	public void removeObserver(final IntegerStorageObserver observer) {
		removal.add(observer);
	}

	/**
	 * Sets the stored value to the specified value only if it is different from
	 * the stored value. If a value change has happened every observer in the
	 * active observer list is notified and the appropriate action is performed.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(final int value) {
		if (this.value != value) {
			final IntegerStorageChange storageChange = new IntegerStorageChange(this, this.value, value);
			this.value = value;

			if (removal.size() > 0) {
				removeFromObservers();
			}

			if (observers.size() > 0) {
				for (final IntegerStorageObserver observer : observers) {
					observer.valueChanged(storageChange);
				}

				removeFromObservers();
			}
		}
	}

}
