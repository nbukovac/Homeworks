package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Class that implements {@link IntegerStorageObserver} and as a action it
 * prints out the square of the value stored in the {@link IntegerStorageChange}
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(final IntegerStorageChange storage) {
		System.out.println(String.format("Provided new value: %d, square is %d", storage.getNewValue(),
				storage.getNewValue() * storage.getNewValue()));
	}

}
