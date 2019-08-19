package similarity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Tag;

public class Jaccard {

	public static double union(List<Tag> lista1, List<Tag> lista2) {
		Set<String> A = new HashSet<String>();
		Set<String> B = new HashSet<String>();
		
		double cont = 0;

		for (Tag termo1 : lista1) {
			A.add(termo1.getName());
		}

		for (Tag termo2 : lista2) {
			B.add(termo2.getName());
		}

		A.addAll(B); 
		
		// União
		
		Iterator<String> itr = A.iterator();

		 System.out.println("----- UNION ------");
		while (itr.hasNext()) {
			System.out.println(itr.next());
			cont++;

		}
		 System.out.println("Quantidade -> " + cont);
		 System.out.println("------------------");

		return cont;
	}
	
	
	
	public static double unionSumSemantic(List<Tag> lista1, List<Tag> lista2, double semanticValue) {
		Set<String> A = new HashSet<String>();
		Set<String> B = new HashSet<String>();
		
		double cont = 0;

		for (Tag termo1 : lista1) {
			A.add(termo1.getName());
		}

		for (Tag termo2 : lista2) {
			B.add(termo2.getName());
		}

		A.addAll(B); // União
		
		Iterator<String> itr = A.iterator();

		System.out.println("----- UNION ------");
		while (itr.hasNext()) {
			System.out.println(itr.next());
			cont++;

		}
		System.out.println("Quantidade -> " + cont);
		System.out.println("------------------");

		return cont + semanticValue;
	}
	
	

	public static double intersection(List<Tag> lista1, List<Tag> lista2) {
		Set<String> A = new HashSet<String>();
		Set<String> B = new HashSet<String>();
		
		double cont = 0;

		for (Tag termo1 : lista1) {
			A.add(termo1.getName());
		}

		for (Tag termo2 : lista2) {
			B.add(termo2.getName());
		}

		A.retainAll(B); // Intersecção
		Iterator<String> itr = A.iterator();

		System.out.println("----- INTERSEPTION ------");
		
		while (itr.hasNext()) {
			System.out.println(itr.next());
			cont++;

		}
		
		System.out.println("Quantidade -> " + cont);
		System.out.println("------------------");

		return cont;
	}

	public static double similarityJaccard(double union , double intersection ) {
		
		double result = (intersection / union);
		
	//	System.out.println("Result Similarity JACCARD de A e B: " + result);
	//	System.out.println("---------------------------------------------------------");
		
		return result;

	}
	
	
}