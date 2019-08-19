package util.strategy;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import database.DBFunctions;
import model.Cenario;
import model.SemanticRanking;
import net.didion.jwnl.JWNLException;
import node.Classifier;
import node.IConstants;
import similarity.Jaccard;
import tagging.TaggingFactory;
import tagging.TestSinonyms;
import util.StringUtilsNode;
import wordnet.Sinonyms;

public class ChooseJSPolissemia implements Similarity {

	double similarityJaccard;
	double calculeSumSemanticLDSD;
	double union;
	double intersection;
	double resultCalculeLDSD;
	double resultCalculeWup;
	double[] calculePolissemia;
	double[] valueCalculePolissemiaSubject;
	double calculeSumSemanticWup;

	@Override
	public void choiceOfSimilarity(List<Cenario> cenarios, Cenario cenario, int userId, int limitTag) {

		DBFunctions dbfunctions = new DBFunctions();

		String[] arrayUserModel = cenario.getTags_user().split(",");

		List<SemanticRanking> semanticRakingPolissemia = new ArrayList<SemanticRanking>();
		List<SemanticRanking> semanticRakingPolissemiaSubject = new ArrayList<SemanticRanking>();
		List<SemanticRanking> semanticRakingJaccardSinonimos = new ArrayList<SemanticRanking>();
		List<SemanticRanking> semanticRakingFormula1 = new ArrayList<SemanticRanking>();
		List<SemanticRanking> semanticRakingFormula2 = new ArrayList<SemanticRanking>();

		for (Cenario c : cenarios) {
			String[] arrayUserTestModel = c.getTags_testset().split(",");
			
			similarityJaccard = CalculeJaccard(arrayUserModel, arrayUserTestModel);

			System.out.println("TESTE resultado similaridade JACCARD com Sinônimos: " + similarityJaccard);
			System.out.println("============================================================================================");

			// HashSet para retirar palavras repetidas
			Set<String> vetorSetUserModel = new HashSet<String>();
			vetorSetUserModel = convertArrayByHashSet(arrayUserModel);
			
			ArrayList<String> userModelWithSinonimys = new ArrayList<String>();
			ArrayList<String> testModelWithSinonimys = new ArrayList<String>();

			System.out.println("======================= O vetor auxiliar que será removido as Tags com sinônimos =====================================================================");

			userModelWithSinonimys = createArrayListWithSinonys(arrayUserModel);
			testModelWithSinonimys = createArrayListWithSinonys(arrayUserTestModel);

			System.out.println("vetor userModelWithSinonimys ANTES de retirar sinônimos: " + userModelWithSinonimys);
			System.out.println("vetor testModelWithSinonimys ANTES de retirar sinônimos: " + testModelWithSinonimys);

			System.out.println("================================================================================================================================================================");

			ArrayList<String> arrayTestModelWithSinonimys = new ArrayList<String>();

			for (String tag : testModelWithSinonimys) {
				arrayTestModelWithSinonimys.add(tag);
			}

			System.out.println("===================================Retira as Tags iguais do tagset TestModel ===================================");

			try {

				RemoveSinonimysvetorOfVetorSetUserModel(vetorSetUserModel, arrayTestModelWithSinonimys);

			} catch (Exception e) {
				System.out.println("Erro ocorrido ao remover tags com sinônimos");
			}

			System.out.println("vetor userModelWithSinonimys DEPOIS de retirar sinônimos: " + userModelWithSinonimys);
			System.out.println("vetor testModelWithSinonimys DEPOIS de retirar sinônimos: " + arrayTestModelWithSinonimys);

			System.out.println("===================================Retira as Tags iguais do TestSet ===================================");

			String[] newHashSetUserSetWithSinonymys = createHashSetUserWithSinonymys(userModelWithSinonimys);

			String[] newHashSetTesSetWithSinonymys = createHashSetUserWithinonynys(arrayTestModelWithSinonimys);

			printTagSetWithoutSinonyms(newHashSetUserSetWithSinonymys);

			System.out.println("=========================================================================================");

			printTagSetWithoutSinonyms(newHashSetTesSetWithSinonymys);

			// Novos vetores sem tag repetidas e sem palavras sinônimas

			int cont = 0;

			for (int i1 = 0; i1 < newHashSetUserSetWithSinonymys.length; i1++) {
				System.out.println("==================");
				System.out.println("Palavra -> " + newHashSetUserSetWithSinonymys[i1]);
				System.out.println("==================");

				try {

					for (String d : Sinonyms.getSinonymous(newHashSetUserSetWithSinonymys[i1])) {
						//System.out.println("Sinonimôs -> " + d);
						cont++;

						for (int j1 = 0; j1 < newHashSetTesSetWithSinonymys.length; j1++) {

							if (d.equals(newHashSetTesSetWithSinonymys[j1])) {
								System.out.println(
										"Encontrou sinônimos -> | " + d + " |" + newHashSetTesSetWithSinonymys[j1]);
								newHashSetTesSetWithSinonymys[j1] = newHashSetUserSetWithSinonymys[i1];
							}
						}
					}

				} catch (JWNLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//System.out.println("Total -> " + cont);
				cont = 0;
			}

			Set<String> setUserModel = new HashSet<String>();
			for (String a : newHashSetUserSetWithSinonymys) {
				setUserModel.add(a);
			}

			Set<String> setTestModel = new HashSet<String>();
			for (String a : newHashSetTesSetWithSinonymys) {
				setTestModel.add(a);
			}
/*
			System.out.println("User Model -> " + setUserModel);
			System.out.println("Test Model -> " + setTestModel);
			System.out.println("------------------- PRINT ARRAY arraySetUserModel ---------------------");
			printArray(newHashSetUserSetWithSinonymys);
			System.out.println("-------------------- PRINT ARRAY arraySetUserModel ----------------------");
			printArray(newHashSetTesSetWithSinonymys);
			System.out.println("----------------------------------------");
*/
			//
			Set<String> toUpperCaseUserTagSet = new HashSet<String>();

			for (String string : newHashSetTesSetWithSinonymys) {
				string = string.substring(0, 1).toUpperCase().concat(string.substring(1));
				toUpperCaseUserTagSet.add(string);
			}

			Set<String> toUpperCaseTestTagSet = new HashSet<String>();

			for (String string : newHashSetTesSetWithSinonymys) {
				string = string.substring(0, 1).toUpperCase().concat(string.substring(1));
				toUpperCaseTestTagSet.add(string);
			}

			System.out.println("===============================================================");
			
			

			// Polissemia
			 calculePolissemia = calculePolysemy(toUpperCaseUserTagSet, toUpperCaseTestTagSet);

			 valueCalculePolissemiaSubject  = calculePolysemySubject(toUpperCaseUserTagSet, toUpperCaseTestTagSet);

			// Jaccard após retirar sinônimos iguais e tags iguais

			double similarityJaccardSinonimos = CalculeJaccard(newHashSetUserSetWithSinonymys, newHashSetTesSetWithSinonymys);
			
			double formula1 = ((similarityJaccardSinonimos + calculePolissemia[1]) / 2);

			double formula2 = ((similarityJaccardSinonimos + valueCalculePolissemiaSubject[1]) / 2);

			System.out.println("TESTE resultado similaridade JACCARD com Sinônimos: " + similarityJaccardSinonimos);
			System.out.println(
					"============================================================================================");
			
			System.out.println("VALOR SIMILARIDADE JACCARD = " + similarityJaccard);

			System.out.println("VALOR DA POLISSEMIA = " + calculePolissemia[1]);

			System.out.println("VALOR DA POLISSEMIA SUBJECT = " + valueCalculePolissemiaSubject[1]);

			System.out.println("VALOR DE JACCARD COM SINÔNIMOS = " + similarityJaccardSinonimos);

			System.out.println("FORMULA 1: F= (JS + P) / 2 = " + formula1);

			System.out.println("FORMULA 2: F= (JS + PS) / 2 = " + formula2);
		

			if (calculePolissemia[1] > 0.0) {
				SemanticRanking semanticRakingTotalPolysemy = new SemanticRanking(1, c.getId_filme(), "POLISSEMIA", calculePolissemia[1], calculePolissemia[0], userId);
				semanticRakingPolissemia.add(semanticRakingTotalPolysemy);
			}
			
		
			
			

			if (valueCalculePolissemiaSubject[1] > 0.0) {
				SemanticRanking similarityRakingPolissemiaSubject = new SemanticRanking(1, c.getId_filme(), "POLISSEMIA|SUBJECT", valueCalculePolissemiaSubject[1], valueCalculePolissemiaSubject[0], userId);
				semanticRakingPolissemiaSubject.add(similarityRakingPolissemiaSubject);
			}

			if (similarityJaccardSinonimos > 0.0) {
				SemanticRanking similarityRakingJaccardSinonimos = new SemanticRanking(1, c.getId_filme(), "JACCARD|SINONIMOS", similarityJaccardSinonimos, similarityJaccardSinonimos, userId);
				semanticRakingJaccardSinonimos.add(similarityRakingJaccardSinonimos);
			}

			if (formula1 > 0.0) {
				SemanticRanking similarityRakingFormula1 = new SemanticRanking(1, c.getId_filme(), "FORMULA1", formula1, formula1, userId);
				semanticRakingFormula1.add(similarityRakingFormula1);
			}

			if (formula2 > 0.0) {
				SemanticRanking similarityRakingFormula2 = new SemanticRanking(1, c.getId_filme(), "FORMULA2", formula2, formula2, userId);
				semanticRakingFormula2.add(similarityRakingFormula2);
			}
		}

		// Polissemia
		for (SemanticRanking semanticPolissemia : semanticRakingPolissemia) {

			if (semanticPolissemia.getScore() != 0.0 || semanticPolissemia.getScore() > 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticPolissemia.getUri2(), semanticPolissemia.getType(),
						semanticPolissemia.getScore(), semanticPolissemia.getSumsemantic(), userId);
			}
		}

		// Polissemia e Categoria Subject
		for (SemanticRanking semanticPolissemiaSubject : semanticRakingPolissemiaSubject) {

			if (semanticPolissemiaSubject.getScore() != 0.0 || semanticPolissemiaSubject.getScore() > 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticPolissemiaSubject.getUri2(),
						semanticPolissemiaSubject.getType(), semanticPolissemiaSubject.getScore(),
						semanticPolissemiaSubject.getSumsemantic(), userId);
			}
		}

