package hr.fer.zemris.java.hw11.jnotepadpp.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Class that extends {@link JLabel} and implements {@link ActionListener}. Show
 * current time that is refreshed every second.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class ClockLabel extends JLabel implements ActionListener {

	/** Serial version uid */
	private static final long serialVersionUID = -6488313521317615953L;

	/** Date format */
	private static final String format = "yyyy/MM/dd HH:mm:ss";

	/** {@link SimpleDateFormat} used to format the time */
	private final SimpleDateFormat formatter;

	/**
	 * Constructs a new {@link ClockLabel} and starts the {@link Timer}.
	 */
	public ClockLabel() {
		formatter = new SimpleDateFormat(format);
		super.setText(formatter.format(new Date()));
		final Timer timer = new Timer(1000, this);
		timer.start();
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		super.setText(formatter.format(new Date()));
	}

}
