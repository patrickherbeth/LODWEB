package node;


public class Evaluation {
	

	public Evaluation(String uri,int correct, int incorrect,String similarityMethod,int userId, double score, double rr, String originalCandidate, int position) {
		super();
		this.similarityMethod = similarityMethod;
		this.userId = userId;
		this.correct = correct;
		this.incorrect = incorrect;
		this.uri = uri;
		this.score = score;
		this.rr = rr;
		this.originalCandidate = originalCandidate;
		this.position = position;
	}

	String similarityMethod;
	
	int userId;
	
	int position;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	int correct;
	
	int incorrect;

	String uri;
	
	String originalCandidate;
	
	double score;

	double rr;
	
	public String getOriginalCandidate() {
		return originalCandidate;
	}

	public void setOriginalCandidate(String originalCandidate) {
		this.originalCandidate = originalCandidate;
	}

	public double getRr() {
		return rr;
	}

	public void setRr(double rr) {
		this.rr = rr;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getSimilarityMethod() {
		return similarityMethod;
	}

	public void setSimilarityMethod(String similarityMethod) {
		this.similarityMethod = similarityMethod;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(int incorrect) {
		this.incorrect = incorrect;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
