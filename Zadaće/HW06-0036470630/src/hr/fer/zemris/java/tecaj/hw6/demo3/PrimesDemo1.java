package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Program that demonstrates the usage of class {@link PrimesCollection}
 * 
 * @version 1.0
 *
 */
public class PrimesDemo1 {

	/**
	 * Entry point of the program
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		final PrimesCollection primesCollection = new PrimesCollection(5);

		for (final Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}

}
