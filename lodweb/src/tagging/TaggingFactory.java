package tagging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import database.DBFunctions;
import metric.PrecisionAndRecall;
import model.Cenario;
import model.Ratings;
import model.Tag;
import node.Classifier;
import node.IConstants;
import node.Lodica;
import util.StringUtilsNode;
import util.strategy.ChooseCosine;
import util.strategy.ChooseMatrix;
import util.strategy.ChooseWUP;
import util.strategy.ChooseLDSD;
import util.strategy.ChooseWUP;
import util.strategy.Similarity;

public class TaggingFactory {

	/*
	 * Faz a comparação para verificar se as Tags são equals
	 */
	public static List<Tag> compareTag(List<Tag> listTag1, List<Tag> listTag2) {
		ArrayList<Tag> listResult = new ArrayList<Tag>();

		for (Tag item1 : listTag1) {
			item1.getName();
			for (Tag item2 : listTag2) {
				item2.getName();
				
				if (item1.getName().equals(item2.getName())) {
					listResult.add(item1);
				}
			}
		}

		return listResult;
	}

	
	/*
	 *  Cria uma lista de Tags de um array de string
	 */
	public static List<Tag> loadTagArray(String[] tagArray) {
		List<Tag> listTag = new ArrayList<Tag>();	
		for (int i = 0; i < tagArray.length; i++) {
			// System.out.println("TextB " + (i+1) + " = " + tagArray[i] );
			listTag.add(new Tag(tagArray[i]));
			
		}
		
		return listTag;
	}
	
	public static String retornaString(String[] modelArray) {
		String palavra = "";
		for (int i = 0; i < modelArray.length; i++) {
			palavra = modelArray[i] + " " + palavra;
		}
		
		return palavra;
	}
	
	/*
	 * concatena Tags de filmes
	 */
	public static String loadNameTagArray(List<Integer> filmes) {
		String text = null;
		
		for(int filme : filmes) {
			text = text + DBFunctions.getNameOfTag(filme);
		}
		
		return text;
	}
	
	public static String listNameFilmString(List<Integer> filmes) {
		String text = "";
		
		for(int filme : filmes) {
			text = text + DBFunctions.findNameOfFilm(filme) + ",";
		}
		
		return text;
	}
	
	
	/*
	 * Converte Lista de inteiros em uma string
	 */
	public static String listNameTag(List<Integer> filmes) {
		String text = "";
		
		for(int filme : filmes) {
			text = text + DBFunctions.getNameOfTag(filme) + ",";
		}
		
		return text;
	}

	/*
	 * Converte uma string em um array
	 */
	public static String[] inputDados(String text) {
		return text.split(" ");
	}

	/*
	 * Concatena Tags da lista de Tags
	 */
	public static String concatenaTagText(List<Tag> listTag) {
		String valueTag = "";
		
		for (Tag tag : listTag) {
			if(listTag.isEmpty()){
				break;
			}
			
			valueTag = valueTag + " " + tag.getName();
		}

		System.out.println(" LISTA -> " + valueTag);

		return valueTag;
	}
	
	/*
	 * Cria um array de string de uma lista de Tags
	 */
	public static String[] convertTypeTagByArray(List<Tag> nameOfTagsFilms) {
		String [] tags = new String[nameOfTagsFilms.size()];
		int cont = 0;
		
		for (Tag tag : nameOfTagsFilms) {
			tags[cont] = tag.getName();
			cont += 1;
			System.out.println("Posição -> " + cont + " Tag -> " + tag.getName());
		}

		return tags;
	}
	
	/*
	 * Contador para retornar o tamanha da lista de tags
	 */
	public static int countAllItem(List<Tag> list) {

		return list.size();
	}

	/*
	 * Calcula a similaridade LDSD
	 */
	public static double[] calculeLDSD(List<Tag> userModel, List<Tag> testeSet, int UserId, int uri2) {
		Map<String, Double> mapResultLDSDweighted = new TreeMap<String, Double>();
		int cont = 0;
		double resultSumSemantic = 0;
		DBFunctions dbFunctions = new DBFunctions();
		double valueLDSD = 0;

		for (Tag item1 : userModel) {
			for (Tag item2 : testeSet) {
				cont = cont++;
			
				String nameTag1 = StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(item1.getName());
				String nameTag2 = StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(item2.getName());
				try {
					valueLDSD = Classifier.calculateSemanticDistance(nameTag1, nameTag2, IConstants.LDSD_JACCARD, UserId);
				} catch (Exception e) {
					calculeLDSD( userModel, testeSet,  UserId,  uri2);
				}
				
				
				System.out.println(" LDSD TAG 1 -> " + nameTag1 + " TAG 2 -> " + nameTag2 + " = " + valueLDSD);
				
				if (valueLDSD < 1.0 && valueLDSD > 0.0) {
					System.out.println("-------------------------------------------");
					System.out.println("Entrou para o calculo : TAG 1 -> " + nameTag1 + " TAG 2 -> " + nameTag2 + " = " + valueLDSD);
					System.out.println("-------------------------------------------");
					mapResultLDSDweighted.put(nameTag1 + nameTag2, valueLDSD);
					
				}
			}
		}

		System.out.println("\n ====================== ARRAYLIST DE ELEMENTOS QUE SERÃO SOMADOS ==================== ");
		System.out.println(mapResultLDSDweighted + " \n");
		System.out.println("\n ================================== RESULTADOS SALVANDO. ====================================== ");

		// Resultado da soma de todos as tags que existe similardade
		resultSumSemantic = sumSemantic(mapResultLDSDweighted);

		if (resultSumSemantic != resultSumSemantic) resultSumSemantic = 0;
		
		double score= resultSumSemantic / mapResultLDSDweighted.size();
		
		return new double[] { resultSumSemantic, score };
		
	}

