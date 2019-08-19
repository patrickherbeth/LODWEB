package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Document;
import model.Tag;

public class TagsQuery {

	private Connection conn;

	public TagsQuery() {
		this.conn = DBConnection.getConnection();
	}
	
	public List<Document> getTagsByMovies(List<Document> movies){
		try {
			Statement ps = conn.createStatement();
			
			int[] idsMovies = movies.stream().mapToInt(a -> a.getId()).toArray();
			
			String query = "SELECT tg.iddocument, t.id, t.tag FROM tag t " + 
					"JOIN tagging tg on t.id = tg.idtag " + 
					"WHERE tg.iddocument in (" + Arrays.toString(idsMovies).replaceAll("\\[|\\]", "") + ") " + 
					"GROUP BY tg.iddocument, t.id";
			
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				int idDocument = rs.getInt(1);
				int idTag = rs.getInt(2);
				String tagName = rs.getString(3);
				
				Document document = movies.stream().filter(a -> a.getId() == idDocument).findFirst().orElse(null);
				document.addNewTag(new Tag(idTag, tagName));
			}
			
			return movies;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public ArrayList<Tag> getAll(){
		ArrayList<Tag> tags = new ArrayList<>();
		
		try {
			Statement ps = conn.createStatement();
			
			String query = "SELECT t.id, t.tag FROM tag t ORDER BY t.id"; 
			
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				int idTag = rs.getInt(1);
				String tagName = rs.getString(2);
				
				tags.add(new Tag(idTag, tagName));
			}
			
			return tags;
		} catch (Exception ex) {
			return null;
		}
	}
}
