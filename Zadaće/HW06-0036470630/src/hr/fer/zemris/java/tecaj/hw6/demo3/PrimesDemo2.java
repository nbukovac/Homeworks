package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Program that demonstrates the usage of class {@link PrimesCollection}
 * 
 * @version 1.0
 *
 */
public class PrimesDemo2 {

	/**
	 * Entry point of the program
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		final PrimesCollection primesCollection = new PrimesCollection(3);

		for (final Integer prime : primesCollection) {
			for (final Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}

}
