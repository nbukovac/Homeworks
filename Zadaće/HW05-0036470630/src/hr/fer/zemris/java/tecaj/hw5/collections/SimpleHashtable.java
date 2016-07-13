package hr.fer.zemris.java.tecaj.hw5.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

/**
 * Class representing a variation of a hash table collection. Hash tables offer
 * very good average complexity operations because of their internal structure.
 * This implementations number of table slots is defined with a power of number
 * 2. If size of the collection reaches 75% of the number of table slots its
 * internal arrays size is doubled for better efficiency and lower average
 * complexity.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 * 
 * @see "https://en.wikipedia.org/wiki/Hash_table"
 *
 * @param <K>
 *            key parameter, used to determine the table slot
 * @param <V>
 *            value parameter, used to specify elements value
 */
public class SimpleHashtable<K, V> implements Iterable<TableEntry<K, V>> {

	/**
	 * Class that implements the {@link Iterator} interface used for iterating
	 * over class elements. This iterator is iterating over {@link TableEntry}s
	 * contained inside a {@link SimpleHashtable} like a hash table.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	private class SimpleHashtableIterator implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Next element in slot
		 */
		private int elementInSlot = 0;

		/**
		 * Current table slot
		 */
		private int currentSlot = 0;

		/**
		 * Number of elements left in collection
		 */
		private int elementsLeft = size;

		/**
		 * Tracks the number of outside modifications during iteration
		 */
		private int modificationCounter = modificationCount;

		/**
		 * Last element found by the next method
		 */
		private TableEntry<K, V> currentElement = null;

		/**
		 * Keeps track if there was a multiple remove request on the same
		 * element
		 */
		private boolean removed = false;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasNext() {
			if (modificationCounter != modificationCount) {
				throw new ConcurrentModificationException("Collection was modified during iteration.");
			}

			removed = false;

			return elementsLeft > 0;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException("There aren't any elements left in this collection");
			}

			int slotElement = 0;

			while (currentSlot < table.length) {
				TableEntry<K, V> entry = table[currentSlot];

				while (slotElement < elementInSlot && entry != null) {
					slotElement++;
					entry = entry.next;
				}

				if (entry != null) {
					if (entry.next != null) {
						elementInSlot++;
					} else {
						elementInSlot = 0;
						currentSlot++;
					}

					elementsLeft--;
					currentElement = entry;

					return entry;
				} else {
					currentSlot++;
					slotElement = 0;

				}
			}

