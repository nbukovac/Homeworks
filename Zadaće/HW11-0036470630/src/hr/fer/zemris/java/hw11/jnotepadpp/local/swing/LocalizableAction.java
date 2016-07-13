package hr.fer.zemris.java.hw11.jnotepadpp.local.swing;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that extends {@link AbstractAction} and provides localization along all
 * defined possibilities in {@link AbstractAction}.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public abstract class LocalizableAction extends AbstractAction {

	/** Serial version uid */
	private static final long serialVersionUID = 4826993606835530639L;

	/** Key for action name */
	private final String key;

	/** Key for action description */
	private final String description;

	/** {@link ILocalizationProvider} used to get action name and description */
	private final ILocalizationProvider provider;

	/**
	 * Constructs a new {@link LocalizableAction} with the specified arguments.
	 * 
	 * @param key
	 *            action name key
	 * @param description
	 *            action description key
	 * @param provider
	 *            {@link ILocalizationProvider} with localization info
	 */
	public LocalizableAction(final String key, final String description, final ILocalizationProvider provider) {
		super();
		this.key = key;
		this.description = description;
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
	 * Changes the {@code NAME} and {@code SHORT_DESCRIPTION} values to the new
	 * localization.
	 */
	private void changeText() {
		putValue(NAME, provider.getString(key));
		putValue(SHORT_DESCRIPTION, provider.getString(description));
	}

}
