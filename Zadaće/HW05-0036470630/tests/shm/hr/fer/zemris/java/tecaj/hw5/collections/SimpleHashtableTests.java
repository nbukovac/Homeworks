package hr.fer.zemris.java.tecaj.hw5.collections;

import static org.junit.Assert.assertEquals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

@SuppressWarnings("javadoc")
public class SimpleHashtableTests {

	SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<>();

	private void fillHashTable() {
		hashTable.put(1, "Burek");
		hashTable.put(4, "Krafna");
		hashTable.put(1454, "Mrkva");
		hashTable.put(12, null);
	}

	@Test
	public void testClear() {
		fillHashTable();

		assertEquals(4, hashTable.size());

		hashTable.clear();

		assertEquals(0, hashTable.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidCapacity() {
		// throws
		new SimpleHashtable<String, String>(0);
	}

	@Test
	public void testContainsKey() {
		assertEquals(false, hashTable.containsKey(null));
		assertEquals(false, hashTable.containsKey(1));

		fillHashTable();
		hashTable.put(4353, "Nikola");

		assertEquals(true, hashTable.containsKey(1));
		assertEquals(true, hashTable.containsKey(4353));

		hashTable.clear();
	}

	@Test
	public void testContainsValue() {
		assertEquals(false, hashTable.containsValue("Nikola"));

		fillHashTable();
		hashTable.put(4353, "Nikola");

		assertEquals(false, hashTable.containsValue("Ivan"));
		assertEquals(true, hashTable.containsValue("Nikola"));
		assertEquals(true, hashTable.containsValue(null));

		hashTable.clear();

		assertEquals(false, hashTable.containsValue("Nikola"));
		assertEquals(false, hashTable.containsValue(null));
	}

	@Test
	public void testGet() {
		fillHashTable();

		assertEquals(null, hashTable.get(12));
		assertEquals("Mrkva", hashTable.get(1454));
		assertEquals(null, hashTable.get(1000));
		assertEquals(null, hashTable.get(null));

		hashTable.clear();
	}

	@Test
	public void testIsEmpty() {
		assertEquals(true, hashTable.isEmpty());

		hashTable.put(32, "Kruh");

		assertEquals(false, hashTable.isEmpty());

		hashTable.remove(32);

		assertEquals(true, hashTable.isEmpty());
	}

	@Test(expected = IllegalStateException.class)
	public void testIteratorDoubleRemove() {
		hashTable.put(23, "Nikola");

		final Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();

		while (iter.hasNext()) {
			iter.next();
			iter.remove();

			// throws
			iter.remove();
		}
	}

	@Test
	public void testIteratorIterating() {
		fillHashTable();

		final Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();

		assertEquals(false, iter == null);

		int iterations = 0;

		while (iter.hasNext()) {
			iter.next();
			iterations++;
		}

		assertEquals(hashTable.size(), iterations);

		hashTable.clear();
	}

	@Test(expected = NoSuchElementException.class)
	public void testIteratorNoSuchElement() {
		final Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();

		// throws
		iter.next();
	}

	@Test(expected = ConcurrentModificationException.class)
	public void testIteratorOutsideModification() {
		hashTable.put(23, "Nikola");

		final Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();

		while (iter.hasNext()) {
			hashTable.remove(23);

			iter.next();
		}
	}

	@Test
	public void testIteratorRemove() {
		fillHashTable();

		assertEquals(4, hashTable.size());

		final Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();

		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}

		assertEquals(0, hashTable.size());
	}

	@Test
	public void testPut() {
		hashTable.put(1, "Burek");
		hashTable.put(4, "Krafna");
		hashTable.put(1454, "Mrkva");

		assertEquals(3, hashTable.size());

		hashTable.put(4, "Burek s mesom");

		assertEquals(3, hashTable.size());
		assertEquals("Burek s mesom", hashTable.get(4));

		hashTable.put(32, "Kruh");

		assertEquals(4, hashTable.size());

		hashTable.clear();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPutKeyNull() {
		// throws
		hashTable.put(null, "Burek");
	}

	@Test
	public void testPutValueNull() {
		hashTable.put(32, null);

		assertEquals(null, hashTable.get(32));

		hashTable.clear();
	}

	@Test
	public void testRemove() {
		fillHashTable();

		assertEquals(4, hashTable.size());

		assertEquals(true, hashTable.containsValue(null));

		hashTable.remove(12);

		assertEquals(false, hashTable.containsValue(null));

		// Removes 1 and 1454
		hashTable.remove(1);
		hashTable.remove(1454);

		// Doesn't remove anything because this key isn't in hash table
		hashTable.remove(55);
		hashTable.remove(7777);
		hashTable.remove(null);

		assertEquals(1, hashTable.size());

		hashTable.remove(4);

		assertEquals(0, hashTable.size());
	}

	@Test
	public void testToString() {
		hashTable.put(23, "Nikola");

		final String hashTableString = hashTable.toString();
		final String string = "[23=Nikola]";

		assertEquals(true, string.equals(hashTableString));

		hashTable.clear();
	}

	@Test
	public void testToString2() {
		final SimpleHashtable<Integer, String> table = new SimpleHashtable<Integer, String>(2);

		table.put(1, "Nikola");
		table.put(2, "Burek");
		table.put(3, "Ivan");

		final String hashTableString = table.toString();

		// Checked with debugger
		final String string = "[1=Nikola, 2=Burek, 3=Ivan]";

		assertEquals(true, string.equals(hashTableString));
	}

}
