package movietagging;

import java.util.ArrayList;

import model.Tag;

public class MovieTag {
	private int idMovie;
	private ArrayList<Tag> tags;

	public MovieTag(int idMovie, ArrayList<Tag> tags) {
		this.idMovie = idMovie;
		this.tags = tags;
	}

	public int getMovie() {
		return idMovie;
	}

	public void setMovie(int idMovie) {
		this.idMovie = idMovie;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
}
