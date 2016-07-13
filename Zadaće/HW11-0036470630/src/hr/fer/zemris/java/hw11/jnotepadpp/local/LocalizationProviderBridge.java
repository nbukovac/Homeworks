package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Class that extend {@link AbstractLocalizationProvider} and is a decorator
 * used to connect and disconnect {@link ILocalizationListener}s to
 * {@link ILocalizationProvider}s.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** Specifies if a {@link ILocalizationListener} is connected */
	private boolean connected;

	/** Parent {@link ILocalizationProvider} */
	private final ILocalizationProvider parent;

	/** Connected {@link ILocalizationListener} */
	private final ILocalizationListener listener;

	/**
	 * Constructs a new {@link LocalizationProviderBridge} with the specified
	 * argument.
	 * 
	 * @param parent
	 *            {@link ILocalizationProvider} parent
	 */
	public LocalizationProviderBridge(final ILocalizationProvider parent) {
		this.parent = parent;
		listener = () -> fire();
	}

	/**
	 * Connects the {@code listener} to the {@code parent} if it isn't already.
	 */
	public void connect() {
		if (!connected) {
			parent.addLocalizationListener(listener);
			connected = true;
		}
	}

	/**
	 * Disconnects the {@code listener} to the {@code parent} if it isn't
	 * already.
	 */
	public void disconnect() {
		if (connected) {
			parent.removeLocalizationListener(listener);
			connected = false;
		}
	}

	@Override
	public String getString(final String key) {
		return parent.getString(key);
	}

}
