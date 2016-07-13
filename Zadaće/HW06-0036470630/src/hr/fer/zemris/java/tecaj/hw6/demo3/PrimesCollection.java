package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that implements {@link Iterable} and with the help of
 * {@link PrimesIterator} iterates over prime numbers
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Class that implements {@link Iterator} and iterates over prime numbers.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	private class PrimesIterator implements Iterator<Integer> {

		/**
		 * Number of elements left to show
		 */
		private int elementsLeft = numberOfElements;

		/**
		 * Current element in collection
		 */
		private int currentElement = 1;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			return elementsLeft > 0;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("There are no more elements in this collection");
			}

			boolean prost = false;

			while (!prost) {
				currentElement++;
				prost = true;

				for (int i = 2, end = (int) Math.sqrt(currentElement); i <= end; i++) {
					if (currentElement % i == 0) {
						prost = false;
						break;
					}
				}
			}

			elementsLeft--;

			return currentElement;
		}

	}

	/**
	 * Number of elements in collection
	 */
	private final int numberOfElements;

	/**
	 * Constructs a new {@link PrimesCollection} with the specified number of
	 * elements.
	 * 
	 * @param numberOfElements
	 *            number of prime numbers to show
	 */
	public PrimesCollection(final int numberOfElements) {
		if (numberOfElements < 1) {
			throw new IllegalArgumentException("Size of PrimeCollection has to be at least 1");
		}

		this.numberOfElements = numberOfElements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}
}
