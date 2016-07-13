package hr.fer.zemris.java.fractals.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents a complex number. Provides all elementary operations
 * with complex numbers such as add, sub, multiply, divide, power, root, negate.
 * It is possible to parse a complex number from a string.
 * 
 * see "https://en.wikipedia.org/wiki/Complex_number"
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Complex {

	/** Complex number {@code 0} */
	public static final Complex ZERO = new Complex(0, 0);

	/** Complex number {@code 1} */
	public static final Complex ONE = new Complex(1, 0);

	/** Complex number {@code -1} */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/** Complex number {@code i} */
	public static final Complex IM = new Complex(0, 1);

	/** Complex number {@code -i} */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Formats double value to 0 decimal spaces.
	 * 
	 * @param value
	 *            for formatting
	 * @return formatted value
	 */
	private static String formatDouble(final double value) {
		return String.format("%.0f", value);
	}

	/**
	 * Returns the imaginary part of a complex number contained in the provided
	 * {@link Matcher} object if it is possible.
	 * 
	 * @param matcher
	 *            {@link Matcher} containing matched parts of the original
	 *            {@link String}
	 * @return imaginary part of a complex number
	 */
	private static double getImaginaryFromString(final Matcher matcher) {
		double imaginary;
		final String imaginaryPart = matcher.group(3);

		if (imaginaryPart.indexOf("i") == imaginaryPart.length() - 1) {
			imaginary = Double.parseDouble(imaginaryPart.replace('i', '1'));
		} else {
			imaginary = Double.parseDouble(matcher.group(3).replace("i", ""));
		}

		return imaginary;
	}

	/**
	 * Parses a complex number from the specified {@link String} argument if the
	 * {@code s} argument contains a valid representation of a complex number.
	 * Examples of valid complex numbers are:
	 * <ul>
	 * <li>0</li>
	 * <li>i4</li>
	 * <li>0-i23</li>
	 * <li>-5+i3.2</li>
	 * </ul>
	 * 
	 * @param s
	 *            {@link String} representation of a complex number
	 * @return null if string can't be parsed, else a new {@link Complex} with
	 *         the parsed value
	 */
	public static Complex parse(final String s) {
		final String patternString = "([+-]?\\d+(\\.\\d+)?)?\\s*([+-]?i\\d*(\\.\\d+)?)?";
		final Pattern pattern = Pattern.compile(patternString);
		final Matcher matcher = pattern.matcher(s);

		Complex complexNumber = null;

		if (matcher.matches()) {
			double real = 0;
			double imaginary = 0;

			if (matcher.group(1) == null) {
				imaginary = getImaginaryFromString(matcher);
			} else if (matcher.group(3) == null) {
				real = Double.parseDouble(matcher.group(1));
			} else {
				real = Double.parseDouble(matcher.group(1));
				imaginary = getImaginaryFromString(matcher);
			}

			complexNumber = new Complex(real, imaginary);
		}

		return complexNumber;
	}

	/**
	 * Real part of a {@link Complex} number
	 */
	private final double real;

	/**
	 * Imaginary part of a {@link Complex} number
	 */
	private final double imaginary;

	/**
	 * Constructs a new {@link Complex} with the default values of {@code} 0 for
	 * {@code real} and {@code imaginary}.
	 */
	public Complex() {
		this(0.0, 0.0);
	}

	/**
	 * Constructs a new {@link Complex} with the specified values for
	 * {@code real} and {@code imaginary}.
	 * 
	 * @param real
	 *            real part
	 * @param imaginary
	 *            imaginary part
	 */
	public Complex(final double real, final double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Adds 2 {@link Complex} object as 2 complex numbers and returns a new
	 * {@link Complex} object with the added value. An
	 * {@link IllegalArgumentException} is thrown if the specified {@code c}
	 * argument is {@code null}.
	 * 
	 * @param c
	 *            other {@link Complex} object
	 * @return new {@link Complex} object with the added value
	 * @throws IllegalArgumentException
	 *             if the specified {@code c} argument is {@code null}.
	 */
	public Complex add(final Complex c) {
		ComplexUtil.checkIfComplexNull(c);

		return new Complex(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Returns the polar angle value of this {@link Complex} from [-PI, PI].
	 * 
	 * @return polar angle
	 */
	private double angle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * Divides 2 {@link Complex} objects as 2 complex numbers and returns a new
	 * {@link Complex} object with the divided value. An
	 * {@link IllegalArgumentException} is thrown if the specified {@code c}
	 * argument is {@code null}.
	 * 
	 * @param c
	 *            other {@link Complex} object
	 * @return new {@link Complex} object with the divided value
	 * @throws IllegalArgumentException
	 *             if the specified {@code c} argument is {@code null}.
	 */
	public Complex divide(final Complex c) {
		ComplexUtil.checkIfComplexNull(c);

		// Euler's formula
		final double magnitude = module() / c.module();
		final double angle = (angle() - c.angle()) % (2 * Math.PI);

		return new Complex(getRealFromPolar(magnitude, angle), getImaginaryFromPolar(magnitude, angle));
	}

	/**
	 * Returns the rectangular value of the imaginary part of this
	 * {@link Complex} number from a polar value.
	 * 
	 * @param magnitude
	 *            polar radius
	 * @param angle
	 *            polar angle
	 * @return rectangular value of the imaginary part
	 */
	private double getImaginaryFromPolar(final double magnitude, final double angle) {
		return magnitude * Math.sin(angle);
	}

	/**
	 * Returns the rectangular value of the real part of this {@link Complex}
	 * number from a polar value.
	 * 
	 * @param magnitude
	 *            polar radius
	 * @param angle
	 *            polar angle
	 * @return rectangular value of the real part
	 */
	private double getRealFromPolar(final double magnitude, final double angle) {
		return magnitude * Math.cos(angle);
	}

	/**
	 * Return the module of this {@link Complex} number
	 * 
	 * @return module of this {@link Complex} number
	 */
	public double module() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Multiplies 2 {@link Complex} objects as 2 complex numbers and returns a
	 * new {@link Complex} object with the multiplied value. An
	 * {@link IllegalArgumentException} is thrown if the specified {@code c}
	 * argument is {@code null}.
	 * 
	 * @param c
	 *            other {@link Complex} object
	 * @return new {@link Complex} object with the multiplied value
	 * @throws IllegalArgumentException
	 *             if the specified {@code c} argument is {@code null}.
	 */
	public Complex multiply(final Complex c) {
		ComplexUtil.checkIfComplexNull(c);

		// Euler's formula
		final double magnitude = module() * c.module();
		final double angle = (angle() + c.angle()) % (2 * Math.PI);

		return new Complex(getRealFromPolar(magnitude, angle), getImaginaryFromPolar(magnitude, angle));
	}

	/**
	 * Negates this {@link Complex} number.
	 * 
	 * @return new {@link Complex} object with the negated value
	 */
	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Calculates the nth power of this {@link Complex} number. The power is
	 * specified as the {@code n} argument. An {@link IllegalArgumentException}
	 * is thrown if the specified {@code c} argument is {@code null}.
	 * 
	 * @param n
	 *            power
	 * @return new {@link Complex} object with the calculated power value
	 */
	public Complex power(final int n) {
		// Euler's formula
		final double magnitude = Math.pow(module(), n);
		final double angle = (angle() * n) % (2 * Math.PI);

		return new Complex(getRealFromPolar(magnitude, angle), getImaginaryFromPolar(magnitude, angle));
	}

	/**
	 * Calculates the nth root of this {@link Complex} number. The number of
	 * roots is specified as the {@code n} argument.
	 * 
	 * @param n
	 *            root
	 * @return new list of {@link Complex} objects with the calculated root
	 *         value
	 */
	public List<Complex> root(final int n) {
		// Euler's formula
		final double magnitude = Math.pow(module(), 1.0 / n);
		final double angle = angle();
		final List<Complex> list = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			final double angleValue = (angle + 2 * i * Math.PI) / n;
			list.add(
					new Complex(getRealFromPolar(magnitude, angleValue), getImaginaryFromPolar(magnitude, angleValue)));
		}

		return list;
	}

	/**
	 * Subs 2 {@link Complex} object as 2 complex numbers and returns a new
	 * {@link Complex} object with the new value. An
	 * {@link IllegalArgumentException} is thrown if the specified {@code c}
	 * argument is {@code null}.
	 * 
	 * @param c
	 *            other {@link Complex} object
	 * @return new {@link Complex} object with the new value
	 * @throws IllegalArgumentException
	 *             if the specified {@code c} argument is {@code null}.
	 */
	public Complex sub(final Complex c) {
		ComplexUtil.checkIfComplexNull(c);

		return new Complex(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * {@inheritDoc}<br/>
	 * {@link String} visualization of a {@link Complex} object is as follows.
	 * {@code "(x+yi)"} where {@code x} is the {@code real} part and {@code y}
	 * is the {@code imaginary} part. If {@code x} or {@code y} is left out then
	 * no brackets are needed.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		if (Math.abs(imaginary) == 0) {
			sb.append(formatDouble(real));
		} else if (Math.abs(real) == 0) {
			sb.append(formatDouble(imaginary)).append('i');
		} else if (imaginary < 0) {
			sb.append('(').append(formatDouble(real)).append("").append(formatDouble(imaginary)).append('i')
					.append(')');
		} else {
			sb.append('(').append(formatDouble(real)).append("+").append(formatDouble(imaginary)).append('i')
					.append(')');
		}

		return sb.toString();
	}
}
