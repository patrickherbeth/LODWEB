package movietagging;

public class Rating {
	
	private int idUser;
	private int idMovie;
	private double rating;
	
	public Rating(int idUser, int idMovie, double rating, String relevant) {
		this.idUser = idUser;
		this.idMovie = idMovie;
		this.rating = rating;
	}

	public int getIdUser() {
		return idUser;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public int getIdMovie() {
		return idMovie;
	}
	
	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}
	
	public double getRating() {
		return rating;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	

}
