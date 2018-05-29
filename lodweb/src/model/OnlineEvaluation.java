package model;

public class OnlineEvaluation {

	public OnlineEvaluation(int userid, String uri, String seed, String similarityMethod, String strategy, int star,
			String accuracy, String understand, String statisfaction, String novelty, String addprofile) {
		super();
		this.userid = userid;
		this.uri = uri;
		this.seed = seed;
		this.similarityMethod = similarityMethod;
		this.strategy = strategy;
		this.star = star;
		this.accuracy = accuracy;
		this.understand = understand;
		this.satisfaction = statisfaction;
		this.novelty = novelty;
		this.addprofile = addprofile;
	}

	private int userid;
	private String uri;
	private String seed;
	private String similarityMethod;
	private String strategy;
	private int star;
	private String accuracy;
	private String understand;
	private String satisfaction;
	private String novelty;
	private String addprofile;

	public String getAddprofile() {
		return addprofile;
	}

	public void setAddprofile(String addprofile) {
		this.addprofile = addprofile;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getUnderstand() {
		return understand;
	}

	public void setUnderstand(String understand) {
		this.understand = understand;
	}

	public String getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}

	public String getNovelty() {
		return novelty;
	}

	public void setNovelty(String novelty) {
		this.novelty = novelty;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public String getSimilarityMethod() {
		return similarityMethod;
	}

	public void setSimilarityMethod(String similarityMethod) {
		this.similarityMethod = similarityMethod;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

}
