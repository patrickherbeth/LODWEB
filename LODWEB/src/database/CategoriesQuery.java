package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import model.Tag;
import movietagging.Category;

public class CategoriesQuery {
	private Connection conn;

	public CategoriesQuery() {
		this.conn = DBConnection.getConnection();
	}

	public Category getByName(String categoryName) {
		try {
			Statement ps = conn.createStatement();

			String query = "SELECT id, name FROM category WHERE name like '%" + categoryName + "%'";

			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				return new Category(rs.getInt(1), rs.getString(2));
			}

		} catch (Exception ex) {
			return null;
		}

		return null;
	}

	public boolean getByTagAndCategory(int idTag, int idCategory) {
		try {
			Statement ps = conn.createStatement();

			String query = "SELECT id_tag, id_category FROM category_tag WHERE id_tag = " + idTag
					+ " and id_category = " + idCategory;

			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				return true;
			}

		} catch (Exception ex) {
			return false;
		}

		return false;
	}

	public List<Category> getAll(){
		try {
			Statement ps = conn.createStatement();
			String query = "SELECT c.id, c.name FROM category c;";

			ResultSet rs = ps.executeQuery(query);

			List<Category> categories = new ArrayList<>();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);

				categories.add(new Category(id, name));
			}
			
			return categories;

		} catch (Exception ex) {
			return null;
		}
	}
	
	public void addNewCategories(String categories) {
		try {
			Statement ps = conn.createStatement();

			String query = "INSERT INTO movielens.category (name) VALUES " + categories;

			ps.executeUpdate(query);
		} catch (Exception ex) {
		}
	}

	public void addNewCategoryTag(String values) {
		try {
			Statement ps = conn.createStatement();

			String query = "INSERT INTO movielens.category_tag (id_tag, id_category) VALUES " + values;

			ps.executeUpdate(query);
		} catch (Exception ex) {
			System.out.println();
		}
	}

	public Set<Tag> getCategoriesFromIdsTags(Set<Tag> tags) {
		try {
			int[] idsTags = tags.stream().mapToInt(a -> a.getId()).toArray();
			
			Statement ps = conn.createStatement();
			String query = "SELECT ct.id_tag, ct.id_category, c.name FROM category_tag ct "
					+ "JOIN category c on ct.id_category = c.id "
					+ "WHERE ct.id_tag in (" + Arrays.toString(idsTags).replaceAll("\\[|\\]", "") + ") "
					+ "ORDER BY ct.id_tag";

			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				int idTagContext = rs.getInt(1);
				int idCategoryContext = rs.getInt(2);
				String nameCategoryContext = rs.getString(3);

				Tag tag = tags.stream().filter(a -> a.getId() == idTagContext).findFirst().orElse(null);

				tag.addCategory(new Category(idCategoryContext, nameCategoryContext));
			}
			
			return tags;

		} catch (Exception ex) {
			return null;
		}
	}
}
