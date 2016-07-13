package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that extends {@link AbstractLocalizationProvider} and it is a
 * singleton. Provides localization services.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** {@link LocalizationProvider} instance */
	private static final LocalizationProvider INSTANCE = new LocalizationProvider();

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return singleton instance
	 */
	public static LocalizationProvider getInstance() {
		return INSTANCE;
	}

	/** Current language */
	private String language;

	/** {@link ResourceBundle} with all localization information */
	private ResourceBundle bundle;

	/**
	 * Constructs a new {@link LocalizationProvider}.
	 */
	private LocalizationProvider() {
		language = "en";
		setResourceBundle();
	}

	@Override
	public String getString(final String key) {
		final String value = bundle.getString(key);
		return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}

	/**
	 * Sets the {@code language} to the specified value.
	 * 
	 * @param language
	 *            new language
	 */
	public void setLanguage(final String language) {
		this.language = language;
		setResourceBundle();
		fire();
	}

	/**
	 * Sets the resource to the new language.
	 */
	private void setResourceBundle() {
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translation",
				Locale.forLanguageTag(language));
	}

}
