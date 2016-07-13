package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that determines what a localization provider has to offer.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface ILocalizationProvider {

	/**
	 * Adds a new {@link ILocalizationListener} to this
	 * {@link ILocalizationProvider}.
	 * 
	 * @param listener
	 *            new {@link ILocalizationListener}
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Returns the localized value stored for the specified {@code key}.
	 * 
	 * @param key
	 *            key to get the localized value
	 * @return localized value
	 */
	String getString(String key);

	/**
	 * Removes a {@link ILocalizationListener} from this
	 * {@link ILocalizationProvider}.
	 * 
	 * @param listener
	 *            {@link ILocalizationListener} for removal
	 */
	void removeLocalizationListener(ILocalizationListener listener);
}
