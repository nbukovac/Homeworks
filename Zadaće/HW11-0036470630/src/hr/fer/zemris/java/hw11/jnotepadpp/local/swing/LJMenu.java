package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that extends {@link JMenu} and provides localization along all defined
 * possibilities in {@link JMenu}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class LJMenu extends JMenu {

	/** Serial version uid */
	private static final long serialVersionUID = 5308276244487255863L;

	/** Key for menu name */
	private final String key;

	/** {@link ILocalizationProvider} used to get menu name */
	private final ILocalizationProvider provider;

	/**
	 * Constructs a new {@link LJMenu} with the specified arguments.
	 * 
	 * @param key
	 *            Key for menu name
	 * @param provider
	 *            {@link ILocalizationProvider} with localization info
	 */
	public LJMenu(final String key, final ILocalizationProvider provider) {
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
	 * Changes the text displayed on the menu.
	 */
	private void changeText() {
		setText(provider.getString(key));
	}
}
