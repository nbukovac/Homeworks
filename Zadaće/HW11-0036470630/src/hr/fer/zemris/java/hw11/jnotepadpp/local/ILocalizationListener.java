package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that determines what a localization listener has to implement.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public interface ILocalizationListener {

	/**
	 * Replaces the current localized value with the new value.
	 */
	void localizationChanged();

}
