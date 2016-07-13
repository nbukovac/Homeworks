package hr.fer.zemris.java.fractals.complex;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComplexTests {

	ComplexPolynomial complexPolynomial;
	ComplexRootedPolynomial complexRooted;

	@Before
	public void initialize() {
		final Complex[] factors = new Complex[] { new Complex(1, 0), new Complex(5, 0), new Complex(2, 0),
				new Complex(7, 2) };

		complexPolynomial = new ComplexPolynomial(factors);
		complexRooted = new ComplexRootedPolynomial(new Complex[] { new Complex(1, 0), new Complex(1, 0) });
	}

	@Test
	public void testComplexParse() {
		Complex number = Complex.parse("3-i2");
		assertEquals(true, "(3-2i)".equals(number.toString()));

		number = Complex.parse("-i");
		assertEquals(true, "-1i".equals(number.toString()));

		number = Complex.parse("33");
		assertEquals(true, "33".equals(number.toString()));
	}

	@Test
	public void testPolynomialApply() {
		final Complex complex = new Complex(1, 0);
		final Complex result = complexPolynomial.apply(complex);

		assertEquals("(15+2i)", result.toString());
	}

	@Test
	public void testPolynomialDerive() {
		final String string = "(21+6i)z^2+4z+5";
		complexPolynomial = complexPolynomial.derive();
		final String complexString = complexPolynomial.toString();

		assertEquals(true, complexString.equals(string));
	}

	@Test
	public void testPolynomialToString() {
		final String string = "(7+2i)z^3+2z^2+5z+1";
		final String complexString = complexPolynomial.toString();

		assertEquals(true, complexString.equals(string));
	}

	@Test
	public void testRootedToPolynomial() {
		final ComplexPolynomial polynomial = complexRooted.toComplexPolynom();
		final String complexString = polynomial.toString();

		assertEquals(true, complexString.equals("1z^2+(-2-0i)z+1"));
	}
}
