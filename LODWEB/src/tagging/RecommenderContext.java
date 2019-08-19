package tagging;

import java.util.List;
import java.util.stream.Collectors;

import model.Document;

public class RecommenderContext {

	private List<Document> movies;
	private PreProcessingText pp;

	public RecommenderContext(int idUser) {
		pp = new PreProcessingText(idUser).start();
		this.movies = pp.moviesUnViewed;
	}

	public List<Document> getCandidateDocimentsByF1() {
		return this.movies;
	}

	public double getFormula1(double jaccard, double polysemy, int categoriesUserModel) {
		return (jaccard + (polysemy / categoriesUserModel)) / 2;
	}

}
