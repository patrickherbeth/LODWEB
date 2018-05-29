package node;


public class NodePrediction implements Comparable<NodePrediction> {
	
	public NodePrediction(String seed, Node node, String evaluationLabel, String predictedLabel, String similarityMethod, Double predictionScore, int userId, int graphStructure, String originalCandidate,String why) {
		super();
		this.seed = seed;
		this.node = node;
		this.evaluationLabel = evaluationLabel;
		this.predictedLabel = predictedLabel;
		this.predictionScore = predictionScore;
		this.similarityMethod = similarityMethod;
		this.graphStructure = graphStructure;
		this.userId = userId;
		this.originalCandidate = originalCandidate;
		this.why = why;
	}	
	

	public NodePrediction(Node node, String evaluationLabel, String predictedLabel, Double predictionScore) {
		super();
		this.node = node;
		this.evaluationLabel = evaluationLabel;
		this.predictedLabel = predictedLabel;
		this.predictionScore = predictionScore;
	}
	
	public NodePrediction(String evaluationLabel, String predictedLabel, Double predictionScore) {
		super();
		this.evaluationLabel = evaluationLabel;
		this.predictedLabel = predictedLabel;
		this.predictionScore = predictionScore;
	}	

	public String getEvaluationLabel() {
		return evaluationLabel;
	}

	public void setEvaluationLabel(String evaluationLabel) {
		this.evaluationLabel = evaluationLabel;
	}

	public String getPredictedLabel() {
		return predictedLabel;
	}

	public void setPredictedLabel(String predictedLabel) {
		this.predictedLabel = predictedLabel;
	}

	Node node;
	
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	String seed;
	
	String why;
	
	public String getWhy() {
		return why;
	}


	public void setWhy(String why) {
		this.why = why;
	}

	String originalCandidate;
	
	public String getOriginalCandidate() {
		return originalCandidate;
	}


	public void setOriginalCandidate(String originalCandidate) {
		this.originalCandidate = originalCandidate;
	}

	String evaluationLabel;
	
	String predictedLabel;
	
	
	//This keeps the usual graph structure when a test node is classified. 0 means the test node has no neighbourhood, 1 means the test node has only his neighbourhood and 2 means that his neighbourhood has a neighborhood
	int graphStructure;
	
	public int getGraphStructure() {
		return graphStructure;
	}


	public void setGraphStructure(int graphStructure) {
		this.graphStructure = graphStructure;
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

	String similarityMethod;
	
	int userId;
	
	Double predictionScore;

	public Double getPredictionScore() {
		return predictionScore;
	}

	public void setPredictionScore(Double predictionScore) {
		this.predictionScore = predictionScore;
	}

	@Override
	public int compareTo(NodePrediction o) {
	   if (this.predictionScore > o.predictionScore) {
		  return -1;
	   } else if (this.predictionScore < o.predictionScore) {
		  return 1;
	   }else{
		  return 0;
	   }
	}
	
	
}
