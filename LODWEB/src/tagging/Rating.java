package tagging;

public class Rating {
	private int iduser;
	private int iddocument;
	private int rating;
	private String relevants;
	
	public Rating( int iduser, int iddocument, int rating, String relevants) {
		this.iduser = iduser;
		this.iddocument = iddocument;
		this.rating = rating;
		this.relevants = relevants;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getRelevants() {
		return relevants;
	}

	public void setRelevants(String relevants) {
		this.relevants = relevants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + iddocument;
		result = prime * result + iduser;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rating other = (Rating) obj;
		if (iddocument != other.iddocument)
			return false;
		if (iduser != other.iduser)
			return false;
		return true;
	}
	
}
