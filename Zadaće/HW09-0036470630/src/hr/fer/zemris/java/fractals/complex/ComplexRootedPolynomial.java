package hr.fer.zemris.java.fractals.complex;

/**
 * Class that represents a factored representation of a polynomial with complex
 * numbers. Every root is represented as a {@link Complex} number. Several
 * operations are available such as transforming to {@link ComplexPolynomial},
 * applying a {@link Complex} number to the polynomial.
 * 
 * <p>
 * Example of a {@link ComplexRootedPolynomial} : (z-i)(z-5)
 * </p>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ComplexRootedPolynomial {

	/**
	 * {@link Complex} roots of a complex polynomial
	 */
	private final Complex[] roots;

	/**
	 * Constructs a new {@link ComplexRootedPolynomial} with the specified
	 * {@link Complex} {@code roots}. If the specified {@code roots} array
	 * contains a {@code null} reference an {@link IllegalArgumentException} is
	 * thrown.
	 * 
	 * @param roots
	 *            {@link Complex} roots
	 * @throws IllegalArgumentException
	 *             if a element in roots is null
	 */
	public ComplexRootedPolynomial(final Complex... roots) {
		for (final Complex complex : roots) {
			ComplexUtil.checkIfComplexNull(complex);
		}

		this.roots = roots;
	}

	/**
	 * Calculates the {@link Complex} value of this
	 * {@link ComplexRootedPolynomial} with the provided {@link Complex}
	 * argument {@code z}. An {@link IllegalArgumentException} is thrown if the
	 * specified {@code z} argument is {@code null}.
	 * 
	 * @param z
	 *            {@link Complex} argument
	 * @return new {@link Complex} with the calculated value
	 * @throws IllegalArgumentException
	 *             f the specified {@code z} argument is {@code null}.
	 */
	public Complex apply(final Complex z) {
		ComplexUtil.checkIfComplexNull(z);

		Complex result = z.sub(roots[0]);

		for (int i = 1, size = roots.length; i < size; i++) {
			result = result.multiply(z.sub(roots[i]));
		}

		return result;
	}

	/**
	 * Finds the closest root for the specified {@link Complex} number {@code z}
	 * . The root has to be inside the specified {@code threshold} to be valid.
	 * An {@link IllegalArgumentException} is thrown if the specified {@code z}
	 * argument is {@code null}.
	 * 
	 * @param z
	 *            {@link Complex} number
	 * @param threshold
	 *            minimal threshold a number has to be inside to be valid
	 * @return index of the closest root or -1 if there isn't a valid root
	 * @throws IllegalArgumentException
	 *             f the specified {@code z} argument is {@code null}.
	 */
	public int indexOfClosestRootFor(final Complex z, final double threshold) {
		ComplexUtil.checkIfComplexNull(z);

		int index = -1;
		double minDistance = Math.abs(roots[0].sub(z).module());

		for (int i = 0, size = roots.length; i < size; i++) {
			final double distance = Math.abs(roots[i].sub(z).module());

			if (distance < threshold && minDistance >= distance) {
				minDistance = distance;
				index = i;
			}
		}

		return index;
	}

	/**
	 * Transforms this {@link ComplexRootedPolynomial} to the adequate
	 * {@link ComplexPolynomial}, by multiplying every root.
	 * 
	 * @return {@link ComplexPolynomial} value of this
	 *         {@link ComplexRootedPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial polynomial = new ComplexPolynomial(roots[0].negate(), Complex.ONE);

		for (int i = 1, size = roots.length; i < size; i++) {
			final ComplexPolynomial next = new ComplexPolynomial(roots[i].negate(), Complex.ONE);
			polynomial = polynomial.multiply(next);
		}

		return polynomial;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		for (int i = 0, size = roots.length; i < size; i++) {
			sb.append("(z - ").append(roots[i].toString()).append(')');
		}

		return sb.toString();
	}
}
