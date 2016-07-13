package hr.fer.zemris.web.servlet.definitions;

/**
 * Class that contains combined informations about bands and the collected
 * votes.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class ResultsInfo {

	/** Bands name */
	private final String bandName;

	/** Number of collected votes */
	private final int numberOfVotes;

	/** URL to a representative song */
	private final String url;

	/**
	 * Constructs a new {@link ResultsInfo} with the specified parameters.
	 * 
	 * @param bandName
	 *            bands name
	 * @param numberOfVotes
	 *            number of collected votes
	 * @param url
	 *            URL to a song
	 */
	public ResultsInfo(final String bandName, final int numberOfVotes, final String url) {
		super();
		this.bandName = bandName;
		this.numberOfVotes = numberOfVotes;
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
	 * Returns the number of collected votes.
	 * 
	 * @return number of votes
	 */
	public int getNumberOfVotes() {
		return numberOfVotes;
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
