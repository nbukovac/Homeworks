package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Interface that defines what actions are performed when something changes in
 * the subject class.
 * 
 * @see "https://en.wikipedia.org/wiki/Observer_pattern"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IntegerStorageObserver {

	/**
	 * When there is a value change in a instance of
	 * {@link IntegerStorageChange} the actions specified by a certain class are
	 * performed.
	 * 
	 * @param storage
	 *            Subject class
	 */
	public void valueChanged(IntegerStorageChange storage);
}
