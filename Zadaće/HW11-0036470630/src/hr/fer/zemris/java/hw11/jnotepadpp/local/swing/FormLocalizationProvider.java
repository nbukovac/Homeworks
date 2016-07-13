package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProviderBridge;

/**
 * Class that extends {@link LocalizationProviderBridge} and serves as a
 * specific decorator for binding {@link JFrame}s and
 * {@link ILocalizationProvider}s.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructs a new {@link FormLocalizationProvider} with the specified
	 * arguments.
	 * 
	 * @param parent
	 *            parent {@link ILocalizationProvider}
	 * @param frame
	 *            frame to set a {@link ILocalizationProvider} to
	 */
	public FormLocalizationProvider(final ILocalizationProvider parent, final JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(final WindowEvent event) {
				disconnect();
			}

			@Override
			public void windowOpened(final WindowEvent event) {
				connect();
			}

		});
	}

}
