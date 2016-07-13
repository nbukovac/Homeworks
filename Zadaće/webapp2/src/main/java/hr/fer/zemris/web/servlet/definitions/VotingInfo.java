package hr.fer.zemris.web.servlet.definitions;

/**
 * Class that contains a representation of the file
 * {@code glasanje-rezultati.txt} used for specifying voting information.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class VotingInfo {

	/** Unique identifier */
	private final int id;

	/** Number of collected votes */
	private int votes;

	/**
	 * Constructs a new {@link VotingInfo} with the specified parameters.
	 * 
	 * @param id
	 *            unique identifier
	 * @param votes
	 *            number of votes
	 */
	public VotingInfo(final int id, final int votes) {
		super();
		this.id = id;
		this.votes = votes;
	}

	/**
	 * Returns the unique identifier.
	 * 
	 * @return unique identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the collected number of votes.
	 * 
	 * @return number of votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Increments the collected number of votes by 1.
	 */
	public void incrementVote() {
		votes++;
	}

}