	/*
	 * Escolhe qual calculo de similaridade irá calcular
	 */
	public static void calculeSimilarityBetweenUserModelAndTestSet(List<Cenario> cenarios, Cenario cenario, int userId,String type) {
		Similarity similarity;
			
		switch (type) {
		case "LDSD":
			similarity = new ChooseLDSD();
			similarity.choiceOfSimilarity(cenarios,cenario, userId , 5);
			break;
		case "WUP":
			similarity = new ChooseWUP();
			similarity.choiceOfSimilarity(cenarios,cenario, userId , 5);
			break;  
		case "JACCARD|JACCARD+LDSD|JACCARD+WUP":
			similarity = new ChooseMatrix();
			similarity.choiceOfSimilarity(cenarios,cenario, userId , 5);
			break;
		case "COSINE":
			similarity = new ChooseCosine();
			similarity.choiceOfSimilarity(cenarios,cenario, userId ,5);
			break;
		default:
			throw new RuntimeException("Strategy not found.");
		}
		
	}
	
    /*
     * Soma todos resultados similaridades semnaticas encontrada entre duas Tag
     */
	public static double sumSemantic(Map<String, Double> mapResultLDSDweighted) {
		double total = 0;

		for (Object key : mapResultLDSDweighted.keySet()) {
			total = total + mapResultLDSDweighted.get(key);
		}

		return total;
	}
	
	
	/*
	 * Remove tags iguais do mapa
	 */
	public static void removeEqual(Map<String, Double> map, Tag item1, Tag item2) {

		for (Object key : map.keySet()) {
			if (key.equals(item2.getName() + item1.getName())) {
				map.remove(key);
			}
		}
	}
	
	/*
	 * Ordena o testSet que vai ser utilizado pelo algoritimo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Ratings> orderTestSetByRating(List<Ratings> testSet) {
		
		Collections.sort(testSet, new Comparator() {
            public int compare(Object o1, Object o2) {
                Ratings p1 = (Ratings) o1;
                Ratings p2 = (Ratings) o2;
                return p1.getRating() > p2.getRating() ? -1 : (p1.getRating() > p2.getRating() ? +1 : 0);
            }
        });
		
		return testSet;
		
	}
	
	/*
	 * Faz o calculo proposto para melhorar a precição da recomendação
	 */
	public static double calculeSimilarityAndJaccard(double  union, double  intersection, double sumSimilarity) {

		double sumSimilarityAndIntersertion = sumSimilarity + intersection;
		double resultCalcule = 0;
									
				resultCalcule = sumSimilarityAndIntersertion / union;		
					
				System.out.println(" ************ DENTRO DO MÉTODO ************");
				System.out.println("VALOR DA UNIÃO -> " + union);
				System.out.println("VALOR DA INTERSEÇÃO -> " + intersection);
				System.out.println("VALOR DA SOMA DA SIMILARIDADE -> " + sumSimilarity);
				System.out.println("VALOR DA SOMA DA SIMILARIDADE COM INTERSEÇÃO -> " + sumSimilarityAndIntersertion);
				System.out.println("VALOR DA SOMA DA SIMILARIDADE COM INTERSEÇÃO DIVIDIDO PELA UNIÃO-> " + resultCalcule);
		
			return resultCalcule;
	}
	
