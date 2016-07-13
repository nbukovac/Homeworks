package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * GUI program that demonstrates usage of the {@link PrimListModel} contained
 * inside a {@link JList}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class PrimDemo extends JFrame {

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
			new PrimDemo().setVisible(true);
		});
	}

	/**
	 * Constructs a new {@link PrimDemo} and initializes the GUI.
	 */
	public PrimDemo() {
		setTitle("Prim demo");
		setSize(400, 400);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initializes the GUI
	 */
	private void initGUI() {
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		final PrimListModel model = new PrimListModel();
		final JList<Integer> leftList = new JList<>(model);
		final JScrollPane leftScrollPane = new JScrollPane(leftList);

		final JList<Integer> rightList = new JList<>(model);
		final JScrollPane rightScrollPane = new JScrollPane(rightList);

		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(leftScrollPane);
		panel.add(rightScrollPane);

		final JButton nextPrimButton = new JButton("Next prim number");
		nextPrimButton.addActionListener(e -> {
			model.addPrimNumber();
		});

		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(nextPrimButton, BorderLayout.PAGE_END);
	}

}
