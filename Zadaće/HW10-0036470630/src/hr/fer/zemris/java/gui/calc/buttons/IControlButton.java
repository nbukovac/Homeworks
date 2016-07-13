package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Interface that contains all buttons used to control the {@link Calculator}s
 * operation flow.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IControlButton {

	/**
	 * Reset button
	 */
	JButton RESET_BUTTON = new JButton("res");

	/**
	 * Button for defining double values
	 */
	JButton DOT_BUTTON = new JButton(".");

	/**
	 * Button used to calculate the defined operation
	 */
	JButton EQUALS_BUTTON = new JButton("=");

	/**
	 * Button that clears the value shown on the screen
	 */
	JButton CLEAR_BUTTON = new JButton("clr");
}
