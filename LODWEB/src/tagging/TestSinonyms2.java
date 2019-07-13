package tagging;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cosinesimilarity.LuceneCosineSimilarity;
import model.SemanticRanking;
import net.didion.jwnl.JWNLException;
import node.SparqlWalk;
import similarity.Jaccard;
import wordnet.Sinonyms;
import wordnet.WordNetFactory;

public class TestSinonyms2 {

	public static long init;
	public static long end;

	public static void main(String[] args) throws JWNLException, InterruptedException {

		// tired feliz bad angry big

		String[] userModel = { "tired", "bad", "angry"};
		String[] testModel = { "tired", "angry"};
		
		double union = Jaccard.union(TaggingFactory.loadTagArray(userModel), TaggingFactory.loadTagArray(testModel));
		double intersection = Jaccard.intersection(TaggingFactory.loadTagArray(userModel), TaggingFactory.loadTagArray(testModel));
		double similarityJaccard = Jaccard.similarityJaccard(union, intersection);
	
		System.out.println("TESTE resultado similaridade JACCARD com Sinônimos: " + similarityJaccard);
		System.out.println("============================================================================================");
			
    	// HashSet para retirar palavras repetidas
		Set<String> vetorSetUserModel = new HashSet<String>();
		Set<String> vetorSetTestModel = new HashSet<String>();
		
		vetorSetUserModel = convertArrayByHashSet(userModel);
		vetorSetTestModel = convertArrayByHashSet(testModel);
	
		ArrayList<String> userModelWithSinonimys = new ArrayList<String>();
		ArrayList<String> testModelWithSinonimys = new ArrayList<String>();
				
		System.out.println("======================= O vetor auxiliar que será removido as Tags com sinônimos =====================================================================");
		
		userModelWithSinonimys = createArrayListWithSinonys(userModel);
		testModelWithSinonimys = createArrayListWithSinonys(testModel);
			
		System.out.println("vetor userModelWithSinonimys ANTES de retirar sinônimos: " + userModelWithSinonimys);
		System.out.println("vetor testModelWithSinonimys ANTES de retirar sinônimos: " + testModelWithSinonimys);
		
		System.out.println("===================================Retira as Tags iguais do tagset UserModel ===================================");
	
		RemoveSinonimysvetorOfVetorSetUserModel(vetorSetUserModel, userModelWithSinonimys);
			
		System.out.println("===================================Retira as Tags iguais do tagset TestModel ===================================");
		
		RemoveSinonimysvetorOfVetorSetTestModel(vetorSetTestModel, testModelWithSinonimys);
		
		System.out.println("vetor userModelWithSinonimys DEPOIS de retirar sinônimos: " + userModelWithSinonimys);
		System.out.println("vetor testModelWithSinonimys DEPOIS de retirar sinônimos: " + testModelWithSinonimys);
		
		System.out.println("===================================Retira as Tags iguais do TestSet ===================================");
		
		String[] newHashSetUserSetWithSinonymys = createHashSetUserWithSinonymys(userModelWithSinonimys);
             
        String[] newHashSetTesSetWithSinonymys = createHashSetUserWithinonynys(testModelWithSinonimys);
          
        printTagSetWithoutSinonyms(newHashSetUserSetWithSinonymys);
        
        System.out.println("=========================================================================================");
		
        
        printTagSetWithoutSinonyms(newHashSetTesSetWithSinonymys);
        
        
        // Novos vetores sem tag repetidas e sem palavras sinônimas
		
		int cont = 0;

		for (int i1 = 0; i1 < newHashSetUserSetWithSinonymys.length; i1++) {
			System.out.println("==================");
			System.out.println("Palavra -> " + newHashSetUserSetWithSinonymys[i1]);
			System.out.println("==================");

			for (String c : Sinonyms.getSinonymous(newHashSetUserSetWithSinonymys[i1])) {
				 System.out.println("Sinonimôs -> " + c);
				 cont++;

				for (int j1 = 0; j1 < newHashSetTesSetWithSinonymys.length; j1++) {
					
					if (c.equals(newHashSetTesSetWithSinonymys[j1])) {
						System.out.println("Encontrou sinônimos -> | " + c + " |" + newHashSetTesSetWithSinonymys[j1]);
						newHashSetTesSetWithSinonymys[j1] = newHashSetUserSetWithSinonymys[i1];
					}
				}
			}
			
			System.out.println("Total -> " + cont);
			cont = 0;
		}
				
		Set<String> setUserModel = new HashSet<String>();
		for(String a: newHashSetUserSetWithSinonymys) {
			setUserModel.add(a);
		}
				
		Set<String> setTestModel = new HashSet<String>();
		for(String a: newHashSetTesSetWithSinonymys) {
			setTestModel.add(a);
		}
			
				
		System.out.println("User Model -> " + setUserModel);
		System.out.println("Test Model -> " + setTestModel);
		System.out.println("------------------- PRINT ARRAY arraySetUserModel ---------------------");
		printArray(newHashSetUserSetWithSinonymys);
		System.out.println("-------------------- PRINT ARRAY arraySetUserModel ----------------------");
		printArray(newHashSetTesSetWithSinonymys);
		System.out.println("----------------------------------------");
		
		//
		Set<String> toUpperCaseUserTagSet = new HashSet<String>();
		
		for (String string : newHashSetTesSetWithSinonymys) {
			string = string.substring(0,1).toUpperCase().concat(string.substring(1));
			toUpperCaseUserTagSet.add(string);
		}
		
		Set<String> toUpperCaseTestTagSet = new HashSet<String>();
		
		for (String string : newHashSetTesSetWithSinonymys) {
			string = string.substring(0,1).toUpperCase().concat(string.substring(1));
			toUpperCaseTestTagSet.add(string);
		}

		
		
		
		
		System.out.println("===============================================================");

		
			
		// Polissemia
		
		double totalPolysemy1 = calculePolysemy(toUpperCaseUserTagSet, toUpperCaseTestTagSet);
		
		double totalPolysemy = calculePolysemyCategorySubject(toUpperCaseUserTagSet, toUpperCaseTestTagSet);
		
		// Jaccard após retirar sinônimos iguais e tags iguais 
 				double similarityJaccardSinonimos = CalculeJaccard(newHashSetUserSetWithSinonymys, newHashSetTesSetWithSinonymys);
		
		System.out.println("TESTE resultado similaridade JACCARD com Sinônimos: " + similarityJaccardSinonimos);
		System.out.println("============================================================================================");
	
		System.out.println("VALOR SIMILARIDADE JACCARD = "  + similarityJaccard);
		
		System.out.println("VALOR DA POLISSEMIA = "  + totalPolysemy);
		
		
		
		DecimalFormat formato = new DecimalFormat("#.##"); 
		Double numero = Double.valueOf(formato.format(totalPolysemy));
		 

		 

		
		
		
	    System.out.println("VALOR DA POLISSEMIA SUBJECT = "  + numero);
		
		System.out.println("VALOR DE JACCARD COM SINÔNIMOS = "  + similarityJaccardSinonimos);
		
		System.out.println("FORMULA 1: F= (JS + P) / 2 = "  + ((similarityJaccardSinonimos + totalPolysemy) / 2));
		
		System.out.println("FORMULA 2: F= (JS + PS) / 2 = "  + ((similarityJaccardSinonimos + totalPolysemy) / 2));
		
		System.out.println("FORMULA 3: F= (JS + P + J) / 3 = "  + ((similarityJaccardSinonimos + totalPolysemy + similarityJaccard) / 3));
		
		System.out.println("FORMULA 4: F= (JS + PS + J) / 3 = "  + ((similarityJaccardSinonimos + totalPolysemy + similarityJaccard) / 3));
		
	}














