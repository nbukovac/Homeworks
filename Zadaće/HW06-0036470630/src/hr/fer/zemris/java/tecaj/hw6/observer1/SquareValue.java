package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Class that implements {@link IntegerStorageObserver} and as a action it
 * prints out the square of the value stored in the {@link IntegerStorage}
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(final IntegerStorage storage) {
		System.out.println(String.format("Provided new value: %d, square is %d", storage.getValue(),
				storage.getValue() * storage.getValue()));
	}

}
