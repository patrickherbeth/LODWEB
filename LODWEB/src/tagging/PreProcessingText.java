package tagging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.text.StringTokenizer;

import database.CategoriesQuery;
import database.TagsQuery;
import database.repository.DocumentsQuery;
import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Jaccard;
import model.Document;
import model.Tag;
import movietagging.Category;
import movietagging.UserMovie;
import net.didion.jwnl.JWNLException;
import stopwords.StopWord;
import wordnet.Sinonyms;

public class PreProcessingText {

	private int idCurrentUser;
	private TagsQuery tagsQuery;
	private DocumentsQuery documentsQuery;
	private CategoriesQuery categoriesQuery;
	private UserMovie userMovie;

	// Movies
	private List<Document> moviesViewed;
	public List<Document> moviesUnViewed;

	// UserModel and TestSet
	private Set<Tag> userModel;
	private final int LIMIT_OF_TAGS = 10;

	public PreProcessingText(int idCurrentUser) {
		this.idCurrentUser = idCurrentUser;
		this.tagsQuery = new TagsQuery();
		this.documentsQuery = new DocumentsQuery();
		this.categoriesQuery = new CategoriesQuery();
		this.moviesViewed = new ArrayList<>();
		this.moviesUnViewed = new ArrayList<>();
		this.userModel = new HashSet<>();
	}

	public PreProcessingText start() {
		selectCandidateMovies();
		createUserModel(LIMIT_OF_TAGS);
		
		try {
			comparisonSinomym();
		} catch (JWNLException e) {
			e.printStackTrace();
		}

		createTestSet();
		treatmentPolysemy();
		calculationJaccard();

		return this;
	}

	// Fist Step
	private void selectCandidateMovies() {
		this.moviesViewed = this.documentsQuery.getDocummentsViewedByUser(this.idCurrentUser, 4);
		this.tagsQuery.getTagsByMovies(this.moviesViewed);
		
		Set<Tag> tagsMoviesViewed = new HashSet<>();
				
		for(Document document: this.moviesViewed) {
			tagsMoviesViewed.addAll(document.getTags());
		}

		this.moviesUnViewed = this.documentsQuery.getDocummentsUnViewedByUser(tagsMoviesViewed.stream().mapToInt(a -> a.getId()).toArray());
		this.tagsQuery.getTagsByMovies(this.moviesUnViewed);

		this.moviesViewed = new ArrayList<>(this.moviesViewed.stream().filter(a -> a.getTags().size() >= 10)
				.collect(Collectors.toList()));
		/*this.moviesUnViewed = new ArrayList<>(this.moviesUnViewed.stream().filter(a -> a.getTags().size() >= 10)
				.collect(Collectors.toList()));*/

		this.userMovie = new UserMovie().setId(this.idCurrentUser).setMoviesViewed(this.moviesViewed)
				.setMoviesUnViewed(this.moviesUnViewed);
	}

	// Second Step
	private void createUserModel(int limitOfTags) {
		for (Document movie : this.moviesViewed) {
			userModel.addAll(movie.getTags());
		}

		userModel = getTagsFromUserModelRandom(userModel, limitOfTags);
	}

	// Third Step
	private void comparisonSinomym() throws JWNLException {
		for (Tag tagUserModel : this.userModel) {
			for (Document movie : moviesUnViewed) {
				for (String synonym : Sinonyms.getSinonymous(tagUserModel.getName())) {
					Tag tagSynonym = movie.getTags().stream().filter(a -> a.getName().equals(synonym)).findFirst()
							.orElse(null);

					if (tagSynonym != null) {
						tagSynonym.setName(tagUserModel.getName());
					}
				}
			}
		}
	}

	// Fourth Step
	private void createTestSet() {
		Cosine cosine = new Cosine();

		for (Document movie : moviesUnViewed) {
			for (Tag tag : movie.getTags()) {
				double cosineSimilarity = this.userModel.stream()
						.mapToDouble(a -> cosine.similarity(a.getName(), tag.getName())).sum();

				tag.setScore(cosineSimilarity);
			}
		}
	}

	private Set<Tag> getTagsFromUserModelRandom(Set<Tag> tags, int sizeElementsReturned) {
		Set<Tag> tagsRandom = new HashSet<>();

		Random random = new Random();
		int i = 0;

		while (tagsRandom.size() < sizeElementsReturned) {
			tagsRandom.add(tags.stream().skip(random.nextInt(tags.size() - 1)).findFirst().get());

			i = i > tags.size() ? 0 : i;
			i++;
		}

		return tagsRandom;
	}

	// Fifth Step
	private void treatmentPolysemy() {
		Set<Tag> tagsUserModel = this.categoriesQuery.getCategoriesFromIdsTags(this.userModel);

		Set<Category> categoriesByUserModel = new HashSet<>();

		for (Tag tag : tagsUserModel) {
			categoriesByUserModel.addAll(tag.getCategories());
		}

		for (Document movie : this.moviesUnViewed) {
			Set<Tag> tagsByUserModel = movie.getTagsWithLimit(LIMIT_OF_TAGS);

			Set<Tag> tagsTestSet = this.categoriesQuery.getCategoriesFromIdsTags(tagsByUserModel);

			Set<Category> categoriesByTestSet = new HashSet<>();

			for (Tag tag : tagsTestSet) {
				categoriesByTestSet.addAll(tag.getCategories());
			}

			Set<Category> intersectionCategoriesByTestSet = new HashSet<>(categoriesByUserModel);
			intersectionCategoriesByTestSet.retainAll(categoriesByTestSet);

			double totalEqualCategories = intersectionCategoriesByTestSet.size();

			movie.addTotalCategoriesEqualsUserModel(
					totalEqualCategories > 0.0 ? totalEqualCategories / categoriesByUserModel.size() : 0.0);
		}
	}

	// Sixth Step
	private void calculationJaccard() {
		for (Document movie : this.moviesUnViewed) {
			Set<Tag> tagsByUserModel = movie.getTagsWithLimit(LIMIT_OF_TAGS);

			movie.setSimilarityJaccard(calculationJaccard(this.userModel, tagsByUserModel));
		}
	}

	public double calculationJaccard(Set<Tag> novosNomes, Set<Tag> novosNomes2) {
		Jaccard jac = new Jaccard();

		Set<Tag> unionTags = new HashSet<>();
		unionTags.addAll(novosNomes);
		unionTags.addAll(novosNomes2);

		Set<Tag> intersectionTags = new HashSet<>();
		intersectionTags.addAll(novosNomes.stream()
				.filter(a -> novosNomes2.stream().anyMatch(b -> jac.similarity(a.getName(), b.getName()) >= 0.3))
				.collect(Collectors.toSet()));

		return (double) intersectionTags.size() / (double) unionTags.size();
	}

	public UserMovie getUserMovie() {
		return userMovie;
	}

	private void removeStopWords() {
		StopWord stopWord = new StopWord();

		for (Tag tag : this.userModel) {
			StringTokenizer stringToken = new StringTokenizer(tag.getName());

			List<String> textTokenizer = stringToken.getTokenList();

			tag.setName(stopWord.removeFromText(textTokenizer).toList());
		}

		for (Document movie : moviesUnViewed) {
			for (Tag tag : movie.getTags()) {
				StringTokenizer stringToken = new StringTokenizer(tag.getName());

				List<String> textTokenizer = stringToken.getTokenList();

				tag.setName(stopWord.removeFromText(textTokenizer).toList());
			}
		}
	}
}

