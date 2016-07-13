package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JFileChooser;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that extends {@link JFileChooser} and provides localization along all
 * defined possibilities in {@link JFileChooser}
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class LJFileChooser extends JFileChooser {

	/** Serial versio uid */
	private static final long serialVersionUID = -7120792068067972094L;

	/** Key for dialog title */
	private final String key;

	/** {@link ILocalizationProvider} used to get dialog title */
	private final ILocalizationProvider provider;

	/**
	 * Constructs a new {@link LJFileChooser} with the specified arguments.
	 * 
	 * @param key
	 *            key for dialog title
	 * @param provider
	 *            {@link ILocalizationProvider} with localization info
	 */
	public LJFileChooser(final String key, final ILocalizationProvider provider) {
		super();
		this.key = key;
		this.provider = provider;

		changeText();

		provider.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				changeText();
			}
		});
	}

	/**
	 * Changes the dialog title.
	 */
	private void changeText() {
		setDialogTitle(provider.getString(key));
	}

}
