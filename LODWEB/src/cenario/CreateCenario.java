package cenario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.CategoriesQuery;
import database.CategoriesTagQuery;
import database.DBFunctions;
import database.TagsQuery;
import model.Document;
import model.Ratings;
import model.Tag;
import movietagging.Category;
import movietagging.CategoryTag;
import node.SparqlWalk;
import tagging.RecommenderContext;
import tagging.TaggingFactory;
import util.StringUtilsNode;

public class CreateCenario {

	public static void main(String[] args) {

		TagsQuery tagsQuery = new TagsQuery();
		CategoriesQuery categoriesQuery = new CategoriesQuery();

		ArrayList<Tag> tags = tagsQuery.getAll();

		Set<String> newCategories = new HashSet<>();
		
		for (Tag tag : tags) {
			List<String> categoriesString = SparqlWalk.getLiteralByUri("http://dbpedia.org/resource/"
					+ StringUtilsNode.configureNameTagWithOutCharacterWithUnderLine(tag.getName()));

			for (String testTag : categoriesString) {
				newCategories.add(testTag);
			}
			
			System.out.println(tag.getId());
		}
		
		String newCategoriesValues = "";
		
		for(String category : newCategories) {
			newCategoriesValues += "(\"" + category + "\"),";
		}
		
		String newStr = newCategoriesValues.substring(0, newCategoriesValues.length() - 1) + ";";

		categoriesQuery.addNewCategories(newStr);
		
		
		
		//fim
		ArrayList<Integer> listUsers2 = new ArrayList<>();
		listUsers2.add(11);
		// listUsers2.add(96);

		for (int idUser : listUsers2) {
			List<Document> userMovie = new RecommenderContext(idUser).getCandidateDocumentsByF1();

			System.out.println();
		}

		DBFunctions dbFunctions = new DBFunctions();

		// Integer[] listUsers = { 11, 96, 121, 129, 133, 190, 205, 208, 271, 279, 316,
		// 318, 320, 342, 348, 359, 370, 395, 409, 451, 460, 469, 471, 482, 489, 500,
		// 505, 534, 540, 558 , 570, 586, 619, 631, 662, 693, 694, 700, 707, 729, 739,
		// 741, 768, 770, 786, 787, 819, 829, 858, 887, 888, 910, 964, 969, 971, 975,
		// 1015, 1166, 1244, 1268, 1271, 1277, 1288, 1339, 1376, 1387, 1408, 1418, 1447,
		// 1453, 1469, 1483, 1486, 1507, 1515, 1516, 1518, 1523, 1584, 1587, 1588, 1593,
		// 1602, 1616, 1619, 1623, 1629, 1644, 1662, 1678, 1686, 1705, 1719, 1738, 1741,
		// 1755, 1763, 1775, 1816, 1826};

		Integer[] listUsers = { 11, 96 };

		String textouserModel = "";
		String textoTestModel = "";
		int limitMin = 10;
		int limitMax = 0;
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
				// for(String tags: Sinonyms.getSinonymous(tag.getName())) {
				// textouserModel = textouserModel + tags + ",";
				// }
			}

			System.out.println(textouserModel);

			List<Ratings> testSet = new ArrayList<Ratings>();
			// preenche duas lista de filmes no total de 50 uma lista com 30 filmes >=4
			// rating e outra com 20 <=3
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