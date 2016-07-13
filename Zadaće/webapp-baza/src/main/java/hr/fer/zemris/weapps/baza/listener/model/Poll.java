package hr.fer.zemris.weapps.baza.listener.model;

public class Poll {

	private final long id;
	private String title;
	private String mesagge;

	public Poll(final long id, final String title, final String mesagge) {
		super();
		this.id = id;
		this.title = title;
		this.mesagge = mesagge;
	}

	public long getId() {
		return id;
	}

	public String getMesagge() {
		return mesagge;
	}

	public String getTitle() {
		return title;
	}

	public void setMesagge(final String mesagge) {
		this.mesagge = mesagge;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
