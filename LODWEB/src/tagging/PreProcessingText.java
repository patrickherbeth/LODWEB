package tagging;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.jena.base.Sys;

import database.CategoriesQuery;
import database.DBFunctions;
import database.TagsQuery;
import database.repository.DocumentsQuery;
import info.debatty.java.stringsimilarity.Cosine;
import info.debatty.java.stringsimilarity.Jaccard;
import model.Document;
import model.SemanticRanking;
import model.Tag;
import movietagging.Category;
import movietagging.UserMovie;
import net.didion.jwnl.JWNLException;
import wordnet.Sinonyms;
import wordnet.WordNetFactory;

public class PreProcessingText {

	private int idCurrentUser;
	private TagsQuery tagsQuery;
	private DocumentsQuery documentsQuery;
	private CategoriesQuery categoriesQuery;
	private UserMovie userMovie;
	private double[] calculeLDSD;
	private double[] calculeWUP;

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

	public PreProcessingText startF2() {
		selectCandidateMovies();
		createUserModel(LIMIT_OF_TAGS);
		createTestSet();
		treatmentPolysemy();
		calculationJaccard();
		//getFormula2();

		return this;
	}

	public PreProcessingText startF1() {
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
		//getFormula1();

		return this;
	}

	// Fist Step
	private void selectCandidateMovies() {

		System.out.println("1 - Selecionar Filmes Candidatos");

		this.moviesViewed = this.documentsQuery.getDocummentsViewedByUser(this.idCurrentUser, 4);

		System.out.println("selectCandidateMovies() value relevant ->  " + this.moviesViewed.size());

		List<Document> arraylist = new ArrayList<Document>();
		arraylist = this.moviesViewed;

		this.tagsQuery.getTagsByMovies(this.moviesViewed);

		Set<Tag> tagsMoviesViewed = new HashSet<>();

		for (Document document : this.moviesViewed) {
			tagsMoviesViewed.addAll(document.getTags());
		}

		this.moviesUnViewed = this.documentsQuery
				.getDocummentsUnViewedByUser(tagsMoviesViewed.stream().mapToInt(a -> a.getId()).toArray());
		this.tagsQuery.getTagsByMovies(this.moviesUnViewed);

		this.moviesViewed = new ArrayList<>(this.moviesViewed.stream().filter(a -> a.getTags().size() >= 10).collect(Collectors.toList()));
		this.moviesUnViewed = new ArrayList<>(this.moviesUnViewed.stream().filter(a -> a.getTags().size() >= 10).collect(Collectors.toList())); 

		this.userMovie = new UserMovie().setId(this.idCurrentUser).setMoviesViewed(this.moviesViewed).setMoviesUnViewed(this.moviesUnViewed);

	}

	// Second Step
	private void createUserModel(int limitOfTags) {

		System.out.println("2 - Criar Modelo de Usuário.");

		for (Document movie : this.moviesViewed) {
			userModel.addAll(movie.getTags());
		}

		userModel = getTagsFromUserModelRandom(userModel, limitOfTags);
	}