	/*
	 * Calcula a similaridade e salva no banco de dados e devolve uma recomendações de filmes
	 */
	public static void createRecomedationSystem(List<Cenario> cenarios, int userId, List<Integer> listTestuser, List<Integer> listRelevants) {
		
		DBFunctions dbFunctions = new DBFunctions();
		Lodica.userId = userId;
						
				for (Cenario cenario : cenarios) {
			    	/*TaggingFactory.calculeSimilarityBetweenUserModelAndTestSet(cenarios, cenario,  userId, "LDSD");
			    	TaggingFactory.calculeSimilarityBetweenUserModelAndTestSet(cenarios, cenario,  userId, "WUP");
					TaggingFactory.calculeSimilarityBetweenUserModelAndTestSet(cenarios, cenario,  userId, "COSINE");
				  	TaggingFactory.calculeSimilarityBetweenUserModelAndTestSet(cenarios, cenario,  userId, "JACCARD|JACCARD+LDSD|JACCARD+WUP");
				  	*/
					/* 
					 * Exibe e retorna a lista com as simiaridades encontrada
					 */
				    // List<Integer> cosineRankedList = dbFunctions.resultRecommendation(userId, "COSINE");
				  	
				  	 
				  	 
				  	 // pegaListaParaAP
				     List<Integer> cosineRankedList= dbFunctions.listForAp(userId, "COSINE");
				  	 List<Integer> jaccardRankedList = dbFunctions.listForAp(userId, "JACCARD");
				  	 List<Integer> WUPRankedList = dbFunctions.listForAp(userId, "WUP");
				     List<Integer> jaccardAndWUPRankedList = dbFunctions.listForAp(userId, "WUP+JACCARD");
				  	 List<Integer> LDSDRankedList = dbFunctions.listForAp(userId, "LDSD");
				  	 List<Integer> jaccardLDSDRankedList = dbFunctions.listForAp(userId, "LDSD+JACCARD");
								
					/*
					 *  Calcula a Precisição, AP e MAP
					 */
				  	
					calculeResultPrecisionAndMAP(cenario.getTags_user(), cosineRankedList, listRelevants, userId, "COSINE");
				    calculeResultPrecisionAndMAP(cenario.getTags_user(), jaccardRankedList, listRelevants, userId, "JACCARD");
				  	calculeResultPrecisionAndMAP(cenario.getTags_user(), WUPRankedList, listRelevants, userId, "WUP");
				  	calculeResultPrecisionAndMAP(cenario.getTags_user(), jaccardAndWUPRankedList, listRelevants, userId, "WUP+JACCARD");
				  	calculeResultPrecisionAndMAP(cenario.getTags_user(), LDSDRankedList, listRelevants, userId, "LDSD");
				    calculeResultPrecisionAndMAP(cenario.getTags_user(), jaccardLDSDRankedList, listRelevants, userId, "LDSD+JACCARD"); 
					
					break;

				}
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
		double AP = (totalRetrived.size()/limit);
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
	 * Converte uma lista de Ratings para uma lista de Integer
	 */
	public static List<Integer> createTestSetList(List<Ratings> testSetList) {

		List<Integer> filmRatingList = new ArrayList<Integer>();

		for (Ratings rating : testSetList) {
			filmRatingList.add(rating.getIddocument());
		}
		return filmRatingList;
	}
	
	/*
	 * Calcula o Precision e o MAP e salva em  resultados
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
	
	public static void calculeResultPrecisionAndMAP(String userModel, List<Integer> rankedList, List<Integer> relevants, int userId, String type) {
		DBFunctions dbFunctions = new DBFunctions();
		 List<Integer>listaAP3 = new ArrayList<Integer>();
		 List<Integer>listaAP5 = new ArrayList<Integer>();
		 List<Integer>listaAP10 = new ArrayList<Integer>();
		 
		if (rankedList == null || rankedList.size() == 0){
			dbFunctions.saveResult(userId, userModel, relevants, 0, 0, 0, 0, 0, type, 0,0,0);
			return;
		}
				
		System.out.println(" \n -------------- PRECISION " + type + " ---------------- \n");
		double precsion = PrecisionAndRecall.precisionAt(rankedList, relevants,rankedList.size());
		System.out.println("VALOR DO PRECISION " + type + " : " + precsion + "\n");
		
		double cont3=0, cont5 =0, cont10 =0;
		for (int i =0; i< rankedList.size(); i++) {
			if (DBFunctions.isRelevant(userId, rankedList.get(i))){
				if(i<3){
					
					cont3++;
				}
				if(i<5){
					cont5++;
				}
				if(i<10){
					cont10++;
				}
			}
			
		}
		double p3 = cont3/3; 
		System.out.println("VALOR DO p@3: " + p3 + "\n");
		double p5 = cont5/5;
		System.out.println("VALOR DO p@5: " + p5 + "\n");
		double p10 = cont10/10;
		System.out.println("VALOR DO p@10: "	+ p10 + "\n");
		
		listaAP3 = getFirstNelementsList(rankedList,3);

		listaAP5 = getFirstNelementsList(rankedList,5);
		
		listaAP10 = getFirstNelementsList(rankedList,10);
		
		double AP3 = PrecisionAndRecall.AP(listaAP3, relevants,null);
		double AP5 = PrecisionAndRecall.AP(listaAP5, relevants,null);
		double AP10 = PrecisionAndRecall.AP(listaAP10, relevants,null);
		
		double media = calculeMAP(p3, p5, p10, type);
		System.out.println("-------- O VALOR DO MAP (ERRADO E : )" + media);
		
		double map = calculeMAP(AP3, AP5, AP10, type);
		System.out.println("-------- O VALOR DO MAP (CORRETO E : )" + map);

		
		dbFunctions.saveResult(userId, userModel, relevants, p3, p5, p10, precsion, map, type, AP3,AP5,AP10);
		
		System.out.println("-------- SALVO PARA O USUÁRIO " + userId);

	}
}
