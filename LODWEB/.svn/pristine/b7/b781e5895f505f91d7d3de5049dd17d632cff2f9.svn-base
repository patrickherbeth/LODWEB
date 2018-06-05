package metric;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import node.NodeUtil;

/**
 * Area under the ROC curve (AUC) of a list of ranked items. See
 * http://recsyswiki.com/wiki/Area_Under_the_ROOC_Curve
 * 
 * @version 2.03
 */
public class AUCString {
	
	
	public static void main(String[] args) {
		
		List<String> rankedItems = new LinkedList<String>();
		rankedItems.add(""+1);
		rankedItems.add(""+34);
		rankedItems.add(""+4);
		rankedItems.add(""+3);
		rankedItems.add(""+345);
		rankedItems.add(""+433);
		rankedItems.add(""+3346);
		

		
		Collection<String> correctItems = new LinkedList<String>();
		correctItems.add(""+1);
		
		NodeUtil.print(compute(rankedItems, correctItems, null));
		
	}
	

	// Prevent instantiation.
	private AUCString() {
	}

	/**
	 * Compute the area under the ROC curve (AUC) of a list of ranked items.
	 * 
	 * See http://recsyswiki.com/wiki/Area_Under_the_ROC_Curve
	 * 
	 * @param rankedItems a list of ranked item IDs, the highest-ranking item first
	 * @param correctItems a collection of positive/correct item IDs
	 * @param ignoreItems a collection of item IDs which should be ignored for the evaluation
	 * @return the AUC for the given data
	 */
	public static double compute(List<String> rankedItems, Collection<String> correctItems, Collection<String> ignoreItems) {

		if (ignoreItems == null)
			ignoreItems = new HashSet<String>();

		Set<String> correctItemsIntersection = new HashSet<String>(ignoreItems);
		correctItemsIntersection.retainAll(correctItems);
		int numCorrectItems = correctItems.size() - correctItemsIntersection.size();

		Set<String> rankedItemsIntersection = new HashSet<String>(ignoreItems);
		rankedItemsIntersection.retainAll(rankedItems);
		int numEvalItems = rankedItems.size() - rankedItemsIntersection.size();

		int numEvalPais = (numEvalItems - numCorrectItems) * numCorrectItems;
		if (numEvalPais < 0)
			throw new IllegalArgumentException("correct items cannot be larger than ranked items");

		if (numEvalPais == 0)
			return 0.5;

		int numCorrectPairs = 0;
		int hitCount = 0;
		for (String itemId : rankedItems) {
			if (ignoreItems.contains(itemId))
				continue;

			if (!correctItems.contains(itemId))
				numCorrectPairs += hitCount;
			else
				hitCount++;
		}

		return (double) numCorrectPairs / numEvalPais;
	}

}
