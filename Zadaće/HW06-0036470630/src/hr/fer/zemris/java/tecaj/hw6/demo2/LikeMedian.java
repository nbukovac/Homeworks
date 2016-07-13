package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Class that is used to determine a median from the provided elements. Element
 * has to implement {@link Comparable} because only types that have specified a
 * natural order can be sorted and a median can be determined.
 * 
 * @author Nikola Bukovac
 * 
 * @param <T>
 *            type of elements that implement interface {@link Comparable}
 */
public class LikeMedian<T extends Comparable<T>> implements Iterable<T> {

	/**
	 * Elements stored inside the collection
	 */
	private final List<T> elements;

	/**
	 * {@link Comparator} used for sorting the elements stored in
	 * {@code elements} list
	 */
	private final Comparator<T> MEDIAN_COMPARATOR = (z1, z2) -> z1.compareTo(z2);

	/**
	 * Constructs a new {@link LikeMedian}.
	 */
	public LikeMedian() {
		super();
		elements = new ArrayList<>();
	}

	/**
	 * Adds a new element to the {@code elements} list.
	 * 
	 * @param element
	 *            new element
	 */
	public void add(final T element) {
		elements.add(element);
	}

	/**
	 * Returns a {@link Optional} with the median value stored in the
	 * {@code elements} list if it exists, if not the a {@code Optional.empty()}
	 * is returned.
	 * 
	 * @return {@code Optinal.of(found value)} if a value is found, else
	 *         {@code Optional.empty()}
	 */
	public Optional<T> get() {
		if (elements.size() == 0) {
			return Optional.empty();
		}

		final List<T> sortedList = new ArrayList<>();

		sortedList.addAll(elements);
		sortedList.sort(MEDIAN_COMPARATOR);

		final T value = sortedList.get((sortedList.size() - 1) / 2);

		if (value == null) {
			return Optional.empty();
		} else {
			return Optional.of(value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}

}
