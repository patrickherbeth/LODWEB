package movietagging;

import java.util.List;

import model.Document;

public class UserMovie {

	private int id;
	private List<Document> userMoviesUnViewed;
	private List<Document> userMoviesViewed;

	public int getId() {
		return id;
	}

	public UserMovie setId(int id) {
		this.id = id;
		
		return this;
	}

	public List<Document> getRecommendedMovies() {
		return userMoviesUnViewed;
	}

	public UserMovie setMoviesUnViewed(List<Document> userMoviesUnViewed) {
		this.userMoviesUnViewed = userMoviesUnViewed;
		
		return this;
	}
	
	public List<Document> getMoviesViewed() {
		return userMoviesViewed;
	}

	public UserMovie setMoviesViewed(List<Document> userMoviesViewed) {
		this.userMoviesViewed = userMoviesViewed;
		
		return this;
	}
}
