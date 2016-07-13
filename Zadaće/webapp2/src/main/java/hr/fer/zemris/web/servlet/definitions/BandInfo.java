package hr.fer.zemris.web.servlet.definitions;

/**
 * Class that contains a representation of the file
 * {@code glasanje-definicija.txt} used for specifying band information.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class BandInfo {

	/** Unique identifier */
	private final int id;

	/** Name of the band */
	private final String bandName;

	/** URL to a representative song */
	private final String url;

	/**
	 * Constructs a new {@link BandInfo} with the sepcified parameters.
	 * 
	 * @param id
	 *            unique identifier
	 * @param bandName
	 *            name of the band
	 * @param url
	 *            URL to a representative song
	 */
	public BandInfo(final int id, final String bandName, final String url) {
		super();
		this.id = id;
		this.bandName = bandName;
		this.url = url;
	}

	/**
	 * Returns the bands name.
	 * 
	 * @return band name
	 */
	public String getBandName() {
		return bandName;
	}

	/**
	 * Returns the bands unique identifier.
	 * 
	 * @return unique identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the URL to one of the bands songs.
	 * 
	 * @return song URL
	 */
	public String getUrl() {
		return url;
	}

}
