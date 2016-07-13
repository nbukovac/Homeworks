package hr.fer.zemris.java.tecaj.hw2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing complex numbers. Provides basic operations with complex
 * number such as creating new complex numbers, mathematical operations and
 * parsing complex numbers from strings.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ComplexNumber {

	/**
	 * Helper method that adds a minus sign after splitting a string by "-".
	 * 
	 * @param number
	 *            to correct back to negative
	 * @return negative number
	 */
	private static String addMinusSign(final String number) {
		return "-" + number;
	}

	/**
	 * Returns a double value of a imaginary number.
	 * 
	 * @param s
	 *            imaginary number
	 * @return imaginary number
	 */
	private static double createDoubleFromImaginary(String s) {
		s = s.substring(0, s.length() - 1);
		return Double.parseDouble(s);
	}

	/**
	 * Fixes negative angle so it is [0, 2 * Math.PI).
	 * 
	 * @param angle
	 *            angle for fixing
	 * @return angle in range [0, 2 * Math.PI)
	 */
	private static double fixNegativeAngle(double angle) {
		if (angle < 0) {
			angle += 2 * Math.PI;
		}

		return angle;
	}

	/**
	 * Formats double value to 5 decimal spaces.
	 * 
	 * @param value
	 *            for formatting
	 * @return formatted value
	 */
	private static String formatDouble(final double value) {
		return String.format("%.5f", value);
	}

	/**
	 * Returns a new {@link ComplexNumber} with set real part to 0 and imaginary
	 * part set to the provided value.
	 * 
	 * @param imaginary
	 *            imaginary part
	 * @return {@link ComplexNumber} as (0, imaginary)
	 */
	public static ComplexNumber fromImaginary(final double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Returns a new {@link ComplexNumber} with set real and imaginary part
	 * according to the calculated value from the provided magnitude and angle.
	 * 
	 * @param magnitude
	 *            radius of the polar representation
	 * @param angle
	 *            angle of the polar representation
	 * @return {@link ComplexNumber} as (magnitude * Math.cos(angle), magnitude
	 *         * Math.sin(angle))
	 */
	public static ComplexNumber fromMagnitudeAndAngle(final double magnitude, double angle) {
		angle = fixNegativeAngle(angle % (2 * Math.PI));

		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Returns a new {@link ComplexNumber} with set real part to the provided
	 * value and imaginary part set to 0.
	 * 
	 * @param real
	 *            real part
	 * @return {@link ComplexNumber} as (real, 0)
	 */
	public static ComplexNumber fromReal(final double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Returns a new {@link ComplexNumber} with set real and imaginary part as
	 * it was parsed from the provided string. If the string represents an
	 * illegal complex number format or it isn't at all an complex number an
	 * {@link NumberFormatException} is thrown.
	 * 
	 * <pre>
	 * Some legal strings -&gt; "+1", "1.23", "i", "12+34i", "-12+12i", "-12-12i", ...;
	 * Illegal strings -&gt; "+13+13i", "Hello", "-6i+3i", "-6i-1", ...;
	 * </pre>
	 * 
	 * @param s
	 *            string representation of a complex number
	 * @return {@link ComplexNumber} as (real, imaginary)
	 * @throws NumberFormatException
	 *             if the provided string is in wrong format
	 */
	public static ComplexNumber parse(String s) {
		s = s.trim();

		if (s.equals("i")) {
			return new ComplexNumber(0, 1);
		}

		final String realPattern = "-?\\d+\\.*\\d*";
		final String imaginaryPattern = "-?\\d+\\.*\\d*i";
		Matcher matcher = setMatcher(s, realPattern);
		ComplexNumber complexNumber = null;

		// Matcher first checks if the given string equals to a real number
		// without the imaginary part
		if (matcher.matches()) {
			complexNumber = new ComplexNumber(Double.parseDouble(s), 0);
		} else {

			// Matcher checks if the given string equals to a imaginary number
			// without the real part
			matcher = setMatcher(s, imaginaryPattern);
			if (matcher.matches()) {
				complexNumber = new ComplexNumber(0, createDoubleFromImaginary(s));

				// Matcher checks if the given string can be a complex number
			} else {
				matcher = setMatcher(s, realPattern + "\\+?" + imaginaryPattern);

				if (matcher.matches()) {

					if (s.contains("+")) {
						final String[] numbers = s.split("\\+");
						complexNumber = new ComplexNumber(Double.parseDouble(numbers[0]),
								createDoubleFromImaginary(numbers[1]));
					} else {
						final String[] numbers = s.split("-");

						if (s.startsWith("-")) {
							complexNumber = new ComplexNumber(Double.parseDouble(addMinusSign(numbers[1])),
									createDoubleFromImaginary(addMinusSign(numbers[2])));
						} else {
							complexNumber = new ComplexNumber(Double.parseDouble(numbers[0]),
									createDoubleFromImaginary(addMinusSign(numbers[1])));
						}
					}
				}
			}
		}

		if (complexNumber == null) {
			throw new NumberFormatException("Illegal number format for the ComplexNumber class");
		}

		return complexNumber;
	}

	/**
	 * Sets {@link Matcher} reference to use a new pattern for matching.
	 * 
	 * @param s
	 *            string we are checking
	 * @param pattern
	 *            we use to check the input string
	 * @return {@link Matcher} updated to check the new pattern
	 */
	private static Matcher setMatcher(final String s, final String pattern) {
		final Pattern p = Pattern.compile(pattern);
		final Matcher matcher = p.matcher(s);
		return matcher;
	}

	/** Real part of the complex number */
	private final double real;

	/** Imaginary part of the complex number */
	private final double imaginary;

	/**
	 * Constructs a new {@link ComplexNumber} with set real and imaginary part;
	 * 
	 * @param real
	 *            real part
	 * @param imaginary
	 *            imaginary part
	 */
	public ComplexNumber(final double real, final double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Adds this {@link ComplexNumber} with the provided {@link ComplexNumber}
	 * and returns a new {@link ComplexNumber} with the added value.
	 * 
	 * @param c
	 *            other {@link ComplexNumber}
	 * @return new {@link ComplexNumber}
	 */
	public ComplexNumber add(final ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Divides this {@link ComplexNumber} with the provided
	 * {@link ComplexNumber} and returns a new {@link ComplexNumber} with the
	 * divided value.
	 * 
	 * @param c
	 *            other {@link ComplexNumber}
	 * @return new {@link ComplexNumber}
	 */
	public ComplexNumber div(final ComplexNumber c) {
		final double magnitude = getMagnitude() / c.getMagnitude();
		final double angle = getAngle() - c.getAngle();

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Returns polar angle of the complex number
	 * 
	 * @return polar angle
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		angle = fixNegativeAngle(angle);

		return angle;
	}

	/**
	 * Returns imaginary part of the complex number
	 * 
	 * @return imaginary
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns polar magnitude of the complex number
	 * 
	 * @return polar magnitude
	 */
	public double getMagnitude() {
		return Math.sqrt(imaginary * imaginary + real * real);
	}

	/**
	 * Returns real part of the complex number
	 * 
	 * @return real
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Multiplies this {@link ComplexNumber} with the provided
	 * {@link ComplexNumber} and returns a new {@link ComplexNumber} with the
	 * multiplied value.
	 * 
	 * @param c
	 *            other {@link ComplexNumber}
	 * @return new {@link ComplexNumber}
	 */
	public ComplexNumber mul(final ComplexNumber c) {
		final double magnitude = getMagnitude() * c.getMagnitude();
		final double angle = getAngle() + c.getAngle();

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Uses nth power on this {@link ComplexNumber} with the provided power and
	 * returns a new {@link ComplexNumber} with the new value.
	 * 
	 * @param n
	 *            power
	 * @return new {@link ComplexNumber}
	 */
	public ComplexNumber power(final int n) {
		final double magnitude = Math.pow(getMagnitude(), n);
		final double angle = getAngle() * n;

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Uses nth root on this {@link ComplexNumber} with the provided root and
	 * returns a new {@link ComplexNumber} array with the new values.
	 * 
	 * @param n
	 *            root
	 * @return new {@link ComplexNumber} array
	 */
	public ComplexNumber[] root(final int n) {
		final double magnitude = Math.pow(getMagnitude(), 1.0 / n);
		final double angle = getAngle();
		final ComplexNumber[] array = new ComplexNumber[n];

		for (int i = 0; i < n; i++) {
			array[i] = fromMagnitudeAndAngle(magnitude, (angle + 2 * i * Math.PI) / n);
		}

		return array;
	}

	/**
	 * Subtracts this {@link ComplexNumber} with the provided
	 * {@link ComplexNumber} and returns a new {@link ComplexNumber} with the
	 * subtracted value.
	 * 
	 * @param c
	 *            other {@link ComplexNumber}
	 * @return new {@link ComplexNumber}
	 */
	public ComplexNumber sub(final ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}

	@Override
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		String s = "";

		if (imaginary == 0) {
			s += formatDouble(real);
		} else if (real == 0) {
			s += formatDouble(imaginary) + "i";
		} else if (imaginary < 0) {
			s += formatDouble(real) + " " + formatDouble(imaginary) + "i";
		} else {
			s += formatDouble(real) + " + " + formatDouble(imaginary) + "i";
		}

		return s;
	}
}
