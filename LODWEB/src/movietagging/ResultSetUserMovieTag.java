package movietagging;

public class ResultSetUserMovieTag {
	private int idUser;
	private int idDocument;
	private int idTag;
	private String tagName;

	public ResultSetUserMovieTag(int idUser, int idDocument, int idTag, String tagName) {
		this.idUser = idUser;
		this.idDocument = idDocument;
		this.idTag = idTag;
		this.tagName = tagName;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
	}

	public int getIdTag() {
		return idTag;
	}

	public void setIdTag(int idTag) {
		this.idTag = idTag;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
