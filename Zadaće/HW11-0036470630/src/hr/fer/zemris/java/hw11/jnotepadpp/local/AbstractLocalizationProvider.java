package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that implements the {@link ILocalizationProvider} interface
 * and thus provides methods for adding, removing and alerting
 * {@link ILocalizationListener}s.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * {@link ILocalizationListener}s that are listening for localization
	 * changes
	 */
	protected List<ILocalizationListener> listeners;

	/**
	 * Constructs a new {@link AbstractLocalizationProvider}.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(final ILocalizationListener listener) {
		listeners.add(listener);
	}

	/**
	 * Alerts every {@link ILocalizationListener} that listens for changes that
	 * a localization change has happened.
	 */
	public void fire() {
		for (final ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}

	@Override
	public void removeLocalizationListener(final ILocalizationListener listener) {
		listeners.remove(listener);
	}

}
