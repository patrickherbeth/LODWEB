package node;

import java.util.List;

public class ThreadPredictions implements Runnable {
	
	private List<NodePrediction> nodePredictions;

	public ThreadPredictions(List<NodePrediction> nodePredictions) {
		this.nodePredictions = nodePredictions;
	    Lodica.findRelationsAndSave(nodePredictions,true);
	}
	
	public void start() {
	      System.out.println("Threading predictions...");
	      Thread  t = new Thread(this);
	      t.start();
	   }	

	@Override
	public void run() {
		Lodica.findRelationsAndSave(nodePredictions,true);
	}

	public static void main(String[] args) {
		for (Node node : Lodica.getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(4)) {
			System.out.println("profile " + node.getURI() );
			Lodica.getDatabaseConnection().deleteIndirectLink(node.getURI());
			Lodica.getDatabaseConnection().deleteLink(node.getURI());
		}
		
		//Lodica.getDatabaseConnection().deleteLink(node.getURI());
		//Lodica.getDatabaseConnection().deleteLink(node.getURI());
		
		for (Node node : Lodica.getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(4)) {
			//ThreadPredictions  t4 = new ThreadPredictions(node.getURI());
			//t4.start();
		}		
		
	}
	
}
