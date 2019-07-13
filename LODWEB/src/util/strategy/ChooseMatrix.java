package util.strategy;

import java.util.ArrayList;
import java.util.List;
import database.DBFunctions;
import model.Cenario;
import model.SemanticRanking;
import similarity.Jaccard;
import tagging.TaggingFactory;
import wordnet.WordNetFactory;

public class ChooseMatrix implements Similarity {
	double similarityJaccard;
	double calculeSumSemanticLDSD;
	double union;
	double intersection;
	double resultCalculeLDSD;
	double resultCalculeWup;
	double calculeSumSemanticWup;

	@Override
	public void choiceOfSimilarity(List<Cenario> cenarios, Cenario cenario, int userId, int limitTag) {
		DBFunctions dbfunctions = new DBFunctions();
		List<SemanticRanking> semanticRaking = new ArrayList<SemanticRanking>();
		List<SemanticRanking> semanticRakingLdsdJaccard = new ArrayList<SemanticRanking>();
		List<SemanticRanking> semanticRakingWupJaccard = new ArrayList<SemanticRanking>();
		
		
		String[] arrayUserModel = cenario.getTags_user().split(",");
		


		for (Cenario c : cenarios) {
			
			 similarityJaccard = 0; calculeSumSemanticLDSD= 0; union = 0; intersection = 0; resultCalculeLDSD =0;resultCalculeWup =0;calculeSumSemanticWup =0;
			
			String[] arrayUserTestModel = c.getTags_testset().split(",");
			
			union = Jaccard.union(TaggingFactory.loadTagArray(arrayUserModel), TaggingFactory.loadTagArray(arrayUserTestModel));

			intersection = Jaccard.intersection(TaggingFactory.loadTagArray(arrayUserModel), TaggingFactory.loadTagArray(arrayUserTestModel));


			/* Calcula Similaridade Jaccard */
			similarityJaccard = Jaccard.similarityJaccard(union, intersection);

			if (similarityJaccard > 0.0) {
				SemanticRanking semanticRakingJaccard = new SemanticRanking(1, c.getId_filme(), "JACCARD", similarityJaccard,userId);
				semanticRaking.add(semanticRakingJaccard);
			} 
	   
			

			/* Calcula Similaridade LDSD + JACCARD */
			
			calculeSumSemanticLDSD = dbfunctions.findSemantic("LDSD",userId, c.getId_filme());
			
			System.out.println("Valor de LDSD é :" + calculeSumSemanticLDSD);
		
			// if calculeSumSemanticLDSD é 0 é pq n tem ldsd , se tiver uniao e intersacao é o mesmo calculado do jaccard então aproveitamos
			if (calculeSumSemanticLDSD !=0  || calculeSumSemanticLDSD !=0.0) {
			resultCalculeLDSD = TaggingFactory.calculeSimilarityAndJaccard(union, intersection, calculeSumSemanticLDSD);
			if (resultCalculeLDSD >1) {
				resultCalculeLDSD = 1;
			}
			
			System.out.println("VALOR DA UNIAO -> " + union + " VALOR DA INTERSEÇÃO -> " + intersection + "VALOR DA SOMA SEMANTICA -> " + calculeSumSemanticLDSD + "RESULTADO = " + resultCalculeLDSD);
			SemanticRanking semanticRakingLdsdJaccard1 = new SemanticRanking(1, c.getId_filme(), "LDSD+JACCARD", resultCalculeLDSD, userId);
			semanticRakingLdsdJaccard.add(semanticRakingLdsdJaccard1);
			
			} else {
				if (union!=0 && intersection != 0) {
				resultCalculeLDSD =  similarityJaccard;
				
				System.out.println("VALOR DA UNIAO -> " + union + " VALOR DA INTERSEÇÃO -> " + intersection + "VALOR DA SOMA SEMANTICA -> " + calculeSumSemanticLDSD + "RESULTADO = " + resultCalculeLDSD);
				SemanticRanking semanticRakingLdsdJaccard1 = new SemanticRanking(1, c.getId_filme(), "LDSD+JACCARD", resultCalculeLDSD, userId);
				semanticRakingLdsdJaccard.add(semanticRakingLdsdJaccard1);
				}
			}
		
			
			
		
			/*  Calcula Similaridade WUP + JACCARD */
			calculeSumSemanticWup = dbfunctions.findSemantic("WUP",userId, c.getId_filme());
			
			if (calculeSumSemanticWup !=0  || calculeSumSemanticWup !=0.0) {
				
				resultCalculeWup = TaggingFactory.calculeSimilarityAndJaccard(union, intersection, calculeSumSemanticWup);

				if (resultCalculeWup >1) {
					resultCalculeWup = 1;
				}
				
				SemanticRanking semanticWup = new SemanticRanking(1, c.getId_filme(), "WUP+JACCARD", resultCalculeWup, userId);
				semanticRakingWupJaccard.add(semanticWup);
				
				} else {
					if (union!=0 && intersection != 0) {
						resultCalculeWup =  similarityJaccard;
						SemanticRanking semanticWup = new SemanticRanking(1, c.getId_filme(), "WUP+JACCARD", resultCalculeWup, userId);
						semanticRakingWupJaccard.add(semanticWup);
					}
				}
			
		}
		
		
		System.out.println("--------------- COMECOU A SALVAR -------------------------");
		/* Salva Similaridade JAccard */
		for (SemanticRanking semanticJaccard : semanticRaking) {

			if (semanticJaccard.getScore() != 0.0  || semanticJaccard.getScore() < 1.0)  {
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticJaccard.getUri2(), semanticJaccard.getType(), semanticJaccard.getScore(),0, userId);
			}
		} 

		/* Salva Similaridade Jaccard + LDSD */
		for (SemanticRanking semanticLdsd : semanticRakingLdsdJaccard) {
			if (semanticLdsd.getScore() != 0.0  || semanticLdsd.getScore() < 1.0)  {
		
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticLdsd.getUri2(), semanticLdsd.getType(), semanticLdsd.getScore(),0, userId);
			}
		}

		/* Salva Similaridade Jaccard + WUP */
		 for (SemanticRanking semanticWup : semanticRakingWupJaccard) {
			if (semanticWup.getScore() != 0.0  || semanticWup.getScore() < 1.0)  {

				dbfunctions.insertOrUpdateSemanticRaking(1, semanticWup.getUri2(), semanticWup.getType(), semanticWup.getScore(),0, userId);
			} 
		} 
		 System.out.println("----------------------- TERMINOU DE SALVAR -------------------------------------");
	} 


}
