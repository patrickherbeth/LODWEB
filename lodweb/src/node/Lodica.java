package node;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Resource;

import cosinesimilarity.LuceneCosineSimilarity;
import database.DBFunctions;
import metric.MRR;
import model.HitRate;
import util.StringUtilsNode;

public class Lodica implements Serializable {
	//init = System.currentTimeMillis();	
	//NodeUtil.print("both ways: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));	
	
	/**
	 * ICA Iteration
	 */
	int icaIteration = -1;
	
	/**
	 * Map for the N+LIKE out of training set.
	 */
	static public Map<Node,Set<Node>> neighboursPlus = new HashMap<Node,Set<Node>>();
	
	/**
	 * Neighbors in N+LIKE
	 */
	public static Set<Node> trainingSet = null;	
	
	/**
	 *  Enable neighbors out of training set are considered during the classification 
	 */
	public static boolean useNPLUS = true;	
	
	/**
	 * Includes the candidates and N-LIKE
	 */
	public static List<Node> cnns = null;

	/**
	 * Set of liked resources that might be considered for training set
	 */
	public static Set<Node> userProfile = null;
	

	/**
	 * This is the original candidate that will be compared against unknown and random items 
	 */
	public static String originalCandidate = null;
	/**
	 * 
	 */
	public static Set<Node> evaluationSet = null;

	/**
	 * Set of unlabeled nodes used for test set. This is actually the candidate set.
	 */
	public static Set<Node> originalUnlabeledNodesToClassify;
	
	/**
	 * Current set of labeled nodes in the cnn set.
	 */
	public static Set<Node> currentCNNLabeledNodes = new HashSet<Node>();	
	
	/**
	 * round of the classification when navigating from web interface
	 */
	public static byte round = 0;	

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tell LODICA the system is under evaluation
	 */
	public static boolean isEvaluation = false;	
	
	/**
	 * Give access to the query data in the database
	 */
	static private DBFunctions dbFunctions = null;

	/**
	 * Current user id
	 */
	static public Integer userId = null;
	
	/**
	 * Node under evaluation
	 */
	static public Node nodeUnderEvaluation = null;

	/**
	 * The set of candidates
	 */
	static public Set<Node> candidates = null;

	/**
	 * init time
	 */
	static long init = 0;
	
	/**
	 * end time
	 */
	static long end = 0;
	
	/**
	 * Provides connection to database
	 */
	public static DBFunctions getDatabaseConnection(){
		if(dbFunctions==null){
			dbFunctions = new DBFunctions();
		}
		return dbFunctions;
	}
	
	static int idToEvaluate = 0;
	
	public static String RUNNING_CNN_REDUCTION_STRATEGY  = null;
	
	boolean saveTest = true;
	
