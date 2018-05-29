package metric;

import java.util.Collections;
import java.util.List;

import node.IConstants;
import node.Lodica;
import node.Node;
import node.NodePrediction;
import node.NodeUtil;


	public class Accuracy {
		
		
		public static void main(String[] args) {
			calculateAccuracy();
			//test();
		}

		/**
		 * 
		 */
		private static void calculateAccuracy() {
			//MAP https://www.youtube.com/watch?v=pM6DJ0ZZee0
			
			List<NodePrediction> predictions = Lodica.getDatabaseConnection().getPredictionsBySeed("http://dbpedia.org/resource/Adam_Lambert",1);
			NodeUtil.updateLabelsAfterClassification(predictions);
			Collections.sort(predictions);

			List<Node> rankedNodes = NodeUtil.getNodesFromPredictionList(predictions);
			
			List<Node> correctRankNodes = rankedNodes;
			
			NodeUtil.printNodes(rankedNodes);
					
		    NodeUtil.labelNodes(correctRankNodes, IConstants.NO_LABEL);
		    
		    NodeUtil.print();
		    
		    NodeUtil.printNodes(correctRankNodes);

			double accuracy = Accuracy.accuracy(rankedNodes, correctRankNodes);

			NodeUtil.print(accuracy);

			double accuracyAt5 = Accuracy.accuracyAt(rankedNodes, correctRankNodes, 5);
			double accuracyAt10 = Accuracy.accuracyAt(rankedNodes, correctRankNodes, 10);
			double accuracyAt20 = Accuracy.accuracyAt(rankedNodes, correctRankNodes, 20);
			NodeUtil.print(accuracyAt5);
			NodeUtil.print(accuracyAt10);
			NodeUtil.print(accuracyAt20);
			
		}			
		
		
	  /**
	 * @param rankedItems
	 * @param correctItems
	 * @param n
	 * @return
	 */
	public static double accuracyAt(List<Node> rankedItems, List<Node> correctItems, int n) {
		if (rankedItems.size()!=correctItems.size()) {
			throw new IllegalArgumentException("rankedItems and correctItems must have same size");
		}

		if (n < 1)
			throw new IllegalArgumentException("n must be at least 1.");

		int tp = 0;
		int tn = 0;

		for (int i = 0; i < rankedItems.size(); i++) {

			Node rankedId  = rankedItems.get(i);
			Node correctId = correctItems.get(i);

			if (rankedId.getURI().equals(correctId.getURI()) && rankedId.getLabel().equals(correctId.getLabel()) && correctId.getLabel().equals(IConstants.LIKE) ){
				tp++;
			}
			
			if (rankedId.getURI().equals(correctId.getURI()) && rankedId.getLabel().equals(correctId.getLabel()) && correctId.getLabel().equals(IConstants.NO_LABEL) ){
				tn++;
			}
			if (i < n ){
				break;
			}
		}
		return (tp+tn)/correctItems.size();
	}	
	  
	  
	  /**
	 * @param rankedItems
	 * @param correctItems
	 * @return
	 */
	public static double accuracy(List<Node> rankedItems, List<Node> correctItems) {
		if (rankedItems.size()!=correctItems.size()) {
			throw new IllegalArgumentException("rankedItems and correctItems must have same size");
		}


		int tp = 0;
		int tn = 0;

		for (int i = 0; i < rankedItems.size(); i++) {

			Node rankedId  = rankedItems.get(i);
			Node correctId = correctItems.get(i);

			if (rankedId.getURI().equals(correctId.getURI()) && rankedId.getLabel().equals(correctId.getLabel()) && correctId.getLabel().equals(IConstants.LIKE) ){
				tp++;
			}
			
			if (rankedId.getURI().equals(correctId.getURI()) && rankedId.getLabel().equals(correctId.getLabel()) && correctId.getLabel().equals(IConstants.NO_LABEL) ){
				tn++;
			}
		}
		return (tp+tn)/correctItems.size();
	}	  
	  
	  
	  
	  
}


