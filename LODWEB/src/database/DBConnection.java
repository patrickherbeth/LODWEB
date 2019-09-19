package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection extends Object {

	private static Connection con;

	private static Statement stmt;

	private static boolean status = false;

	public static Connection getConnection() {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String connection = "jdbc:mysql://movielens.cckscb3acsht.us-east-1.rds.amazonaws.com:3306/movielens?user=root&password=masterroot";
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(connection);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void finalizarConexaoBD() {
		try {
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Statement getStatement() {
		return stmt;
	}

	public static boolean getStatus() {
		return status;
	}

}