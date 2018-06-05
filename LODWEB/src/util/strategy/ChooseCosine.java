package util.strategy;

import java.util.ArrayList;
import java.util.List;
import cosinesimilarity.LuceneCosineSimilarity;
import database.DBFunctions;
import model.Cenario;
import model.SemanticRanking;
import tagging.TaggingFactory;

public class ChooseCosine implements Similarity {

	@Override
	public void choiceOfSimilarity(List<Cenario> cenarios, Cenario cenario, int userId, int limitTag) {
		double cosineSimilarity;
		DBFunctions dbfunctions = new DBFunctions();
		String[] arrayUserModel = cenario.getTags_user().split(",");

		List<SemanticRanking> semanticRaking = new ArrayList<SemanticRanking>();

		for (Cenario c : cenarios) {

			String[] arrayUserTestModel = c.getTags_testset().split(",");

			cosineSimilarity = LuceneCosineSimilarity.getCosineSimilarity(TaggingFactory.retornaString(arrayUserModel), TaggingFactory.retornaString(arrayUserTestModel));

			System.out.println("\n UserModel : " + TaggingFactory.retornaString(arrayUserModel) + "\n TestModel: "
					+ TaggingFactory.retornaString(arrayUserTestModel) + "\n Tiveram similaridade: "
					+ cosineSimilarity);

			if (cosineSimilarity > 0.0) {
				SemanticRanking semanticRaking1 = new SemanticRanking(1, c.getId_filme(), "COSINE", cosineSimilarity, userId); 
				semanticRaking.add(semanticRaking1);
			}
		}

		for (SemanticRanking semanticRaking2 : semanticRaking) {
			if (semanticRaking2.getScore() != 0.0  || semanticRaking2.getScore() > 1.0)  {
				dbfunctions.insertOrUpdateSemanticRaking(1, semanticRaking2.getUri2(), semanticRaking2.getType(), semanticRaking2.getScore(),0, userId);
			}
		}
	}
}
