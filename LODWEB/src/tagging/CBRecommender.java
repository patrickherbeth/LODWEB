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

		init = System.currentTimeMillis();

		//Integer[] listUsers = { 11 };
		Integer[] listUsers = { 11, 96, 121, 129, 133, 190, 205, 208, 271, 279, 316, 318, 320, 342, 348, 359, 370, 395, 409, 451, 460, 469, 471, 482, 489, 505, 534, 540, 558, 570 };
		
		//Integer[] listUsers = { 11, 96, 121, 129, 133, 190, 205, 208, 271, 279 };   
		
		  //Integer[] listUsers = { 489 }; 

		
		
		
		for (int i = 0; i < listUsers.length; i++) {

			List<Cenario> TestSets = DBFunctions.selectCenario(listUsers[i]);
			List<Integer> listTestSet = new ArrayList<Integer>();
			List<Integer> listRelevants = new ArrayList<Integer>();
			
			
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println("USUÁRIO TESTADO -> " + listUsers[i]);
			System.out.println("-------------------------------------------------------------------------------------");
			
			DBFunctions.inrrelevantesFilmes(listUsers[i]);

			for (Cenario testSet : TestSets) {
				listTestSet.add(testSet.getId_filme());
				if (testSet.getRelevance().equals("relevant")) {
					listRelevants.add(testSet.getId_filme());
				}
			}

			TaggingFactory.createRecomedationSystem(TestSets, listUsers[i], listTestSet, listRelevants);
		}
	}
}
