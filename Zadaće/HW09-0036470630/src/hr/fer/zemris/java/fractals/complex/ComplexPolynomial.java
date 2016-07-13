package hr.fer.zemris.java.fractals.complex;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents polynomials containing complex numbers that are
 * represented with the {@link Complex} class. Several elementary operations are
 * provided such as derivation, multiplying and applying complex value.
 * <p>
 * Example of a {@link ComplexPolynomial} : 7z^2-(10+i0.2)z-i
 * </p>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ComplexPolynomial {

	/** Polynomial factors */
	private final Complex[] factors;

	/**
	 * Constructs a new {@link ComplexPolynomial} with the provided
	 * {@link Complex} factors. If the specified {@code factors} array contains
	 * a {@code null} reference an {@link IllegalArgumentException} is thrown.
	 * 
	 * @param factors
	 *            {@link Complex} factors
	 * @throws IllegalArgumentException
	 *             if a element in factors is null
	 */
	public ComplexPolynomial(final Complex... factors) {
		for (final Complex complex : factors) {
			ComplexUtil.checkIfComplexNull(complex);
		}

		this.factors = factors;
	}

	/**
	 * Calculates the {@link Complex} value of this {@link ComplexPolynomial}
	 * with the provided {@link Complex} argument {@code z}. An
	 * {@link IllegalArgumentException} is thrown if the specified {@code z}
	 * argument is {@code null}.
	 * 
	 * @param z
	 *            {@link Complex} argument
	 * @return new {@link Complex} with the calculated value
	 * @throws IllegalArgumentException
	 *             f the specified {@code z} argument is {@code null}.
	 */
	public Complex apply(final Complex z) {
		ComplexUtil.checkIfComplexNull(z);

		Complex result = factors[0];

		for (int i = 1, size = factors.length; i < size; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}

		return result;
	}

	/**
	 * Derives this {@link ComplexPolynomial} and returns a new
	 * {@link ComplexPolynomial} with the derived value. The derived
	 * {@link ComplexPolynomial} is one order smaller than the original
	 * {@link ComplexPolynomial}. If the order of this {@link ComplexPolynomial}
	 * is &lt; 1 then {@code null} is returned.
	 * 
	 * @return a new {@link ComplexPolynomial} with the derived value if order
	 *         is &gt;= 1, else null
	 */
	public ComplexPolynomial derive() {
		int order = order();

		if (order < 1) {
			return null;
		}

		final Complex[] polynomFactors = new Complex[order];

		for (int i = factors.length - 1; i >= 1 && order > 0; i--, order--) {
			polynomFactors[order - 1] = factors[i].multiply(new Complex(order, 0));
		}

		return new ComplexPolynomial(polynomFactors);
	}

	/**
	 * Multiplies this {@link ComplexPolynomial} with the specified
	 * {@link ComplexPolynomial} and returns a new {@link ComplexPolynomial}
	 * with the multiplied value. An {@link IllegalArgumentException} is thrown
	 * if the specified {@code p} argument is {@code null}.
	 * 
	 * @param p
	 *            other {@link ComplexPolynomial}
	 * @return a new {@link ComplexPolynomial} with the multiplied value.
	 * @throws IllegalArgumentException
	 *             if the specified {@code p} argument is {@code null}
	 */
	public ComplexPolynomial multiply(final ComplexPolynomial p) {
		ComplexUtil.checkIfComplexNull(p, "Polynomial can't be null for the multiply operation");

		final Map<Integer, Complex> polynoms = new HashMap<>();

		for (int i = 0, size = factors.length; i < size; i++) {
			for (int j = 0, sizeP = p.factors.length; j < sizeP; j++) {
				final Complex multiplied = factors[i].multiply(p.factors[j]);
				final int key = i + j;

				if (polynoms.containsKey(key)) {
					final Complex presentComplex = polynoms.get(key);
					polynoms.put(key, presentComplex.add(multiplied));
				} else {
					polynoms.put(key, multiplied);
				}
			}
		}

		final int size = order() + p.order() + 1;
		final Complex[] polynomFactors = new Complex[size];

		for (int i = 0; i < size; i++) {
			polynomFactors[i] = polynoms.get(i);
		}

		return new ComplexPolynomial(polynomFactors);
	}

	/**
	 * Returns the order of this {@link ComplexPolynomial}. Order is the highest
	 * power a factor has. Here it is the biggest index in the {@code factors}
	 * array.
	 * 
	 * @return order of this {@link ComplexPolynomial}
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	@Override
	public String toString() {
		int order = order();
		final StringBuilder sb = new StringBuilder();

		for (int i = factors.length - 1; i >= 0; i--, order--) {
			sb.append(factors[i]);

			if (order > 1) {
				sb.append("z^").append(order).append('+');
			} else if (order == 1) {
				sb.append('z').append('+');
			}

		}

		return sb.toString();
	}

}
