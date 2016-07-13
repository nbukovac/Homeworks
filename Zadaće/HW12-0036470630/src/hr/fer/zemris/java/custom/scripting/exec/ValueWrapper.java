package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;

/**
 * Class that provides mathematical operations and number comparing on objects
 * that can be considered as a instance of {@link Double}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ValueWrapper {

	/**
	 * Value stored in this {@link ValueWrapper}
	 */
	private Object value;

	/**
	 * Constructs a new {@link ValueWrapper} with the provided value;
	 * 
	 * @param value
	 *            object value
	 */
	public ValueWrapper(final Object value) {
		super();
		this.value = value;
	}

	/**
	 * Checks if the provided {@code objectValue} is a instance of a valid
	 * object type. Valid object types are {@link Integer}, {@link Double},
	 * parsable {@link String} and a null reference.
	 * 
	 * @param objectValue
	 *            provided object
	 * @return true if valid object type, else false
	 */
	private boolean checkObjectType(final Object objectValue) {
		return objectValue instanceof Integer || objectValue instanceof Double || objectValue instanceof String
				|| objectValue == null;
	}

	/**
	 * Decrements the stored value with the provided value.
	 * 
	 * @param decValue
	 *            decrement value
	 */
	public void decrement(final Object decValue) {
		doOperation(decValue, (t, u) -> t - u, true);
	}

	/**
	 * Determines the double value of {@code objectValue}. If
	 * {@code objectValue} can't be interpreted as a {@code double} then a
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param objectValue
	 *            object
	 * @return double value of objectValue
	 * @throws IllegalArgumentException
	 *             If {@code objectValue} can't be interpreted as a
	 *             {@code double}
	 */
	private double determineValue(final Object objectValue) {
		if (!checkObjectType(objectValue)) {
			throw new IllegalArgumentException("Illegal object type is passed to ValueWrapper");
		}

		if (objectValue instanceof Integer) {
			final Integer intValue = (Integer) objectValue;
			return intValue.intValue();

		} else if (objectValue instanceof Double) {
			final Double doubleValue = (Double) objectValue;
			return doubleValue.doubleValue();

		} else if (objectValue instanceof String) {
			return parseNumber(objectValue);

		} else {
			return 0;
		}
	}

	/**
	 * Divides the stored value with the provided value. An
	 * {@link ArithmeticException} is thrown if a division by zero is tried.
	 * 
	 * @param divValue
	 *            divide value
	 * @throws ArithmeticException
	 *             if a division by zero is tried
	 */
	public void divide(final Object divValue) {
		if (determineValue(divValue) == 0.0) {
			throw new ArithmeticException("Division by zero isn't a valid operation");
		}

		doOperation(divValue, (t, u) -> t / u, true);
	}

	/**
	 * Does the operation specified by {@code operation} and depending on
	 * {@code overloadValue} sets the {@code value} to the new calculated value.
	 * 
	 * @param objectValue
	 *            operation value
	 * @param operation
	 *            type of operation
	 * @param overloadValue
	 *            flag used to determine if value needs to be set or not
	 * @return operation result
	 */
	private int doOperation(final Object objectValue, final BinaryOperator<Double> operation,
			final boolean overloadValue) {

		double thisValue = determineValue(value);
		final double otherValue = determineValue(objectValue);

		thisValue = operation.apply(thisValue, otherValue);

		if (overloadValue) {
			setValueBasedOnType(objectValue, thisValue);
		}

		return (int) thisValue;
	}

	/**
	 * Returns the value stored in {@code value}.
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Increments the stored value with the provided value.
	 * 
	 * @param incValue
	 *            increment value
	 */
	public void increment(final Object incValue) {
		doOperation(incValue, (t, u) -> t + u, true);
	}

	/**
	 * Multiplies the stored value with the provided value.
	 * 
	 * @param mulValue
	 *            multiply value
	 */
	public void multiply(final Object mulValue) {
		doOperation(mulValue, (t, u) -> t * u, true);
	}

	/**
	 * Compares two numbers by a specified set of rules. If a value is a null
	 * reference it is treated as a integer value of 0, if both values are valid
	 * number types then normal number comparison is done. If two numbers are
	 * equal, 0 is returned, if this {@code value} is greater than
	 * {@code withValue} then 1 is returned, else -1.
	 * 
	 * @param withValue
	 *            other number
	 * @return If two numbers are equal, 0 is returned, if this {@code value} is
	 *         greater than {@code withValue} then 1 is returned, else -1.
	 */
	public int numCompare(final Object withValue) {
		if (value == null && withValue == null) {
			return 0;
		}

		if (value == null) {
			return doOperation(withValue, (t, u) -> (double) -u.compareTo(0.0), false);
		} else if (withValue == null) {
			return doOperation(withValue, (t, u) -> (double) t.compareTo(0.0), false);
		}

		return doOperation(withValue, (t, u) -> (double) t.compareTo(u), false);
	}

	/**
	 * Parses a number from a {@link String}, first as a integer then if failed
	 * as a double. If both parse attempts fail a {@link NumberFormatException}
	 * is thrown.
	 * 
	 * @param objectValue
	 *            String for parsing
	 * @return parsed number
	 * @throws NumberFormatException
	 *             If both parse attempts fail
	 */
	private double parseNumber(final Object objectValue) {
		try {
			final int number = Integer.parseInt(objectValue.toString());
			return number;

		} catch (final NumberFormatException e) {
			final double doubleNumber = Double.parseDouble(objectValue.toString());
			return doubleNumber;
		}
	}

	/**
	 * Sets the stored value to the provided value.
	 * 
	 * @param value
	 *            new value
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * Sets the value based on the types of arguments used in operations. If
	 * both values were {@link Integer}s then the end result is a
	 * {@link Integer}, else if one value was a {@link Double} then the end
	 * result is a {@link Double}.
	 * 
	 * @param changeValue
	 *            the value specified with the operation
	 * @param newValue
	 *            new value for this {@code value}
	 */
	private void setValueBasedOnType(final Object changeValue, final double newValue) {
		if (value instanceof Integer && changeValue instanceof Integer) {
			value = Integer.valueOf((int) newValue);

		} else if (changeValue instanceof Double || value instanceof Double) {
			value = Double.valueOf(newValue);

		} else {
			final double number = parseNumber(changeValue);

			if (Math.rint(number) == number) {
				value = Integer.valueOf((int) newValue);
			} else {
				value = Double.valueOf(newValue);
			}
		}

	}
}
