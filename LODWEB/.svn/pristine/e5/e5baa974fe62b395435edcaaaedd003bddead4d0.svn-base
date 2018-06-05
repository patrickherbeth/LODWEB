package metric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
	public class PrecisionAndRecall {
		
		
		
		

	  // Prevent instantiation.
	  private PrecisionAndRecall() {}

  /**
   * Compute the average precision (AP) of a list of ranked items.
   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
   * @param correctItems a collection of positive/correct item IDs
   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
   * @return the AP for the given list
   */
	public static double AP(List<Integer> rankedItems, Collection<Integer> correctItems, Collection<Integer> ignoreItems) {

		if (ignoreItems == null)
			ignoreItems = new ArrayList<Integer>();

		int hitCount = 0; double avgPrecSum = 0; int leftOut = 0;

		for (int i = 0; i < rankedItems.size(); i++) {
			int itemId = rankedItems.get(i);
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
	  public static double precision(List<Integer> rankedItems, Collection<Integer> correctItems) {
	    return precisionAt(rankedItems, correctItems, new HashSet<Integer>(), correctItems.size());
	  }
	  
	  /**
	   * Compute the recall of a list of ranked items. In this case N is the correctItems size
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @return the recall@N for the given data
	   */
	  public static double recall(List<Integer> rankedItems, Collection<Integer> correctItems) {
	    return recallAt(rankedItems, correctItems, new HashSet<Integer>(), correctItems.size());
	  }		
		

	  /**
	   * Compute the precision@N of a list of ranked items at several N.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	   * @param ns the cutoff positions in the list
	   * @return the precision@N for the given data at the different positions N
	   */
	  public static HashMap<Integer, Double> precisionAt(List<Integer> rankedItems,Collection<Integer> correctItems,Collection<Integer> ignoreItems,int[] ns) {
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
	  public static double precisionAt(List<Integer> rankedItems, Collection<Integer> correctItems, int n) {
	    return precisionAt(rankedItems, correctItems, new HashSet<Integer>(), n);
	  }

	  /**
	   * Compute the precision@N of a list of ranked items.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	   * @param n the cutoff position in the list
	   * @return the precision@N for the given data
	   */
	  public static double precisionAt(List<Integer> rankedItems,Collection<Integer> correctItems,Collection<Integer> ignoreItems,int n) {
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
	  public static HashMap<Integer, Double> recallAt(List<Integer> rankedItems,Collection<Integer> correctItems,Collection<Integer> ignoreItems,int[] ns){
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
	  public static double recallAt(List<Integer> rankedItems, Collection<Integer> correctItems, int n) {
	    return recallAt(rankedItems, correctItems, new HashSet<Integer>(), n);
	  }

	  /**
	   * Compute the recall@N of a list of ranked items.
	   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	   * @param correctItems a collection of positive/correct item IDs
	   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	   * @param n the cutoff position in the list
	   * @return the recall@N for the given data
	   */
	  public static double recallAt(List<Integer> rankedItems,Collection<Integer> correctItems,Collection<Integer> ignoreItems,int n) {
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
	  public static int hitsAt(List<Integer> rankedItems, Collection<Integer> correctItems, Collection<Integer> ignoreItems,int n) {

		if (n < 1)
	      throw new IllegalArgumentException("n must be at least 1.");

	    int hitCount = 0;
	    
	    int leftOut  = 0;

	    for (int i = 0; i < rankedItems.size(); i++) {
	    
	      int itemId = rankedItems.get(i);
	      
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


