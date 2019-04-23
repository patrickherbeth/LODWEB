package cenario;

import java.util.ArrayList;
import java.util.List;
import database.DBFunctions;
import model.Tag;

public class CenarioSemTag {

	public static void main(String[] args) {

		DBFunctions dbFunctions = new DBFunctions();

		// pega os 100 usuários guarda os os id do filmes( 5 ou mais depende do
		// cenario)
		// tags dos filmes pertencentes ao user model
		Integer[] listUsers = { 11 };

		String textouserModel = "";
		String textoTestModel = "";
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

			 for (int j = 0; j < 50; j++) {
				
					dbFunctions.insertOrUpdateCenarioSemTag(listUsers[i], textouserModel);
				}

			System.out.println(textoTestModel);
		}
	}

}