		// Polissemia e Jaccard e Sinônimos
		for (SemanticRanking semanticJaccardSinonimos : semanticRakingJaccardSinonimos) {

			if (semanticJaccardSinonimos.getScore() != 0.0 || semanticJaccardSinonimos.getScore() > 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticJaccardSinonimos.getUri2(),
						semanticJaccardSinonimos.getType(), semanticJaccardSinonimos.getScore(),
						semanticJaccardSinonimos.getSumsemantic(), userId);
			}
		}

		// Formula 1
		for (SemanticRanking semanticFormula1 : semanticRakingFormula1) {

			if (semanticFormula1.getScore() != 0.0 || semanticFormula1.getScore() > 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticFormula1.getUri2(), semanticFormula1.getType(),
						semanticFormula1.getScore(), semanticFormula1.getSumsemantic(), userId);
			}
		}

		// Formula 2
		for (SemanticRanking semanticFormula2 : semanticRakingFormula2) {

			if (semanticFormula2.getScore() != 0.0 || semanticFormula2.getScore() > 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticFormula2.getUri2(), semanticFormula2.getType(),
						semanticFormula2.getScore(), semanticFormula2.getSumsemantic(), userId);
			}
		}
	}


	/**
	 * @param newHashSetUserSetWithSinonymys
	 */
	public static void printTagSetWithoutSinonyms(String[] newHashSetUserSetWithSinonymys) {
		System.out.println("vetor userModelWithSinonimys RETIRADA TAGs REPETIDA de retirar sinônimos: ");
		for (String nome : newHashSetUserSetWithSinonymys) {
			System.out.println("vetor userModelWithSinonimys: " + nome);
		}
	}
	 	
	/**
	 * @param arraySetUserModel
	 */
	public static void printArray(String[] arraySetUserModel) {
		for (String string : arraySetUserModel) {
			System.out.println("arraySetUserModel -> " + string);
		}
	}

	/**
	 * @param testModelWithSinonimys
	 * @return
	 */
	public static String[] createHashSetUserWithinonynys(ArrayList<String> testModelWithSinonimys) {
		HashSet<String> hsNomes2 = new HashSet<String>();
		for (String n2 : testModelWithSinonimys) {
			hsNomes2.add(n2);
		}
		int j = 0;
		String[] novosNomes2 = new String[hsNomes2.size()];
		for (String nome2 : hsNomes2) {
			novosNomes2[j++] = nome2;
		}
		return novosNomes2;
	}

	/**
	 * @param userModelWithSinonimys
	 * @return
	 */
	public static String[] createHashSetUserWithSinonymys(ArrayList<String> userModelWithSinonimys) {
		HashSet<String> hsNomes = new HashSet<String>();
		for (String n : userModelWithSinonimys) {
			hsNomes.add(n);
		}
		
		int i = 0;
		String[] novosNomes = new String[hsNomes.size()];
		for (String nome : hsNomes) {
			novosNomes[i++] = nome;
		}
		
		return novosNomes;
	}

	/**
	 * @param vetorSetTestModel
	 * @param testModelWithSinonimys
	 * @throws JWNLException
	 */
	public static void RemoveSinonimysvetorOfVetorSetTestModel(Set<String> vetorSetTestModel,
			ArrayList<String> testModelWithSinonimys) throws JWNLException {
		for (String d : vetorSetTestModel) {
			System.out.println("----------------------------");
			System.out.println("Palavra do Vetor -> " + d);
			System.out.println("----------------------------");

			for (String string : Sinonyms.getSinonymous(d)) {
				for (String dd : vetorSetTestModel) {

					if (dd.equals(string)) {
						System.out.println("Encontrou TestModel sinônimos -> " + dd + " e " + string);

						testModelWithSinonimys.remove(string);
					}
				}
			}
		}
	}

	/**
	 * @param vetorSetUserModel
	 * @param userModelWithSinonimys
	 * @throws JWNLException
	 */
	public static void RemoveSinonimysvetorOfVetorSetUserModel(Set<String> vetorSetUserModel,
			ArrayList<String> userModelWithSinonimys) throws JWNLException {
		for (String c : vetorSetUserModel) {
			System.out.println("----------------------------");
			System.out.println("Palavra do Vetor -> " + c);
			System.out.println("----------------------------");

			for (String string : Sinonyms.getSinonymous(c)) {
				for (String cc : vetorSetUserModel) {

					if (cc.equals(string)) {
						System.out.println("Encontrou UserModel sinônimos -> " + cc + " e " + string);

						userModelWithSinonimys.remove(string);

					}
				}
			}

		}
	}

	/**
	 * @param novosNomes
	 * @param novosNomes2
	 * @return
	 */
	public static double CalculeJaccard(String[] novosNomes, String[] novosNomes2) {
		double unionSinonimos = Jaccard.union(TaggingFactory.loadTagArray(novosNomes),
				TaggingFactory.loadTagArray(novosNomes2));
		double intersectionSinonimos = Jaccard.intersection(TaggingFactory.loadTagArray(novosNomes),
				TaggingFactory.loadTagArray(novosNomes2));
		double similarityJaccardSinonimos = Jaccard.similarityJaccard(unionSinonimos, intersectionSinonimos);
		return similarityJaccardSinonimos;
	}

	/**
	 * @param userModel
	 */
	public static ArrayList<String> createArrayListWithSinonys(String[] userModel) {
		ArrayList<String> tagSetelWithSinonimys = new ArrayList<String>();
		for (String a : userModel) {
			tagSetelWithSinonimys.add(a);
		}
		return tagSetelWithSinonimys;
	}

	/**
	 * @param cont
	 * @param setUserModel
	 * @param setTestModel
	 * @return
	 */
	public static double[] calculePolysemy(Set<String> setUserModel, Set<String> setTestModel) {
		double count = 0;
		double resultSemantic = 0;
		Map<String, Double> mapResultPolisemyWeighted = new TreeMap<String, Double>();
		double div = setUserModel.size() * setTestModel.size();
		
		
		// System.out.println("VALOR DA SOMA DE USER MODEL + TEST MODEL -> " + div);

		for (String user : setUserModel) {
			for (String test : setTestModel) {

				try {
					if (TestSinonyms.CountListResultSet(StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(user), StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(test)) > 1) {
						
					    System.out.println("POLISSEMIA: Encontrado categoria igual: TAG usermodel: " + user + " TAG testModel: " + test);
						count = count + 1;
						mapResultPolisemyWeighted.put(user + test, (count / div));
					}
				} catch (Exception e) {
					calculePolysemy(setUserModel, setTestModel);
				}
			}
		}
		
		
		
		System.out.println("\n ====================== ARRAYLIST DE ELEMENTOS QUE SERÃO SOMADOS ==================== ");
		System.out.println(mapResultPolisemyWeighted + " \n");
		System.out.println("\n ================================== RESULTADOS ====================================== ");
		
		// Resultado da soma de todos as tags que existe similardade dividida pela quantidade de itens da lista
		resultSemantic = TaggingFactory.sumSemantic(mapResultPolisemyWeighted);
		
		double score= (count / div);
		
		 System.out.println("VALOR TOTAL DE TODAS AS CATEGORIAS ENCONTRADA: " + " count: " + count + " div: " + div + " resultado: " + (count / div));
		
		return new double[] { resultSemantic, score };	
	}
	
	public static double[] calculePolysemySubject(Set<String> setUserModel, Set<String> setTestModel) {
		double count = 0;
		double resultSemantic = 0;
		Map<String, Double> mapResultPolisemyWeighted = new TreeMap<String, Double>();
		double div = setUserModel.size() * setTestModel.size();

		for (String user : setUserModel) {
			for (String test : setTestModel) {
				try {
					if (TestSinonyms.CountListResultSetCategorySubject(StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(user), StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(test)) > 1) {
						System.out.println("POLISSEMIA SUBJECT: Encontrado categoria igual: TAG usermodel: " + user + " TAG testModel: " + test);
						count = count + 1;
						mapResultPolisemyWeighted.put(user + test, (count / div));
					}
				} catch (Exception e) {
					calculePolysemySubject(setUserModel, setTestModel);
				}
			}
		}
		
		
		System.out.println("\n ====================== ARRAYLIST DE ELEMENTOS QUE SERÃO SOMADOS ==================== ");
		System.out.println(mapResultPolisemyWeighted + " \n");
		System.out.println("\n ================================== RESULTADOS ====================================== ");
		
		 //Resultado da soma de todos as tags que existe similardade dividida pela quantidade de itens da lista
		resultSemantic = TaggingFactory.sumSemantic(mapResultPolisemyWeighted);
		
		double score= (count / div);
		
		
		 System.out.println("VALOR TOTAL DE TODAS AS CATEGORIAS ENCONTRADA: " + " count: " + count + " div: " + div + " resultado: " + (count / div));
		
		return new double[] { resultSemantic, score };	
		
	}
	
	
	/*
	 * Métodos utilizados para cálculo da polissemia e sinônimos
	 */

	/**
	 * @param testModel
	 * @return
	 */
	public static Set<String> convertArrayByHashSet(String[] testModel) {

		Set<String> vetorAux = new HashSet<String>();

		for (String a : testModel) {
			// System.out.println("vetor TModel: " + a);
			vetorAux.add(a);
		}

		return vetorAux;
	}

	/**
	 * @param userModel
	 * @return
	 */
	public static ArrayList<String> vetorAuxRemoveTagsWithSynonyms(String[] userModel) {

		ArrayList<String> modelWithSinonimys = new ArrayList<String>();

		for (String a : userModel) {
			modelWithSinonimys.add(a);
		}

		return modelWithSinonimys;
	}

	/**
	 * @param vetorSetUserModel
	 * @return
	 * @throws JWNLException
	 */
	public static ArrayList<String> removeSinonynousTagSet(Set<String> vetorTagSetModel) throws JWNLException {

		ArrayList<String> tagSetlWithSinonimys = new ArrayList<String>();

		for (String c : vetorTagSetModel) {
			 System.out.println("----------------------------");
			 System.out.println("Palavra do Vetor -> " + c);
			 System.out.println("----------------------------");

			for (String string : Sinonyms.getSinonymous(c)) {
				for (String cc : vetorTagSetModel) {
					if (cc.equals(string)) {
						 System.out.println("EncontroUserModel u sinônimos -> " + cc + " e " + string);

						tagSetlWithSinonimys.remove(string);

					}
				}
			}
		}

		return tagSetlWithSinonimys;

	}

}
