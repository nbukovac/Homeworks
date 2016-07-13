package hr.fer.zemris.java.gui.calc;

import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.buttons.ButtonType;

/**
 * Class that does all calculations and logic operations for class
 * {@link Calculator}. If this is viewed as a MVC application this class would
 * be the controller in this pattern while the {@link Calculator} class then
 * could be considered as the view.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class BackendCalculator {

	/** Value on screen */
	private double screenValue;

	/** Value stored in memory */
	private double memoryValue;

	/** Last binary operator */
	private DoubleBinaryOperator binaryOperator;

	/** Last unary operator */
	private DoubleUnaryOperator unaryOperator;

	/** Flag to check if a operation completed */
	private boolean operationCompleted;

	/** Flag to check if the memory is set */
	private boolean memorySet;

	/**
	 * Flag used to determine if a number that is entered is entered as a double
	 */
	private boolean dotActive;

	/** Current offset for a double value */
	private double doubleOffset = 0.1;

	/** Stack structure */
	private final Stack<Double> stack;

	/**
	 * If set to true then the binary operation is executed, else the unary.
	 */
	private boolean operation;

	/** {@link JLabel} reference for the screen */
	private final JLabel screenLabel;

	/**
	 * Constructs a new {@link BackendCalculator} with the specified
	 * {@link Calculator} screen label.
	 * 
	 * @param screenLabel
	 *            {@link Calculator} screen
	 */
	public BackendCalculator(final JLabel screenLabel) {
		this.screenLabel = screenLabel;
		stack = new Stack<>();
	}

	/**
	 * Determines which button is pressed to decide what kind of operation is
	 * done with the provided {@code value}.
	 * 
	 * @param buttonType
	 *            {@link ButtonType} of the pressed button
	 * @param value
	 *            value describing the pressed button or the operation the
	 *            button does
	 */
	public void buttonPress(final ButtonType buttonType, final Object value) {
		switch (buttonType) {
		case BINARY:
			operation = true;
			resetEnteringDouble();

			if (memorySet) {
				controlPress("=");
			} else {
				memoryValue = screenValue;
				screenValue = 0;
				memorySet = true;
			}

			binaryOperator = (DoubleBinaryOperator) value;

			break;
		case CONTROL:
			controlPress(value.toString());

			break;
		case DIGIT:
			digitPress((int) value);

			break;
		case UNARY:
			operation = false;
			resetEnteringDouble();
			unaryOperator = (DoubleUnaryOperator) value;
			controlPress("=");

			memorySet = true;

			break;
		case STACK:
			stackPress(value.toString());
			break;
		default:
			break;

		}

		updateScreen();
	}

	/**
	 * Determines which of the {@code ButtonType.CONTROL} buttons is pressed and
	 * based on the pressed button does the required operation.
	 * 
	 * @param name
	 *            name of the pressed button
	 */
	public void controlPress(final String name) {
		if (name.equals("=")) {
			final double tempValue = screenValue;

			if (operation && binaryOperator != null) {
				screenValue = binaryOperator.applyAsDouble(memoryValue, screenValue);
				binaryOperator = null;

			} else if (!operation && unaryOperator != null) {
				screenValue = unaryOperator.applyAsDouble(screenValue);
			}

			operationCompleted = true;
			memoryValue = tempValue;
		} else if (name.equals("clr")) {
			screenValue = 0;
			resetEnteringDouble();

		} else if (name.equals("res")) {
			operation = false;
			binaryOperator = null;
			unaryOperator = null;
			memoryValue = 0;
			screenValue = 0;
			memorySet = false;
			stack.clear();
			resetEnteringDouble();

		} else {
			dotActive = true;
		}
	}

	/**
	 * Sets the {@code screenValue} so that it reflects the correctly entered
	 * number whether it is a double or a integer number.
	 * 
	 * @param value
	 *            value of the pressed {@code ButtonType.DIGIT} button
	 */
	public void digitPress(final int value) {
		if (!dotActive) {
			if (operationCompleted) {
				memoryValue = screenValue;
				screenValue = value;
				operationCompleted = false;
			} else {
				screenValue *= 10;
				screenValue += value;
			}
		} else {
			screenValue += value * doubleOffset;
			doubleOffset *= 0.1;
		}
	}

	/**
	 * Stops treating the entered number, as a double number.
	 */
	private void resetEnteringDouble() {
		dotActive = false;
		doubleOffset = 0.1;
	}

	/**
	 * Determines which one of the {@code ButtonType.STACK} buttons is pressed
	 * based on the {@code operation} argument and does the specified operation.
	 * 
	 * @param operation
	 *            type of stack operation
	 */
	private void stackPress(final String operation) {
		if (operation.equals("pop")) {
			if (stack.empty()) {

			} else {
				screenValue = stack.pop();
			}
		} else {
			stack.push(screenValue);
		}

	}

	/**
	 * Updates the value on the {@link Calculator} screen.
	 */
	private void updateScreen() {
		screenLabel.setText(String.format("%.2f", screenValue));
	}

}
