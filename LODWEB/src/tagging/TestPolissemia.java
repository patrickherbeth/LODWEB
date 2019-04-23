package tagging;

import node.SparqlWalk;

public class TestPolissemia {
	
	public static void Main(String []args) {
		
		String[] userModel = { "tired", "bad", "angry", "big", "cut", "pierced", "volumed", "big",  "tired", "drained" };
		String[] testModel = { "drained", "umbrageous", "drained", "umbrageous", "cut", "perforated", "rangy" };
		
		int value = SparqlWalk.getResoureType("home");
		System.out.println(value);
		
	}
}
