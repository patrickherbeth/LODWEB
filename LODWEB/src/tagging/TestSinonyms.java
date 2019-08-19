package tagging;

import java.util.ArrayList;
import net.didion.jwnl.JWNLException;
import node.SparqlWalk;
import wordnet.Sinonyms;

public class TestSinonyms {

	public static long init;
	public static long end;

	public static void main(String[] args) throws JWNLException, InterruptedException {

		// tired feliz bad angry big

		//String[] userModel = { "Tired", "Bad", "Angry", "Big", "Cut"};
		String[] userModel = { "tired", "bad", "angry", "big", "cut", "pierced", "volumed", "big",  "tired", "drained" };
		String[] testModel = { "Tired", "Angry", "Big", "Pierced"};
		
		
		/**
		 * Sinonimos
		 */
		
		  ArrayList<String> tagAndSinonymes = new ArrayList<String>();
		  
		  for (int i = 0; i < userModel.length; i++) {
		  tagAndSinonymes.add(userModel[i]);
		  
		  for (String c : Sinonyms.getSinonymous(userModel[i])) {
		  tagAndSinonymes.add(c); } }
		  
		  String[] arr = tagAndSinonymes.toArray(new String[tagAndSinonymes.size()]);
				  
		  for (int j = 0; j < arr.length; j++) { System.out.println(arr[j]); }
		 

		/**
		 * Polissemia
		 */
		
		ArrayList<String> listTestModel = new ArrayList<String>();
		for (int i = 0; i < testModel.length; i++) {
			listTestModel.add(testModel[i]);
		}
				
		ArrayList<String> listUserModel = new ArrayList<String>();
		for (int i = 0; i < userModel.length; i++) {
			listUserModel.add(userModel[i]);
		}
		
		//for (String testTag : listTestModel) {
		//System.out.println(testTag);
		//}	
		
		//for (String userTag : listUserModel) {
			//System.out.println(userTag);
		//}
		
		double count = 0;
		double result = 0;
		
		for (String user : listUserModel) {
			for (String test : listTestModel) {
				if (user.equals(test)) {
		//			System.out.println("Encontrado categoria igual");
					count = count + 1;
					result = CountListResultSet(user, test) + result;
				}
			}
		}
		
		System.out.println("RESULTADO DE TODAS PALAVRAS POLISSEMICA -> " + result);

	}

	

	public static double CountListResultSet(String userModel, String testModel) {
	
		double count = 0;
		
		userModel = testModel = "Star";
		
		for (String userSet : SparqlWalk.getLiteralByUri("http://dbpedia.org/resource/" + userModel)) {
		
			for (String testSet : SparqlWalk.getLiteralByUri("http://dbpedia.org/resource/" + testModel)) {
				
				if (userSet.equals(testSet)) {
					
					//System.out.println("ENCONTRADO RDF:TYPE -> User Model: " + userSet + "|" + "Test Model: " + testSet + " = " + count);
					count = count + 1;
				}
			}
		}
		
		if(count > 0) {
			System.out.println("=======================================================================================");
			System.out.println("Resultado da quantidade de categorias encontradas-> " + count);
			System.out.println("=======================================================================================");
		}
		
		return count;

	}
	
	
	public static double CountListResultSetCategorySubject(String userModel, String testModel) {

		double count = 0;
			
		for (String userSet : SparqlWalk.getLiteralSubjectByUri("http://dbpedia.org/resource/" + userModel)) {
		
			for (String testSet : SparqlWalk.getLiteralSubjectByUri("http://dbpedia.org/resource/" + testModel)) {
				
				if (userSet.equals(testSet)) {
					
				//	System.out.println("ENCONTRADO DCT:SUBJECT -> User Model: " + userSet + "|" + "Test Model: " + testSet + " = " + count);
					count = count + 1;
				}
			}
		}
		
		if(count > 0) {
			System.out.println("=======================================================================================");
			System.out.println("Resultado da quantidade de categorias encontradas-> " + count);
			System.out.println("=======================================================================================");
		}
		
		return count;
	}
}
