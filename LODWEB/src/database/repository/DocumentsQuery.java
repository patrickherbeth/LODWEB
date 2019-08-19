package database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import database.DBConnection;
import model.Document;

public class DocumentsQuery {
	private Connection conn;

	public DocumentsQuery() {
		this.conn = DBConnection.getConnection();
	}

	public ArrayList<Document> getDocummentsViewedByUser(int idUser, int rating) {
		ArrayList<Document> movies = new ArrayList<>();

		try {
			String query = "SELECT doc.id, doc.desc, rat.rating FROM document as doc "
					+ "JOIN rating as rat on doc.id = rat.iddocument WHERE rat.iduser = ? and rat.rating >= ?";

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setInt(1, idUser);
			ps.setInt(2, rating);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				movies.add(new Document(rs.getInt(1), rs.getString(2), rs.getInt(3)));
			}

			return movies;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public ArrayList<Document> getDocummentsUnViewedByUser( int[] idsMoviesViewed) {
		ArrayList<Document> movies = new ArrayList<>();

		try {
			Statement st = conn.createStatement();
			
			String query = "SELECT doc.id, doc.desc FROM document as doc " + 
					"					   where doc.id in (SELECT tag.iddocument FROM tagging as tag where tag.id not in ("+ Arrays.toString(idsMoviesViewed).replaceAll("\\[|\\]", "") + ")) " + 
					"					   order by doc.id";
			
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				movies.add(new Document(rs.getInt(1), rs.getString(2)));
			}

			return movies;
		} catch (Exception ex) {
			return null;
		}
	}
}
