package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Interface that contains all buttons that define a unary operation our
 * {@link Calculator} can do. It also defines how an operation is done.
 * operation flow.
 * <p>
 * Original operations:
 * <ul>
 * <li>SIN</li>
 * <li>COS</li>
 * <li>TAN</li>
 * <li>CTG</li>
 * <li>LN</li>
 * <li>LOG</li>
 * <li>REVERT</li>
 * <li>NEGATE</li>
 * </ul>
 * </p>
 * <p>
 * Inverted operations:
 * <ul>
 * <li>ARCSIN</li>
 * <li>ARCCOS</li>
 * <li>ARCTAN</li>
 * <li>ARCCTG</li>
 * <li>POWER_10</li>
 * <li>POWER_E</li>
 * </ul>
 * </p>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IUnaryOperationButton {

	/** Button for the negate operation */
	JButton NEGATE = new JButton("+/-");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the negate operation is
	 * performed
	 */
	DoubleUnaryOperator NEGATE_OPERATION = x -> (x * -1);

	/** Button for the natural logarithm operation */
	JButton LN = new JButton("ln");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the natural logarithm
	 * operation is performed
	 */
	DoubleUnaryOperator LN_OPERATION = x -> (Math.log(x));

	/** Button for the cotangent operation */
	JButton CTG = new JButton("ctg");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the cotangent operation is
	 * performed
	 */
	DoubleUnaryOperator CTG_OPERATION = x -> (Math.cos(x) / Math.sin(x));

	/** Button for the tangent operation */
	JButton TAN = new JButton("tan");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the tangent operation is
	 * performed
	 */
	DoubleUnaryOperator TAN_OPERATION = x -> (Math.tan(x));

	/** Button for the sine operation */
	JButton SIN = new JButton("sin");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the sine operation is
	 * performed
	 */
	DoubleUnaryOperator SIN_OPERATION = x -> (Math.sin(x));

	/** Button for the cosine operation */
	JButton COS = new JButton("cos");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the cosine operation is
	 * performed
	 */
	DoubleUnaryOperator COS_OPERATION = x -> (Math.cos(x));

	/** Button for the 10 logarithm operation */
	JButton LOG = new JButton("log");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the 10 logarithm operation
	 * is performed
	 */
	DoubleUnaryOperator LOG_OPERATION = x -> (Math.log10(x));

	/** Button for the revert operation */
	JButton REVERT = new JButton("1/x");
	/**
	 * {@link DoubleUnaryOperator} that specifies how revert operation is
	 * performed
	 */
	DoubleUnaryOperator REVERT_OPERATION = x -> (1 / x);

	// Inverted operations

	/** Button for the arc cotangent operation */
	JButton ARCCTG = new JButton("arcctg");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the arc cotangent
	 * operation is performed
	 */
	DoubleUnaryOperator ARCCTG_OPERATION = x -> (Math.atan(1 / Math.cos(x) / Math.sin(x)));

	/** Button for the arc tangent operation */
	JButton ARCTAN = new JButton("arctan");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the arc tangent operation
	 * is performed
	 */
	DoubleUnaryOperator ARCTAN_OPERATION = x -> (Math.atan(x));

	/** Button for the arc cosine operation */
	JButton ARCCOS = new JButton("arccos");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the arc cosine operation
	 * is performed
	 */
	DoubleUnaryOperator ARCCOS_OPERATION = x -> (Math.acos(x));

	/** Button for the arc sine operation */
	JButton ARCSIN = new JButton("arcsin");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the arc sine operation is
	 * performed
	 */
	DoubleUnaryOperator ARCSIN_OPERATION = x -> (Math.asin(x));

	/** Button for the 10 on power x operation */
	JButton POWER_10 = new JButton("10^");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the 10 on power x
	 * operation is performed
	 */
	DoubleUnaryOperator POWER_10_OPERATION = x -> (Math.pow(10, x));

	/** Button for the e on power x operation */
	JButton POWER_E = new JButton("e^");
	/**
	 * {@link DoubleUnaryOperator} that specifies how the e on power x operation
	 * is performed
	 */
	DoubleUnaryOperator POWER_E_OPERATION = x -> (Math.pow(Math.E, x));
}
