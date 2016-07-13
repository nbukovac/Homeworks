package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Interface that contains all buttons used to do stack operations with our
 * {@link Calculator}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface IStackOperationButton {

	/** Button for push stack operation */
	JButton PUSH = new JButton("push");

	/** Button for pop stack operation */
	JButton POP = new JButton("pop");
}
