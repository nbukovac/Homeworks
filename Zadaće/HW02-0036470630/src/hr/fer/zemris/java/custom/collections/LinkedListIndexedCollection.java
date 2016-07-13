package hr.fer.zemris.java.custom.collections;

/**
 * Derivative class of class <code>Collection</code> that is implemented as an
 * linked list. Inherits all of <code>Collection</code> classes methods but also
 * provides some additional methods for adding, removing and finding contained
 * objects. Null references aren't allowed in this <code>Collection</code> but
 * duplicate elements are.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Represents an list element
	 */
	private static class ListNode {

		/** Reference to the previous element in the list */
		ListNode previous;

		/** Reference to the next element in the list */
		ListNode next;

		/** Object stored in ListNode */
		Object value;
	}

	/** Number of elements currently in collection */
	private int size;

	/** Reference to the first element in the list */
	private ListNode first;

	/** Reference to the last element in the list */
	private ListNode last;

	/**
	 * Default constructor used to initialize the <code>first</code> and
	 * <code>last</code> fields.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
	}

	/**
	 * Constructs a new {@link LinkedListIndexedCollection} using an existing
	 * collection. All elements of the other collection will be added to this
	 * collection.
	 * 
	 * @param collection
	 *            source collection
	 */
	public LinkedListIndexedCollection(final Collection collection) {
		this();
		addAll(collection);
		size = collection.size();
	}

	@Override
	/**
	 * The new element is added at the end of the list. Null references aren't
	 * allowed and thus a exception will be thrown.
	 * 
	 * @throws IllegalArgumentException
	 *             if a null reference is provided as the value argument
	 */
	public void add(final Object value) {
		checkNull(value);

		if (first == null) {
			first = new ListNode();
			first.value = value;
			last = first;
			size = 1;

		} else {
			final ListNode temp = new ListNode();
			temp.previous = last;
			last.next = temp;
			temp.value = value;
			last = temp;
			size++;
		}
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
		first = last = null;
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
	 * Finds the ListNode which is located at the specified index. This is done
	 * in an n/2 + 1 average time.
	 * 
	 * @param index
	 *            position of the element
	 * @return ListNode at the specified position
	 */
	private ListNode findListNode(final int index) {
		ListNode help;
		int position = 0;

		if (index <= size / 2) {
			help = first;
			while (position < index) {
				help = help.next;
				position++;
			}
		} else {
			help = last;
			while (position >= index) {
				help = help.previous;
				position--;
			}
		}

		return help;
	}

	@Override
	public void forEach(final Processor processor) {
		ListNode help = first;

		while (help != null) {
			processor.process(help.value);
			help = help.next;
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
		checkIndex(index, size);

		return findListNode(index).value;
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
		ListNode help = first;
		int index = 0;

		while (help != null) {
			if (help.value.equals(value)) {
				return index;
			}
			index++;
			help = help.next;
		}

		return -1;
	}

	/**
	 * Inserts the provided <code>Object</code> at the specified
	 * <code>position</code> in the list. <code>value</code> can't be a null
	 * reference, if it is an {@link IllegalArgumentException} will be thrown,
	 * and position can't be &lt; 0 or &gt; <code>size</code>, if it is an
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
		checkIndex(position, size + 1);
		checkNull(value);

		final ListNode newNode = new ListNode();
		newNode.value = value;
		size++;

		if (position == 0) {
			newNode.next = first;
			first.previous = newNode;
			first = newNode;
			return;
		} else if (position == size - 1) {
			newNode.previous = last;
			last.next = newNode;
			last = newNode;
			return;
		}

		final ListNode nodeOnPosition = findListNode(position);
		final ListNode previous = nodeOnPosition.previous;

		newNode.previous = previous;
		newNode.next = nodeOnPosition;
		previous.next = newNode;
		nodeOnPosition.previous = newNode;

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
		if (index == 0) {
			first = first.next;
			first.previous = null;

		} else if (index == size - 1) {
			last = last.previous;
			last.next = null;

		} else {
			ListNode help = findListNode(index);
			final ListNode previous = help.previous;
			final ListNode next = help.next;

			previous.next = next;
			next.previous = previous;
			help = null;
		}

		size--;
	}

	@Override
	public boolean remove(final Object value) {
		final int objectIndex = indexOf(value);

		if (objectIndex != -1) {
			remove(objectIndex);
			return true;
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
		int i = 0;
		ListNode help = first;

		while (help != null) {
			array[i] = help.value;
			i++;
			help = help.next;
		}

		return array;
	}
}
