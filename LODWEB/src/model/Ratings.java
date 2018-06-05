package model;

public class Ratings {

	private int id;
	private int iduser;
	private int iddocument;
	private double rating;

	public Ratings(int id, int iduser, int iddocument, double rating) {
		super();
		this.id = id;
		this.iduser = iduser;
		this.iddocument = iddocument;
		this.rating = rating;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public int getIddocument() {
		return iddocument;
	}

	public void setIddocument(int iddocument) {
		this.iddocument = iddocument;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
}