	/**
	 * @param newHashSetUserSetWithSinonymys
	 */
	public static void printTagSetWithoutSinonyms(String[] newHashSetUserSetWithSinonymys) {
		System.out.println("vetor userModelWithSinonimys RETIRADA TAGs REPETIDA de retirar sinônimos: ");
	        for(String nome : newHashSetUserSetWithSinonymys) {
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
        for(String n2 : testModelWithSinonimys) {
            hsNomes2.add(n2);
        }
        int j = 0;
        String[] novosNomes2 = new String[hsNomes2.size()];
        for(String nome2 : hsNomes2) {
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
        for(String n : userModelWithSinonimys) {
            hsNomes.add(n);
        }
        int i = 0;
        String[] novosNomes = new String[hsNomes.size()];
        for(String nome : hsNomes) {
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
		for(String d: vetorSetTestModel) {
			System.out.println("----------------------------");
			System.out.println("Palavra do Vetor -> " + d);
			System.out.println("----------------------------");
			
			for (String string : Sinonyms.getSinonymous(d)) {
				for(String dd: vetorSetTestModel) {
								
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
	public static void RemoveSinonimysvetorOfVetorSetUserModel(Set<String> vetorSetUserModel, ArrayList<String> userModelWithSinonimys)
			throws JWNLException {
		for(String c: vetorSetUserModel) {
			System.out.println("----------------------------");
			System.out.println("Palavra do Vetor -> " + c);
			System.out.println("----------------------------");
			
			for (String string : Sinonyms.getSinonymous(c)) {
				for(String cc: vetorSetUserModel) {
								
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
		double unionSinonimos = Jaccard.union(TaggingFactory.loadTagArray(novosNomes), TaggingFactory.loadTagArray(novosNomes2));
		double intersectionSinonimos = Jaccard.intersection(TaggingFactory.loadTagArray(novosNomes), TaggingFactory.loadTagArray(novosNomes2));
		double similarityJaccardSinonimos = Jaccard.similarityJaccard(unionSinonimos, intersectionSinonimos);
		return similarityJaccardSinonimos;
	}


	
	/**
	 * @param userModel
	 */
	public static ArrayList<String> createArrayListWithSinonys (String[] userModel) {
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
	public static double calculePolysemy(Set<String> setUserModel, Set<String> setTestModel) {
		double count = 0;
		double div = setUserModel.size() * setTestModel.size();
		
		
		for (String user : setUserModel) {
			for (String test : setTestModel) {
												
				if(TestSinonyms.CountListResultSetCategorySubject(user, test)  > 1) {
					System.out.println("POLISSEMIA: Encontrado categoria igual: TAG usermodel: "+ user + " TAG testModel: " + test);
					count = count + 1;
				}
		
			}
		}
		System.out.println("VALOR TOTAL DE TODAS AS CATEGORIAS ENCONTRADA: " + " count: " + count + " div: " + div + " resultado: " + (count / div));
		return (count / div);
	}
	
	
	public static double calculePolysemyTESTE(Set<String> setUserModel, Set<String> setTestModel) {
		double count = 0;
		double div = setUserModel.size() * setTestModel.size();
				
		
		for (String user : setUserModel) {
			for (String test : setTestModel) {
												
				if(TestSinonyms.CountListResultSetCategorySubject(user, test)  > 8) {
					System.out.println("POLISSEMIA: Encontrado categoria igual: TAG usermodel: "+ user + " TAG testModel: " + test);
					count = count + 1;
				}
			}
		}
		
		
		
	 		
		System.out.println("VALOR TOTAL POLISSEMIA DCT:SUBJECT DE TODAS AS CATEGORIAS ENCONTRADA: " + " count: " + count + " div: " + div + " resultado: " + (count / div));
		return (count / div);
		
		
	}
	
	
	
	
	
	public static double calculePolysemyCategorySubject(Set<String> setUserModel, Set<String> setTestModel) {
		double count = 0;
		double div = setUserModel.size() * setTestModel.size();
		
		for (String user : setUserModel) {
			for (String test : setTestModel) {
				System.out.println("POLISSEMIA TESTMODEL LISTA DE CATEGORIAS ENCONTRADA: " + user);
								
				if(TestSinonyms.CountListResultSetCategorySubject(user, test)  > 8) {
					System.out.println("POLISSEMIA: Encontrado categoria igual: TAG usermodel: "+ user + " TAG testModel: " + test);
					count = count + 1;
				}
			}
		}
	 		
		System.out.println("VALOR TOTAL POLISSEMIA DCT:SUBJECT DE TODAS AS CATEGORIAS ENCONTRADA: " + " count: " + count + " div: " + div + " resultado: " + (count / div));
		return (count / div);
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
			//System.out.println("vetor TModel: " + a);
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

	

