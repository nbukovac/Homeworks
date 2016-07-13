package hr.fer.zemris.java.custom.collections;

/**
 * Derivative class of class <code>Collection</code> that is implemented as an
 * array. Inherits all of <code>Collection</code> classes methods but also
 * provides some additional methods for adding, removing and finding contained
 * objects. Null references aren't allowed in this <code>Collection</code> but
 * duplicate elements are.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Returns the size of a <code>Collection</code>. If a collection is
	 * <code>null</code> returns 0.
	 * 
	 * @param collection
	 *            <code>Collection</code> to check
	 * @return size of <code>Collection</code> or 0 if it is null
	 */
	private static int checkCollection(final Collection collection) {
		return (collection == null) ? 0 : collection.size();
	}

	/** Number of elements currently in collection */
	private int size;

	/** Maximum number of elements in collection. Doubles when full. */
	private int capacity;

	/** Storage for the collection elements. */
	private Object[] elements;

	/**
	 * Default constructor which creates a collection with a capacity for 16
	 * elements
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Constructs a new <code>ArrayIndexedCollection</code> so that it contains
	 * all the elements as the provided collection. If the user provides a null
	 * reference as the <code>collection</code> argument, an exception will be
	 * thrown because the collection capacity can't be smaller than 1.
	 * 
	 * @param collection
	 *            source collection
	 * @throws IllegalArgumentException
	 *             if a null reference is provided as <code>collection</code>
	 *             argument
	 */
	public ArrayIndexedCollection(final Collection collection) {
		this(collection, checkCollection(collection));
	}

	/**
	 * Constructs a new <code>ArrayIndexedCollection</code> so that it contains
	 * all the elements as the provided collection and it also enables the user
	 * to specify the capacity of the collection. If the
	 * <code>initialCapacity</code> argument is smaller than the source
	 * collection size, <code>capacity</code> is set to the source collection
	 * size. If the user provides a null reference as the
	 * <code>collection</code> argument and doesn't set the
	 * <code>initialCapacity</code> to be bigger than 0, an exception will be
	 * thrown because the collection capacity can't be smaller than 1.
	 * 
	 * @param collection
	 *            source collection
	 * @param initialCapacity
	 *            sets the capacity of the collection
	 * @throws IllegalArgumentException
	 *             if a null reference is provided as <code>collection</code>
	 *             argument and <code>initialCapacity</code> isn't set to be
	 *             &lt; 0, or if <code>initialCapacity</code> is set to be &lt;=
	 *             0
	 */
	public ArrayIndexedCollection(final Collection collection, final int initialCapacity) {
		this(Math.max(checkCollection(collection), initialCapacity));
		addAll(collection);
	}

	/**
	 * Constructs a new <code>ArrayIndexedCollection</code> with a user defined
	 * capacity. The minimum capacity can be 1.
	 * 
	 * @param initialCapacity
	 *            sets the capacity of the collection
	 * @throws IllegalArgumentException
	 *             if <code>initialCapacity</code> is smaller than 1.
	 */
	public ArrayIndexedCollection(final int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity can't be < 1.");
		}

		capacity = initialCapacity;
		elements = new Object[capacity];
	}

	@Override
	/**
	 * The new element is added at the end of the <code>elements</code> array.
	 * Null references aren't allowed and thus a exception will be thrown. If
	 * the <code>elements</code> array is full, storage capacity will be doubled
	 * so the new element can be added
	 * 
	 * @throws IllegalArgumentException
	 *             if a null reference is provided as the value argument
	 */
	public void add(final Object value) {
		if (size == capacity) {
			expandElements();
		}

		checkNull(value);

		elements[size] = value;

		size++;
	}

	/**
	 * Checks if the provided <code>index</code> is &lt; 0 or &gt;= limit, if it
	 * is a {@link IndexOutOfBoundsException} is thrown.
	 * 
	 * @param index
	 *            value to check
	 * @param limit
	 *            upper limit
	 * @throws IndexOutOfBoundsException
	 *             if index is &lt; 0 or &gt;= limit
	 */
	private void checkIndex(final int index, final int limit) {
		if (index < 0 || index >= limit) {
			throw new IndexOutOfBoundsException("The provided index is out of range");
		}
	}

	/**
	 * Checks if a <code>Object</code> is a null reference, if it is throws an
	 * {@link IllegalArgumentException}.
	 * 
	 * @param value
	 *            Object to check
	 * @throws IllegalArgumentException
	 *             if value is a null reference
	 */
	private void checkNull(final Object value) {
		if (value == null) {
			throw new IllegalArgumentException("Null reference can't be added to this collection");
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	@Override
	public boolean contains(final Object value) {
		final int index = indexOf(value);

		if (index == -1) {
			return false;
		}

		return true;
	}

	/**
	 * Expands the <code>elements</code> field by doubling its capacity, when it
	 * is full.
	 */
	private void expandElements() {
		final Object[] help = elements;
		capacity *= 2;
		size = 0;
		elements = new Object[capacity];

		for (final Object o : help) {
			add(o);
		}
	}

	@Override
	public void forEach(final Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Returns the <code>Object</code> at the specified index. Index has to be
	 * from 0 to <code>size</code> - 1 or else an
	 * {@link IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index
	 *            position of the element
	 * @return Object at the specified position
	 * @throws IndexOutOfBoundsException
	 *             if the index argument is &lt; 0 or &gt;= size
	 */
	public Object get(final int index) {
		checkIndex(index, size());

		return elements[index];
	}

	/**
	 * Finds the first occurrence of an <code>Object</code> specified by the
	 * <code>value</code> argument and returns the objects position. If there
	 * isn't an <code>Object</code> equal to <code>value</code> returns -1.
	 * 
	 * @param value
	 *            Object we want to find
	 * @return position of the Object equal to value if found, else -1
	 */
	public int indexOf(final Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Inserts the provided <code>Object</code> at the specified
	 * <code>position</code> in the <code>elements</code> array.
	 * <code>value</code> can't be a null reference, if it is an
	 * {@link IllegalArgumentException} will be thrown, and position can't be
	 * &lt; 0 or &gt; <code>size</code>, if it is an
	 * {@link IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param value
	 *            Object to add to the collection
	 * @param position
	 *            to add the value in
	 * @throws IndexOutOfBoundsException
	 *             if the index argument is &lt; 0 or &gt; size
	 * @throws IllegalArgumentException
	 *             if value is a null reference
	 */
	public void insert(final Object value, final int position) {
		checkIndex(position, size() + 1);

		checkNull(value);

		if (size + 1 >= capacity) {
			expandElements();
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		elements[position] = value;
		size++;
	}

	/**
	 * Removes the element at the specified position. Argument
	 * <code>index</code> has to be &gt;= 0 and &lt; <code>size</code> or an
	 * {@link IndexOutOfBoundsException} is thrown
	 * 
	 * @param index
	 *            position of the element specified for removal
	 * @throws IndexOutOfBoundsException
	 *             if the index argument is &lt; 0 or &gt;= size
	 */
	public void remove(final int index) {
		checkIndex(index, size());

		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}

		elements[size - 1] = null;

		size--;
	}

	@Override
	public boolean remove(final Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}

		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		final Object[] array = new Object[size()];
		for (int i = 0; i < size; i++) {
			array[i] = elements[i];
		}

		return array;
	}

}