			return currentElement;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void remove() {
			if (removed) {
				throw new IllegalStateException(
						"While iterating you can't remove elements without iterating to the next element");
			}

			SimpleHashtable.this.remove(currentElement.key);

			if (currentElement.next != null) {
				elementInSlot--;
			}

			removed = true;
			modificationCounter++;
		}

	}

	/**
	 * Class used for containment of elements in a {@link SimpleHashtable}.
	 * Every elements has its key, value and a reference to the next element in
	 * a linked list if there was an overflow during table creation.
	 * 
	 * @author Nikola Bukovac
	 *
	 * @param <K>
	 *            key parameter
	 * @param <V>
	 *            value parameter
	 */
	public static class TableEntry<K, V> {

		/**
		 * Key used to specify the slot where a entry is stored
		 */
		private final K key;

		/**
		 * Value stored in this {@link SimpleHashtable.TableEntry}
		 */
		private V value;

		/**
		 * Reference to the next {@link SimpleHashtable.TableEntry} in local
		 * linked list
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructs a new {@link SimpleHashtable.TableEntry} set to the
		 * provided arguments.
		 * 
		 * @param key
		 *            element key
		 * @param value
		 *            element value
		 * @param next
		 *            reference to the next element of the linked list
		 */
		public TableEntry(final K key, final V value, final TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns the key of this {@link SimpleHashtable.TableEntry}
		 * 
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value of this {@link SimpleHashtable.TableEntry}
		 * 
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value of this {@link SimpleHashtable.TableEntry} with the
		 * provided <code>value</code>
		 * 
		 * @param value
		 *            the value to set
		 */
		public void setValue(final V value) {
			this.value = value;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * Defines default number of slots
	 */
	private static final int DEFAULT_SLOTS = 16;

	/**
	 * Hash table slots
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Number of slots in hash table
	 */
	private int size;

	/**
	 * Keeps track of modifications performed on this {@link SimpleHashtable}
	 */
	private int modificationCount;

	/**
	 * Keeps track if the last put operation was adding a new element to this
	 * {@link SimpleHashtable} or was it only changing value of existing element
	 */
	private boolean putChange;

	/**
	 * Constructs a new {@link SimpleHashtable} with a predefined number of
	 * table slots. Table with 16 slots is created with this constructor.
	 */
	public SimpleHashtable() {
		this(DEFAULT_SLOTS);
	}

	/**
	 * Constructs a new {@link SimpleHashtable} with a user defined number of
	 * slots. Although the user specifies the number of slots, the first equal
	 * or bigger power of 2 value is used for the number of table slots. For
	 * example if user defined 24 as the number of slots, the table will be set
	 * to 32 slots because 32 is the first power of 2 bigger or equal to 24. An
	 * {@link IllegalArgumentException} is thrown if <code>capacity</code> is
	 * &lt; 1.
	 * 
	 * @param capacity
	 *            user defined capacity
	 * @throws IllegalArgumentException
	 *             if <code>capacity</code> is &lt; 1.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(final int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException(
					"At least one slot is required for SimpleHashtable creation. " + capacity + " isn't valid");
		}

		final int realCapacity = calculateNumberOfSlots(capacity);

		size = 0;
		putChange = false;
		modificationCount = 0;
		table = new TableEntry[realCapacity];
	}

	/**
	 * Returns number of slots fixed to a power of 2 bigger than the user
	 * defined <code>capacity</code>
	 * 
	 * @param capacity
	 *            user defined capacity
	 * @return number of slots
	 */
	private int calculateNumberOfSlots(final int capacity) {
		int realCapacity = 1;

		while (capacity > realCapacity) {
			realCapacity *= 2;
		}
		return realCapacity;
	}

	/**
	 * Returns in which table slot (key, value) pair belongs based on
	 * <code>key</code>s <code>hashCode()</code> method and the number of table
	 * slots. Table slots are defined by the length of <code>table</code>
	 * 
	 * @param key
	 *            key used to store the (key, value) pair in the right table
	 *            slot
	 * @return table slot
	 */
	private int calculateTableSlot(final Object key) {
		return calculateTableSlot(key, table.length);
	}

	/**
	 * Returns in which table slot (key, value) pair belongs based on
	 * <code>key</code>s <code>hashCode()</code> method and the number of table
	 * slots.
	 * 
	 * @param key
	 *            key used to store the (key, value) pair in the right table
	 *            slot
	 * @param slots
	 *            number of table slots
	 * @return table slot
	 */
	private int calculateTableSlot(final Object key, final int slots) {
		return Math.abs(key.hashCode() % slots);
	}

	/**
	 * Checks if the number of slots is big enough for efficient hash table
	 * operations with a small number of overflows. Overflow limit is reached
	 * when collection <code>size</code> reached 75% of the number of slots.
	 * 
	 * @return true if limit is reached, else false
	 */
	private boolean checkOverflowLimit() {
		return size * 1.0 / table.length >= 0.75;
	}

	/**
	 * Clears all elements from this {@link SimpleHashtable}.
	 */
	public void clear() {
		for (int i = 0, end = table.length; i < end; i++) {
			table[i] = null;
		}

		size = 0;
	}

	/**
	 * Checks if the provided <code>key</code> is contained in this
	 * {@link SimpleHashtable}. Average complexity is O(1).
	 * 
	 * @param key
	 *            key to check
	 * @return true if the key is contained inside this {@link SimpleHashtable},
	 *         else false
	 */
	public boolean containsKey(final Object key) {
		return get(key) != null;
	}

	/**
	 * Checks if the provided <code>value</code> is contained in this
	 * {@link SimpleHashtable}. Average complexity is O(n).
	 * 
	 * @param value
	 *            value to check
	 * @return true if the value is contained inside this
	 *         {@link SimpleHashtable}, else false
	 */
	public boolean containsValue(final Object value) {
		final Iterator<TableEntry<K, V>> iter = iterator();

		while (iter.hasNext()) {
			if (value != null && value.equals(iter.next().value)) {
				return true;
			} else if (value == null && iter.next().value == null) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Expands the <code>table</code> when <code>size</code> reaches 75% of the
	 * number of table slots. Number of table slots is doubled.
	 */
	@SuppressWarnings("unchecked")
	private void expandHashtable() {
		final int newCapacity = table.length * 2;

		TableEntry<K, V>[] newTable = new TableEntry[newCapacity];

		for (final TableEntry<K, V> entry : this) {
			newTable = insertIntoTable(entry.key, entry.value, newTable);
		}

		table = newTable;
	}

	/**
	 * Returns the value of the element specified by the <code>key</code>
	 * argument. If the key doesn't exist inside the collection
	 * <code>null</code> is returned. Average complexity is O(1).
	 * 
	 * @param key
	 *            key of the element
	 * @return value of the element if found, else null
	 */
	public V get(final Object key) {
		if (key == null) {
			return null;
		}

		final int slot = calculateTableSlot(key);

		if (table[slot] != null) {
			TableEntry<K, V> entry = table[slot];

			while (entry != null) {
				if (entry.key.equals(key)) {
					return entry.value;
				}

				entry = entry.next;
			}
		}

		return null;
	}

	/**
	 * Insert a new element in <code>table</code> based on the calculated slot,
	 * if the <code>key</code> is already inside the collection then the element
	 * value is only updated.
	 * 
	 * @param key
	 *            element key
	 * @param value
	 *            element value
	 * @param table
	 *            table in which the change is going to happen
	 * @return updated table
	 */
	private TableEntry<K, V>[] insertIntoTable(final K key, final V value, final TableEntry<K, V>[] table) {
		final int slot = calculateTableSlot(key, table.length);
		putChange = false;

		TableEntry<K, V> entry = table[slot];
		TableEntry<K, V> last = null;

		if (entry != null) {
			while (entry != null) {
				if (entry.key.equals(key)) {
					entry.value = value;
					putChange = true;

					return table;
				}

				last = entry;
				entry = entry.next;
			}

			entry = new TableEntry<K, V>(key, value, null);
			last.next = entry;
		} else {
			table[slot] = new TableEntry<K, V>(key, value, null);
		}

		return table;
	}

	/**
	 * Checks if this {@link SimpleHashtable} is empty
	 * 
	 * @return true if this {@link SimpleHashtable} is empty, else false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new SimpleHashtableIterator();
	}

	/**
	 * Adds a new {@link TableEntry} to this {@link SimpleHashtable} based on
	 * the key argument. If the size of the collection reached 75% of the number
	 * of table slots, <code>table</code> is expanded by double. An
	 * {@link IllegalArgumentException} is thrown if key is null. Average
	 * complexity is O(1).
	 * 
	 * @param key
	 *            element key
	 * @param value
	 *            element value
	 * @throws IllegalArgumentException
	 *             if key is null
	 */
	public void put(final K key, final V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be a null reference. Put operation can't proceed");
		}

		if (checkOverflowLimit()) {
			expandHashtable();
		}

		table = insertIntoTable(key, value, table);

		if (!putChange) {
			size++;
			modificationCount++;
		}
	}

	/**
	 * Removes a {@link TableEntry} with the specified <code>key</code> if it is
	 * found in the collection. Average complexity is O(1).
	 * 
	 * @param key
	 *            element key
	 */
	public void remove(final Object key) {
		if (key == null) {
			return;
		}

		final int slot = calculateTableSlot(key);

		TableEntry<K, V> entry = table[slot];
		TableEntry<K, V> last = entry;

		while (entry != null) {
			if (entry.key.equals(key)) {

				if (last.key.equals(key)) {
					table[slot] = entry.next;
				} else {
					last.next = entry.next;
				}

				modificationCount++;
				size--;
				break;
			}

			last = entry;
			entry = entry.next;
		}
	}

	/**
	 * Returns the number of (key, value) pairs stored in this
	 * {@link SimpleHashtable}
	 * 
	 * @return number of (key, value) pairs
	 */
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		int counter = 0;

		for (int i = 0, end = table.length; i < end; i++) {
			TableEntry<K, V> entry = table[i];

			while (entry != null) {
				sb.append(entry.toString());
				counter++;

				if (counter < size) {
					sb.append(", ");
				}

				entry = entry.next;
			}

		}

		sb.append("]");

		return sb.toString();
	}

}
