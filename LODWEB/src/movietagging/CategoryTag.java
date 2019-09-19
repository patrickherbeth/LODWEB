package movietagging;

public class CategoryTag {
	private int idTag;
	private int idCategory;

	public CategoryTag(int idTag, int idCategory) {
		super();
		this.idTag = idTag;
		this.idCategory = idCategory;
	}

	public int getIdTag() {
		return idTag;
	}

	public void setIdTag(int idTag) {
		this.idTag = idTag;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
}
