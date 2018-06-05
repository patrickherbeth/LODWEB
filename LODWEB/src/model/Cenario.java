package model;

public class Cenario {

	private int id;
	private int iduser;
	private String tags_user;
	private String tags_testset;
	private int id_filme;
	private String relevance;

	public Cenario(int id, int id_user, String tags_user, String tags_testset, int id_filme, String relevance) {
		super();
		this.id = id;
		this.tags_user = tags_user;
		this.tags_testset = tags_testset;
		this.id_filme = id_filme;
		this.relevance = relevance;

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

	public String getTags_user() {
		return tags_user;
	}

	public void setTags_user(String tags_user) {
		this.tags_user = tags_user;
	}

	public String getTags_testset() {
		return tags_testset;
	}

	public void setTags_testset(String tags_testset) {
		this.tags_testset = tags_testset;
	}

	public int getId_filme() {
		return id_filme;
	}

	public void setId_filme(int id_filme) {
		this.id_filme = id_filme;
	}

	public String getRelevance() {
		return relevance;
	}

	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}

}
