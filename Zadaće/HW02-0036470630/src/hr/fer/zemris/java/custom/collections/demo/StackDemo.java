package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Program which solves basic mathematical operations on integers such as "+",
 * "-", "*", "/" and "%". Expressions should be in postfix representation as it
 * is shown in the example.
 * 
 * <pre>
 * 	Example:
 * "8 2 -" is interpreted as "8 - 2"
 * </pre>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class StackDemo {

	/**
	 * Performs operation defined by the <code>operation</code> argument on
	 * <code>number1</code> and <code>number2</code>. Operations where order is
	 * relevant are performed as number2 "operation" number1. If the operation
	 * isn't specified an {@link UnsupportedOperationException} is thrown.
	 * 
	 * @param operation
	 *            specified mathematical operation
	 * @param number1
	 *            first number
	 * @param number2
	 *            second number
	 * @return operation result
	 */
	private static int calculate(final String operation, final int number1, final int number2) {
		int result = 0;

		switch (operation) {
		case "+":
			result = number1 + number2;
			break;
		case "-":
			result = number2 - number1;
			break;
		case "/":
			result = number2 / number1;
			break;
		case "*":
			result = number1 * number2;
			break;
		case "%":
			result = number2 % number1;
			break;
		default:
			throw new UnsupportedOperationException();
		}

		return result;
	}

	/**
	 * Main method of the program that expects a single argument expression in a
	 * correct format.
	 * 
	 * @param args
	 *            expression for processing
	 */
	public static void main(final String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
			return;
		}

		final String[] arguments = args[0].trim().split("\\s+");
		final ObjectStack stack = new ObjectStack();

		// For every argument try to parse the number, if the parsing fails
		// check if the argument is
		// an operation, if not throw an UnsupportedOperationException and end
		// the program, else do
		// the operation and store the result on the Stack. If the stack was
		// empty or there was a divide by 0,
		// throw an appropriate exception and end the program
		for (final String argument : arguments) {
			try {
				final int number = Integer.parseInt(argument);
				stack.push(new Integer(number));

			} catch (final Exception e) {
				try {
					final int result = calculate(argument, ((Integer) stack.pop()).intValue(),
							((Integer) stack.pop()).intValue());
					stack.push(new Integer(result));

				} catch (final ArithmeticException e1) {
					System.err.println("You are trying to divide with 0. You can't do that.");
					return;

				} catch (final EmptyStackException e2) {
					System.err.println("All of the provided number elements were processed "
							+ "but an another operation was requested. You can't do operations with "
							+ "only 0 or 1 elements");
					return;

				} catch (final UnsupportedOperationException e3) {
					System.err.println("You requested an illegal operation or you entered an invalid "
							+ "number type. Legal operations are" + " +, *, /, - and %."
							+ "Legal number type is integer.");
					return;
				}
			}
		}

		if (stack.size() != 1) {
			System.err.println("An error occured while parsing the input expression");
		} else {
			System.out.println("Expression evaluates to " + stack.pop().toString());
		}
	}

}
