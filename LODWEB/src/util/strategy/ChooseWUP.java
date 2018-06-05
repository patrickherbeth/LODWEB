package util.strategy;

import java.util.ArrayList;
import java.util.List;

import database.DBFunctions;
import model.Cenario;
import model.SemanticRanking;
import tagging.TaggingFactory;
import wordnet.WordNetFactory;

public class ChooseWUP implements Similarity {
	double similarityJaccard;
	double[] calculeWUP;
	double union;
	double intersection;
	double ResultCalcule;

	@Override
	public void choiceOfSimilarity(List<Cenario> cenarios, Cenario cenario, int userId, int limitTag) {

		DBFunctions dbfunctions = new DBFunctions();
		String[] arrayUserModel = cenario.getTags_user().split(",");
		List<SemanticRanking> listSemanticRakingWup = new ArrayList<SemanticRanking>();

		for (Cenario c : cenarios) {
			String[] arrayUserTestModel = c.getTags_testset().split(",");

		
			calculeWUP = WordNetFactory.calculeWUP(TaggingFactory.loadTagArray(arrayUserModel), TaggingFactory.loadTagArray(arrayUserTestModel));

			
			if (calculeWUP[1] > 0.0) {
				SemanticRanking semanticRakingWup = new SemanticRanking(1, c.getId_filme(), "WUP", calculeWUP[1],
						calculeWUP[0], userId);
				listSemanticRakingWup.add(semanticRakingWup);
			}
		}

		for (SemanticRanking semantic : listSemanticRakingWup) {

			if (semantic.getScore() != 0.0 || semantic.getScore() < 1.0) {
				dbfunctions.insertOrUpdateSemanticRaking(1, semantic.getUri2(), semantic.getType(), semantic.getScore(),
						semantic.getSumsemantic(), userId);
			}
		}
	}
}
