package movietagging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import database.DBFunctions;
import metric.PrecisionAndRecall;

public class Evaluation {
	
	public static void main(String[]args) {
		List<Integer> relevants = new ArrayList<Integer>();
		List<Integer> listaAP3 = new ArrayList<Integer>();
		List<Integer> listaAP5 = new ArrayList<Integer>();
		List<Integer> listaAP10 = new ArrayList<Integer>();
		
		double AP3 = PrecisionAndRecall.AP(listaAP3, relevants, null);
		double AP5 = PrecisionAndRecall.AP(listaAP5, relevants, null);
		double AP10 = PrecisionAndRecall.AP(listaAP10, relevants, null);
		
		calculeMAP(AP3, AP5, AP10, "PS");
		
		
	}
	
	
	/*
	 * Calcula a AP(Average Precision)
	 */
	public static double calculeAP(List<Integer> rankedList, String similarity, int limit) {

		List<Integer> totalRetrived = new ArrayList<Integer>(); 
		int cont = 0;
		
		for (int ranked: rankedList) {
			cont++;
			
			if(cont <= limit) {
				totalRetrived.add(ranked);
			}
		}

	//	double AP = PrecisionAndRecall.AP(listRankedByLimit, testList, new ArrayList<Integer>());
		double AP = (totalRetrived.size() / limit);
		System.out.println("VALOR AP: " + similarity + ": " + AP + " Qtd -> " + totalRetrived.size());

		return AP;
	}
	
	/*
	 * Calcula Mean Average Precision
	 */
	public static double calculeMAP(double ap3, double ap5, double ap10, String nameSimilarity) {

		double map = (ap3 + ap5 + ap10) / 3;

		System.out.println("\n VALOR MAP " + nameSimilarity + ": " + map);
		
		return map;

	}

	/*
	 * Calcula o resultado da Precision and MAP e salva o Resultado
	 */
	public static void calculeResultPrecisionAndMAP(String userModel, List<Integer> rankedList, List<Integer> relevants, int userId, String type) {
		DBFunctions dbFunctions = new DBFunctions();
		List<Integer> listaAP3 = new ArrayList<Integer>();
		List<Integer> listaAP5 = new ArrayList<Integer>();
		List<Integer> listaAP10 = new ArrayList<Integer>();
		double p10, p20, p30, AP3, AP5, AP10, media, map;

		if (rankedList == null || rankedList.size() == 0) {
			dbFunctions.saveResult(userId, userModel, relevants, 0, 0, 0, 0, 0, type, 0, 0, 0);
			return;
		}

		System.out.println(" \n -------------- PRECISION " + type + " ---------------- \n");
		double precsion = PrecisionAndRecall.precisionAt(rankedList, relevants, rankedList.size());
		System.out.println("VALOR DO PRECISION " + type + " : " + precsion + "\n");

		double cont3 = 0, cont5 = 0, cont10 = 0;
		for (int i = 0; i < rankedList.size(); i++) {
			if (DBFunctions.isRelevant(userId, rankedList.get(i))) {
				if (i < 3) {
					cont3++;
				}
				if (i < 5) {
					cont5++;
				}
				if (i < 10) {
					cont10++;
				}
			}
		}

		p10 = cont3 / 3;
		p20 = cont5 / 5;
		p30 = cont10 / 10;
		
		listaAP3 = getFirstNelementsList(rankedList, 3);
		listaAP5 = getFirstNelementsList(rankedList, 5);
		listaAP10 = getFirstNelementsList(rankedList, 10);

		AP3 = PrecisionAndRecall.AP(listaAP3, relevants, null);
		AP5 = PrecisionAndRecall.AP(listaAP5, relevants, null);
		AP10 = PrecisionAndRecall.AP(listaAP10, relevants, null);

		media = calculeMAP(p10, p20, p30, type);
		map = calculeMAP(AP3, AP5, AP10, type);
		
		System.out.println("VALOR DO p@3: " + p10 + "\n");
		System.out.println("VALOR DO p@5: " + p20 + "\n");
		System.out.println("VALOR DO p@10: " + p30 + "\n");

		System.out.println("-------- O VALOR DO MAP (ERRADO E : )" + media);
		System.out.println("-------- O VALOR DO MAP (CORRETO E : )" + map);
		System.out.println("-------- SALVO PARA O USUÁRIO " + userId);

		dbFunctions.saveResult(userId, userModel, relevants, p10, p20, p30, precsion, map, type, AP3, AP5, AP10);
	}
	
	
	/*
	 * Seleciona os elementos da lista
	 */
	public static List<Integer> getFirstNelementsList(List<Integer> list, int amountElements){
		 List<Integer>nElements = new ArrayList<Integer>();
		 
		 if (list.size() < amountElements){
			 return list;
		 }
		 for (int i = 0; i <amountElements; i++) {
			 nElements.add(list.get(i));
		 }
		 
		return nElements;
	}
	
	public void saveMapPrecison(String type, double map_3, double map_5, double map_10, double p_3, double p_5,
			double p_10) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		// String userModel = TaggingFactory.listNameFilmString(userModelList);

		try {

			try {
				String query = "INSERT INTO `lod`.`map_precision` (`algorithm`, `map_3`,  `map_5`, `map_10`,`p_3`,`p_5`,`p_10`) VALUES (?, ?, ?, ? ,? ,? ,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, type);
				ps.setDouble(2, map_3);
				ps.setDouble(3, map_5);
				ps.setDouble(4, map_10);
				ps.setDouble(5, p_3);
				ps.setDouble(6, p_5);
				ps.setDouble(7, p_10);
				ps.execute();
				ps.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
