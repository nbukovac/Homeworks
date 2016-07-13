package hr.fer.zemris.java.custom.collections;

/**
 * Collection implemented as a stack. Provides basic stack operations such as
 * pop, push and peek, as well as basic collection operations. Adapts the
 * {@link ArrayIndexedCollection} to provide the necessary operations.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ObjectStack {

	/** Stores collection elements */
	private final ArrayIndexedCollection stack;

	/**
	 * Constructs a new empty {@link ObjectStack}.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}

	/**
	 * Checks if the collection is empty and if it is throws an
	 * {@link EmptyStackException}.
	 * 
	 * @throws EmptyStackException
	 *             if the collection is empty
	 */
	private void checkStack() {
		if (stack.isEmpty()) {
			throw new EmptyStackException();
		}
	}

	/**
	 * Removes all of the elements in this collection.
	 */
	public void clear() {
		stack.clear();
	}

	/**
	 * Checks if there are any elements currently in this collection.
	 * 
	 * @return false if there are no elements in this collection, else true
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns the top element from the collection. If the collection is empty
	 * an {@link EmptyStackException} will be thrown.
	 * 
	 * @return top element
	 * @throws EmptyStackException
	 *             if the collection is empty
	 */
	public Object peek() {
		checkStack();

		final int index = size() - 1;
		return stack.get(index);
	}

	/**
	 * Returns the top element from the collection and then removes it from the
	 * collection. If the collection is empty an {@link EmptyStackException}
	 * will be thrown.
	 * 
	 * @return top element
	 * @throws EmptyStackException
	 *             if the collection is empty
	 */
	public Object pop() {
		checkStack();

		final int index = size() - 1;

		final Object returnValue = stack.get(index);
		stack.remove(index);

		return returnValue;
	}

	/**
	 * Adds a new element to the <code>ObjectStack</code>. Null references are
	 * not allowed as a valid <code>value</code> and thus a
	 * {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param value
	 *            Object to add to the collection
	 * @throws IllegalArgumentException
	 *             if a null reference is passed as <code>value</code> argument
	 */
	public void push(final Object value) {
		stack.add(value);
	}

	/**
	 * Returns the number of elements currently stored in this collection.
	 * 
	 * @return number of elements in collection
	 */
	public int size() {
		return stack.size();
	}
}
