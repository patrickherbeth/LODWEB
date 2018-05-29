package node;

import java.util.ArrayList;
import java.util.List;

public abstract class IConstants {
	
	
	public final static String CATEGORY_BOOK = "BOOK";

	public final static String CATEGORY_MOVIE = "MOVIE";

	public final static String CATEGORY_MUSIC = "MUSIC";
	
	public final static String SORRY_NO_IMAGE = "http://www.bluetownshipfire.com/wp-content/uploads/2017/02/no-thumb.jpg";

	public final static String LIKE = "LIKE";

	public final static String DISLIKE = "DISLIKE";

	public final static String NO_LABEL = "NOLABEL";
	
	public final static String LDSD = "LDSD";
	
	public final static String LDSD_LOD = "LDSD_LOD";
	
	
	/*  Mestrado Patrick */
	
	public final static String LDSD_JACCARD = "LDSD+JACCARD";
	
	/* ------------------------------------------------------------------ */

	public final static String SEED_EVALUATION = "SEED_EVALUATION";
	
	
	

	// Constants for passants caching
	public static final String DIRECT = "DIRECT";
	
	public static final String OUTGOING = "OUTGOING";
	
	public static final String INCOMING = "INCOMING";
	
	public static final int ANY_ID = 0;
	
	
	// Triple Store Constants
	public static final String TSS_FUSEKI = "FUSEKI";
	
	public static final String TSS_DBPEDIA = "DBPEDIA";
	
	/*
	 * public final static String
	 * RELATIONAL_OBSERVED_ATTRIBUTE_INDIRECT_INCOMING =
	 * "RELATIONAL_OBSERVED_ATTRIBUTE_INDIRECT_INCOMING"; public final static
	 * String RELATIONAL_OBSERVED_ATTRIBUTE_INDIRECT_OUTGOING =
	 * "RELATIONAL_OBSERVED_ATTRIBUTE_INDIRECT_OUTGOING"; public final static
	 * String RELATIONAL_UNOBSERVED_ATTRIBUTE =
	 * "RELATIONAL_UNOBSERVED_ATTRIBUTE_";
	 */

	public final static String RELATIONAL_OBSERVED_ATTRIBUTE_INDIRECT_INCOMING = "RA_II";
	public final static String RELATIONAL_OBSERVED_ATTRIBUTE_INDIRECT_OUTGOING = "RA_IO";
	public final static String RELATIONAL_OBSERVED_ATTRIBUTE_DIRECT = "RA_D";
	public final static String RELATIONAL_UNOBSERVED_ATTRIBUTE = "UA_";

	public static final int K = 3;

	public static int N = 1;
	
	public static int N_PRIME = 2;

	public static boolean USE_MAX_MIN_CLASSIFIER = true;

	// Similarity calculus will remove abstract, comment and name properties
	public static boolean REMOVE_COMMON_FEATURES = false;

	public static double semanticSimilarityThreshold = 0.5;

	public static double CLASSIFICATION_THRESHOLD = 0.0;

	public static int AMOUNT_OF_ITERATIONS_TO_STABALIZE = 3;

	public static boolean useSemanticSimilarityForSelectingTrainingSet = false;

	// used to consider or not predicted label as a observed attributed
	public static boolean ADD_PREDICTED_LABEL = true;
	
	public static boolean DISCARD_PREDICTED_LABEL = false;
	
	public static boolean CHECK_LABEL = false;	

	// penalize similarity by ontology property amount
	public static boolean PENALIZE_SIMILARITY_BY_AMOUNT_OF_ONTOLOGY_DATATYPE_PROPERTY = false;

	public static int NUMBER_OF_ITERATIONS = 3;

	public final static String wikiPageExternalLink = "http://dbpedia.org/ontology/wikiPageExternalLink";

	public final static String owlsameAs = "https://www.w3.org/2002/07/owl#sameAs";

	public static final int TOTAL_CANDIDATES_TO_COMPARE_HIT_RATE = 50;
	
	public static Integer MAX_CANDIDATES = null;
	
	public static final int HIT_RATE_TOP_MAX = 50;

	public static final int NUMBER_OF_RESOURCE_TO_BREAK_SPARQL_QUERY = 5;

	public static boolean ADD_EXPLANATIONS = false;

	public static boolean FILTER_CATEGORY = false;

	public static boolean PRINT = true;

	// Only one can be selected true
	public static boolean USE_ICA = false;
	
	public static boolean USE_SEMANTIC_DISTANCE = true;

	public static boolean useNeighboursSelectedBySemanticSimilarityForTraningSet = false;

	public static boolean computeSemanticSimilarityOfRelationalAttributes = false;

	public static String SIMILARITY_METHOD_IN_USE = null;
	
	public static List<String> getLabels() {
		List<String> labels = new ArrayList<String>();
		//labels.add(IConstants.DISLIKE);
		labels.add(IConstants.LIKE);
		return labels;
	}

	public static String getValidLabel(String label) {
		if (getLabels().contains(label)) {
			return label;
		}
		return null;
	}
	
	// Similarity Strategies
	public static final String ABSTRACT_SIMILARITY = "ABSTRACT_SIMILARITY";
	
	public static final String PASSANT_SIMILARITY = "PASSANT_SIMILARITY";
	
	public static final int MAX_ONLINE_EVALUATIONS_PER_USER = 9;
	
	public static final int MAX_ONLINE_EVALUATIONS_PER_SEED = 3;
	
	public static final int MIN_PROFILE_SIZE = 3;

	public static final String MISSING = "Missing";
	
	public static final String CNN_REDUCTION_STRATEGY_NONE = "CNN_REDUCTION_STRATEGY_NONE";
	
	public static final String CNN_REDUCTION_STRATEGY_TU = "CNN_REDUCTION_STRATEGY_TU";
	
	public static final String CNN_REDUCTION_STRATEGY_RANDOM = "CNN_REDUCTION_STRATEGY_RANDOM";
	
	public static final String PICK_ANOTHER_SEED = "http://dbpedia.org/resource/PICK_ANOTHER_SEED";
	
	public static final String NO_RECOMMENDATION = "No recommendation";

	public static final String BRAZILIAN_DBPEDIA = "http://pt.dbpedia.org/sparql";
	
	public static final String ENGLISH_DBPEDIA = "http://dbpedia.org/sparql";

	public static String SYSTEM_DATA_LANGUAGE = null;
	
	public static final String STRATEGY_NONE = "none";
	public static final String STRATEGY_HYBRID = "hybrid";

}
