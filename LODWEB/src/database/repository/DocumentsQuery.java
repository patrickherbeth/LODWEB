package database.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			String query = "SELECT m.id, m.title, rat.rating FROM movie as m " + 
					"JOIN rating as rat on m.id = rat.id_movie " + 
					"WHERE rat.id_user = ? and rat.rating >= ?";

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
			
			
			String query = "SELECT rat.id_movie, avg(rat.rating) as rating from movielens.rating as rat " + 
					"where rat.id_movie in (SELECT tag.id_movie FROM movielens.tag_movie as tag where id_tag not in (" + Arrays.toString(idsMoviesViewed).replaceAll("\\[|\\]", "") + ")) " + 
					"group by rat.id_movie, rating " + 
					"order by rating desc LIMIT 50";
			
			
			/*
			String query = "SELECT doc.id, doc.title FROM movie as doc "
						 + "where doc.id in (SELECT tag.id_movie FROM tag_movie as tag where tag.id_tag not in ("+ Arrays.toString(idsMoviesViewed).replaceAll("\\[|\\]", "") +")) "
						 + "group by doc.id, doc.title " 
						 + "order by doc.id LIMIT 100";
				
			*/
			
			System.out.println(query);
			
			
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				movies.add(new Document(rs.getInt(1), rs.getInt(2)));
			}

			return movies;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public void saveRecommenderForluma1(int id_user, int title, double rating, double jaccard, double polissemy) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO recommendation_f1 (id_user, id_movie, rating, jaccard, polissemy) VALUES (?, ?, ?, ?, ?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, id_user);
				ps.setInt(2, title);
				ps.setDouble(3, rating);
				ps.setDouble(4, jaccard);
				ps.setDouble(5, polissemy);
				ps.execute();
				ps.close();
				conn.close();
					
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void saveRecommenderForluma2(int id_user, int title, double rating, double jaccard, double polissemy) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO recommendation_f2 (id_user, id_movie, rating, jaccard, polissmy) VALUES (?, ?, ?, ?, ?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, id_user);
				ps.setInt(2, title);
				ps.setDouble(3, rating);
				ps.setDouble(4, jaccard);
				ps.setDouble(5, polissemy);
				ps.execute();
				ps.close();
				conn.close();
					
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void saveRecommenderLDSD(int id_user, int title, double rating, double ldsd) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO recommendation_ldsd (id_user, id_movie, rating, ldsd) VALUES (?, ?, ?, ?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, id_user);
				ps.setInt(2, title);
				ps.setDouble(3, rating);
				ps.setDouble(4, ldsd);
				ps.execute();
				ps.close();
				conn.close();
					
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void saveRecommenderWUP(int id_user, int title, double rating, double wup) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO recommendation_wup (id_user, id_movie, rating, wup) VALUES (?, ?, ?, ?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, id_user);
				ps.setInt(2, title);
				ps.setDouble(3, rating);
				ps.setDouble(4, wup);
				ps.execute();
				ps.close();
				conn.close();
					
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
