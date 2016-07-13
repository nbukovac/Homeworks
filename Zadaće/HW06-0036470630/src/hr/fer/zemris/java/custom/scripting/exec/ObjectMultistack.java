package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw6.helpers.RequirementHelper;

/**
 * Class that provides a map functionality but instead of containing only one
 * value per key, supports a stack like structure allowing multiple values under
 * one key implemented as a stack.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ObjectMultistack {

	/**
	 * Class that represents a node in a stack like linked list containing a
	 * {@link ValueWrapper} used for arithmetic operation.
	 * 
	 * @author Nikola Bukovac
	 * @version 1.0
	 */
	private static class MultiStackEntry {

		/**
		 * {@link ValueWrapper} containing a value
		 */
		private final ValueWrapper valueWrapper;

		/**
		 * Reference to the next stack node
		 */
		private MultiStackEntry next;

		/**
		 * Reference to the previous stack node
		 */
		private MultiStackEntry previous;

		/**
		 * Constructs a new {@link MultiStackEntry} with the provided
		 * {@link ValueWrapper} reference.
		 * 
		 * @param valueWrapper
		 *            {@link ValueWrapper} reference
		 */
		public MultiStackEntry(final ValueWrapper valueWrapper) {
			super();
			this.valueWrapper = valueWrapper;
		}

	}

	/**
	 * Storage element for this collection
	 */
	private final Map<String, MultiStackEntry> map;

	/**
	 * Number of contained elements
	 */
	private int size;

	/**
	 * Constructs a new {@link ObjectMultistack}.
	 */
	public ObjectMultistack() {
		super();
		this.map = new HashMap<>();
		size = 0;
	}

	/**
	 * Returns the {@link MultiStackEntry} at the top of the stack under a
	 * provided key. An {@link IllegalArgumentException} is thrown if the
	 * provided key is null. An {@link EmptyStackException} is thrown if the
	 * entry under the provided key is null
	 * 
	 * @param name
	 *            key
	 * @return {@link MultiStackEntry} at the top of the stack
	 * @throws IllegalArgumentException
	 *             if the provided key is null
	 * @throws EmptyStackException
	 *             if the entry under the provided key is null
	 */
	private MultiStackEntry getMultiStackEntry(final String name) {
		RequirementHelper.checkArgumentNull(name, "Null reference isn't a valid key for a map entry!");

		MultiStackEntry stackEntry = map.get(name);

		if (stackEntry == null) {
			throw new EmptyStackException("Stack entry under this key doesn't exist!");
		}

		if (stackEntry.valueWrapper == null) {
			throw new EmptyStackException("The stack under this key is empty!");
		}

		while (stackEntry.next != null) {
			stackEntry = stackEntry.next;
		}

		return stackEntry;
	}

	/**
	 * Checks if the collection is empty. Returns {@code true} if it is empty,
	 * if not {@code false}.
	 * 
	 * @return true if empty, else false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the {@link ValueWrapper} at the top of the stack under the
	 * provided key. An {@link IllegalArgumentException} is thrown if the
	 * provided key is null. An {@link EmptyStackException} is thrown if the
	 * entry under the provided key is null
	 * 
	 * @param name
	 *            key
	 * @return {@link ValueWrapper} at the top of the stack under the provided
	 *         key
	 * @throws IllegalArgumentException
	 *             if the provided key is null
	 * @throws EmptyStackException
	 *             if the entry under the provided key is null
	 */
	public ValueWrapper peek(final String name) {
		final MultiStackEntry stackEntry = getMultiStackEntry(name);

		return stackEntry.valueWrapper;

	}

	/**
	 * Returns the {@link ValueWrapper} at the top of the stack under the
	 * provided key and removes the {@link MultiStackEntry} from the stack. An
	 * {@link IllegalArgumentException} is thrown if the provided key is null.
	 * An {@link EmptyStackException} is thrown if the entry under the provided
	 * key is null
	 * 
	 * @param name
	 *            key
	 * @return {@link ValueWrapper} at the top of the stack under the provided
	 *         key
	 * @throws IllegalArgumentException
	 *             if the provided key is null
	 * @throws EmptyStackException
	 *             if the entry under the provided key is null
	 */
	public ValueWrapper pop(final String name) {
		final MultiStackEntry stackEntry = getMultiStackEntry(name);
		final ValueWrapper valueWrapper = stackEntry.valueWrapper;

		if (stackEntry.previous == null) {
			map.put(name, null);
		} else {
			stackEntry.previous.next = null;
		}

		size--;

		return valueWrapper;
	}

	/**
	 * Places a new {@link MultiStackEntry} with the provided
	 * {@link ValueWrapper} reference at the top of the stack under the provided
	 * key. An {@link IllegalArgumentException} is thrown if the provided key or
	 * {@code valueWrapper} is null.
	 * 
	 * @param name
	 *            key
	 * @param valueWrapper
	 *            {@link ValueWrapper} reference
	 * @throws IllegalArgumentException
	 *             f the provided key or {@code valueWrapper} is null.
	 */
	public void push(final String name, final ValueWrapper valueWrapper) {
		RequirementHelper.checkArgumentNull(name, "Null reference isn't a valid key for a map entry!");
		RequirementHelper.checkArgumentNull(valueWrapper, "ValueWrapper can't be null");

		MultiStackEntry stackEntry = map.get(name);

		if (stackEntry == null) {
			map.put(name, new MultiStackEntry(valueWrapper));
		} else {
			stackEntry = getMultiStackEntry(name);

			while (stackEntry.next != null) {
				stackEntry = stackEntry.next;
			}

			stackEntry.next = new MultiStackEntry(valueWrapper);
			stackEntry.next.previous = stackEntry;
		}

		size++;
	}
}
