package metric;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

		/**
		 * Area under the ROC curve (AUC) of a list of ranked items.
		 * See http://recsyswiki.com/wiki/Area_Under_the_ROC_Curve
		 * @version 2.03
		 */
		public class ReciprocalRank {
			// Prevent instantiation.
			  private ReciprocalRank() {}

			  /**
			   * Compute the reciprocal rank of a list of ranked items.	
			   * 
			   * See http://en.wikipedia.org/wiki/Mean_reciprocal_rank
			   * 
			   * @param ranked_items a list of ranked item IDs, the highest-ranking item first
			   * @param correct_items a collection of positive/correct item IDs
			   * @param ignore_items a collection of item IDs which should be ignored for the evaluation
			   * @return the mean reciprocal rank for the given data
			   */
			  public static double compute(List<Integer> ranked_items, Collection<Integer> correct_items, Collection<Integer> ignore_items) {
			    if (ignore_items == null)
			      ignore_items = new HashSet<Integer>();

			    int pos = 0;

			    for (int item_id : ranked_items) {
			      if (ignore_items.contains(item_id))
			        continue;

			      if (correct_items.contains(ranked_items.get(pos++)))
			        return (double) 1 / (pos);
			    }

			    return 0;
			  }
		 
		
		
		}


