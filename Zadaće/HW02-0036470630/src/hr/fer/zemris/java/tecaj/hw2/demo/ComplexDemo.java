package hr.fer.zemris.java.tecaj.hw2.demo;

import hr.fer.zemris.java.tecaj.hw2.ComplexNumber;

/**
 * Program which demonstrates usage of the {@link ComplexNumber} class.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ComplexDemo {

	/**
	 * Entry point of the program
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		final ComplexNumber c1 = new ComplexNumber(2, 3);
		final ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		final ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];

		System.out.println(c3);

	}

}
