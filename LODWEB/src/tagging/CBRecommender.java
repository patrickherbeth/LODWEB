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

		//Integer[] listUsers = {  };
		Integer[] listUsers = { 11 };

		for (int i = 0; i < listUsers.length; i++) {

			List<Cenario> TestSets = DBFunctions.selectCenario(listUsers[i]);
			List<Integer> listTestSet = new ArrayList<Integer>();
			List<Integer> listRelevants = new ArrayList<Integer>();

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
