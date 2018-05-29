package model;

public class TagSim {

	int id;
	int idTag1;
	int idTag2;
	int sim;
	String tipo;

	public TagSim(int id, int idTag1, int idTag2, int sim, String tipo) {
		super();
		this.id = id;
		this.idTag1 = idTag1;
		this.idTag2 = idTag2;
		this.sim = sim;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}

	public int getIdTag1() {
		return idTag1;
	}

	public void setIdTag1(int idTag1) {
		this.idTag1 = idTag1;
	}

	public int getIdTag2() {
		return idTag2;
	}

	public void setIdTag2(int idTag2) {
		this.idTag2 = idTag2;
	}

	public int getSim() {
		return sim;
	}

	public void setSim(int sim) {
		this.sim = sim;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
