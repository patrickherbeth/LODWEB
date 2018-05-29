package metric;

import java.util.ArrayList;



import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.bouncycastle.asn1.x509.IssuingDistributionPoint;

import node.IConstants;
import node.Lodica;
import node.Node;
import node.NodePrediction;
import node.NodeUtil;

	/**
	 * Precision and recall at different positions in the list.
	 * Precision and recall are classical evaluation measures from information retrieval.
	 *
	 * This class contains methods for computing precision and recall up to different positions
	 * in the recommendation list, and the average precision (AP).
	 *
	 * The mean of the AP over different users is called mean average precision (MAP)
	 * @version 2.03
	 */
	public class PrecisionAndRecallString {
		
		
		public static void main(String[] args) {
			calculatePrecisionRecall();
			//test();
		}
		
		
		public static void test() {
			List<String> rankedItems = new LinkedList<String>();
			rankedItems.add(""+1);

			Collection<String> correctItems = new LinkedList<String>();
			correctItems.add(""+1);
			correctItems.add(""+2);

			NodeUtil.print(precisionAt(rankedItems, correctItems, correctItems.size()));
			NodeUtil.print(recallAt(rankedItems, correctItems, correctItems.size()));
		}		
		
		
		/**
		 * @param predictions
		 */
		private static void calculatePrecisionRecall() {
			//MAP https://www.youtube.com/watch?v=pM6DJ0ZZee0
			
			List<NodePrediction> predictions = Lodica.getDatabaseConnection().getPredictionsBySeed("http://dbpedia.org/resource/Adam_Lambert",1);
			Collections.sort(predictions);

			List<Node> rankedNodes = NodeUtil.getNodesFromPredictionList(predictions);
			
			List<Node> correctRankNodes = NodeUtil.getNodesFromPredictionList(NodeUtil.getRandomPredictions(predictions));

			List<String> rankedNodesIds = NodeUtil.getUrisByList(rankedNodes);
			
			List<String> correctRankNodesIds = NodeUtil.getUrisByList(correctRankNodes);
			
			double precision = PrecisionAndRecallString.precision(rankedNodesIds, correctRankNodesIds);
			double recall = PrecisionAndRecallString.recall(rankedNodesIds, correctRankNodesIds);
			double fmeasure = PrecisionAndRecallString.fmeasure(precision, recall);

			NodeUtil.print(precision);
			NodeUtil.print(recall);
			NodeUtil.print(fmeasure);			

			double precisionAt5 = PrecisionAndRecallString.precisionAt(rankedNodesIds, correctRankNodesIds, 5);
			double precisionAt10 = PrecisionAndRecallString.precisionAt(rankedNodesIds, correctRankNodesIds, 10);
			double precisionAt20 = PrecisionAndRecallString.precisionAt(rankedNodesIds, correctRankNodesIds, 20);
			NodeUtil.print(precisionAt5);
			NodeUtil.print(precisionAt10);
			NodeUtil.print(precisionAt20);
			
			double recallAt5 = PrecisionAndRecallString.recallAt(rankedNodesIds, correctRankNodesIds, 5);
			double recallAt10 = PrecisionAndRecallString.recallAt(rankedNodesIds, correctRankNodesIds, 10);
			double recallAt20 = PrecisionAndRecallString.recallAt(rankedNodesIds, correctRankNodesIds, 20);
			NodeUtil.print(recallAt5);
			NodeUtil.print(recallAt10);
			NodeUtil.print(recallAt20);
			
			
			double averagePrecision = PrecisionAndRecallString.AP(rankedNodesIds, correctRankNodesIds, null);
			NodeUtil.print(averagePrecision);
		}			
		
		

	  // Prevent instantiation.
	  private PrecisionAndRecallString() {}

  /**
   * Compute the average precision (AP) of a list of ranked items.
   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
   * @param correctItems a collection of positive/correct item IDs
   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
   * @return the AP for the given list
   */
	public static double AP(List<String> rankedItems, Collection<String> correctItems, Collection<String> ignoreItems) {

		if (ignoreItems == null)
			ignoreItems = new ArrayList<String>();

		int hitCount = 0;
		double avgPrecSum = 0;
		int leftOut = 0;

		for (int i = 0; i < rankedItems.size(); i++) {
			String itemId = rankedItems.get(i);
			if (ignoreItems.contains(itemId)) {
				leftOut++;
				continue;
			}

			if (!correctItems.contains(itemId))
				continue;

			hitCount++;

			avgPrecSum += (double) hitCount / (i + 1 - leftOut);
		}

		if (hitCount != 0)
			return avgPrecSum / hitCount;
		else
			return 0;
	}
	
		
	
	/**
	 * Harmonic Mean
	 * @param precision
	 * @param recall
	 * @return
	 */
	public static double fmeasure(double precision, double recall) {

		if (precision + recall > 0) {
			return 2 * (precision * recall) / (precision + recall);
		} else {
			// cannot divide by zero, return error code
			return -1;
		}
	}
	
	  /**
	   * Compute the precision of a list of ranked items. In this case N is the correctItems size
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @return the precision@N for the given data
	   */
	  public static double precision(List<String> rankedItems, Collection<String> correctItems) {
	    return precisionAt(rankedItems, correctItems, new HashSet<String>(), correctItems.size());
	  }
	  
	  /**
	   * Compute the recall of a list of ranked items. In this case N is the correctItems size
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @return the recall@N for the given data
	   */
	  public static double recall(List<String> rankedItems, Collection<String> correctItems) {
	    return recallAt(rankedItems, correctItems, new HashSet<String>(), correctItems.size());
	  }	

	  /**
	   * Compute the precision@N of a list of ranked items at several N.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	   * @param ns the cutoff positions in the list
	   * @return the precision@N for the given data at the different positions N
	   */
	  public static HashMap<Integer, Double> precisionAt(List<String> rankedItems,Collection<String> correctItems,Collection<String> ignoreItems,int[] ns) {
	    HashMap<Integer, Double> precisionAtN = new HashMap<Integer, Double>();
	    for (int n : ns)
	      precisionAtN.put(n, precisionAt(rankedItems, correctItems, ignoreItems, n));

	    return precisionAtN;
	  }

	  /**
	   * Compute the precision@N of a list of ranked items.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param n the cutoff position in the list
	   * @return the precision@N for the given data
	   */
	  public static double precisionAt(List<String> rankedItems, Collection<String> correctItems, int n) {
	    return precisionAt(rankedItems, correctItems, new HashSet<String>(), n);
	  }

	  /**
	   * Compute the precision@N of a list of ranked items.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	   * @param n the cutoff position in the list
	   * @return the precision@N for the given data
	   */
	  public static double precisionAt(List<String> rankedItems,Collection<String> correctItems,Collection<String> ignoreItems,int n) {
	    return (double) hitsAt(rankedItems, correctItems, ignoreItems, n) / n;
	  }

	  /**
	   * Compute the recall@N of a list of ranked items at several N.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	   * @param ns the cutoff positions in the list
	   * @return the recall@N for the given data at the different positions N
	   */
	  public static HashMap<Integer, Double> recallAt(List<String> rankedItems, Collection<String> correctItems, Collection<String> ignoreItems, int[] ns){
	    HashMap<Integer, Double> recallAtN = new HashMap<Integer, Double>();
	    for (int n : ns)
	      recallAtN.put(n, recallAt(rankedItems, correctItems, ignoreItems, n));
	    return recallAtN;
	  }
	  
	  /**
	   * Compute the recall@N of a list of ranked items.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param n the cutoff position in the list
	   * @return the recall@N for the given data
	   */
	  public static double recallAt(List<String> rankedItems, Collection<String> correctItems, int n) {
	    return recallAt(rankedItems, correctItems, new HashSet<String>(), n);
	  }

	  /**
	   * Compute the recall@N of a list of ranked items.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	   * @param n the cutoff position in the list
	   * @return the recall@N for the given data
	   */
	  public static double recallAt(List<String> rankedItems, Collection<String> correctItems, Collection<String> ignoreItems,int n) {
	    return (double) hitsAt(rankedItems, correctItems, ignoreItems, n) / correctItems.size();
	  }

	  /**
	   * Compute the number of hits until position N of a list of ranked items.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	   * @param n the cutoff position in the list
	   * @return the hits@N for the given data
	   */
	  public static int hitsAt(List<String> rankedItems, Collection<String> correctItems, Collection<String> ignoreItems,int n) {

		if (n < 1)
			throw new IllegalArgumentException("n must be at least 1.");

		int hitCount = 0;

		int leftOut = 0;

		for (int i = 0; i < rankedItems.size(); i++) {

			String itemId = rankedItems.get(i);

			if (ignoreItems.contains(itemId)) {
				leftOut++;
				continue;
			}

			if (!correctItems.contains(itemId))
				continue;

			if (i < n + leftOut)
				hitCount++;
			else
				break;
		}
		return hitCount;
	}
	  
}


