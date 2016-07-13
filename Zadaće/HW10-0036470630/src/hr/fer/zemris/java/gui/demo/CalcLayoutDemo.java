package hr.fer.zemris.java.gui.demo;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Program that demonstrates how the {@link CalcLayout} interacts.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class CalcLayoutDemo extends JFrame {

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
			new CalcLayoutDemo().setVisible(true);
		});
	}

	/**
	 * Constructs a new {@link CalcLayoutDemo} and defines how the GUI
	 * interacts.
	 */
	public CalcLayoutDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("CalcLayout demo");
		setSize(300, 300);
		initGUI();
	}

	/**
	 * Initializes the GUI by filling it with {@link Component}s
	 */
	private void initGUI() {
		final JPanel p = new JPanel(new CalcLayout(3));

		final JLabel label1 = new JLabel("1,1");
		label1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.add(label1, new RCPosition(1, 1));

		final JLabel label2 = new JLabel("2,3");
		label2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.add(label2, new RCPosition(2, 3));

		final JLabel label3 = new JLabel("2,7");
		label3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.add(label3, new RCPosition(2, 7));

		final JLabel label4 = new JLabel("4,2");
		label4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.add(label4, new RCPosition(4, 2));

		final JLabel label5 = new JLabel("4,5");
		label5.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.add(label5, new RCPosition(4, 5));

		final JLabel label6 = new JLabel("4,7");
		label6.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.add(label6, new RCPosition(4, 7));

		final JLabel label7 = new JLabel("5,2");
		label7.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.add(label7, new RCPosition(5, 2));

		final JLabel label8 = new JLabel("2,5");
		label8.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		p.add(label8, "2,5");

		getContentPane().add(p);
	}

}
