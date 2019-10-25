package tagging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.sun.faces.facelets.tag.jstl.core.ForEachHandler;

import database.DBFunctions;
import metric.PrecisionAndRecall;
import model.Cenario;
import model.Document;
import model.Ratings;
import model.Tag;
import net.didion.jwnl.JWNLException;
import node.Classifier;
import node.IConstants;
import node.Lodica;
import util.StringUtilsNode;
import util.strategy.ChooseCosine;
import util.strategy.ChooseJSPolissemia;
import util.strategy.ChooseMatrix;
import util.strategy.ChooseWUP;
import util.strategy.ChooseLDSD;
import util.strategy.Similarity;
import wordnet.Sinonyms;

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
	
	public static List<Tag> loadTagArrayAndSinomyms(String[] tagArray) {
		List<Tag> listTag = new ArrayList<Tag>();	
		for (int i = 0; i < tagArray.length; i++) {
			// System.out.println("TextB " + (i+1) + " = " + tagArray[i] );
			
			try {
				Sinonyms.getSinonymous("tired");
			} catch (JWNLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
		for(Integer filme : filmes) {
			text = text + DBFunctions.findNameOfFilm(filme) + ",";
		}
		
		return text;
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
	 * Calcula a similaridade LDSD
	 */
	public static double[] calculeLDSD(Set<Tag> userModel, Set<Tag> testeSet, int UserId) {
		Map<String, Double> mapResultLDSDweighted = new TreeMap<String, Double>();
		int cont = 0;
		double resultSumSemantic = 0;
		double valueLDSD = 0;

		for (Tag item1 : userModel) {
			for (Tag item2 : testeSet) {
				cont = cont++;
			
				String nameTag1 = StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(item1.getName());
				String nameTag2 = StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(item2.getName());
				
				try {
					valueLDSD = Classifier.calculateSemanticDistance(nameTag1, nameTag2, IConstants.LDSD_LOD, UserId);
				} catch (Exception e) {
					calculeLDSD( userModel, testeSet,  UserId);
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
		case "POLISSEMIA":
			similarity = new ChooseJSPolissemia();
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
	 * Calculo proposto para melhorar a precisão
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
	public static void createRecomedationSystem(List<Document> userModel, List<Document> testSet, int userId) {

		DBFunctions dbFunctions = new DBFunctions();
		Lodica.userId = userId;
		List<Integer> filmsrelevants = new ArrayList<Integer>();
		
		
		for (Document document : testSet) {
			if(document.getRating() > 4) {
				filmsrelevants.add(document.getId());
			}
		}
		
		

		for (Document movie : userModel) {
			for (Document unmovie : testSet) {
			
			/*
			 * Cria lista para calcular AP 
			 */
				/* 
				 * Exibe e retorna a lista com as simiaridades encontrada
				 */
			
			  	 List<Integer> WUPRankedList = dbFunctions.resultRecommendation(userId, "WUP");
		   

			 /*
			 * Calcula a Precisição, AP e MAP
			 */
			
			calculeResultPrecisionAndMAP(userModel, WUPRankedList, filmsrelevants, userId, "WUP");
		
			}
			
			break;
			
		
			
		}

		TaggingFactory.saveCalculeMAP();
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
	 * Retorna uma lista com o filmes do Test Set
	 */
	public static List<Integer> createTestSetList(List<Ratings> testSetList) {

		List<Integer> filmRatingList = new ArrayList<Integer>();

		for (Ratings rating : testSetList) {
			filmRatingList.add(rating.getIddocument());
		}
		return filmRatingList;
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
	
	/*
	 * Calcula o resultado da Precision and MAP e salva o Resultado
	 */
	public static void calculeResultPrecisionAndMAP(List<Document> set, List<Integer> testSet, List<Integer> relevants, int userId, String type) {
		DBFunctions dbFunctions = new DBFunctions();
		List<Integer> listaAP10 = new ArrayList<Integer>();
		List<Integer> listaAP20 = new ArrayList<Integer>();
		List<Integer> listaAP30 = new ArrayList<Integer>();
		List<Integer> listIntegerUserModel = new ArrayList<Integer>();
		List<Integer> listIntegerTestSet = new ArrayList<Integer>();
		List<Integer> listIntegerrelevants = new ArrayList<Integer>();
		double p10, p20, p30, AP10, AP20, AP30, media, map;

		if (testSet == null || testSet.size() == 0) {
			dbFunctions.saveResult(userId, set.toString(), relevants, 0, 0, 0, 0, 0, type, 0, 0, 0);
			return;
		}

		System.out.println(" \n -------------- PRECISION " + type + " ---------------- \n");
		
		
		
		for (Document movie : set) {
			listIntegerUserModel.add(movie.getId());
		}
		
		
		
		double precsion = PrecisionAndRecall.precisionAt(listIntegerTestSet, listIntegerrelevants, testSet.size());
		System.out.println("VALOR DO PRECISION " + type + " : " + precsion + "\n");

		double cont3 = 0, cont5 = 0, cont10 = 0;
		
		for (int i = 0; i < testSet.size(); i++) {
			if (DBFunctions.isFilmRelevant(userId, testSet.get(i))) {
				if (i < 10) {
					cont3++;
				}
				if (i < 20) {
					cont5++;
				}
				if (i < 30) {
					cont10++;
				}
			}
		}

		p10 = cont3 / 10;
		p20 = cont5 / 20;
		p30 = cont10 / 30;
		
		List<Integer> listNumber = new ArrayList<Integer>();
		
		
		
		
		
		listaAP10 = getFirstNelementsList(testSet, 10);
		listaAP20 = getFirstNelementsList(testSet,20);
		listaAP30 = getFirstNelementsList(testSet, 30);
		
		
		

		AP10 = PrecisionAndRecall.AP(listaAP10, listIntegerTestSet, null);
		AP20 = PrecisionAndRecall.AP(listaAP20, listIntegerTestSet, null);
		AP30 = PrecisionAndRecall.AP(listaAP30, listIntegerTestSet, null);

		media = calculeMAP(p10, p20, p30, type);
		map = calculeMAP(AP10, AP20, AP30, type);
		
		System.out.println("VALOR DO p@10: " + p10 + "\n");
		System.out.println("VALOR DO p@20: " + p20 + "\n");
		System.out.println("VALOR DO p@30: " + p30 + "\n");

		System.out.println("-------- O VALOR DO MAP (ERRADO E : )" + media);
		System.out.println("-------- O VALOR DO MAP (CORRETO E : )" + map);
		System.out.println("-------- SALVO PARA O USUÁRIO " + userId);

		dbFunctions.saveResult(userId, set.toString(), relevants, p10, p20, p30, precsion, map, type, AP10, AP20, AP30);
	}
	
	/*
	 * Salva o resultado do MAP
	 */
	public static void saveCalculeMAP() {
		
		DBFunctions dbfunctions = new DBFunctions();

		String[] listAlgorithm= {"LDSD","WUP","COSINE","JACCARD","LDSD+JACCARD","WUP+JACCARD", "POLISSEMIA", "POLISSEMIA|SUBJECT", "JACCARD|SINONIMOS", "FORMULA1", "FORMULA2"};
		
		for (int i = 0; i < listAlgorithm.length; i++) {
			double map10= dbfunctions.calculeMap("ap10",listAlgorithm[i]);
			double map20= dbfunctions.calculeMap("ap20",listAlgorithm[i]);
			double map30= dbfunctions.calculeMap("ap30",listAlgorithm[i]);
			double p_10= dbfunctions.calculeMap("p10",listAlgorithm[i]);
			double p_20= dbfunctions.calculeMap("p20",listAlgorithm[i]);
			double p_30= dbfunctions.calculeMap("p30",listAlgorithm[i]);
			
			dbfunctions.saveMapPrecison(listAlgorithm[i], map10, map20, map30, p_10, p_20, p_30);
		}
	}
}
