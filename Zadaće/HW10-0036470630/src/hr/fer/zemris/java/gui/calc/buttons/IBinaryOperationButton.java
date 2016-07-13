package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Interface that contains all buttons that define a binary operation our
 * {@link Calculator} can do. It also defines how an operation is done.
 * operation flow.<br/>
 * <p>
 * Original operations:
 * <ul>
 * <li>ADD</li>
 * <li>SUB</li>
 * <li>MULTIPLY</li>
 * <li>DIVIDE</li>
 * <li>X_POWER_N</li>
 * </ul>
 * </p>
 * <p>
 * Inverted operations:
 * <ul>
 * <li>NTH_ROOT</li>
 * </ul>
 * </p>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IBinaryOperationButton {

	/** Button for the add operation */
	JButton ADD = new JButton("+");
	/**
	 * {@link DoubleBinaryOperator} that specifies how the add operation is
	 * performed
	 */
	DoubleBinaryOperator ADD_OPERATION = (x, y) -> (x + y);

	/** Button for the sub operation */
	JButton SUB = new JButton("-");
	/**
	 * {@link DoubleBinaryOperator} that specifies how the sub operation is
	 * performed
	 */
	DoubleBinaryOperator SUB_OPERATION = (x, y) -> (x - y);

	/** Button for the multiply operation */
	JButton MULTIPLY = new JButton("*");
	/**
	 * {@link DoubleBinaryOperator} that specifies how the multiply operation is
	 * performed
	 */
	DoubleBinaryOperator MULTIPLY_OPERATION = (x, y) -> (x * y);

	/** Button for the divide operation */
	JButton DIVIDE = new JButton("/");
	/**
	 * {@link DoubleBinaryOperator} that specifies how the divide operation is
	 * performed
	 */
	DoubleBinaryOperator DIVIDE_OPERATION = (x, y) -> (x / y);

	/** Button for the x on nth power operation */
	JButton X_POWER_N = new JButton("x^n");
	/**
	 * {@link DoubleBinaryOperator} that specifies how the x on nth power
	 * operation is performed
	 */
	DoubleBinaryOperator X_POWER_N_OPERATION = (x, y) -> (Math.pow(x, y));

	// Inverted operations

	/** Button for the nth root of x operation */
	JButton NTH_ROOT = new JButton("n\u221Ax");
	/**
	 * {@link DoubleBinaryOperator} that specifies how the nth root of x
	 * operation is performed
	 */
	DoubleBinaryOperator NTH_ROOT_OPERATION = (x, y) -> (Math.pow(x, 1 / y));
}
