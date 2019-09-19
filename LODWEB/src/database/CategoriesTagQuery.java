package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import movietagging.CategoryTag;

public class CategoriesTagQuery {
	private Connection conn;

	public CategoriesTagQuery() {
		this.conn = DBConnection.getConnection();
	}

	public List<CategoryTag> getAll() {
		try {
			Statement ps = conn.createStatement();

			String query = "SELECT c.id_tag, c.id_category FROM movielens.category_tag c;";

			ResultSet rs = ps.executeQuery(query);

			List<CategoryTag> categoriesTag = new ArrayList<>();

			while (rs.next()) {
				int idTag = rs.getInt(1);
				int idCategory = rs.getInt(2);

				categoriesTag.add(new CategoryTag(idTag, idCategory));
			}
			
			return categoriesTag;

		} catch (Exception ex) {
			return null;
		}
	}
}
