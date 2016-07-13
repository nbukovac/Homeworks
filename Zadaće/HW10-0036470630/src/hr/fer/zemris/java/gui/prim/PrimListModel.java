package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class that implements {@link ListModel} and provides a model for calculating
 * prime numbers.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * Prime numbers
	 */
	private final List<Integer> primeNumbers;

	/**
	 * {@link ListDataListener}s observing changes
	 */
	private final List<ListDataListener> listeners;

	/**
	 * Constructs a new {@link PrimListModel} with the first element set to 1.
	 */
	public PrimListModel() {
		primeNumbers = new ArrayList<>();
		listeners = new ArrayList<>();

		primeNumbers.add(Integer.valueOf(1));
	}

	@Override
	public void addListDataListener(final ListDataListener listDataListener) {
		listeners.add(listDataListener);
	}

	/**
	 * Adds a new prime number to the {@code primeNumbers} list and alerts all
	 * the {@code listeners}.
	 */
	public void addPrimNumber() {
		final int position = primeNumbers.size();
		primeNumbers.add(nextPrimNumber());

		final ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);

		for (final ListDataListener listDataListener : listeners) {
			listDataListener.intervalAdded(event);
		}
	}

	@Override
	public Integer getElementAt(final int index) {
		return primeNumbers.get(index);
	}

	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	/**
	 * Calculates the next prime number.
	 * 
	 * @return next prime number
	 */
	private int nextPrimNumber() {
		final int lastPrimNumber = primeNumbers.get(primeNumbers.size() - 1);
		int primNumber = lastPrimNumber;
		boolean notPrim = true;

		while (notPrim) {
			primNumber++;
			notPrim = false;

			for (int i = 2, root = (int) Math.sqrt(primNumber); i <= root; i++) {
				if (primNumber % i == 0) {
					notPrim = true;
					break;
				}
			}
		}

		return primNumber;
	}

	@Override
	public void removeListDataListener(final ListDataListener listDataListener) {
		listeners.remove(listDataListener);
	}

}