	/**
	 * This is a fancy main to keep the program running under up to 10000 exceptions.
	 * @param args
	 * @throws Exception
	 */
	public static void main989(String[] args) throws Exception {
		Lodica ica = null;
		boolean loop = true;
		boolean userManuallyStarting = true;
		for (int retries = 0;loop; retries++) {
			try {
				init = System.currentTimeMillis();
				ica = new Lodica();
				if (userManuallyStarting) {
					getDatabaseConnection().deletePredictions();
					//getDatabaseConnection().deleteSemantics();
					ica.startTOPNExperiment();
					NodeUtil.print("TOPNExperiment - Time elapse: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
					NodeUtil.print(System.currentTimeMillis() - init);
					//DBFunctions.testHibridHitRate();
					DBFunctions.testCNNReducedHitRate();
					userManuallyStarting = false;
				}
				
				loop = false;
			} catch (org.apache.http.conn.HttpHostConnectException e) {
				loop=true;
				getDatabaseConnection().deleteEvaluationByUserId(userId);
				idToEvaluate = userId;
				//getDatabaseConnection().deletePredictionByUserId(userId);
				e.printStackTrace();
				if (retries < 10000) {
					NodeUtil.print("restarting...#"+retries);
					continue;
				} else {
					throw e;
				}
			} catch (Exception e) {
				e.printStackTrace();
				loop=true;
				getDatabaseConnection().deleteEvaluationByUserId(userId);
				getDatabaseConnection().deletePredictionByUserId(userId);
				idToEvaluate = idToEvaluate+1;
				e.printStackTrace();
				if (retries < 10000) {
					NodeUtil.print("restarting...#"+retries);
					continue;
				} else {
					throw e;
				}
			}
		}
	}
	
	
	/**
	 * This is a fancy main to keep the program running under up to 10000 exceptions.
	 * @param args
	 * @throws Exception
	 */
	public static void main98(String[] args) throws Exception {
		Lodica ica = null;
		boolean loop = true;
		boolean userManuallyStarting = true;
		for (int retries = 0;loop; retries++) {
			try {
				init = System.currentTimeMillis();
				ica = new Lodica();
				if (userManuallyStarting) {
					
					//getDatabaseConnection().deleteSemantics();
					ica.startBehaviourExperimentCNNReduction();
					//DBFunctions.testHibridHitRate();
					//DBFunctions.testCNNReducedHitRate();
					userManuallyStarting = false;
				}
				
				loop = false;
			} catch (org.apache.http.conn.HttpHostConnectException e) {
				loop=true;
				getDatabaseConnection().deleteEvaluationByUserId(userId);
				idToEvaluate = userId;
				//getDatabaseConnection().deletePredictionByUserId(userId);
				e.printStackTrace();
				if (retries < 10000) {
					NodeUtil.print("restarting...#"+retries);
					continue;
				} else {
					throw e;
				}
			} catch (Exception e) {
				e.printStackTrace();
				loop=true;
				getDatabaseConnection().deleteEvaluationByUserId(userId);
				getDatabaseConnection().deletePredictionByUserId(userId);
				idToEvaluate = idToEvaluate+1;
				e.printStackTrace();
				if (retries < 10000) {
					NodeUtil.print("restarting...#"+retries);
					continue;
				} else {
					throw e;
				}
			}
		}
	}
	
	
	
	/**
	 * This is a fancy main to keep the program running under up to 10000 exceptions.
	 * @param args
	 * @throws Exception
	 */
	public static void main999(String[] args) throws Exception {
		Lodica ica = null;
		boolean loop = true;
		boolean userManuallyStarting = true;
		for (int retries = 0;loop; retries++) {
			try {
				init = System.currentTimeMillis();
				ica = new Lodica();
				if (userManuallyStarting) {
					getDatabaseConnection().deletePredictions();
					//getDatabaseConnection().deleteSemantics();
					ica.startTOPNExperimentCNNReduction();
					//DBFunctions.testHibridHitRate();
					//DBFunctions.testCNNReducedHitRate();
					userManuallyStarting = false;
				}
				
				loop = false;
			} catch (org.apache.http.conn.HttpHostConnectException e) {
				loop=true;
				getDatabaseConnection().deleteEvaluationByUserId(userId);
				idToEvaluate = userId;
				//getDatabaseConnection().deletePredictionByUserId(userId);
				e.printStackTrace();
				if (retries < 10000) {
					NodeUtil.print("restarting...#"+retries);
					continue;
				} else {
					throw e;
				}
			} catch (Exception e) {
				e.printStackTrace();
				loop=true;
				getDatabaseConnection().deleteEvaluationByUserId(userId);
				getDatabaseConnection().deletePredictionByUserId(userId);
				idToEvaluate = idToEvaluate+1;
				e.printStackTrace();
				if (retries < 10000) {
					NodeUtil.print("restarting...#"+retries);
					continue;
				} else {
					throw e;
				}
			}
		}
	}	
	
	/**
	 * @throws Exception
	 */
	private void startTOPNExperimentCNNReduction() throws Exception {
		
		IConstants.N = 1;
		
		IConstants.N_PRIME = 2;

		useNPLUS = true;
		
		isEvaluation = true;
		
		IConstants.FILTER_CATEGORY = true;
		
		IConstants.SIMILARITY_METHOD_IN_USE = IConstants.LDSD_LOD;
		
		SparqlWalk.setService(IConstants.TSS_DBPEDIA);

		//208844 is the max of user ids
		//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(9,11);
		//NodeUtil.print("A total of "+ids.size()+" users will be evaluated");
		//for (Integer userId : ids) {
		//208844 is the max of user ids
		//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(50,70);
		//NodeUtil.print("A total of "+ids.size()+" users will be evaluated");

		//for (Integer userId : ids) {
		//int randomStart = ThreadLocalRandom.current().nextInt(483,208844);
		
		//fred
		int LIKED_ITEMS = 20;
		
		List<String> strategies = new LinkedList<String>();
		strategies.add(IConstants.CNN_REDUCTION_STRATEGY_RANDOM);
		strategies.add(IConstants.CNN_REDUCTION_STRATEGY_TU);
		strategies.add(IConstants.CNN_REDUCTION_STRATEGY_NONE);

		
		List<Integer> likedItems = new LinkedList<Integer>();
		//likedItems.add(3);
		likedItems.add(10);
		//likedItems.add(15);
		//likedItems.add(20);
		
		List<Integer> cnnCuts = new LinkedList<Integer>();
		//maxSizes.add(0);
		//maxSizes.add(20);
		//maxSizes.add(50);
		cnnCuts.add(100);
		//maxSizes.add(150);
		//maxSizes.add(200);

		
		for (Integer likedItem : likedItems) {
			
			LIKED_ITEMS = likedItem;

			for (Integer cnnCut : cnnCuts) {
				
				IConstants.MAX_CANDIDATES = cnnCut;

				Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(LIKED_ITEMS-1,LIKED_ITEMS+1);
				//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(49,51);
				List<Integer> idsFinal = new ArrayList<Integer>(ids).subList(0,100);


				Set<Node> candidate = null;
					for (Integer userId : idsFinal) {


						
						
/*						if (count>5) {
							break;
						}
						count++;*/
						
						//TimeUnit.SECONDS.sleep(1);
					    //TimeUnit.MILLISECONDS.sleep(300);
		
						Lodica.round = 0;
						
						Lodica.userId = userId;
		
						userProfile = getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId);
						
						if (userProfile==null || userProfile.isEmpty()) {
							NodeUtil.print("User ID "+  Lodica.userId + " has no profile and likes nothing ");
							continue;
						}
						
						//to cache the links from nodes in the user profile
						for (Node node : userProfile) {
							ThreadLod  t4 = new ThreadLod(node.getURI());
							t4.start();
						}
		
						
						candidate = getDatabaseConnection().getRandomUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId,1);
						Set<Node> nodesToCompare = getDatabaseConnection().getRandomUnionNonLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId,IConstants.TOTAL_CANDIDATES_TO_COMPARE_HIT_RATE);
						originalCandidate = new LinkedList<Node>(candidate).getFirst().getURI();
						evaluationSet = new HashSet<Node>();
						evaluationSet.addAll(candidate);
						evaluationSet.addAll(nodesToCompare);
						
							
							//NodeUtil.printNodesWithoutID(evaluationSet);
				
			
						//NodeUtil.print(originalCandidate);
				
							for (String strategy : strategies) {
								RUNNING_CNN_REDUCTION_STRATEGY = strategy;
								
								if (Lodica.getDatabaseConnection().checkTopNCNN(userId,IConstants.SIMILARITY_METHOD_IN_USE, IConstants.MAX_CANDIDATES, LIKED_ITEMS, RUNNING_CNN_REDUCTION_STRATEGY)) {
									continue;
								}								
								
								//NodeUtil.print("ROUND "+round+" user id "+ userId +" CNN CUT "+ IConstants.MAX_CANDIDATES +" and "+ likedItem +" liked items and strategy "+RUNNING_CNN_REDUCTION_STRATEGY);
								Lodica.getDatabaseConnection().deleteEvaluation();
								Lodica.getDatabaseConnection().deletePredictions();
								Lodica.getDatabaseConnection().deleteHitRate();
								init = System.currentTimeMillis();
								for (Node node : evaluationSet) {
									//Classification start point for that specific node in the evaluation set
										nodeUnderEvaluation = node; 
										candidates = new HashSet<Node>(); 
										candidates.add(node);
										List<NodePrediction> nops = runAndEvaluateLODICA(candidates);
										if(!saveTest){
											continue;
										}
										//NodeUtil.printPredictions(nops);
										round++;
									}
								
								
								//NodeUtil.print("\nAll evaluations done for user "+ userId);
								
								if(saveTest){
									DBFunctions.testCNNReducedHitRate(IConstants.LDSD_LOD);
									String evaluationTime = StringUtilsNode.getDuration(System.currentTimeMillis() - init);
									HitRate hitRate = Lodica.getDatabaseConnection().getHitRate(IConstants.SIMILARITY_METHOD_IN_USE);
									Lodica.getDatabaseConnection().insertOrUpdateTopNCNN(userId,IConstants.SIMILARITY_METHOD_IN_USE, IConstants.MAX_CANDIDATES, LIKED_ITEMS, RUNNING_CNN_REDUCTION_STRATEGY, hitRate.getTop1(), hitRate.getTop3(), hitRate.getTop5(), hitRate.getTop10(), hitRate.getTop15(), hitRate.getTop20(), hitRate.getTop25(), hitRate.getTop30(), hitRate.getAfter(), evaluationTime );	
									saveTest = true;
								}
								
								
								//DBFunctions.exportData("sql//"+LIKED_ITEMS+IConstants.MAX_CANDIDATES+".csv","hitrate",""+LIKED_ITEMS+""+IConstants.MAX_CANDIDATES,RUNNING_CNN_REDUCTION_STRATEGY,""+userId,time);									
							}
					}
					
					NodeUtil.print("We are finished for "+ likedItem +" liked items");
					

					//Lodica.getDatabaseConnection().deleteEvaluation();
					//Lodica.getDatabaseConnection().deletePredictions();
					//Lodica.getDatabaseConnection().deletePredictions();
		
				}
			}

	}
	
	/**
	 * @throws Exception
	 */
	private void startBehaviourExperimentCNNReduction() throws Exception {
		
		IConstants.N = 1;
		
		IConstants.N_PRIME = 2;

		useNPLUS = true;
		
		isEvaluation = false;
		
		IConstants.FILTER_CATEGORY = true;
		
		IConstants.SIMILARITY_METHOD_IN_USE = IConstants.LDSD_LOD;
		
		SparqlWalk.setService(IConstants.TSS_DBPEDIA);


		int CNN_SIZE = 100;
		
		
		
		
		//fred
		int PROFILE_SIZE = 0;
		
		List<String> strategies = new LinkedList<String>();
		strategies.add(IConstants.CNN_REDUCTION_STRATEGY_NONE);
		strategies.add(IConstants.CNN_REDUCTION_STRATEGY_TU);
		strategies.add(IConstants.CNN_REDUCTION_STRATEGY_RANDOM);

		List<Integer> cutTest = new LinkedList<Integer>();
		cutTest.add(500);
		cutTest.add(1000);
		cutTest.add(1500);
		cutTest.add(2000);
		cutTest.add(5000);
		cutTest.add(8000);
		
		//List<Integer> likedItems = new LinkedList<Integer>();
		//likedItems.add(10);
		//likedItems.add(15);
		//likedItems.add(20);
		
		List<Integer> cutsCNNSize = new LinkedList<Integer>();
		cutsCNNSize.add(50);
		cutsCNNSize.add(100);
		cutsCNNSize.add(150);
		cutsCNNSize.add(200);
		

		for (Integer cnnSize : cutTest) {
			CNN_SIZE = cnnSize;
		

		//	for (Integer likedItem : likedItems) {
				
				//PROFILE_SIZE = likedItem;
	
				for (Integer maxSize : cutsCNNSize) {
					
					IConstants.MAX_CANDIDATES = maxSize;
	
					//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(PROFILE_SIZE-1,PROFILE_SIZE+1);
					//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(49,51);

					//List<Integer> idsFinal = new ArrayList<Integer>(ids).subList(0,1);
					List<Integer> idsFinal = new ArrayList<Integer>();
					idsFinal.add(208875);

					for (Integer userId : idsFinal) {
	
							
							//TimeUnit.SECONDS.sleep(1);
						    //TimeUnit.MILLISECONDS.sleep(300);
			
							Lodica.round = 0;
							
							Lodica.userId = userId;
							
/*							if(Lodica.getDatabaseConnection().checkCNNCutTest(userId, PROFILE_SIZE, IConstants.MAX_CANDIDATES, CNN_SIZE)){
								continue;
							}*/
							
							/*if(Lodica.getDatabaseConnection().checkCNNCutTest(userId, "none",PROFILE_SIZE, IConstants.MAX_CANDIDATES, CNN_SIZE)){
								continue;
							}*/
			
							userProfile = getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId);
							
							userProfile = new HashSet<Node>(new ArrayList<Node>(userProfile).subList(0,19));
							
							if (userProfile==null || userProfile.isEmpty()) {
								NodeUtil.print("User ID "+  Lodica.userId + " has no profile and likes nothing ");
								continue;
							}
							
							PROFILE_SIZE  = userProfile.size();
							
						
							//to cache the links from nodes in the user profile
							for (Node node : userProfile) {
								ThreadLod  t4 = new ThreadLod(node.getURI());
								t4.start();
								
							}
							evaluationSet = new HashSet<Node>();
							for (Node node : userProfile) {
								if(CNN_SIZE<1000){
									evaluationSet.addAll(getDatabaseConnection().getItemsByURIAndCNNSizeAndConvertToNode(node.getURI(),CNN_SIZE-100,CNN_SIZE+100));
								}else{
									evaluationSet.addAll(getDatabaseConnection().getItemsByURIAndCNNSizeAndConvertToNode(node.getURI(),CNN_SIZE-500,CNN_SIZE+500));
								}
								
							}
							
							if(evaluationSet.isEmpty()){
								Lodica.getDatabaseConnection().insertOrUpdateCNNCUTTest(userId,"none",PROFILE_SIZE,IConstants.MAX_CANDIDATES, CNN_SIZE, 0,0,0,0);
								continue;
							}
							
							for (Node node : evaluationSet) {
								originalCandidate = node.getURI();
								nodeUnderEvaluation = node; 
								candidates = new HashSet<Node>(); 
								candidates.add(node);
								NodeUtil.print("Node tested:"+node.getURI()+ "a total of "+idsFinal.size()+" users will be evaluated for CNN size "+ IConstants.MAX_CANDIDATES +" and "+ PROFILE_SIZE +" CNN SIZE "+CNN_SIZE);
								List<NodePrediction> referenceResult = null;
								List<NodePrediction> testResultTU = null;
								List<NodePrediction> testResultRANDOM = null;
								//Classification start point for that specific node in the evaluation set
								
/*								if (!new File("sql//CNN_CUT_TEST.csv").exists()) {
									DBFunctions.saveCSVData("sql//CNN_CUT_TEST.csv","URI","PROFILE_SIZE","CNN_CUT_SIZE","CNN_SIZE_TEST","TOTAL_PREDICTED_LIKES","ALL_RANDOM","ALL_TU","RANDOM_TU");
								}*/
								
								if(Lodica.getDatabaseConnection().checkCNNCutTest(userId, node.getURI(),PROFILE_SIZE, IConstants.MAX_CANDIDATES, CNN_SIZE)){
									continue;
								}
								
								int TOTAL_PREDICTED_LIKE = 0;
								
								for (String strategy : strategies) {
									getDatabaseConnection().deletePredictions();
									RUNNING_CNN_REDUCTION_STRATEGY = strategy;
	
									if (strategy.equals(IConstants.CNN_REDUCTION_STRATEGY_NONE)) {
										referenceResult = runAndEvaluateLODICA(candidates);
										TOTAL_PREDICTED_LIKE = NodeUtil.getPredictionsByLabel(referenceResult, IConstants.LIKE).size();
										if (TOTAL_PREDICTED_LIKE==0) {
											Lodica.getDatabaseConnection().insertOrUpdateCNNCUTTest(userId, node.getURI(),PROFILE_SIZE,IConstants.MAX_CANDIDATES, CNN_SIZE, TOTAL_PREDICTED_LIKE,0,0,0);
											break;
										}
									}else if (strategy.equals(IConstants.CNN_REDUCTION_STRATEGY_TU)) {
										testResultTU = runAndEvaluateLODICA(candidates);
									}else{
										testResultRANDOM = runAndEvaluateLODICA(candidates);
									}
									
	
									
								}
								
								if (TOTAL_PREDICTED_LIKE>0) {
								
									int ALL_RANDOM = NodeUtil.countItersectionPredictionByURIAndLabel(referenceResult,testResultRANDOM,IConstants.LIKE);
									int ALL_TU = NodeUtil.countItersectionPredictionByURIAndLabel(referenceResult,testResultTU,IConstants.LIKE);
									int RANDOM_TU = NodeUtil.countItersectionPredictionByURIAndLabel(testResultRANDOM,testResultTU,IConstants.LIKE);
									//DBFunctions.saveCSVData("sql//CNN_CUT_TEST.csv",node.getURI().replace("http://dbpedia.org/resource/",""),""+PROFILE_SIZE,""+IConstants.MAX_CANDIDATES,""+CNN_SIZE,""+TOTAL_PREDICTED_LIKE,""+ALL_RANDOM,""+ALL_TU,""+RANDOM_TU);
									Lodica.getDatabaseConnection().insertOrUpdateCNNCUTTest(userId, node.getURI(),PROFILE_SIZE,IConstants.MAX_CANDIDATES, CNN_SIZE, TOTAL_PREDICTED_LIKE,ALL_RANDOM,ALL_TU,RANDOM_TU);
									
								}
								
							}
				
							NodeUtil.print("\nAll evaluations done for user "+ userId);
						}
	
						//Lodica.getDatabaseConnection().deleteEvaluation();
						//Lodica.getDatabaseConnection().deletePredictions();
						//Lodica.getDatabaseConnection().deletePredictions();
			
					}
				
		
		}
	}	
	

	/**
	 * @throws Exception
	 */
	private void startTOPNExperiment() throws Exception {
		
		IConstants.N = 1;
		
		IConstants.N_PRIME = 2;

		useNPLUS = true;
		
		isEvaluation = true;
		
		IConstants.FILTER_CATEGORY = true;
		
		IConstants.SIMILARITY_METHOD_IN_USE = IConstants.LDSD_LOD;
		
		SparqlWalk.setService(IConstants.TSS_DBPEDIA);

		//208844 is the max of user ids
		//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(9,11);
		//NodeUtil.print("A total of "+ids.size()+" users will be evaluated");
		//for (Integer userId : ids) {
		//208844 is the max of user ids
		//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(50,70);
		//NodeUtil.print("A total of "+ids.size()+" users will be evaluated");

		//for (Integer userId : ids) {
		//int randomStart = ThreadLocalRandom.current().nextInt(483,208844);
		
		//fred
		int LIKED_ITEMS = 20;
		
		Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(LIKED_ITEMS-1,LIKED_ITEMS+1);
		//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(49,51);
		List<Integer> idsFinal = new ArrayList<Integer>(ids).subList(0,100);
		NodeUtil.print("A total of "+idsFinal.size()+" users will be evaluated");
		for (Integer userId : idsFinal) {

		//for (int userId = 1; userId < IConstants.TOP_MORE; userId++) {

			Lodica.round = 0;
			
			Lodica.userId = userId;

			//userId = 118803;//Lodica.userId = 118803;

/*			if (getDatabaseConnection().existCompleteEvaluationByUserId(Lodica.userId)) {
				continue;
			}*/
			
			userProfile = getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId);
			
			if (userProfile==null || userProfile.isEmpty()) {
				NodeUtil.print("User ID "+  Lodica.userId + " has no profile and likes nothing ");
				continue;
			}
			
			//to cache the links from nodes in the user profile
			for (Node node : userProfile) {
				ThreadLod  t4 = new ThreadLod(node.getURI());
				t4.start();
			}

			//TimeUnit.SECONDS.sleep(1);
			//TimeUnit.MILLISECONDS.sleep(100);

			//for (Node node : userProfile) {
			//Set<Node> testNodes = userProfile;
			int totalCompared = getDatabaseConnection().getTotalEvaluationsDoneByUserSoFar(IConstants.LDSD_LOD,userId);
			
			//The random movie to be tested. This movie will be disliked, we expect to predict a LIKE to him.
			Set<Node> candidate = getDatabaseConnection().getRandomUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId,1);

			int x = IConstants.TOTAL_CANDIDATES_TO_COMPARE_HIT_RATE-totalCompared;
			
			Set<Node> nodesToCompare = null; 
			if (x > 0 && x < IConstants.TOTAL_CANDIDATES_TO_COMPARE_HIT_RATE) {
				nodesToCompare = getDatabaseConnection().getRandomUnionNonLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId,IConstants.TOTAL_CANDIDATES_TO_COMPARE_HIT_RATE-totalCompared);	
			}else{
				nodesToCompare = getDatabaseConnection().getRandomUnionNonLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId,IConstants.TOTAL_CANDIDATES_TO_COMPARE_HIT_RATE);
			}

			//This is the SEED_EVALUATION
			originalCandidate = new LinkedList<Node>(candidate).getFirst().getURI();

			evaluationSet = new HashSet<Node>();
			evaluationSet.addAll(candidate);
			evaluationSet.addAll(nodesToCompare);
			
			for (Node node : evaluationSet) {
				nodeUnderEvaluation = node; 
				candidates = new HashSet<Node>(); 
				candidates.add(node);
				//Classification start point for that specific node in the evaluation set
				runAndEvaluateLODICA(candidates);
				round++;
			}
			
			//calculateRR(Lodica.userId,originalCandidate,IConstants.LDSD);
			//calculateRR(Lodica.userId,originalCandidate,IConstants.LDSD_LOD);
			//if (Lodica.userId==1) {
				//getDatabaseConnection().deleteHitRate();	
			//}
			//getDatabaseConnection().updateEvaluationHitRate(IConstants.LDSD);
			//getDatabaseConnection().updateEvaluationHitRate(IConstants.LDSD_LOD);
			NodeUtil.print("\nAll evaluations done for user "+ userId);
		}
	}
	

	
	/**
	 *  LOD + ICA + LDSD starts from here
	 * @param candidates
	 * @return
	 * @throws Exception
	 */
	public List<NodePrediction> runAndEvaluateLODICA(Set<Node> candidates) throws Exception {
		
		if(IConstants.SIMILARITY_METHOD_IN_USE==null){
			throw new Exception("SIMILARITY_METHOD_IN_USE is null. Please set one");
		}
		
		//NodeUtil.print("\n------------------------------------LODICA ON ROUND ("+(++round)+")------------"+StringUtilsNode.getHourNow()+"------------------- \n");
		
		//init = System.currentTimeMillis();
		
		//NodeUtil.print("\nUser "+userId+" has "+userProfile.size()+" items to be evaluated and "+(userProfile.size()-round)+" are missing...\n");
		//NodeUtil.print("\nUser "+userId+" likes "+userProfile.size()+" items and has "+evaluationSet.size()+" items to be evaluated and "+(evaluationSet.size()-round)+" are missing...\n");
		//NodeUtil.print("Candidate under evaluation "+nodeUnderEvaluation.getURI()+" for user "+userId);
		//NodeUtil.print("\nLOADING CNNs...\n");
		
		//Building the CNNs. Including the candidate plus N-LIKE from the candidate set
		cnns = new LinkedList<Node>();
		NodeUtil.addIDsToCandidates(candidates);
		cnns.addAll(candidates);
		
		loadNeighbourhoodFromLOD(candidates);
		
		
		//NodeUtil.checkForDistinctIdsAndURIs(cnns);
		//NodeUtil.print("CNNs size "+cnns.size());
		//NodeUtil.print("CNNs PLUS size "+NodeUtil.getLabeledNodes(cnns).size());
		//NodeUtil.print("CNNs - Time elapse: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
		
		//NodeUtil.showGraph(true,cnns);
		

		// Building N+LIKE set from each member of CNNs. We build the N+LIKE set separately due to performance issues
		//calculateNPLUS();	

		NodeUtil.bootstrapNullLabelsNodes(cnns, IConstants.NO_LABEL);
				
  
		//NodeUtil.print("\nStep 1: INITIAL CLASSIFICATION USING ONLY LOCAL CLASSIFIER...\n");
		if (originalUnlabeledNodesToClassify==null) {
			originalUnlabeledNodesToClassify = new HashSet<Node>();
		}
		originalUnlabeledNodesToClassify = NodeUtil.getUnlabeledNodes(cnns);
		

		//this is only for online test as a baseline method.
/*		if(IConstants.SIMILARITY_METHOD_IN_USE.equals(IConstants.ABSTRACT_SIMILARITY)){
			List<NodePrediction> memoryPredictions = classifyContentBased();
			return memoryPredictions;
		}*/
		
		
		//init = System.currentTimeMillis();
		List<NodePrediction> memoryPredictions = classify();
		//NodeUtil.print("Local classifier: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));		
		
		//NodeUtil.printPredictions(memoryPredictions);
		//NodeUtil.showGraph(true,cnns);
		NodeUtil.updateLabelsAfterClassification(memoryPredictions);
		//NodeUtil.showGraph(true,cnns);

		//NodeUtil.print("------------------------------------------------------------------------------------------------------------");
		//NodeUtil.printPredictions(memoryPredictions);
		//NodeUtil.showGraph(true,cnns);
		//NodeUtil.print("After classification: - Time elapse: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
		
		//calculating and evaluating NO_ICA part 
		//calculateAndSaveEvaluationMetrics();
		
		if(IConstants.SIMILARITY_METHOD_IN_USE.equals(IConstants.LDSD)){
			return memoryPredictions;
		}
		
		
		// Here is the flag to use ICA
		IConstants.USE_ICA = true;
		//IConstants.USE_ICA = false;
		
		if (IConstants.USE_ICA) {
			//NodeUtil.print("\nStep 2: ITERATIVE CLASSIFICATION USING ICA...\n");
			for (int i = 0; ((i < IConstants.AMOUNT_OF_ITERATIONS_TO_STABALIZE) && IConstants.USE_ICA); i++) {
				//NodeUtil.print("ITERATION "+(i+1)+"/"+IConstants.AMOUNT_OF_ITERATIONS_TO_STABALIZE+"---------------------------------\n");
				icaIteration = i;
				//NodeUtil.print(IConstants.USE_ICA);
				//init = System.currentTimeMillis();
				memoryPredictions = classify();
				//NodeUtil.print("Iterative classifier round("+i+") Time: "+StringUtilsNode.getDuration(System.currentTimeMillis() - init));
				//NodeUtil.showGraph(true,cnns);
				NodeUtil.updateLabelsAfterClassification(memoryPredictions);
				//NodeUtil.showGraph(true,cnns);
			}			
		}

		//NodeUtil.print("Iterative classifier: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
		//calculating metrics and evaluating after ICA iterations 
		//calculateAndSaveEvaluationMetrics();
		IConstants.USE_ICA = false;		
		return memoryPredictions;
	}
	

	/**
	 * Classify all nodes. It calculate the LDSD distance Ndist
	 * @return
	 */
	private List<NodePrediction> classify() {
		

		List<NodePrediction> memoryPredictions = new ArrayList<NodePrediction>();
		
		List<Node> testNodes = new ArrayList<Node>(originalUnlabeledNodesToClassify);

		if (isEvaluation) {
			NodeUtil.labelNodes(candidates, IConstants.NO_LABEL);
			testNodes.addAll(candidates);
		}	
		
		//NodeUtil.print(testNodes.size()+" nodes to be classified");
		
		//Current set of labeled nodes in the cnn set. This must be created here for later usage during the LDSD_LOD classification LDSD_LOD.java
		currentCNNLabeledNodes = NodeUtil.getLabeledNodes(cnns);
		
		//NodeUtil.printNodes(currentCNNLabeledNodes);

		//NodeUtil.showGraph(IConstants.USE_ICA, cnns);
		//NodeUtil.print("Original candidate "+originalCandidate);
		//NodeUtil.print("User profile "+userProfile);
		//NodeUtil.showGraph(true, cnns);

		for (Node nodeTest : testNodes) {
			
/*			if (!IConstants.USE_ICA && !nodeUnderEvaluation.getURI().equals(nodeTest.getURI())) {
				ThreadLod  t4 = new ThreadLod(nodeTest.getURI());
				t4.start();
			}*/




			// classify new ones only, except the node under evaluation. ICA predictions are always calculated.
			//if (isEvaluation && NodeUtil.isDistinctURI(nodeTest, nodeUnderEvaluation) && !IConstants.USE_ICA) {
			//if (NodeUtil.isDistinctURI(nodeTest, nodeUnderEvaluation) && !IConstants.USE_ICA) {
			if (isEvaluation && !IConstants.USE_ICA) {

				NodePrediction existingPrediction = Lodica.getDatabaseConnection().getPrediction(nodeUnderEvaluation.getURI(),nodeTest.getURI(),userId,IConstants.USE_ICA);
				if (existingPrediction!=null) {
					memoryPredictions.add(existingPrediction);
					continue;
				}				
			}
			
			//Here NN-d(r,Tu,dist) takes place
			double shortestNeighbourDistance = Double.MAX_VALUE;
			double shortestNeighbourOfNeighbourDistance = Double.MAX_VALUE;
			double differenceScore = -1d;
			
			
			//Set<Node> labeledNeighbours = NodeUtil.getLabeledNodes(nodeTest.getNodes());
			Set<Node> labeledNeighbours = getTU(nodeTest,IConstants.N_PRIME);
			//Set<Node> labeledNeighbours = new HashSet<Node>();;

			//just in case we want LIKED nodes in the CNN
		//	if (isEvaluation && !NodeUtil.isDistinctURI(nodeTest, nodeUnderEvaluation)) {
			//	labeledNeighbours.add(nodeTest);
				//fr
/*				NodeUtil.print("Node Test:");
				NodeUtil.printNode(nodeTest);
				NodeUtil.print("LABELED NODES:");
				NodeUtil.printNodes(labeledNeighbours);
				NodeUtil.print();*/				
		//	}
			
/*			NodeUtil.print("Node Test:");
			NodeUtil.printNode(nodeTest);
			NodeUtil.print("LABELED NODES:");
			NodeUtil.printNodes(labeledNeighbours);
			NodeUtil.print();*/	
			
			
			Node nearestNeighbour = null;
			Node nearestNeighbourOfnearestNeighbour = null;
			for (Node neighbour : labeledNeighbours) {
				
				if (neighbour.getURI().equals(nodeTest.getURI())) {
					//NodeUtil.print("N1 "+neighbour.getURI());
					continue;
				}
				
				Double dist = getSemanticDistance(nodeTest, neighbour);
				//NodeUtil.print("d1: "+StringUtilsNode.getNumberFormated(dist));
				//NodeUtil.print("d1: "+dist);
				if (dist<=shortestNeighbourDistance) {
					shortestNeighbourDistance = dist;
					nearestNeighbour = neighbour;
				}
			}
			if (nearestNeighbour!=null) {
				//labeledNeighbours = NodeUtil.getLabeledNodes(nearestNeighbour.getNodes());
				labeledNeighbours = getTU(nearestNeighbour,IConstants.N_PRIME);
				
				//labeledNeighbours = NodeUtil.getLabeledNodesExcept(labeledNeighbours, nodeTest);
				
				for (Node neighbour2 : labeledNeighbours) {
					if (neighbour2.getURI().equals(nodeTest.getURI())) {
						//NodeUtil.print("N2: "+neighbour2.getURI());
						continue;
					}
					
					Double dist = getSemanticDistance(nearestNeighbour, neighbour2);
					//NodeUtil.print("d2: "+StringUtilsNode.getNumberFormated(dist));
					//NodeUtil.print("d2: "+dist);
					if (dist<=shortestNeighbourOfNeighbourDistance) {
						shortestNeighbourOfNeighbourDistance = dist;
						nearestNeighbourOfnearestNeighbour=neighbour2;
					}					
				}	
			}
			
			
			
			//NodeUtil.print("shortestNeighbourDistance "+shortestNeighbourDistance);
			//NodeUtil.print("shortestNeighbourOfNeighbourDistance "+shortestNeighbourOfNeighbourDistance);
			

			
			
			//NodeUtil.print("Original candidate"+originalCandidate);
			//NodeUtil.print("User profile "+userProfile);
			//NodeUtil.showGraph(false, cnns);
			
			
   		    // This calculates the usual graph structure when a test node is classified. 0 means the test node has no neighbourhood, 1 means the test node has only his neighbourhood and 2 means that his neighbourhood has a neighborhood
			int graphStructureDistance = 0;
			//if (shortestNeighbourDistance != Double.MAX_VALUE && shortestNeighbourOfNeighbourDistance != Double.MAX_VALUE && shortestNeighbourDistance !=shortestNeighbourOfNeighbourDistance) {
			if (shortestNeighbourDistance != Double.MAX_VALUE && shortestNeighbourOfNeighbourDistance != Double.MAX_VALUE && shortestNeighbourDistance > (-1)) {			
				//averageDeltaDistance = ((shortestNeighbourOfNeighbourDistance-shortestNeighbourDistance) * ((shortestNeighbourDistance+shortestNeighbourOfNeighbourDistance)/2d));				
				differenceScore = shortestNeighbourOfNeighbourDistance-shortestNeighbourDistance;
				//differenceScore = (shortestNeighbourOfNeighbourDistance+shortestNeighbourDistance)/2d;
				graphStructureDistance = 2;
			} else if (shortestNeighbourDistance != Double.MAX_VALUE && shortestNeighbourOfNeighbourDistance == Double.MAX_VALUE) {
				shortestNeighbourOfNeighbourDistance = 1d;
				//shortestNeighbourOfNeighbourDistance = 0d;
				//averageDeltaDistance = ((shortestNeighbourOfNeighbourDistance-shortestNeighbourDistance) * ((shortestNeighbourDistance+shortestNeighbourOfNeighbourDistance)/2d));
				differenceScore = shortestNeighbourOfNeighbourDistance-shortestNeighbourDistance;
				//differenceScore = (shortestNeighbourOfNeighbourDistance+shortestNeighbourDistance)/2d;
				graphStructureDistance = 1;
			} 
						
			
			StringBuilder why = new StringBuilder();
			if (IConstants.ADD_EXPLANATIONS) {
				if (nearestNeighbourOfnearestNeighbour!=null) {
					why = new StringBuilder();
					why.append("The distance from <b>"+NodeUtil.removeNamespace(nodeTest.getURI())+"</b> to its nearest neighbour <b>"+NodeUtil.removeNamespace(nearestNeighbour.getURI())+"</b> (d="+StringUtilsNode.getDecimalFormat(shortestNeighbourDistance)+") ");
					why.append("is lesser or equal than the distance from <b>"+NodeUtil.removeNamespace(nearestNeighbour.getURI())+"</b> to his nearest neighbour <b>"+NodeUtil.removeNamespace(nearestNeighbourOfnearestNeighbour.getURI())+"</b> (d="+StringUtilsNode.getDecimalFormat(shortestNeighbourOfNeighbourDistance)+"), which is recommended to you or is in your profile.");				
				}else if (nearestNeighbour!=null){
					why = new StringBuilder();
					why.append("The distance from <b>"+NodeUtil.removeNamespace(nodeTest.getURI())+"</b> to its nearest neighbour <b>"+NodeUtil.removeNamespace(nearestNeighbour.getURI())+"</b> is "+StringUtilsNode.getDecimalFormat(shortestNeighbourDistance)+" and <b>");
					why.append(NodeUtil.removeNamespace(nearestNeighbour.getURI())+"</b>, which is recommended to you or is in your profile, is not connected to any item that you like.");
				}
			}

						
/*			NodeUtil.print();
			NodeUtil.print(nodeTest.getURI());
			NodeUtil.print(nodeTest.getLabel());
			NodeUtil.print(why.toString());
			if (nearestNeighbourOfnearestNeighbour!=null) {
				NodeUtil.print(nearestNeighbourOfnearestNeighbour.getURI());	
			}
			NodeUtil.print();*/
			
						
			//Keep the predictions for late update (of labels)
			NodePrediction prediction = null;
			//NodeUtil.print(userId);
			if (isEvaluation) {
				if (shortestNeighbourDistance<=shortestNeighbourOfNeighbourDistance && shortestNeighbourDistance != Double.MAX_VALUE) {
					if (isEvaluation && !NodeUtil.isDistinctURI(nodeTest, nodeUnderEvaluation)) {
						prediction = new NodePrediction(IConstants.SEED_EVALUATION, nodeTest, nodeTest.getLabel(),IConstants.LIKE,DBFunctions.checkSimilarityMethod(IConstants.USE_ICA),differenceScore,userId,graphStructureDistance,originalCandidate,why.toString());					
					}else{
						prediction = new NodePrediction(nodeUnderEvaluation.getURI(), nodeTest, nodeTest.getLabel(),IConstants.LIKE,DBFunctions.checkSimilarityMethod(IConstants.USE_ICA),differenceScore,userId,graphStructureDistance,originalCandidate,why.toString());
					}
				}else{
					if (isEvaluation && !NodeUtil.isDistinctURI(nodeTest, nodeUnderEvaluation)) {
						prediction = new NodePrediction(IConstants.SEED_EVALUATION, nodeTest, nodeTest.getLabel(),IConstants.NO_LABEL,DBFunctions.checkSimilarityMethod(IConstants.USE_ICA),differenceScore,userId,graphStructureDistance,originalCandidate,why.toString());					
					}else{
						prediction = new NodePrediction(nodeUnderEvaluation.getURI(), nodeTest, nodeTest.getLabel(),IConstants.NO_LABEL,DBFunctions.checkSimilarityMethod(IConstants.USE_ICA),differenceScore,userId,graphStructureDistance,originalCandidate,why.toString());
					}				
				}
			}else{
				if (shortestNeighbourDistance<=shortestNeighbourOfNeighbourDistance && shortestNeighbourDistance != Double.MAX_VALUE) {
					prediction = new NodePrediction(nodeUnderEvaluation.getURI(), nodeTest, nodeTest.getLabel(),IConstants.LIKE,DBFunctions.checkSimilarityMethod(IConstants.USE_ICA),differenceScore,userId,graphStructureDistance,originalCandidate,why.toString());
				}else{
					prediction = new NodePrediction(nodeUnderEvaluation.getURI(), nodeTest, nodeTest.getLabel(),IConstants.NO_LABEL,DBFunctions.checkSimilarityMethod(IConstants.USE_ICA),differenceScore,userId,graphStructureDistance,originalCandidate,why.toString());
				}
			}
			
			
			

			
	
			//NodeUtil.print(why.toString());			
	
			

			//getDatabaseConnection().insertOrUpdatePrediction(prediction);	
			//NodeUtil.print(prediction.getSimilarityMethod());
			//NodeUtil.printPrediction(prediction);
			
			
			memoryPredictions.add(prediction);
			
			
		/*	if (System.currentTimeMillis() - init > 20000) {
				init = 0;
				break;
			} */
		}
		
		if (IConstants.USE_ICA && icaIteration==2) {
			//NodeUtil.writeExcel(userId, nodeUnderEvaluation.getURI(), IConstants.N, IConstants.N_PRIME, cnns.size(),getTotalTU(), memoryPredictions.size(), (StringUtilsNode.getDuration(System.currentTimeMillis() - init)), System.currentTimeMillis() - init);
			getDatabaseConnection().insertOrUpdatePredictions(memoryPredictions);
			icaIteration=-1;
		}else if (icaIteration==-1){
			getDatabaseConnection().insertOrUpdatePredictions(memoryPredictions);
		}
		
		
		//NodeUtil.writeExcel(userId, nodeUnderEvaluation.getURI(), IConstants.N, IConstants.N_PRIME, cnns.size(),getTotalTU(), memoryPredictions.size(), (StringUtilsNode.getDuration(System.currentTimeMillis() - init)), System.currentTimeMillis() - init);
		
		
		//String content = "USER "+userId+"	N	"+"NODE "+nodeUnderEvaluation.getURI()+"	N	"+IConstants.N+"	N_PRIME	"+IConstants.N_PRIME+"	CNN SIZE	"+cnns.size()+"	CNNs PLUS SIZE	"+NodeUtil.getLabeledNodes(cnns).size()+"	CLASSIFICATIONS	"+memoryPredictions.size()+"	CNNs PLUS SIZE	"+NodeUtil.getLabeledNodes(cnns).size()+"	TIME	"+(StringUtilsNode.getDuration(System.currentTimeMillis() - init));
		//NodeUtil.print(content);
/*		if (temp==2) {
		//if (temp==0) {
			NodeUtil.writeExcel(userId, nodeUnderEvaluation.getURI(), IConstants.N, IConstants.N_PRIME, cnns.size(),getTotalTU(), memoryPredictions.size(), (StringUtilsNode.getDuration(System.currentTimeMillis() - init)), System.currentTimeMillis() - init);
			NodeUtil.print(temp);
			NodeUtil.print("Saving...");
			temp = 0;
		}*/
		
		//StringUtilsNode.writeFile("statistic", content,true);
		//NodeUtil.print(content);
		//NodeUtil.print("Classification has ended with "+memoryPredictions.size()+" predictions saved");
		//NodeUtil.printPredictions(memoryPredictions);
		return memoryPredictions;
		
	}
	
	
	/**
	 * Classify all nodes. It calculates the Content-Based Similarity.
	 * @return
	 */
	private List<NodePrediction> classifyContentBased() {

		List<NodePrediction> memoryPredictions = new ArrayList<NodePrediction>();
		
		List<Node> testNodes = new ArrayList<Node>(originalUnlabeledNodesToClassify);
		
		//NodeUtil.print(testNodes.size()+" nodes to be classified");
		//NodeUtil.printNodes(currentCNNLabeledNodes);
		//NodeUtil.showGraph(IConstants.USE_ICA, cnns);
		//NodeUtil.print("Original candidate "+originalCandidate);
		//NodeUtil.print("User profile "+userProfile);
		//NodeUtil.showGraph(true, cnns);

		for (Node nodeTest : testNodes) {
		
			NodePrediction existingPrediction = Lodica.getDatabaseConnection().getPrediction(nodeUnderEvaluation.getURI(),nodeTest.getURI(), userId, IConstants.ABSTRACT_SIMILARITY);
			if (existingPrediction!=null) {
				memoryPredictions.add(existingPrediction);
				continue;
			}				
			
			double sim = 0d;
			if (NodeUtil.isDistinctURI(nodeUnderEvaluation, nodeTest)) {
				String descURI = SparqlWalk.getDescription(nodeUnderEvaluation.getURI());
				Set<String> likeItems = Lodica.getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToString(userId);
				String profileLikes = StringUtilsNode.getStringList(new ArrayList<String>(likeItems));
				String predDescURI = SparqlWalk.getDescription(nodeTest.getURI()); 
				sim = LuceneCosineSimilarity.getCosineSimilarity(descURI+profileLikes, predDescURI);
			}else{
				//to avoid seed to be recommended
				sim = 0;
			}
			String why = null;
			if (IConstants.ADD_EXPLANATIONS) {
				why = "The description of <b>"+NodeUtil.removeNamespace(nodeTest.getURI())+"</b> is similar ("+String.format( "%.2f", (sim))+") to the description of your query <b>"+NodeUtil.removeNamespace(nodeUnderEvaluation.getURI())+"</b> and the items in your profile.";
			}
			//Keep the predictions for late update (of labels)
			NodePrediction prediction =  new NodePrediction(nodeUnderEvaluation.getURI(), nodeTest, nodeTest.getLabel(),IConstants.NO_LABEL,IConstants.ABSTRACT_SIMILARITY,(1d-sim),userId,1,originalCandidate,why.toString());
			memoryPredictions.add(prediction);
	
		}
		
		Collections.sort(memoryPredictions);
		
		for (int i = 0; i < Math.min(IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED,memoryPredictions.size()) ; i++) {
			memoryPredictions.get(i).setPredictedLabel(IConstants.LIKE);
		}
		
		getDatabaseConnection().insertOrUpdatePredictions(memoryPredictions);
		//NodeUtil.print(content);
		//NodeUtil.print("Classification has ended with "+memoryPredictions.size()+" predictions saved");
		return memoryPredictions;
		
	}

	/**
	 * Adds incoming and outgoing links to create the neighborhood from LOD and convert them to nodes.
	 * 
	 * @param nodeOriginal
	 * @param userId
	 * @return 
	 */
	//public  synchronized  static void  findRelationsAndSave(String uri,boolean filterCategory) {
	public static int findRelationsAndSave(String uri, boolean filterCategory) {
	    //System.out.println("Threading " +  uri );
		IConstants.FILTER_CATEGORY = filterCategory;
		List<Resource> resources = new ArrayList<Resource>();
		try {
			resources = getDatabaseConnection().getLinksIncomingAndOutcoming(uri);
			//NodeUtil.printResources(resources);
			if (resources.isEmpty()) {
				resources = SparqlWalk.getDBpediaSubjectsAndObjectsBothWays(uri);
				//NodeUtil.printResources(resources);
				if (!resources.isEmpty()) {
					getDatabaseConnection().saveIncomingAndOutgoingLinks(uri,resources);
					//NodeUtil.print("Link "+nodeOriginal.getURI()+" inserted "+resources.size()+ " links");				
				}
			}
			//NodeUtil.print("Ending thread - Time elapse: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
		} catch (Exception e) {
			NodeUtil.print(e.getMessage());
		}
		return resources.size();
		
	}
	
	
	public static void findRelationsAndSave(Set<String> uris,boolean filterCategory) {
		IConstants.FILTER_CATEGORY = filterCategory;
		for (String uri : uris) {
			List<Resource> resources = new ArrayList<Resource>();
			try {
				resources = getDatabaseConnection().getLinksIncomingAndOutcoming(uri);
				//NodeUtil.printResources(resources);
				if (resources.isEmpty()) {
					resources = SparqlWalk.getDBpediaSubjectsAndObjectsBothWays(uri);
					//NodeUtil.printResources(resources);
					if (!resources.isEmpty()) {
						getDatabaseConnection().saveIncomingAndOutgoingLinks(uri,resources);
						//NodeUtil.print("Link "+nodeOriginal.getURI()+" inserted "+resources.size()+ " links");				
					}
				}		
			} catch (Exception e) {
				NodeUtil.print(e.getMessage());
			}
		}
	}
	
	
	public static void findRelationsAndSave(List<NodePrediction> nodePredictions,boolean filterCategory) {
		IConstants.FILTER_CATEGORY = filterCategory;
		for (NodePrediction nodePrediction : nodePredictions) {
			List<Resource> resources = new ArrayList<Resource>();
			try {
				String uri = nodePrediction.getNode().getURI();
				resources = getDatabaseConnection().getLinksIncomingAndOutcoming(uri);
				//NodeUtil.printResources(resources);
				if (resources.isEmpty()) {
					resources = SparqlWalk.getDBpediaSubjectsAndObjectsBothWays(uri);
					//NodeUtil.printResources(resources);
					if (!resources.isEmpty()) {
						getDatabaseConnection().saveIncomingAndOutgoingLinks(uri,resources);
						//NodeUtil.print("Link "+nodeOriginal.getURI()+" inserted "+resources.size()+ " links");				
					}
				}		
			} catch (Exception e) {
				NodeUtil.print(e.getMessage());
			}
		}
	}		



	/**
	 * Adds incoming and outgoing links to create the neighborhood from LOD and convert them to nodes.
	 * 
	 * @param nodeOriginal
	 * @param userId
	 * @return 
	 */
	private Set<Node> addNeighborhoodFromLODAndConvertToNodes(Node nodeOriginal, int userId) {

		List<Resource> resources = new ArrayList<Resource>();
		try {
			if(SparqlWalk.USE_SPARQL_CACHE){
				resources = getDatabaseConnection().getLinksIncomingAndOutcoming(nodeOriginal.getURI());	
			}
			
			//System.out.println("load from cache "+resources.size());
			//NodeUtil.printResources(resources);
			if (resources.isEmpty()) {
				resources = SparqlWalk.getDBpediaSubjectsAndObjectsBothWays(nodeOriginal.getURI());
				//System.out.println("load from lod "+resources.size());
				//NodeUtil.printResources(resources);
				if (!resources.isEmpty()) {
					getDatabaseConnection().saveIncomingAndOutgoingLinks(nodeOriginal.getURI(),resources);
					//System.out.println("saving cache "+resources.size());
					//NodeUtil.print("Link "+nodeOriginal.getURI()+" inserted "+resources.size()+ " links");				
				}else{
					new HashSet<Node>();
				}
			}
			
			
			//NodeUtil.print("CNN - Time elapse: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));


			//System.out.println("cnn size "+resources.size());
/*			if(isEvaluation && resources.size()<IConstants.MAX_CANDIDATES){
				saveTest = false;
				return new HashSet<Node>();
			}
			
			saveTest = true;*/
			
			// this is getting a sublist of resources if performance is down fred
			if (resources.size()>IConstants.MAX_CANDIDATES) {
				
				//NodeUtil.print(" errrrrrrrrrrrrrrrrrrrrrroooooooooooooooooooooooooooooooooooooooo ");
				if (RUNNING_CNN_REDUCTION_STRATEGY.equals(IConstants.CNN_REDUCTION_STRATEGY_TU)) {
					resources = reducedCNNByTU(resources);	
				}else if (RUNNING_CNN_REDUCTION_STRATEGY.equals(IConstants.CNN_REDUCTION_STRATEGY_RANDOM)) {
					resources = reduceCNNRandomly(resources);	
				}
				//resources = reducedCNNByLinkingSize(resources);	
			}/*else if (isEvaluation) {
				
				return new HashSet<Node>();
			}		
			*/

/*			//resources = new ArrayList<Resource>(new HashSet<Resource>(resources));
			if (resources.size()<before) {
				//NodeUtil.printNode(nodeUnderEvaluation);
				NodeUtil.print(resources.size()+" after CNN reduction");
				
				//NodeUtil.printResources(resources);
			}*/

			
			
			Set<Node> nodesToGraph = new HashSet<Node>(NodeUtil.convertResourceInNodes(resources, Integer.valueOf(NodeUtil.getMaxNodeID(cnns)), false,cnns));
			
			//Resources in the user profile can be part of CNNs
			for (Node userProfileNode : userProfile) {
				for (Node nodeToGraph : nodesToGraph) {
					//NodeUtil.print(node.getURI().trim());
					//NodeUtil.print(resource.getURI().trim());
					if (userProfileNode.getURI().equals(nodeToGraph.getURI())) {
						nodeToGraph.setLabel(IConstants.LIKE);
					}
				}
			}		
	
			for (Node newNode : nodesToGraph) {
				NodeUtil.connect2Nodes(newNode, nodeOriginal);	
			}
		
		} catch (Exception e) {
			NodeUtil.print(e.getMessage());
			return new HashSet<Node>();
		}

		return new HashSet<Node>(nodeOriginal.getNodes());
	}



	private List<Resource> reducedCNNByTU(List<Resource> resources) throws SQLException {
		List<Resource> resourcesIn = new ArrayList<Resource>(IConstants.MAX_CANDIDATES);
		List<Resource> resourcesOut = new ArrayList<Resource>();
			
		for (Resource localResource : resources) {
			for (Node userProfileNode : userProfile) {
			//NodeUtil.print(node.getURI().trim());
			//NodeUtil.print(resource.getURI().trim());
				if (userProfileNode.getURI().equals(localResource.getURI())) {
					resourcesIn.add(localResource);
				}
			}
			resourcesOut.add(localResource);
		}
			
			
			Set<Node> userProfileReduced = NodeUtil.removeNodesByURI(userProfile,resourcesIn);
			for (int i = 0; i < resourcesOut.size()&&resourcesIn.size()<=IConstants.MAX_CANDIDATES; i++) {
    		   Resource testResource = resources.get(i);
			   if(!getDatabaseConnection().getLinksIncomingAndOutcomingFinalFromNode(testResource.getURI(),userProfileReduced).isEmpty()) {
				   //NodeUtil.print("this guy connects to profile "+testResource.getURI());
				   resourcesIn.add(testResource);
			   }
			}
			
			if (resourcesIn.size()<IConstants.MAX_CANDIDATES) {
				for (int i = 0; i < IConstants.MAX_CANDIDATES; i++) {
					Collections.shuffle(resourcesOut);
					resourcesIn.add(resourcesOut.get(0));
				}
			}
		
			resources = resourcesIn.subList(0, IConstants.MAX_CANDIDATES);
		return resources;
	}

	private List<Resource> reduceCNNRandomly(List<Resource> resources) {
		List<Resource> resourcesNew = new ArrayList<Resource>();

			for (int i = 0; i < IConstants.MAX_CANDIDATES; i++) {
				Collections.shuffle(resources);
				resourcesNew.add(resources.get(0));
			}

		resources = resourcesNew;
		return resources;
	}
	
	private List<Resource> reducedCNNByLinkingSize(List<Resource> resources) throws SQLException {
		List<Resource> resourcesNew = new ArrayList<Resource>();
			for (Resource resourceLocal : resources) {
			
				List<Resource> linkedResources = getDatabaseConnection().getLinksIncomingAndOutcoming(resourceLocal.getURI());
				//NodeUtil.printResources(resources);
				if (linkedResources.isEmpty()) {
					linkedResources = SparqlWalk.getDBpediaSubjectsAndObjectsBothWays(resourceLocal.getURI());
					//NodeUtil.printResources(resources);
					if (!linkedResources.isEmpty()) {
						getDatabaseConnection().saveIncomingAndOutgoingLinks(resourceLocal.getURI(),linkedResources);
						//NodeUtil.print("Link "+nodeOriginal.getURI()+" inserted "+resources.size()+ " links");				
					}else{
						linkedResources = new ArrayList<Resource>();
					}
				}
				
				if (linkedResources.size() >= IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED) {
					resourcesNew.add(resourceLocal);
				}
				
				if (resourcesNew.size() == IConstants.MAX_CANDIDATES) {
					break;
				}					
				
			}
			resources = resourcesNew;
		return resources;
	}	

	/**
	 * @param nodeCandidate
	 * @param userId
	 * @return
	 */
	private Set<Node> addNeighborhoodTUFromLODAndConvertToNodes(Node nodeCandidate, int userId) {

		List<Resource> resources = new ArrayList<Resource>();

		Set<Node> nodesTU = new HashSet<Node>();
		
		try {

			//NodeUtil.print(userProfile.size());
			Set<Node> userProfileReducedNodesNotInCNN = NodeUtil.removeNodesByURI(userProfile,new HashSet<Node>(cnns));
			//NodeUtil.print(userProfileReduced.size());
			
/*			if (userProfileReduced.size()<userProfile.size()) {
				NodeUtil.showGraph(true, cnns);
			}*/
			
			
			if (userProfileReducedNodesNotInCNN.isEmpty()) {
				return new HashSet<Node>();
			}
			
			//First it tries to retrieve from database
			resources = getDatabaseConnection().getLinksIncomingAndOutcomingFinalFromNode(nodeCandidate.getURI(),userProfileReducedNodesNotInCNN);
			if (resources.isEmpty()) {
				return nodesTU;
			}

/*			if (resources.isEmpty()) {
				if (userProfileReduced.size()>IConstants.NUMBER_OF_RESOURCE_TO_BREAK_SPARQL_QUERY) {
					double parts = Math.ceil((double)userProfileReduced.size()/(double)IConstants.NUMBER_OF_RESOURCE_TO_BREAK_SPARQL_QUERY);
					for (int i = 0; i < parts; i++) {
						int limit = (i*IConstants.NUMBER_OF_RESOURCE_TO_BREAK_SPARQL_QUERY)+(IConstants.NUMBER_OF_RESOURCE_TO_BREAK_SPARQL_QUERY-1);
						if ((userProfileReduced.size()-1)<limit) {
							limit=userProfileReduced.size()-1;
						}
						resources.addAll(SparqlWalk.getDirectLinksBetween2ResourcesInBothWaysForNodes(nodeOriginal.getURI(), new HashSet(new ArrayList<Node>(userProfileReduced).subList(i*IConstants.NUMBER_OF_RESOURCE_TO_BREAK_SPARQL_QUERY,limit))));
					}
					
					for (Node node : userProfileReduced) {
						Set<Node> nodes = new HashSet<Node>();
						nodes.add(node);
						resources.addAll(SparqlWalk.getDirectLinksBetween2ResourcesInBothWaysForNodes(nodeCandidate.getURI(),nodes));
					}
					
				}else{
					resources = SparqlWalk.getDirectLinksBetween2ResourcesInBothWaysForNodes(nodeCandidate.getURI(), userProfileReduced);
				}
				
				//resources = SparqlWalk.getDirectLinksBetween2ResourcesInBothWaysForNodes(nodeOriginal.getURI(), userProfileReduced);
				if (!resources.isEmpty()) {
					getDatabaseConnection().checkAndinsertLinks(nodeCandidate.getURI(),resources);
					//NodeUtil.print("Link "+nodeOriginal.getURI()+" inserted "+resources.size()+ " links");
				}else{
					 return nodesTU;
				}
			}*/
	
			// At here the candidate nodes are connected to TU in the graph.
			nodesTU = new HashSet<Node>(NodeUtil.convertResourceInNodes(resources, Integer.valueOf(NodeUtil.getMaxNodeID(cnns)),IConstants.LIKE, false,cnns));
	
			for (Node newNode : nodesTU) {
				NodeUtil.connect2Nodes(newNode, nodeCandidate);	
			}
		
		} catch (Exception e) {
			NodeUtil.print(e.getMessage());
			return nodesTU;
		}

		return nodesTU;
	}
	/**
	 * Load the CNNs set from linked data and convert them into nodes one the graph.
	 * @param candidates
	 * @param filterDomain - filter by the domains of the dataset i.e movies, music and books fred
	 */
	private Set<Node> loadNeighbourhoodFromLOD(Set<Node> candidates) {
		//printNodes(allnodes);
		Set<Node> newNeighbours = new HashSet<Node>();
		Set<Node> tempNodes = new HashSet<Node>(candidates);
		for (int i = 0; i < IConstants.N; i++) {
			Set<Node> aux = null; 
			for (Node node : tempNodes) {
					//NodeUtil.print("On degree "+i+" loading neighbourhood for "+node.getURI()+ " from starting node "+nodeUnderEvaluation.getURI());
				    aux = new HashSet<Node>();
					aux.addAll(addNeighborhoodFromLODAndConvertToNodes(node, userId));
					newNeighbours.addAll(aux);
					//NodeUtil.print("On degree "+(i+1)+" neighbourhood size is "+newNeighbours.size()+ " for starting node "+nodeUnderEvaluation.getURI());
			}
			//NodeUtil.print("For N = "+(i+1)+" and starting node "+nodeUnderEvaluation.getURI() +" the neighbourhood size is "+newNeighbours.size());
			tempNodes = new HashSet<Node>(aux);
		}
		//NodeUtil.printNodes(newNeighbours);
		return newNeighbours;
	}

	/**
	 * Retrieves Tu by NPRIME for a given node.
	 * @param nodeTestCandidate
	 * @return
	 */
	private Set<Node> loadTUbyNPrime(Node nodeTestCandidate) {
		//printNodes(allnodes);
		Set<Node> newNeighbours = new HashSet<Node>();
		Set<Node> tempCandidateNodes = new HashSet<Node>();
		tempCandidateNodes.add(nodeTestCandidate);
		for (int i = 0; i < IConstants.N_PRIME; i++) {
			Set<Node> aux = new HashSet<Node>(); 
			for (Node tempCandidateNode : tempCandidateNodes) {
					//NodeUtil.print("On degree "+i+" loading neighbourhood for "+node.getURI()+ " from starting node "+nodeUnderEvaluation.getURI());
					aux.addAll(addNeighborhoodTUFromLODAndConvertToNodes(tempCandidateNode, userId));
					if (aux.isEmpty()) {
						break;
					}
					newNeighbours.addAll(aux);
					//NodeUtil.print("On degree "+(i+1)+" neighbourhood size is "+newNeighbours.size()+ " for starting node "+nodeUnderEvaluation.getURI());
			}
			
			tempCandidateNodes = new HashSet<Node>(aux);
		}
		//printNodes(newNeighbours);
		if (!newNeighbours.isEmpty()) {
			//NodeUtil.print("For N_PRIME = "+IConstants.N_PRIME+" and node "+nodeToTU.getURI() +" the TU neighbourhood size is "+newNeighbours.size());	
		}
		return newNeighbours;
	}

	
	/**
	 * TU is a training set to classify a given node. TU includes his direct labeled nodes from CNNs and new labeled nodes from a N_Prime distance.  
	 * @param nodeTestCandidate
	 * @param nPrime
	 * @return Set<Node> We return the node.getNodes() because the TU got part of the graph and are connected to the test node
	 */
	private Set<Node> getTU(Node nodeTestCandidate, int nPrime) {
		//init = System.currentTimeMillis();
		Set<Node> newTus = neighboursPlus.get(nodeTestCandidate);
		if (newTus==null) {
			newTus = loadTUbyNPrime(nodeTestCandidate);
			neighboursPlus.put(nodeTestCandidate, newTus);
		}
		//NodeUtil.print("TU: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
		//NodeUtil.printNodes(neighboursPlus.get(node));	
		//NodeUtil.printNodes(node.getNodes());

		//NodeUtil.getLabeledNodes(node.getNodes());
		return NodeUtil.getLabeledNodesExcept(nodeTestCandidate.getNodes(),nodeTestCandidate);
	}	
	
	
	

	/**
	 * @return
	 */
	public int getTotalTU(){
		int total = 0;
		for (Node node : originalUnlabeledNodesToClassify) {
			if (neighboursPlus!=null && neighboursPlus.get(node)!=null) {
				total = total + neighboursPlus.get(node).size();	
			}
		}
		return total;
	}
	
	/**
	 * @param nodeTest
	 * @param neighbour
	 * @param dist
	 * @return
	 */
	private Double getSemanticDistance(Node nodeTest, Node neighbour) {
		double dist = 1d;
		try {
			//if (IConstants.USE_SEMANTIC_DISTANCE && IConstants.USE_ICA) {
			if (IConstants.USE_ICA) {
				return Classifier.calculateSemanticDistance(nodeTest, neighbour,IConstants.LDSD_LOD);
			//}else if (IConstants.USE_SEMANTIC_DISTANCE && !IConstants.USE_ICA){
			}else if (!IConstants.USE_ICA){
				return Classifier.calculateSemanticDistance(nodeTest, neighbour,IConstants.LDSD);	
			}
			
		} catch (Exception e) {
			NodeUtil.print("Semantic Distance Exception: "+e.getMessage());
			return dist;
		}
		return dist;
	}
	
	
	@SuppressWarnings("unused")
	private void calculateNPLUS() {
		if(useNPLUS){
			neighboursPlus = new HashMap<Node,Set<Node>>();
			trainingSet = new HashSet<Node>();
			NodeUtil.print("\n----------------------------------LOADING NEIGHBOURS+ PLUS-----------------------------------------------\n");
			addDirectLabeledNodesFromUserProfileOptimized(new HashSet<Node>(NodeUtil.getLabeledNodes(cnns)),new HashSet<Node>(userProfile));
			//NodeUtil.print("N+LIKE : "+neighboursPlus.toString());
			//NodeUtil.print("N+LIKE   direct key size : "+neighboursPlus.keySet().size());
			addIndirectLabeledNodesFromUserProfileOptimized(neighboursPlus);
			//NodeUtil.print("N+LIKE indirect key size : "+neighboursPlus.keySet().size());
			//NodeUtil.print("N+LIKE : - Time elapse: "+(System.currentTimeMillis() - init));
			
			NodeUtil.print("N+LIKE size: "+trainingSet.size());
			//NodeUtil.print("N+LIKE : - Time elapse: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
			NodeUtil.print("Labelled Nodes: "+NodeUtil.getLabeledNodes(cnns));
		}
	}
	
	/**
	 * Building indirect N+
	 * @param userId
	 * @param neighboursPlus
	 */
	public void addIndirectLabeledNodesFromUserProfileOptimized(Map<Node, Set<Node>> neighboursPlus) {
		Set<Node> keyNodesHere = neighboursPlus.keySet();
		Set<Node> keyNodesLocal = new HashSet<Node>();
		for (Node node : keyNodesHere) {
			keyNodesLocal.addAll(neighboursPlus.get(node));
		}
		Set<Node> userProfileReduced = NodeUtil.removeNodesByURI(userProfile,trainingSet);
		addDirectLabeledNodesFromUserProfileOptimized(keyNodesLocal,userProfileReduced);			
	}

	/**
	 * Building direct N+
	 * @param nodes
	 */
	public void addDirectLabeledNodesFromUserProfileOptimized(Set<Node> nodes, Set<Node> userProfile) {
		// Very important for efficiency.
		userProfile = NodeUtil.removeNodesByURI(userProfile,new HashSet<Node>(cnns));
		Set<String> uriUserProfilesLabeledNodes = NodeUtil.getSetStringURI(userProfile);
		for (Node node : nodes) {
			int nodeId = generateNextIdForNPlus(cnns);
			//NodeUtil.print("Before N+ direct - Time elapse: "+(System.currentTimeMillis() - init));
			
			
			List<Resource> resources = getDatabaseConnection().getLinksIncomingAndOutcoming(node.getURI(),uriUserProfilesLabeledNodes);
			if (resources.isEmpty()) {
				resources = SparqlWalk.getDirectLinksBetween2ResourcesInBothWays(node.getURI(), uriUserProfilesLabeledNodes);
				if (!resources.isEmpty()) {
					getDatabaseConnection().saveIncomingAndOutgoingLinks(node.getURI(),resources);
					NodeUtil.print("Link "+node.getURI()+" inserted "+resources.size()+ " links");
				}else{
					continue;
				}
			}			
			Set<Node> existingUserNodesProfile = NodeUtil.createNewLabeledNodesNotInNPlus(resources,IConstants.LIKE,trainingSet,nodeId);			

			for (Node existingUserNodeProfile : existingUserNodesProfile) {
				if (NodeUtil.isDistinctURI(node, existingUserNodeProfile)) {

					//if neighboursPlus already exists	
					if (neighboursPlus.containsKey(node) && (!neighboursPlus.get(node).contains(existingUserNodeProfile)) && existingUserNodeProfile.getLabel().equals(IConstants.LIKE)) {
							Set<Node> labeledNodesLocal = new HashSet<Node>();
							labeledNodesLocal.addAll(neighboursPlus.get(node));
							labeledNodesLocal.add(existingUserNodeProfile);
							trainingSet.add(existingUserNodeProfile);
							//NodeUtil.printNodes(traininSet);							
							neighboursPlus.put(node, labeledNodesLocal);
							NodeUtil.connect2Nodes(node, existingUserNodeProfile);
					// first time when neighboursPlus is empty or null
					}else if (existingUserNodeProfile.getLabel().equals(IConstants.LIKE)) {
							Set<Node> labeledNodesLocal = new HashSet<Node>();
							labeledNodesLocal.add(existingUserNodeProfile);
							trainingSet.add(existingUserNodeProfile);
							neighboursPlus.put(node, labeledNodesLocal);
							NodeUtil.connect2Nodes(node, existingUserNodeProfile);							
					}

				}
			}
		}
	}
	

	/**
	 * Generate the ids of N+ new nodes
	 * @return
	 */
	private static int generateNextIdForNPlus(List<Node> cnns) {
		int neighboursPlusMaxSize = 0;
		for (Node node : neighboursPlus.keySet()) {
			neighboursPlusMaxSize = neighboursPlusMaxSize + (neighboursPlus.get(node).size());
		}
		int id = Integer.valueOf(NodeUtil.getMaxNodeID(cnns)) + neighboursPlusMaxSize;
		return id;
	}
	
	/**
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void calculateMRR() throws Exception {


		// loading userIds from DB
		//Set<Integer> userIds = Lodica.getDatabaseConnection().getUserIdsForLikedMoviesMusicsBooks();
		Set<Integer> userIds = new HashSet<Integer>();
		userIds.add(1);
		
		for (Integer userId : userIds) {
			
			Lodica.userId = userId;

			userProfile = getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(Lodica.userId);			

			for (Node node : userProfile) {
				
				List<NodePrediction> predictions = getDatabaseConnection().getPredictionsBySeed(node.getURI(),userId);

				List<NodePrediction> testPredictions = predictions;
				Collections.reverse(testPredictions);
				double mrr = 0d;
				for (NodePrediction prediction : predictions) {
					mrr =+ MRR.calculateMRR(prediction.getSeed(), NodeUtil.getSetStringURIFromPrediction(testPredictions));
				}
				NodeUtil.print("MRR for"+node.getURI()+" is "+mrr);	
			}
		}
	}
	
	/**
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void calculateRRByScale(int userid, String originalCandidate, String method) throws Exception {
		LinkedList<Evaluation> evaluations = new LinkedList<Evaluation>(getDatabaseConnection().getEvaluationsByOriginalCandidateSortedByScore(userid,originalCandidate,method));
		//NodeUtil.printEvaluations(evaluations);
		for (Evaluation evaluation : evaluations) {
			double position = Math.round(StringUtilsNode.scale(evaluation.getScore(), -1, 1, evaluations.size(), 1));
			double rr = 1d / position;
			//NodeUtil.print("Position is "+position);
			//NodeUtil.print("RR is "+rr);
			evaluation.setRr(rr);
			evaluation.setPosition((int)position);
			//NodeUtil.printEvaluation(evaluation);
		}
		
		//NodeUtil.printEvaluations(evaluations);
		
		getDatabaseConnection().insertOrUpdateEvaluations(evaluations);
	}
	
	public static void calculateRR(LinkedList<Evaluation> evaluations) {
		double position = 0;
		double previousScore = 2;
		for (Evaluation evaluation : evaluations) {
			if (evaluation.getScore() < previousScore) {
				position = position + 1;
			}
			double rr = 1d / position;
			previousScore = evaluation.getScore();

			// NodeUtil.print("Position is "+position);
			// NodeUtil.print("RR is "+rr);
			evaluation.setRr(rr);
			evaluation.setPosition((int) position);
			// NodeUtil.printEvaluation(evaluation);
		}
	}	
	
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		Lodica ica = null;
		try {
			init = System.currentTimeMillis();
			ica = new Lodica();
			IConstants.FILTER_CATEGORY = true;
			SparqlWalk.USE_SPARQL_CACHE = false;
			SparqlWalk.USE_SPARQL_CACHE = false;
			//ThreadLod  t4 = new ThreadLod("http://dbpedia.org/resource/Finding_Nemo");
			//t4.start();
			Lodica.getDatabaseConnection().deletePredictionByUserId(208879);
			RUNNING_CNN_REDUCTION_STRATEGY = IConstants.CNN_REDUCTION_STRATEGY_TU;
			IConstants.MAX_CANDIDATES = 20;
			List<NodePrediction> nops = null;
			nops = ica.startWeb(208879, "http://pt.dbpedia.org/resource/Mar_Morto_(livro)", IConstants.LDSD_LOD);
			NodeUtil.print("Time elapse: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
			NodeUtil.printPredictions(nops);
			//ica.startWeb(208875, "http://dbpedia.org/resource/Reggae", IConstants.LDSD_LOD);
			//ica.startWeb(4, "http://dbpedia.org/resource/Thomas_Newman", true);
			//ica.startWeb(4, "http://dbpedia.org/resource/Brazil", true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public static void main2(String[] args) throws Exception {
		Lodica ica = null;
		boolean loop = true;
		boolean userManuallyStarting = true;
		for (int retries = 0;loop; retries++) {
			try {
				init = System.currentTimeMillis();
				ica = new Lodica();
				IConstants.FILTER_CATEGORY = true;
				SparqlWalk.USE_SPARQL_CACHE = true;				
				if (userManuallyStarting) {
					for (Item item : Lodica.getDatabaseConnection().getItemsByCategory(IConstants.CATEGORY_MUSIC)) {
						ThreadLod  t4 = new ThreadLod(item.getUri());
						t4.start();
						//ica.startWeb(208875, "http://dbpedia.org/resource/Alan_Jackson", IConstants.LDSD_LOD);
						ica.startWeb(208875, item.getUri(), IConstants.LDSD_LOD);				
						NodeUtil.print("Evaluated "+item.getUri());
					}
					userManuallyStarting = false;
				}
				
				loop = false;
			} catch (org.apache.http.conn.HttpHostConnectException e) {
				loop=true;
				getDatabaseConnection().deleteEvaluationByUserId(userId);
				idToEvaluate = userId;
				//getDatabaseConnection().deletePredictionByUserId(userId);
				e.printStackTrace();
				if (retries < 10000) {
					NodeUtil.print("restarting...#"+retries);
					continue;
				} else {
					throw e;
				}
			} catch (Exception e) {
				e.printStackTrace();
				loop=true;
				getDatabaseConnection().deleteEvaluationByUserId(userId);
				getDatabaseConnection().deletePredictionByUserId(userId);
				idToEvaluate = idToEvaluate+1;
				e.printStackTrace();
				if (retries < 10000) {
					NodeUtil.print("restarting...#"+retries);
					continue;
				} else {
					throw e;
				}
			}
		}
	}	
	
	
	
	/**
	 * @throws Exception
	 */
	public List<NodePrediction> startWeb(int userIdFromRest, String uri, String similarityMethod) throws Exception {	
		
		List<NodePrediction> nops = null;

		// A few settings
		//Lodica ica = new Lodica();
		
		IConstants.ADD_EXPLANATIONS = true;

		IConstants.N = 1;
		
		IConstants.N_PRIME = 2;
		
		IConstants.PRINT = true;

		useNPLUS = true;
		
		isEvaluation = false;
		
		//Set<Integer> ids = getDatabaseConnection().getUserIdsWithLikedItemsByRange(0,3);
		//List<Integer> idsFinal = new ArrayList<Integer>(ids).subList(0,1);		
		
		List<Integer> idsFinal = new ArrayList<Integer>();
		
		idsFinal.add(userIdFromRest);
		
		//NodeUtil.print("A total of "+idsFinal.size()+" users will be evaluated");

		IConstants.SIMILARITY_METHOD_IN_USE = similarityMethod;
		
		for (Integer userId : idsFinal) {

			Lodica.round = 0;
			
			Lodica.userId = userId;

			userProfile = getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId);

			Node seed = new Node(null,IConstants.NO_LABEL,uri);
			
			if (NodeUtil.getUris(userProfile).contains(uri)) {
				seed = new Node(null,IConstants.LIKE,uri);
			}
			
			originalCandidate = seed.getURI();
			evaluationSet = new HashSet<Node>();
			evaluationSet.add(seed);
	
			//The candidates should be here only the nodes not liked by the user which connects to the seed. The data structure should be change the 
			for (Node node : evaluationSet) {
				nodeUnderEvaluation = node; 
				candidates = new HashSet<Node>(); 
				candidates.add(node);
				//Classification start point. 
				
				nops = new ArrayList<NodePrediction>();
				nops.addAll(runAndEvaluateLODICA(candidates));
			}
			//NodeUtil.printPredictions(nops);
			//NodeUtil.print(nops.size()+" evaluations processed for user "+ userId);
		}

		
/*		} catch (org.apache.http.conn.HttpHostConnectException e) {
			new HttpHostConnectException("Http Host Connect Exception");
		} catch (java.sql.SQLNonTransientConnectionException e) {
			new Exception("Database out of connection");
			
		} catch (Exception e) {
			new Exception("Exception:" +e.getMessage());
		}*/
		
		return nops;
		

		
	}	


}