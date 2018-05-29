package metric;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import node.NodeUtil;

/**
 * Normalized discounted cumulative gain (NDCG) of a list of ranked items.
 * See http://recsyswiki.com/wiki/Discounted_Cumulative_Gain
 * @version 2.03
 */
public class NDCGString {
	
	
	public static void main(String[] args) {
		
		System.out.println("ooooo");

		
		List<String> rankedItems = new LinkedList<String>();
		rankedItems.add("casa"+1);
		rankedItems.add("carro"+34);
		rankedItems.add(""+4);
		rankedItems.add(""+3);

		
		Collection<String> correctItems = new LinkedList<String>();
		correctItems.add("casa"+1);
		correctItems.add("carro"+2);
		correctItems.add("vvv"+3);
		correctItems.add("vvv"+60);
		
		NodeUtil.print(calculateNDCG(rankedItems, correctItems, null));
		
	}

  /**
   * Compute the normalized discounted cumulative gain (NDCG) of a list of ranked items.
   * 
   * See http://recsyswiki.com/wiki/Discounted_Cumulative_Gain
   * 
   * @param rankedItems a list of ranked item IDs, the highest-ranking item first
   * @param correctItems a collection of positive/correct item IDs
   * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
   * @return the NDCG for the given data
   */
	public static double calculateNDCG(List<String> rankedItems, Collection<String> correctItems, Collection<String> ignoreItems) {

		if (ignoreItems == null)
			ignoreItems = new HashSet<String>();

		double dcg = 0;
		
		double idcg = computeIDCG(correctItems.size());
		
		int left_out = 0;

		for (int i = 0; i < rankedItems.size(); i++) {
			
			String item_id = rankedItems.get(i);
		
			if (ignoreItems.contains(item_id)) {
				left_out++;
				continue;
			}

			if (!correctItems.contains(item_id))
				continue;

			// compute NDCG part
			int rank = i + 1 - left_out;
			
			dcg += Math.log(2) / Math.log(rank + 1);

		}

		return dcg / idcg;
	}

	  /**
	   * Computes the ideal DCG given the number of positive items..
	   * 
	   * See http://recsyswiki.com/wiki/Discounted_Cumulative_Gain
	   * 
	   * @return the ideal DCG
	   * <param name='n'>the number of positive items
	   */
	  static double computeIDCG(int n)
	  {
	    double idcg = 0;
	    for (int i = 0; i < n; i++)
	      idcg += Math.log(2) / Math.log(i + 2);
	    return idcg;
	  }
	}
	

