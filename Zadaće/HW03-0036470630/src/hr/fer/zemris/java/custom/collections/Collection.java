package hr.fer.zemris.java.custom.collections;

/**
 * A base class used to specify the necessary methods a collection requires to
 * provide to the end user. Implements methods for adding, removing and finding
 * objects and it also has method for checking the collection size, and if it
 * empty.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Collection {

	/**
	 * Default constructor method for <code>Collection</code> class
	 */
	protected Collection() {

	}

	/**
	 * Adds a new element to the collection.
	 * 
	 * @param value
	 *            Object we want to add to the collection.
	 */
	public void add(final Object value) {
	}

	/**
	 * Adds all elements from a different collection to this collection. This is
	 * achieved using a <code>Processor</code> class and calling add method on
	 * every element of the other collection. If the other collection is null
	 * nothing happens.
	 * 
	 * @param other
	 *            source collection
	 */
	public void addAll(final Collection other) {
		if (other == null) {
			return;
		}

		class LocalProcessor extends Processor {

			@Override
			public void process(final Object value) {
				add(value);
			}
		}

		other.forEach(new LocalProcessor());
	}

	/**
	 * Removes all of the elements in this collection.
	 */
	public void clear() {
	}

	/**
	 * Checks if the collection contains the provided element.
	 * 
	 * @param value
	 *            Object we want to find in the collection
	 * @return <code>false</code> if <code>value</code> isn't found, else true
	 */
	public boolean contains(final Object value) {
		return false;
	}

	/**
	 * For every element in collection calls method process of the provided
	 * <code>Processor</code> class.
	 * 
	 * @param processor
	 *            specifies what will be done to the collection elements
	 */
	public void forEach(final Processor processor) {
	}

	/**
	 * Checks if a collection is empty. A collection is empty if it doesn't
	 * contain any elements in its container.
	 * 
	 * @return <code>true</code> if collection is empty, else <code>false</code>
	 *         .
	 */
	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}

	/**
	 * Removes the first occurrence of the provided element and returns
	 * <code>true</code> if the operation was successful or <code>false</code>
	 * if not.
	 * 
	 * @param value
	 *            Object we want to remove from the collection
	 * @return false if value wasn't found, else true
	 */
	public boolean remove(final Object value) {
		return false;
	}

	/**
	 * Returns the number of elements this <code>Collection</code> contains.
	 * 
	 * @return size of collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Returns the current collections elements as an Object array.
	 * 
	 * @return an Object array containing all the elements from this collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

}
