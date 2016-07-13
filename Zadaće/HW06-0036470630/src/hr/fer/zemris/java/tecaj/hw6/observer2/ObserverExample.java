package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Program that demonstrates usage of {@link IntegerStorageChange} class and the
 * Observer pattern
 * 
 * @version 1.0
 */
public class ObserverExample {

	/**
	 * Entry point of the program
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		final IntegerStorage istorage = new IntegerStorage(20);
		final IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(3));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(observer);

		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(3));

		istorage.setValue(13);
		istorage.setValue(22);

		istorage.addObserver(new SquareValue());

		istorage.setValue(15);
	}
}
