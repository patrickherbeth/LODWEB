package cenario;

import java.util.ArrayList;
import java.util.List;
import database.DBFunctions;
import model.Cenario;
import model.Ratings;
import model.Tag;
import tagging.TaggingFactory;

public class CreateCenario {

	public static void main(String[] args) {

		DBFunctions dbFunctions = new DBFunctions();

		Integer[] listUsers = { 11 };

		String textouserModel = "";
		String textoTestModel = "";
		int limitMin = 30;
		int limitMax = 20;
		int limitTAg = 5;


		for (int i = 0; i < listUsers.length; i++) {

			textouserModel = "";
			textoTestModel = "";
			List<Integer> userModel = dbFunctions.createUserModel(listUsers[i], 5);

			ArrayList<Tag> listaTags = dbFunctions.getNameOfTagsOfFilms(userModel, limitTAg);
			for (Tag tag : listaTags) {
				if (tag.getName() != null) {
					textouserModel = textouserModel + tag.getName() + ",";
				}
			}

			System.out.println(textouserModel);

			List<Ratings> testSet = new ArrayList<Ratings>();
			// preenche duas lista de filmes no total de 50 uma lista com 30 filmes >=4 rating e outra com 20 <=3
			testSet = DBFunctions.createTestSet(listUsers[i], limitMin, limitMax, 4);
			List<Integer> testSetList = TaggingFactory.createTestSetList(testSet);

			for (int j = 0; j < testSetList.size(); j++) {

				List<Integer> tagsTestSet = dbFunctions.findTagOfDocumentWithLimitTag(testSetList.get(j), 5);
				textoTestModel = "";
				for (int valor : tagsTestSet) {
					if (DBFunctions.findTagById(valor) != null) {
						textoTestModel = textoTestModel + DBFunctions.findTagById(valor) + ",";
					}
				}
				dbFunctions.insertOrUpdateCenario(listUsers[i], textouserModel, textoTestModel, testSetList.get(j));
			}
		}
	}

}