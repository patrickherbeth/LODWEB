package node;

public class ThreadLod implements Runnable {
	
	private String uri;

	public ThreadLod(String uri) {
		this.uri = uri;
	    //Lodica.findRelationsAndSave(uri,true);
	}
	
	public void start () {

	      Thread  t = new Thread(this, uri);
	      t.start ();
	   }	

	@Override
	public void run() {
		Lodica.findRelationsAndSave(uri,true);
		
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
			ThreadLod  t4 = new ThreadLod(node.getURI());
			t4.start();
		}		
		
	}
	
}