	// Third Step
	private void comparisonSinomym() throws JWNLException {

		System.out.println("3 - Tratamento de Sinominôs");

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

		System.out.println("4 - Cria TestSet");

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
	public void treatmentPolysemy() {

		System.out.println("5 - Tratamento de Polissemia");

		Set<Tag> tagsUserModel = this.categoriesQuery.getCategoriesFromIdsTags(this.userModel);

		Set<Category> categoriesByUserModel = new HashSet<>();

		if (tagsUserModel != null) {
			for (Tag tag : tagsUserModel) {
				categoriesByUserModel.addAll(tag.getCategories());
			}
		}

		for (Document movie : this.moviesUnViewed) {
			Set<Tag> tagsByUserModel = movie.getTagsWithLimit(LIMIT_OF_TAGS);

			Set<Tag> tagsTestSet = this.categoriesQuery.getCategoriesFromIdsTags(tagsByUserModel);

			if (tagsTestSet != null) {

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
	}

	// Sixth Step
	public void calculationJaccard() {

		System.out.println("6 - Calcula Jaccard com o tratamento de Polissemia e Sinônimos");

		for (Document movie : this.moviesUnViewed) {
			Set<Tag> tagsByUserModel = movie.getTagsWithLimit(LIMIT_OF_TAGS);

			System.out.println("ID -> " + movie.getId());
			System.out.println("Titulo -> " + movie.getName());
			System.out.println("Calculo Jaccard -> " + calculationJaccard(this.userModel, tagsByUserModel));
			
			double jaccard = calculationJaccard(this.userModel, tagsByUserModel);
			
			System.out.println("Valor Formula 1 -> " + (movie.getSimilarityJaccard() + movie.getTotalCategoriesEqualsUserModel()) / 2);
			
			System.out.println("----------------------------------------------");
			//System.out.println("Valor Rating -> " + movie.getRating());

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

	/*
	 * public void GetRating () {
	 * 
	 * 
	 * Object[] arraylist = getUserMovie().getMoviesUnViewed().toArray(); for (int i
	 * = 0; i < arraylist.length; i++) {
	 * 
	 * System.out.println("valor rating -> " + arraylist[i].toString(); } }
	 */
	
	public void getFormula1() {

		for (Document movie : this.moviesUnViewed) {
			Set<Tag> tagsByUserModel = movie.getTagsWithLimit(LIMIT_OF_TAGS);

			double jaccard = calculationJaccard(this.userModel, tagsByUserModel);

			
		if(((movie.getSimilarityJaccard() + movie.getTotalCategoriesEqualsUserModel()) / 2) > 0) {
			System.out.println("--------------------FÓRMULA 1-------------------");
			System.out.println("Nome ID -> " + movie.getId());
			System.out.println("Nome Rating -> " + movie.getName());
			System.out.println("Similaridade Jaccard -> " + movie.getSimilarityJaccard());
			System.out.println("Valor tratamento Polissemia -> " + movie.getTotalCategoriesEqualsUserModel());
			System.out.println("Valor Formula 1 -> " + (movie.getSimilarityJaccard() + movie.getTotalCategoriesEqualsUserModel()) / 2);

			this.documentsQuery.saveRecommenderForluma1(this.idCurrentUser, movie.getId(),  movie.getRating(), movie.getSimilarityJaccard(), movie.getTotalCategoriesEqualsUserModel());
			
		}
			
			

		}
	}
	
	public void getFormula2() {

		for (Document movie : this.moviesUnViewed) {
			Set<Tag> tagsByUserModel = movie.getTagsWithLimit(LIMIT_OF_TAGS);

			double jaccard = calculationJaccard(this.userModel, tagsByUserModel);
			
			if(((movie.getSimilarityJaccard() + movie.getTotalCategoriesEqualsUserModel()) / 2) > 0) {

				System.out.println("--------------------FÓRMULA 2-------------------");
				System.out.println("Nome ID do Filme -> " + movie.getId());
				System.out.println("Nome Rating -> " + movie.getName());
				System.out.println("Similaridade Jaccard -> " + movie.getSimilarityJaccard());
				System.out.println("Valor tratamento Polissemia -> " + movie.getTotalCategoriesEqualsUserModel());
				System.out.println("Valor Formula 2 -> " + (movie.getSimilarityJaccard() + movie.getTotalCategoriesEqualsUserModel()) / 2);
				
				this.documentsQuery.saveRecommenderForluma2(this.idCurrentUser, movie.getId(),  movie.getRating(), movie.getSimilarityJaccard(), movie.getTotalCategoriesEqualsUserModel());
			
			}
		}
	}
	
	public void getLDSD() {
		DBFunctions dbfunctions = new DBFunctions();
		List<SemanticRanking> listSemanticRakingLDSD = new ArrayList<SemanticRanking>();
		
		

		for (Document movie : this.moviesUnViewed) {
			Set<Tag> tagsByUserModel = movie.getTagsWithLimit(LIMIT_OF_TAGS);

			calculeLDSD = TaggingFactory.calculeLDSD(tagsByUserModel, this.userModel, idCurrentUser);

			if (calculeLDSD[0] > 0) {

				System.out.println("--------------------LDSD-------------------");
				System.out.println("Nome ID do Filme-> " + movie.getId());
				System.out.println("Nome Filme -> " + movie.getName());
				System.out.println("Nome Rating -> " + movie.getRating());
				System.out.println("Valor LDSD -> " + calculeLDSD[1]);

				SemanticRanking semanticRakingLDSD = new SemanticRanking(1, movie.getId(), "LDSD", calculeLDSD[1], calculeLDSD[0], this.idCurrentUser);
				listSemanticRakingLDSD.add(semanticRakingLDSD);
				
				this.documentsQuery.saveRecommenderLDSD(this.idCurrentUser, movie.getId(), movie.getRating(), calculeLDSD[1]);
			}
		}
		
		for (SemanticRanking semantic : listSemanticRakingLDSD) {

			if (semantic.getScore() != 0.0 || semantic.getScore() > 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semantic.getUri2(), semantic.getType(), semantic.getScore(), semantic.getSumsemantic(), this.idCurrentUser);
			}
		}
		
	}
	
	public void getWUP() {
		DBFunctions dbfunctions = new DBFunctions();
		List<SemanticRanking> listSemanticRakingWup = new ArrayList<SemanticRanking>();
		
		for (Document movie : this.moviesUnViewed) {
			Set<Tag> tagsTestSet = movie.getTagsWithLimit(LIMIT_OF_TAGS);

			List<Tag> listTagsTestSet = tagsTestSet.stream().collect(Collectors.toList());
			List<Tag> listUserModel = tagsTestSet.stream().collect(Collectors.toList());
			
			
			calculeWUP = WordNetFactory.calculeWUP(listTagsTestSet, listUserModel);

			if (calculeWUP[1] > 0.0) {

				System.out.println("--------------------WUP-------------------");
				System.out.println("Nome ID do Filme-> " + movie.getId());
				System.out.println("Nome Filme -> " + movie.getName());
				System.out.println("Nome Rating -> " + movie.getRating());
				System.out.println("Valor WUP -> " + calculeWUP[1]);
				
				SemanticRanking semanticRakingWup = new SemanticRanking(1, movie.getId(), "WUP", calculeWUP[1], calculeWUP[0], this.idCurrentUser);
				listSemanticRakingWup.add(semanticRakingWup);
				
				this.documentsQuery.saveRecommenderWUP(this.idCurrentUser, movie.getId(), movie.getRating(), calculeWUP[1]);
			}
		}
		
		for (SemanticRanking semantic : listSemanticRakingWup) {

			if (semantic.getScore() != 0.0 || semantic.getScore() < 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semantic.getUri2(), semantic.getType(), semantic.getScore(), semantic.getSumsemantic(), this.idCurrentUser);
			}
		}
	}
}
