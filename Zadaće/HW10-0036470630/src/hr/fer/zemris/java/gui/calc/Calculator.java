package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.ButtonType;
import hr.fer.zemris.java.gui.calc.buttons.IBinaryOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.IControlButton;
import hr.fer.zemris.java.gui.calc.buttons.IStackOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.IUnaryOperationButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * GUI program that provides the functionality of a calculator. It is possible
 * to do unary and binary operations with number as well as to store those
 * numbers on a stack do stack operations with them. Also there are control
 * buttons such as {@code reset}(resets the {@link Calculator} to its initial
 * state), {@code clear}(clears the number on the screen) and {@code equals}
 * (does the last stored operation). <br/>
 * <p>
 * Because of the great number of provided operations there is a
 * {@link JCheckBox} component used to invert some operations to provide
 * alternative operations to the already provided ones. If the {@link JCheckBox}
 * is selected then the inverted operations are shown.
 * </p>
 * <p>
 * To see all possible binary operations see the {@link IBinaryOperationButton}.
 * <br/>
 * To see all possible unary operations see the {@link IUnaryOperationButton}.
 * <br/>
 * To see all possible stack operations see the {@link IStackOperationButton}.
 * <br/>
 * </p>
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Calculator extends JFrame {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Program entry point used to start the GUI.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}

	/**
	 * {@link BackendCalculator} used for calculations and program flow
	 */
	private BackendCalculator backend;

	/**
	 * Constructs a new {@link Calculator} and initializes the GUI.
	 */
	public Calculator() {
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		initGUI();
	}

	/**
	 * Colors the specified {@link JButton} to {@code Color.LIGHT_GRAY}.
	 * 
	 * @param button
	 *            {@link JButton} to color
	 */
	private void colorButton(final JButton button) {
		button.setOpaque(true);
		button.setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Initializes original binary operation buttons specified by
	 * {@link IBinaryOperationButton} and positions them accordingly on the
	 * specified {@link JPanel}.
	 * 
	 * @param panel
	 *            {@link JPanel} where the buttons are added
	 */
	private void initBinaryOperationButtons(final JPanel panel) {
		final Map<JButton, DoubleBinaryOperator> binaryButtons = originalBinaryButtons(panel);

		binaryButtons.put(IBinaryOperationButton.ADD, IBinaryOperationButton.ADD_OPERATION);
		panel.add(IBinaryOperationButton.ADD, "5,6");

		binaryButtons.put(IBinaryOperationButton.SUB, IBinaryOperationButton.SUB_OPERATION);
		panel.add(IBinaryOperationButton.SUB, "4,6");

		binaryButtons.put(IBinaryOperationButton.MULTIPLY, IBinaryOperationButton.MULTIPLY_OPERATION);
		panel.add(IBinaryOperationButton.MULTIPLY, "3,6");

		binaryButtons.put(IBinaryOperationButton.DIVIDE, IBinaryOperationButton.DIVIDE_OPERATION);
		panel.add(IBinaryOperationButton.DIVIDE, "2,6");

		setupBinaryButtons(binaryButtons);
	}

	/**
	 * Initializes all buttons contained by a {@link Calculator}.
	 * 
	 * @param panel
	 *            {@link JPanel} where all buttons are added
	 */
	private void initButtons(final JPanel panel) {
		initDigitButtons(panel);
		initControlButtons(panel);
		initUnaryOperationButtons(panel);
		initBinaryOperationButtons(panel);
		initStackOperationButtons(panel);

		final JCheckBox checkBox = new JCheckBox("Inv");

		final ActionListener action = a -> {
			final JCheckBox cBox = (JCheckBox) a.getSource();
			invertOperations(cBox.isSelected(), panel);
			panel.revalidate();
		};

		checkBox.addActionListener(action);
		checkBox.setOpaque(true);
		checkBox.setBackground(Color.RED);

		panel.add(checkBox, "5,7");
	}

	/**
	 * Initializes all control buttons specified by {@link IControlButton}.
	 * 
	 * @param panel
	 *            {@link JPanel} where all buttons are added
	 */
	private void initControlButtons(final JPanel panel) {
		final List<JButton> controlButtons = new ArrayList<>();

		final ActionListener action = a -> {
			final JButton b = (JButton) a.getSource();
			backend.buttonPress(ButtonType.CONTROL, b.getText());
		};

		controlButtons.add(IControlButton.RESET_BUTTON);
		controlButtons.add(IControlButton.DOT_BUTTON);
		controlButtons.add(IControlButton.EQUALS_BUTTON);
		controlButtons.add(IControlButton.CLEAR_BUTTON);

		for (final JButton button : controlButtons) {
			button.addActionListener(action);
			colorButton(button);
		}

		panel.add(IControlButton.DOT_BUTTON, "5,5");
		panel.add(IControlButton.EQUALS_BUTTON, "1,6");
		panel.add(IControlButton.CLEAR_BUTTON, "1,7");
		panel.add(IControlButton.RESET_BUTTON, "2,7");
	}

	/**
	 * Initializes all digit buttons and adds them to the specified
	 * {@link JPanel}.
	 * 
	 * @param panel
	 *            {@link JPanel} where all buttons are added
	 */
	private void initDigitButtons(final JPanel panel) {
		final JButton[] digitButtons = new JButton[10];

		final ActionListener action = a -> {
			final JButton b = (JButton) a.getSource();
			final int index = Integer.parseInt(b.getText());
			backend.buttonPress(ButtonType.DIGIT, Integer.valueOf(index));
		};

		for (int i = 0; i < 10; i++) {
			digitButtons[i] = new JButton(i + "");
			digitButtons[i].addActionListener(action);
			colorButton(digitButtons[i]);
		}

		panel.add(digitButtons[0], "5,3");
		panel.add(digitButtons[1], "4,3");
		panel.add(digitButtons[2], "4,4");
		panel.add(digitButtons[3], "4,5");
		panel.add(digitButtons[4], "3,3");
		panel.add(digitButtons[5], "3,4");
		panel.add(digitButtons[6], "3,5");
		panel.add(digitButtons[7], "2,3");
		panel.add(digitButtons[8], "2,4");
		panel.add(digitButtons[9], "2,5");
	}

	/**
	 * Initializes the GUI for this program.
	 */
	private void initGUI() {
		final JPanel panel = new JPanel(new CalcLayout(5));

		final JLabel screen = new JLabel("0", SwingConstants.RIGHT);
		screen.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		screen.setBackground(Color.YELLOW);
		screen.setOpaque(true);
		screen.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 26));
		panel.add(screen, "1,1");

		backend = new BackendCalculator(screen);

		initButtons(panel);

		panel.repaint();

		getContentPane().add(panel);
	}

	/**
	 * Initializes all stack operation buttons specified by
	 * {@link IStackOperationButton} and adds them to the specified
	 * {@link JPanel}.
	 * 
	 * @param panel
	 *            {@link JPanel} where all buttons are added
	 */
	private void initStackOperationButtons(final JPanel panel) {
		final List<JButton> stackButtons = new ArrayList<>();

		stackButtons.add(IStackOperationButton.POP);
		stackButtons.add(IStackOperationButton.PUSH);

		final ActionListener action = a -> {
			final JButton b = (JButton) a.getSource();
			backend.buttonPress(ButtonType.STACK, b.getText());
		};

		for (final JButton button : stackButtons) {
			button.addActionListener(action);
			colorButton(button);
		}

		panel.add(IStackOperationButton.POP, "4,7");
		panel.add(IStackOperationButton.PUSH, "3,7");
	}

	/**
	 * Initializes original unary operation buttons specified by
	 * {@link IUnaryOperationButton} and positions them accordingly on the
	 * specified {@link JPanel}.
	 * 
	 * @param panel
	 *            {@link JPanel} where the buttons are added
	 */
	private void initUnaryOperationButtons(final JPanel panel) {
		final Map<JButton, DoubleUnaryOperator> unaryButtons = originalUnaryButtons(panel);

		unaryButtons.put(IUnaryOperationButton.REVERT, IUnaryOperationButton.REVERT_OPERATION);
		panel.add(IUnaryOperationButton.REVERT, "2,1");

		unaryButtons.put(IUnaryOperationButton.NEGATE, IUnaryOperationButton.NEGATE_OPERATION);
		panel.add(IUnaryOperationButton.NEGATE, "5,4");

		setupUnaryButtons(unaryButtons);

	}

	/**
	 * Returns a {@link Map} with the inverted binary buttons as they are
	 * specified by {@link IBinaryOperationButton}.
	 * 
	 * @param panel
	 *            {@link JPanel} where the buttons are added
	 * @return {@link Map} with the inverted binary buttons
	 */
	private Map<JButton, DoubleBinaryOperator> invertedBinaryButtons(final JPanel panel) {
		final Map<JButton, DoubleBinaryOperator> binaryButtons = new HashMap<>();

		binaryButtons.put(IBinaryOperationButton.NTH_ROOT, IBinaryOperationButton.NTH_ROOT_OPERATION);
		panel.add(IBinaryOperationButton.NTH_ROOT, "5,1");

		return binaryButtons;
	}

	/**
	 * Returns a {@link Map} with the inverted unary buttons as they are
	 * specified by {@link IUnaryOperationButton}.
	 * 
	 * @param panel
	 *            {@link JPanel} where the buttons are added
	 * @return {@link Map} with the inverted unary buttons
	 */
	private Map<JButton, DoubleUnaryOperator> invertedUnaryButtons(final JPanel panel) {
		final Map<JButton, DoubleUnaryOperator> unaryButtons = new HashMap<>();

		unaryButtons.put(IUnaryOperationButton.ARCSIN, IUnaryOperationButton.ARCSIN_OPERATION);
		panel.add(IUnaryOperationButton.ARCSIN, "2,2");

		unaryButtons.put(IUnaryOperationButton.ARCCOS, IUnaryOperationButton.ARCCOS_OPERATION);
		panel.add(IUnaryOperationButton.ARCCOS, "3,2");

		unaryButtons.put(IUnaryOperationButton.ARCTAN, IUnaryOperationButton.ARCTAN_OPERATION);
		panel.add(IUnaryOperationButton.ARCTAN, "4,2");

		unaryButtons.put(IUnaryOperationButton.ARCCTG, IUnaryOperationButton.ARCCTG_OPERATION);
		panel.add(IUnaryOperationButton.ARCCTG, "5,2");

		unaryButtons.put(IUnaryOperationButton.POWER_10, IUnaryOperationButton.POWER_10_OPERATION);
		panel.add(IUnaryOperationButton.POWER_10, "3,1");

		unaryButtons.put(IUnaryOperationButton.POWER_E, IUnaryOperationButton.POWER_E_OPERATION);
		panel.add(IUnaryOperationButton.POWER_E, "4,1");

		return unaryButtons;
	}

	/**
	 * Inverts the operations based on the {@code selected} argument. If
	 * {@code selected} is true then the inverted operations are shown, else the
	 * original are shown.
	 * 
	 * @param selected
	 *            specifies if inverted operations are to be shown or not
	 * @param panel
	 *            {@link JPanel} where the buttons are added
	 */
	private void invertOperations(final boolean selected, final JPanel panel) {
		Map<JButton, DoubleUnaryOperator> unaryButtons;
		Map<JButton, DoubleBinaryOperator> binaryButtons;

		if (selected) {
			unaryButtons = originalUnaryButtons(panel);
			binaryButtons = originalBinaryButtons(panel);

			for (final JButton button : unaryButtons.keySet()) {
				panel.remove(button);
			}

			for (final JButton button : binaryButtons.keySet()) {
				panel.remove(button);
			}

			unaryButtons = invertedUnaryButtons(panel);
			binaryButtons = invertedBinaryButtons(panel);

		} else {
			unaryButtons = invertedUnaryButtons(panel);
			binaryButtons = invertedBinaryButtons(panel);

			for (final JButton button : unaryButtons.keySet()) {
				panel.remove(button);
			}

			for (final JButton button : binaryButtons.keySet()) {
				panel.remove(button);
			}

			binaryButtons = originalBinaryButtons(panel);
			unaryButtons = originalUnaryButtons(panel);
		}

		setupUnaryButtons(unaryButtons);
		setupBinaryButtons(binaryButtons);
		panel.repaint();
		// repaint();
	}

	/**
	 * Returns a {@link Map} with the original binary buttons that can be
	 * inverted as they are specified by {@link IBinaryOperationButton}.
	 * 
	 * @param panel
	 *            {@link JPanel} where the buttons are added
	 * @return {@link Map} with the original binary buttons
	 */
	private Map<JButton, DoubleBinaryOperator> originalBinaryButtons(final JPanel panel) {
		final Map<JButton, DoubleBinaryOperator> binaryButtons = new HashMap<>();

		binaryButtons.put(IBinaryOperationButton.X_POWER_N, IBinaryOperationButton.X_POWER_N_OPERATION);
		panel.add(IBinaryOperationButton.X_POWER_N, "5,1");

		return binaryButtons;
	}

	/**
	 * Returns a {@link Map} with the original unary buttons that can be
	 * inverted as they are specified by {@link IBinaryOperationButton}.
	 * 
	 * @param panel
	 *            {@link JPanel} where the buttons are added
	 * @return {@link Map} with the original unary buttons
	 */
	private Map<JButton, DoubleUnaryOperator> originalUnaryButtons(final JPanel panel) {
		final Map<JButton, DoubleUnaryOperator> unaryButtons = new HashMap<>();

		unaryButtons.put(IUnaryOperationButton.SIN, IUnaryOperationButton.SIN_OPERATION);
		panel.add(IUnaryOperationButton.SIN, "2,2");

		unaryButtons.put(IUnaryOperationButton.COS, IUnaryOperationButton.COS_OPERATION);
		panel.add(IUnaryOperationButton.COS, "3,2");

		unaryButtons.put(IUnaryOperationButton.TAN, IUnaryOperationButton.TAN_OPERATION);
		panel.add(IUnaryOperationButton.TAN, "4,2");

		unaryButtons.put(IUnaryOperationButton.CTG, IUnaryOperationButton.CTG_OPERATION);
		panel.add(IUnaryOperationButton.CTG, "5,2");

		unaryButtons.put(IUnaryOperationButton.LOG, IUnaryOperationButton.LOG_OPERATION);
		panel.add(IUnaryOperationButton.LOG, "3,1");

		unaryButtons.put(IUnaryOperationButton.LN, IUnaryOperationButton.LN_OPERATION);
		panel.add(IUnaryOperationButton.LN, "4,1");

		return unaryButtons;
	}

	/**
	 * Sets a {@link ActionListener} to every binary button and also colors
	 * them.
	 * 
	 * @param binaryButtons
	 *            buttons specified for setting up
	 */
	private void setupBinaryButtons(final Map<JButton, DoubleBinaryOperator> binaryButtons) {
		final ActionListener action = a -> {
			final JButton b = (JButton) a.getSource();
			backend.buttonPress(ButtonType.BINARY, binaryButtons.get(b));
		};

		for (final JButton button : binaryButtons.keySet()) {
			colorButton(button);
			button.addActionListener(action);
		}
	}

	/**
	 * Sets a {@link ActionListener} to every unary button and also colors them.
	 * 
	 * @param unaryButtons
	 *            buttons specified for setting up
	 */
	private void setupUnaryButtons(final Map<JButton, DoubleUnaryOperator> unaryButtons) {
		final ActionListener action = a -> {
			final JButton b = (JButton) a.getSource();
			backend.buttonPress(ButtonType.UNARY, unaryButtons.get(b));
		};

		for (final JButton button : unaryButtons.keySet()) {
			colorButton(button);
			button.addActionListener(action);
		}
	}
}
