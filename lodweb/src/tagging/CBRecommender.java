package tagging;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import database.DBFunctions;
import metric.PrecisionAndRecall;
import model.Cenario;
import node.NodeUtil;

public class CBRecommender {

	public static long init;
	public static long end;

	public static void main(String[] args) {
		DBFunctions dbfunctions = new DBFunctions();
		/*
		List<Integer> rankedItems = new LinkedList<Integer>();
		rankedItems.add(5);
		
		rankedItems.add(10);
		rankedItems.add(17);
		
		
	
//fazer um metodo para calcular p@n  r/q n é o número de documentos retornados
//		r é o número de documentos considerados relevantes e retornados até a posição n da lista ordenada
		
		
		// ap ta sendo calculado corretamente pelo AP

		Collection<Integer> correctItems = new LinkedList<Integer>();
		correctItems.add(1);
		correctItems.add(10);
		correctItems.add(11);
		correctItems.add(4);
		correctItems.add(2);
		correctItems.add(3);
		correctItems.add(17);
		
		//System.out.println(" \n -------------- PRECISION " + type + " ---------------- \n");
		double precsion = PrecisionAndRecall.AP(rankedItems, correctItems,null);
		System.out.println("VALOR DO PRECISION " +  " : " + precsion + "\n");
*/
		/*
		 * Quantidade de usuarios
		 */

		init = System.currentTimeMillis();
		//CreateScenario.createScenario();
				
		Integer[] listUsers = { 11, 96, 121, 129, 133, 190, 205, 208, 271, 279, 316, 318, 320, 342, 348, 359, 370,
				395, 409, 451, 460, 469, 471, 482, 489, 500, 505, 534, 540, 558};
		
	/*	for(int i=0; i < listUsers.length; i++) {
			DBFunctions.inrrelevantesFilmes(listUsers[i]);
			
		}*/
		
		
		for(int i=0; i < listUsers.length; i++) {
		
			List<Cenario> TestSets = DBFunctions.selectCenario(listUsers[i]);
			List <Integer> listTestSet= new ArrayList<Integer>();
			List <Integer> listRelevants= new ArrayList<Integer>();
		
			
			for (Cenario testSet : TestSets) {
					listTestSet.add(testSet.getId_filme());
					if (testSet.getRelevance().equals("relevant")){
						listRelevants.add(testSet.getId_filme());
					}
			}
			
			TaggingFactory.createRecomedationSystem(TestSets, listUsers[i], listTestSet, listRelevants);
		}
		
		String[] listAlgorithm= {"LDSD","WUP","COSINE","JACCARD","LDSD+JACCARD","WUP+JACCARD"};
		
		for (int i = 0; i < listAlgorithm.length; i++) {
			double map3= dbfunctions.calculeMap("ap3",listAlgorithm[i]);
			double map5= dbfunctions.calculeMap("ap5",listAlgorithm[i]);
			double map10= dbfunctions.calculeMap("ap10",listAlgorithm[i]);
			double p_3= dbfunctions.calculeMap("p3",listAlgorithm[i]);
			double p_5= dbfunctions.calculeMap("p5",listAlgorithm[i]);
			double p_10= dbfunctions.calculeMap("p10",listAlgorithm[i]);
			
			dbfunctions.saveMapPrecison(listAlgorithm[i], map3, map5, map10, p_3, p_5, p_10);
		}
	
	}
	

}
