package hr.fer.zemris.weapps.baza.listener.model;

public class PollOption {

	private final long id;
	private String optionTitle;
	private String optionLink;
	private long pollID;
	private long votesCount;

	public PollOption(final long id, final String optionTitle, final String optionLink, final long pollID,
			final long votesCount) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	public long getId() {
		return id;
	}

	public String getOptionLink() {
		return optionLink;
	}

	public String getOptionTitle() {
		return optionTitle;
	}

	public long getPollID() {
		return pollID;
	}

	public long getVotesCount() {
		return votesCount;
	}

	public void setOptionLink(final String optionLink) {
		this.optionLink = optionLink;
	}

	public void setOptionTitle(final String optionTitle) {
		this.optionTitle = optionTitle;
	}

	public void setPollID(final long pollID) {
		this.pollID = pollID;
	}

	public void setVotesCount(final long votesCount) {
		this.votesCount = votesCount;
	}

}
