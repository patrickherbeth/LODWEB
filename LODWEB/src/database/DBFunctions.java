//SET GLOBAL time_zone = '+3:00';
package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import com.drew.metadata.photoshop.PsdHeaderDescriptor;
import com.mchange.v2.cfg.PropertiesConfigSource.Parse;
import com.sun.xml.bind.v2.runtime.reflect.opt.TransducedAccessor_field_Double;

import cosinesimilarity.LuceneCosineSimilarity;
import model.Cenario;
import model.HitRate;
import model.OnlineEvaluation;
import model.Ratings;
import model.SemanticRanking;
import model.Tag;
import model.User;
import movietagging.Rating;
import node.Classifier;
import node.Evaluation;
import node.IConstants;
import node.Item;
import node.Lodica;
import node.Node;
import node.NodePrediction;
import node.NodeUtil;
import node.SparqlWalk;
import parser.Parser;
import tagging.CBRecommender;
import tagging.TaggingFactory;
import util.StringUtilsNode;
import model.Document;


/**
 *
 * @author freddurao
 */
public class DBFunctions {

	private static boolean hasBefore = false;

	private static Connection conn;

	public static Connection getConnection() {
		conn = DBConnection.getConnection();
		return conn;
	}

	/*
	 * public static MysqlConnection getMySqlConnection(){ MysqlConnection
	 * mysqlConnection = new MysqlConnection() conn = DBConnection.getConnection();
	 * }
	 */

	public DBFunctions() {

		if (StringUtilsNode.getMachineName().equalsIgnoreCase("pcdochefe")) {
			IDatabaseConstants.DB_PASSWORD = "durao";
			IDatabaseConstants.PATH_TO_MYSQL_EXECUTABLES = "";
		} else if (StringUtilsNode.getMachineName().equalsIgnoreCase("insight213")) {
			IDatabaseConstants.PATH_TO_MYSQL_EXECUTABLES = "";
		}
		conn = DBConnection.getConnection();
	}

	public static void exportData(String filename, String table, String optional, String time) {
		try {
			FileWriter fw = new FileWriter(filename, true);
			if (new File(filename).exists()) {
				FileReader fr = new FileReader(filename);
				char[] a = new char[50];
				fr.read(a);
				for (char c : a)
					fw.append(c);
				fr.close();
			}

			Connection conn = DBConnection.getConnection();
			String query = "select * from " + table;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				fw.append(rs.getString(1));
				fw.append(',');
				fw.append(rs.getString(2));
				fw.append(',');
				fw.append(rs.getString(3));
				fw.append(',');
				fw.append(rs.getString(4));
				fw.append(',');
				fw.append(rs.getString(5));
				fw.append(',');
				fw.append(rs.getString(6));
				fw.append(',');
				fw.append(rs.getString(7));
				fw.append(',');
				fw.append(rs.getString(8));
				fw.append(',');
				fw.append(rs.getString(9));
				fw.append(',');
				fw.append(rs.getString(10));
				fw.append(',');
				fw.append(optional);
				fw.append(',');
				fw.append(time);
				fw.append(',');
				fw.append('\n');
			}
			System.out.println(filename);
			fw.flush();
			fw.close();
			conn.close();
			System.out.println("CSV File is created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exportData(String filename, String table, String optional, String optional2, String time) {
		try {
			FileWriter fw = new FileWriter(filename, true);
			if (new File(filename).exists()) {
				FileReader fr = new FileReader(filename);
				char[] a = new char[50];
				fr.read(a);
				for (char c : a)
					fw.append(c);
				fr.close();
			}

			Connection conn = DBConnection.getConnection();
			String query = "select * from " + table;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				fw.append(rs.getString(1));
				fw.append(',');
				fw.append(rs.getString(2));
				fw.append(',');
				fw.append(rs.getString(3));
				fw.append(',');
				fw.append(rs.getString(4));
				fw.append(',');
				fw.append(rs.getString(5));
				fw.append(',');
				fw.append(rs.getString(6));
				fw.append(',');
				fw.append(rs.getString(7));
				fw.append(',');
				fw.append(rs.getString(8));
				fw.append(',');
				fw.append(rs.getString(9));
				fw.append(',');
				fw.append(rs.getString(10));
				fw.append(',');
				fw.append(optional);
				fw.append(',');
				fw.append(optional2);
				fw.append(',');
				fw.append(time);
				fw.append(',');
				fw.append('\n');
			}
			System.out.println(filename);
			fw.flush();
			fw.close();
			conn.close();
			System.out.println("CSV File is created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exportData(String filename, String table, String optional, String optional2, String optional3,
			String time) {
		try {
			FileWriter fw = new FileWriter(filename, true);
			if (new File(filename).exists()) {
				FileReader fr = new FileReader(filename);
				char[] a = new char[50];
				fr.read(a);
				for (char c : a)
					fw.append(c);
				fr.close();
			}

			Connection conn = DBConnection.getConnection();
			String query = "select * from " + table;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				fw.append(rs.getString(1));
				fw.append(',');
				fw.append(rs.getString(2));
				fw.append(',');
				fw.append(rs.getString(3));
				fw.append(',');
				fw.append(rs.getString(4));
				fw.append(',');
				fw.append(rs.getString(5));
				fw.append(',');
				fw.append(rs.getString(6));
				fw.append(',');
				fw.append(rs.getString(7));
				fw.append(',');
				fw.append(rs.getString(8));
				fw.append(',');
				fw.append(rs.getString(9));
				fw.append(',');
				fw.append(rs.getString(10));
				fw.append(',');
				fw.append(optional);
				fw.append(',');
				fw.append(optional2);
				fw.append(',');
				fw.append(optional3);
				fw.append(',');
				fw.append(time);
				fw.append(',');
				fw.append('\n');
			}
			// System.out.println(filename);
			fw.flush();
			fw.close();
			conn.close();
			// System.out.println("CSV File is created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveCSVData(String filename, String uri, String optional1, String optional2, String optional3,
			String optional4, String optional5, String optional6, String optional7) {
		try {
			FileWriter fw = new FileWriter(filename, true);
			if (new File(filename).exists()) {
				FileReader fr = new FileReader(filename);
				char[] a = new char[50];
				fr.read(a);
				for (char c : a)
					fw.append(c);
				fr.close();
			}
			fw.append(uri);
			fw.append(',');
			fw.append(optional1);
			fw.append(',');
			fw.append(optional2);
			fw.append(',');
			fw.append(optional3);
			fw.append(',');
			fw.append(optional4);
			fw.append(',');
			fw.append(optional5);
			fw.append(',');
			fw.append(optional6);
			fw.append(',');
			fw.append(optional7);
			fw.append(',');
			fw.append('\n');
			fw.flush();
			fw.close();
			System.out.println("CSV File is created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exportData(String filename, String table, String optional) {
		try {
			FileWriter fw = new FileWriter(filename, true);
			if (new File(filename).exists()) {
				FileReader fr = new FileReader(filename);
				char[] a = new char[50];
				fr.read(a);
				for (char c : a)
					fw.append(c);
				fr.close();
			}

			Connection conn = DBConnection.getConnection();
			String query = "select * from " + table;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				fw.append(rs.getString(1));
				fw.append(',');
				fw.append(rs.getString(2));
				fw.append(',');
				fw.append(rs.getString(3));
				fw.append(',');
				fw.append(rs.getString(4));
				fw.append(',');
				fw.append(rs.getString(5));
				fw.append(',');
				fw.append(rs.getString(6));
				fw.append(',');
				fw.append(rs.getString(7));
				fw.append(',');
				fw.append(rs.getString(8));
				fw.append(',');
				fw.append(rs.getString(9));
				fw.append(',');
				fw.append(rs.getString(10));
				fw.append(',');
				fw.append(optional);
				fw.append(',');
				fw.append('\n');
			}
			fw.flush();
			fw.close();
			conn.close();
			System.out.println("CSV File is created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadBrazilianTitles() {
		DBFunctions dbFunctions = new DBFunctions();
		// http://mappings.dbpedia.org/server/ontology/classes/
		dbFunctions.getBrazillianItems("MusicalWork", IConstants.CATEGORY_MUSIC, 1000000000);
		dbFunctions.getBrazillianItems("Band", IConstants.CATEGORY_MUSIC, 1000000000);

		dbFunctions.getBrazillianItems("Film", IConstants.CATEGORY_MOVIE, 1000000000);
		dbFunctions.getBrazillianItems("MovieDirector", IConstants.CATEGORY_MOVIE, 1000000000);

		dbFunctions.getBrazillianItems("WrittenWork", IConstants.CATEGORY_BOOK, 1000000000);
		dbFunctions.getBrazillianItems("Writer", IConstants.CATEGORY_BOOK, 1000000000);
	}

	public void analyseOnlineEvaluationByUser(String similarityMethod) {
		NodeUtil.print("USERID\tSKIP\tADDS\tNOVEL\tSATIS\tUNDERS\tACC\tSTAR");

		List<Integer> userIds = getUsersFromOnlineEvaluation(similarityMethod);
		for (Integer userId : userIds) {
			int totalSkipUser = 0;
			int noveltyUser = 0;
			int satisfactionUser = 0;
			int understandUser = 0;
			int accuracyUser = 0;
			int starUser = 0;
			int addProfileUser = 0;

			List<String> seeds = getSeedsFromOnlineEvaluationByUser(userId, similarityMethod);

			for (String seed : seeds) {

				// NodeUtil.print("seed " + seed);

				int totalSkip = 0;
				int novelty = 0;
				int satisfaction = 0;
				int understand = 0;
				int accuracy = 0;
				int star = 0;
				int addProfile = 0;

				List<OnlineEvaluation> onlineEvaluations = getOnlineEvaluationsByUserAndSeed(userId, seed,
						similarityMethod);

				for (OnlineEvaluation onlineEvaluation : onlineEvaluations) {

					if (onlineEvaluation.getUri().contains(IConstants.PICK_ANOTHER_SEED)) {
						totalSkip++;
						continue;
					}

					novelty = novelty + Integer.valueOf(onlineEvaluation.getNovelty());
					satisfaction = satisfaction + Integer.valueOf(onlineEvaluation.getSatisfaction());
					understand = understand + Integer.valueOf(onlineEvaluation.getUnderstand());
					accuracy = accuracy + Integer.valueOf(onlineEvaluation.getAccuracy());
					star = star + Integer.valueOf(onlineEvaluation.getStar());
					if (Boolean.valueOf(onlineEvaluation.getAddprofile())) {
						addProfile = addProfile + 1;
					}

					// NodeUtil.print("uri "+onlineEvaluation.getUri()+"seed
					// "+onlineEvaluation.getSeed()+" totalSkip "+totalSkip+" total1 "+total1+"
					// total2 "+total2+" total3 "+total3+" total4 "+total4+" total5 "+total5);
				}

				totalSkipUser = totalSkipUser + totalSkip;
				noveltyUser = noveltyUser + novelty;
				satisfactionUser = satisfactionUser + satisfaction;
				understandUser = understandUser + understand;
				accuracyUser = accuracyUser + accuracy;
				starUser = starUser + star;
				addProfileUser = addProfileUser + addProfile;

				/*
				 * NodeUtil.print("USERID\tSKIP\tADDS\tNOVEL\tSATIS\tUNDERS\tACC\tSTAR");
				 * NodeUtil.print(userId + "\t" +
				 * StringUtilsNode.getDecimalFormat((float)totalSkip/IConstants.
				 * MAX_ONLINE_EVALUATIONS_PER_SEED) + "\t" +
				 * StringUtilsNode.getDecimalFormat((float)addProfile/IConstants.
				 * MAX_ONLINE_EVALUATIONS_PER_SEED) + "\t" +
				 * StringUtilsNode.getDecimalFormat((float)novelty/IConstants.
				 * MAX_ONLINE_EVALUATIONS_PER_SEED) + "\t" +
				 * StringUtilsNode.getDecimalFormat((float)satisfaction/IConstants.
				 * MAX_ONLINE_EVALUATIONS_PER_SEED) + "\t" +
				 * StringUtilsNode.getDecimalFormat((float)understand/IConstants.
				 * MAX_ONLINE_EVALUATIONS_PER_SEED) + "\t" +
				 * StringUtilsNode.getDecimalFormat((float)accuracy/IConstants.
				 * MAX_ONLINE_EVALUATIONS_PER_SEED) + "\t" +
				 * StringUtilsNode.getDecimalFormat((float)star/IConstants.
				 * MAX_ONLINE_EVALUATIONS_PER_SEED));
				 */

				// NodeUtil.print("totalSkip " + totalSkip + " addProfile " + addProfile + "
				// novelty " + novelty + " satisfaction " + satisfaction+ " understand " +
				// understand + " accuracy " + accuracy + " star " + star);

			}

			float realEvaluationsSize = seeds.size()
					- getSeedsFromOnlineEvaluationByUserWithSkip(userId, similarityMethod).size();
			;

			// NodeUtil.print(userId+"\t"+totalSkip + "\t" + addProfile + "\t" + novelty +
			// "\t" + satisfaction+ "\t" + understand + "\t" + accuracy + "\t" + star);
			NodeUtil.print(userId + "\t"
					+ StringUtilsNode
							.getDecimalFormat((float) totalSkipUser / (IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED))
					+ "\t"
					+ StringUtilsNode.getDecimalFormat(
							(float) addProfileUser / (IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED * realEvaluationsSize))
					+ "\t"
					+ StringUtilsNode.getDecimalFormat(
							(float) noveltyUser / (IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED * realEvaluationsSize))
					+ "\t"
					+ StringUtilsNode.getDecimalFormat((float) satisfactionUser
							/ (IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED * realEvaluationsSize))
					+ "\t"
					+ StringUtilsNode.getDecimalFormat(
							(float) understandUser / (IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED * realEvaluationsSize))
					+ "\t"
					+ StringUtilsNode.getDecimalFormat(
							(float) accuracyUser / (IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED * realEvaluationsSize))
					+ "\t" + StringUtilsNode.getDecimalFormat(
							(float) starUser / (IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED * realEvaluationsSize)));

		}

	}

	public static void main(String[] args) {

		DBFunctions dbFunctions = new DBFunctions();
		// dbFunctions.analyseOnlineEvaluationByUser(IConstants.LDSD_LOD);
		// System.out.println();

		// DBFunctions.loadBrazilianTitles();

		/*
		 * dbFunctions.updateRealImages(dbFunctions.getItemsByCategory(IConstants.
		 * CATEGORY_MOVIE)); System.out.println("CATEGORY_MOVIE FINISHED");
		 * dbFunctions.updateRealImages(dbFunctions.getItemsByCategory(IConstants.
		 * CATEGORY_BOOK)); System.out.println("CATEGORY_BOOK FINISHED");
		 * dbFunctions.updateRealImages(dbFunctions.getItemsByCategory(IConstants.
		 * CATEGORY_MUSIC)); System.out.println("CATEGORY_BOOK MUSIC");
		 */

		/*
		 * dbFunctions.insertNewBookInaSequence(
		 * "http://pt.dbpedia.org/resource/Jorge_Amado");
		 * dbFunctions.insertNewBookInaSequence(
		 * "http://pt.dbpedia.org/resource/Tieta_do_Agreste");
		 * dbFunctions.insertNewBookInaSequence(
		 * "http://pt.dbpedia.org/resource/Dona_Flor_e_Seus_Dois_Maridos");
		 * dbFunctions.insertNewBookInaSequence(
		 * "http://pt.dbpedia.org/resource/Capitães_da_Areia");
		 * dbFunctions.insertNewBookInaSequence(
		 * "http://pt.dbpedia.org/resource/Gabriela,_Cravo_e_Canela");
		 * 
		 * dbFunctions.extractCoverAndGetItemsByCategoryAndURI(IConstants.CATEGORY_BOOK,
		 * dbFunctions.getBookByURI("http://pt.dbpedia.org/resource/Jorge_Amado"));
		 * dbFunctions.extractCoverAndGetItemsByCategoryAndURI(IConstants.CATEGORY_BOOK,
		 * dbFunctions.getBookByURI("http://pt.dbpedia.org/resource/Tieta_do_Agreste"));
		 * dbFunctions.extractCoverAndGetItemsByCategoryAndURI(IConstants.CATEGORY_BOOK,
		 * dbFunctions.getBookByURI(
		 * "http://pt.dbpedia.org/resource/Dona_Flor_e_Seus_Dois_Maridos"));
		 * dbFunctions.extractCoverAndGetItemsByCategoryAndURI(IConstants.CATEGORY_BOOK,
		 * dbFunctions.getBookByURI("http://pt.dbpedia.org/resource/Capitães_da_Areia"))
		 * ;
		 * dbFunctions.extractCoverAndGetItemsByCategoryAndURI(IConstants.CATEGORY_BOOK,
		 * dbFunctions.getBookByURI(
		 * "http://pt.dbpedia.org/resource/Gabriela,_Cravo_e_Canela"));
		 * 
		 * 
		 */

		// DBFunctions.mysqlImport(IDatabaseConstants.PATH_TO_MYSQL_EXECUTABLES,IDatabaseConstants.DB_IP_ADDRESS,
		// IDatabaseConstants.DB_PORT, IDatabaseConstants.DB_USERNAME,
		// IDatabaseConstants.DB_PASSWORD,
		// IDatabaseConstants.DB_SCHEMA,"topncut","sql//","test");

		// dbFunctions.getItemsByCNNSizeAndConvertToNode(10,20,2);

		/*
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/20th_Century_Fox");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Animal");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Brazil");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/California"
		 * );
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Canada");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Chile");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/China");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Columbia_Pictures");
		 * dbFunctions.deleteBookLikeByUriAndUserID(
		 * "http://dbpedia.org/resource/Composer",208875);
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Contemporary_Christian_music");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Dublin");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/English_language");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Fantasy");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Film_director");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Film_score"
		 * ); dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Gospel_music");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Hardcover")
		 * ; dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/HarperCollins");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Hawaii");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Hip_hop_music");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Historical_fiction");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Indonesia")
		 * ;
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Ireland");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Jakarta");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/London");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Los_Angeles");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Manga");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Mexico");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/New_York");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Novel");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Ontario");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Pakistan");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Paramount_Pictures");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Paris");
		 * dbFunctions.deleteBookLikeByUriAndUserID(
		 * "http://dbpedia.org/resource/Pitcher",208875);
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Reggae");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Republic_of_Ireland");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Rhythm_and_blues");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Rock_music"
		 * ); dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Science_fiction");
		 * dbFunctions.deleteBookLikeByUriAndUserID(
		 * "http://dbpedia.org/resource/São_Paulo_FC",208875);
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Time_in_Azerbaijan");
		 * dbFunctions.insertUserProfile(208875,"http://dbpedia.org/resource/Uganda");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/United_States");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Warner_Bros.");
		 * dbFunctions.insertUserProfile(208875,
		 * "http://dbpedia.org/resource/Young-adult_fiction");
		 */

		// dbFunctions.deleteOnlineEvaluationByUserAndRecommendationUri(208845,"http://dbpedia.org/resource/Andrew_Stanton");
		/*
		 * //dbFunctions.insertOrUpdateUser("aa", "aa", "aa", "aa");
		 * dbFunctions.getItemsByCategory(IConstants.CATEGORY_MOVIE);
		 * System.out.println("CATEGORY_MOVIE FINISHED");
		 * //dbFunctions.searchItemsByTitle("love");
		 * dbFunctions.getItemsByCategory(IConstants.CATEGORY_MUSIC);
		 * System.out.println("CATEGORY_MUSIC FINISHED");
		 * dbFunctions.getItemsByCategory(IConstants.CATEGORY_BOOK);
		 * System.out.println("CATEGORY_BOOK FINISHED");
		 */

		// DBFunctions.testGeral();
	}

	public static void testCNNReducedHitRateLDSDLOD() {

		DBFunctions dbFunctions = new DBFunctions();
		dbFunctions.cleanEvaluationAndHitRate();
		dbFunctions.updateAllEvaluations();
		dbFunctions.calculateRRForAllMethods();
		dbFunctions.updateAllEvaluationHitRate();

		// dbFunctions.getEvaluationHitRate(40,1,IConstants.LDSD_LOD);

	}

	public static void testCNNReducedHitRate() {

		DBFunctions dbFunctions = new DBFunctions();
		dbFunctions.cleanEvaluationAndHitRate();
		dbFunctions.updateAllEvaluations();
		dbFunctions.calculateRRForAllMethods();
		dbFunctions.updateAllEvaluationHitRate();

		// dbFunctions.getEvaluationHitRate(40,1,IConstants.LDSD_LOD);

	}

	public static void testCNNReducedHitRate(String sim) {
		DBFunctions dbFunctions = new DBFunctions();
		dbFunctions.cleanEvaluationAndHitRate();
		dbFunctions.updateEvaluation(sim);
		dbFunctions.calculateRRForAllUsers(sim);
		dbFunctions.updateEvaluationHitRate(IConstants.LDSD_LOD);
	}

	public static void testHibridHitRate() {

		DBFunctions dbFunctions = new DBFunctions();

		if (new File("sql//HITRATE_LODICA.csv").exists()) {
			new File("sql//HITRATE_LODICA_" + IConstants.PASSANT_SIMILARITY + ".csv").delete();
			new File("sql//HITRATE_LODICA_" + IConstants.ABSTRACT_SIMILARITY + ".csv").delete();
		}

		if (new File("sql//prediction.sql").exists()) {
			new File("sql//prediction.sql").delete();
		}

		// CURRENT APPROACH

		/**/ DBFunctions.mysqlExport(IDatabaseConstants.PATH_TO_MYSQL_EXECUTABLES, IDatabaseConstants.DB_IP_ADDRESS,
				IDatabaseConstants.DB_PORT, IDatabaseConstants.DB_USERNAME, IDatabaseConstants.DB_PASSWORD,
				IDatabaseConstants.DB_SCHEMA, "prediction", "sql//");
		dbFunctions.deletePredictions();
		DBFunctions.mysqlImport(IDatabaseConstants.PATH_TO_MYSQL_EXECUTABLES, IDatabaseConstants.DB_IP_ADDRESS,
				IDatabaseConstants.DB_PORT, IDatabaseConstants.DB_USERNAME, IDatabaseConstants.DB_PASSWORD,
				IDatabaseConstants.DB_SCHEMA, "prediction", "sql//", "prediction");
		dbFunctions.updateAllEvaluations();
		dbFunctions.calculateRRForAllMethods();
		dbFunctions.updateAllEvaluationHitRate();
		DBFunctions.exportData("sql//HITRATE_LODICA.csv", "hitrate", "LODICA");

		dbFunctions.deletePredictions();
		DBFunctions.mysqlImport(IDatabaseConstants.PATH_TO_MYSQL_EXECUTABLES, IDatabaseConstants.DB_IP_ADDRESS,
				IDatabaseConstants.DB_PORT, IDatabaseConstants.DB_USERNAME, IDatabaseConstants.DB_PASSWORD,
				IDatabaseConstants.DB_SCHEMA, "prediction", "sql//", "prediction");
		dbFunctions.updateRankSimilarity(IConstants.LDSD, IConstants.ABSTRACT_SIMILARITY);
		// dbFunctions.updateRankSimilarity(IConstants.LDSD_LOD,IConstants.ABSTRACT_SIMILARITY);
		dbFunctions.updateAllEvaluations();
		dbFunctions.calculateRRForAllMethods();
		dbFunctions.updateAllEvaluationHitRate();
		DBFunctions.exportData("sql//HITRATE_LODICA_" + IConstants.ABSTRACT_SIMILARITY + ".csv", "hitrate",
				IConstants.ABSTRACT_SIMILARITY);

		dbFunctions.deletePredictions();
		DBFunctions.mysqlImport(IDatabaseConstants.PATH_TO_MYSQL_EXECUTABLES, IDatabaseConstants.DB_IP_ADDRESS,
				IDatabaseConstants.DB_PORT, IDatabaseConstants.DB_USERNAME, IDatabaseConstants.DB_PASSWORD,
				IDatabaseConstants.DB_SCHEMA, "prediction", "sql//", "prediction");
		dbFunctions.updateRankSimilarity(IConstants.LDSD, IConstants.PASSANT_SIMILARITY);
		// dbFunctions.updateRankSimilarity(IConstants.LDSD_LOD,IConstants.PASSANT_SIMILARITY);
		dbFunctions.updateAllEvaluations();
		dbFunctions.calculateRRForAllMethods();
		dbFunctions.updateAllEvaluationHitRate();
		DBFunctions.exportData("sql//HITRATE_LODICA_" + IConstants.PASSANT_SIMILARITY + ".csv", "hitrate",
				IConstants.PASSANT_SIMILARITY);

	}

	public static void main3(String[] args) {
		DBFunctions dbFunctions = new DBFunctions();

		dbFunctions.getUserIdsForLikedMoviesMusicsBooks();

		/*
		 * IConstants.FILTER_CATEGORY = true;
		 * dbFunctions.deleteSimilarityByUserIdAndMethod(4, IConstants.LDSD_LOD);
		 * dbFunctions.deletePredictionByUserIdAndSimilarityMethod(4,
		 * IConstants.LDSD_LOD);
		 * 
		 * for (int i = 0; i < 5; i++) { dbFunctions.insertUserProfile(4,
		 * "http://dbpedia.org/resource/Cork");
		 * //dbFunctions.deleteBookLikeByUriAndUserID("http://dbpedia.org/resource/Cork"
		 * ,4); //dbFunctions.deleteBookByUrigetLinksIncomingAndOutcoming(
		 * "http://dbpedia.org/resource/Cork");
		 * 
		 * }
		 */
		// dbFunctions.cleanPredictionAndEvaluationAndHitRate();
		// dbFunctions.updateAllEvaluations();
		// dbFunctions.calculateRRForAllMethods();
		// dbFunctions.updateAllEvaluationHitRate();
		// dbFunctions.getEvaluationHitRate(40,1,IConstants.LDSD_LOD);

	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main2(String[] args) throws Exception {
		for (int retries = 0;; retries++) {
			try {
				DBFunctions dbFunctions = new DBFunctions();
				// dbFunctions.collectDomainStatitics();
				// System.out.println(dbFunctions.getUserIdsWithLikedItemsByRange(60,70));
				dbFunctions.deletePredictionByUserId(1);
			} catch (Exception e) {
				if (retries < 10000) {
					NodeUtil.print("restarting #" + retries);
					continue;
				} else {
					throw e;
				}
			}
		}
	}

	/**
	 * calculateRRWithoutScalingForAllMethods
	 */
	private void calculateRRForAllMethods() {
		calculateRRForAllUsers(IConstants.LDSD);
		calculateRRForAllUsers(IConstants.LDSD_LOD);
	}

	/**
	 * updateAllEvaluationHitRate
	 */
	private void updateAllEvaluationHitRate() {
		deleteHitRate();
		updateEvaluationHitRate(IConstants.LDSD);
		updateEvaluationHitRate(IConstants.LDSD_LOD);
	}

	/**
	 * 
	 */
	private void updateAllEvaluations() {
		deleteEvaluation();
		updateEvaluation(IConstants.LDSD);
		updateEvaluation(IConstants.LDSD_LOD);
	}

	/**
	 * @param similarityMethod
	 */
	private void updateEvaluation(String similarityMethod) {

		// StringBuilder stringBuilder = new StringBuilder();

		LinkedList<Integer> userIds = getUserIdsOfPredictedItems(similarityMethod);

		if (userIds.isEmpty()) {
			NodeUtil.print("No users users evluated yet");
			System.exit(0);
		}

		List<Evaluation> allEvaluations = new ArrayList<Evaluation>();

		for (Integer userId : userIds) {

			List<NodePrediction> evaluations = getPredictionEvaluations(userId, similarityMethod);

			for (NodePrediction nodePrediction : evaluations) {

				// double rr = calculateRR();

				int correctPrediction = 0;

				int incorrectPrediction = 0;

				// NodeUtil.printPrediction(nodePrediction);
				// NodeUtil.print(nodePrediction.getNode().getURI());
				// NodeUtil.print(nodePrediction.getOriginalCandidate());

				if (nodePrediction.getPredictedLabel().equals(IConstants.LIKE)) {
					correctPrediction++;
				} else {
					incorrectPrediction++;
				}

				// String query = nodePrediction.getNode().getURI() +"\t"+
				// correctPrediction+"\t"+ incorrectPrediction+"\t"+similarityMethod+"\t"+
				// nodePrediction.getUserId()+"\t"+nodePrediction.getPredictionScore()+"\t"+
				// null+"\t"+nodePrediction.getOriginalCandidate()+"\t"+null;
				// stringBuilder.append(query);
				// StringUtilsNode.writeFile("eval",query,"txt", true);
				// NodeUtil.print(query);

				// NodeUtil.printPrediction(nodePrediction);
				/*
				 * try { //TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) {
				 * // TODO Auto-generated catch block e.printStackTrace(); }
				 */

				allEvaluations
						.add(new Evaluation(nodePrediction.getNode().getURI(), correctPrediction, incorrectPrediction,
								similarityMethod, nodePrediction.getUserId(), nodePrediction.getPredictionScore(),
								Double.MIN_VALUE, nodePrediction.getOriginalCandidate(), 0));
				// insertOrUpdateEvaluation(nodePrediction.getNode().getURI(),correctPrediction,
				// incorrectPrediction,similarityMethod,
				// nodePrediction.getUserId(),nodePrediction.getPredictionScore(),
				// -1d,nodePrediction.getOriginalCandidate(), 0);

			}
		}

		// Evaluation evaluation =
		// getDatabaseConnection().getEvaluation(predictedLeftOut.getNode().getURI(),
		// IConstants.USE_ICA, predictedLeftOut.getUserId());
		// NodeUtil.print("\nEVALUATION ONE LEAVE OUT...\n");
		// NodeUtil.printEvaluation(evaluation);

		insertOrUpdateEvaluations(allEvaluations);
		// NodeUtil.print("Evaluations calculated for "+similarityMethod);
	}

	public void updateRankSimilarity(String similarityMethod, String hybridStrategy) {

		LinkedList<Integer> userIds = getUserIdsOfPredictedItems(similarityMethod);

		for (Integer userId : userIds) {

			Lodica.userId = userId;

			// NodeUtil.print(userId);

			List<String> uriSeeds = this.getSeedUrisByUserAndSimilarity(userId, similarityMethod);

			for (String uri : uriSeeds) {

				// NodeUtil.print(uri);

				List<NodePrediction> nodePredictions = Lodica.getDatabaseConnection().getPredictions(uri, userId,
						isUsingICA(similarityMethod));

				double treshold = 0.0;

				for (NodePrediction nodePrediction : nodePredictions) {

					if (!nodePrediction.getPredictedLabel().equals(IConstants.LIKE)
							&& nodePrediction.getSimilarityMethod().equals(similarityMethod)) {

						if (hybridStrategy.equals(IConstants.ABSTRACT_SIMILARITY)) {

							String uriSeed = SparqlWalk.getDescription(uri);

							String predDescURI = SparqlWalk.getDescription(nodePrediction.getNode().getURI());

							// NodeUtil.print(uriSeed);
							// NodeUtil.print(predDescURI);

							double simi = LuceneCosineSimilarity.getCosineSimilarity(uriSeed, predDescURI);

							// NodeUtil.print(nodePrediction.getPredictionScore());
							// NodeUtil.print(simi);
							// double similarity = Math.max(nodePrediction.getPredictionScore(),simi);
							double similarity = StringUtilsNode.scale(1d - (simi), 0, 1, 1, -1);
							nodePrediction.setPredictionScore(similarity);
							// NodeUtil.print(similarity);
							if (similarity >= treshold) {
								nodePrediction.setPredictedLabel(IConstants.LIKE);
								// RESOLVING ALREADY FOR THE OTHER TYPE
								NodePrediction nodePredictionOtherType = Lodica.getDatabaseConnection().getPrediction(
										uri, nodePrediction.getNode().getURI(), userId, !isUsingICA(similarityMethod));
								if (nodePredictionOtherType.getPredictedLabel() != IConstants.LIKE) {
									nodePredictionOtherType.setPredictedLabel(IConstants.LIKE);
									nodePredictionOtherType.setPredictionScore(simi);
									insertOrUpdatePrediction(nodePredictionOtherType);
								}
							}
						} else if (hybridStrategy.equals(IConstants.PASSANT_SIMILARITY)) {

							// NodeUtil.print(uri);
							// NodeUtil.print(nodePrediction.getNode().getURI());

							double simi = Classifier.calculateSemanticDistance(uri, nodePrediction.getNode().getURI(),
									similarityMethod, userId);

							// NodeUtil.print(simi);
							// double similarity = Math.max(nodePrediction.getPredictionScore(),simi);
							nodePrediction.setPredictionScore(simi);
							// NodeUtil.print(similarity);
							if (simi >= treshold) {
								nodePrediction.setPredictedLabel(IConstants.LIKE);
								// RESOLVING ALREADY FOR THE OTHER TYPE
								NodePrediction nodePredictionOtherType = Lodica.getDatabaseConnection().getPrediction(
										uri, nodePrediction.getNode().getURI(), userId, !isUsingICA(similarityMethod));
								if (nodePredictionOtherType.getPredictedLabel() != IConstants.LIKE) {
									nodePredictionOtherType.setPredictedLabel(IConstants.LIKE);
									nodePredictionOtherType.setPredictionScore(simi);
									insertOrUpdatePrediction(nodePredictionOtherType);
								}
							}
						}
					}
				}
				insertOrUpdatePredictions(nodePredictions);
			}
		}

		NodeUtil.print("Rank updated for hybrid strategy " + hybridStrategy);
	}

	/**
	 * calculateRRWithoutScaling
	 * 
	 * @param similarityMethod
	 */
	private void calculateRRForAllUsers(String similarityMethod) {
		LinkedList<Integer> userIds = new LinkedList<Integer>();
		userIds = getUserIdsOfEvaluatedItems(similarityMethod);
		for (Integer userId : userIds) {
			LinkedList<Evaluation> evaluations = new LinkedList<Evaluation>();
			evaluations = new LinkedList<Evaluation>(getEvaluationsByMethodSortedByScore(similarityMethod, userId));

			// NodeUtil.printEvaluations(evaluations);
			Lodica.calculateRR(evaluations);
			// NodeUtil.printEvaluations(evaluations);
			insertOrUpdateEvaluations(evaluations);
		}
		// NodeUtil.print("RR calculated for "+similarityMethod);
	}

	/**
	 * cleanPredictionAndEvaluationAndHitRate
	 */
	public void cleanPredictionAndEvaluationAndHitRate() {
		clearPrediction();
		clearEvaluation();
		deleteHitRate();
		NodeUtil.print("Predictions and Evaluation and HitRate are cleared up");
	}

	/**
	 * cleanPredictionAndEvaluationAndHitRate
	 */
	public void cleanEvaluationAndHitRate() {
		clearEvaluation();
		deleteHitRate();
		// NodeUtil.print("Evaluation and HitRate are cleared up");
	}

	/**
	 * @return
	 */
	public void setSafe(int number) {
		Connection conn = DBConnection.getConnection();
		String query = "USE LOD;SET SQL_SAFE_UPDATES=" + number;
		executeAndClose(conn, query);
	}

	public void deleteFrom(String tableName) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM lod." + tableName;
		executeAndClose(conn, query);
	}

	public void clearEvaluation() {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`evaluation` WHERE `lod`.`evaluation`.`uri` != 'JUST TO PASS THE MYSQL SAFE MODE' ";
		executeAndClose(conn, query);
	}

	public void clearPrediction() {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`prediction` WHERE `lod`.`prediction`.`seed` != 'JUST TO PASS THE MYSQL SAFE MODE'";
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteEvaluationByUserId(int userId) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`evaluation` WHERE `lod`.`evaluation`.`uri` != 'JUST TO PASS THE MYSQL SAFE MODE'  and  `lod`.`evaluation`.`userid` = "
				+ userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public static void deleteItem(String uri) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`item` WHERE `lod`.`item`.`uri` = '" + StringEscapeUtils.escapeSql(uri) + "'";
		// NodeUtil.print(query);
		NodeUtil.print("Removing " + uri);
		executeAndClose(conn, query);
	}

	public void deleteOnlineEvaluationByUserAndRecommendationUri(int userId, String uri) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`onlineevaluation` WHERE `lod`.`onlineevaluation`.`uri` = '"
				+ StringEscapeUtils.escapeSql(uri) + "'  and  `lod`.`onlineevaluation`.`userid` = " + userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteLink(String uri) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`link` WHERE `lod`.`link`.`uri1` = '" + StringEscapeUtils.escapeSql(uri)
				+ "'  or  `lod`.`link`.`uri2` = '" + StringEscapeUtils.escapeSql(uri) + "'";
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteIndirectLink(String uri) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`indirect_link` WHERE `lod`.`indirect_link`.`uri1` = '"
				+ StringEscapeUtils.escapeSql(uri) + "'  or  `lod`.`indirect_link`.`uri3` = '"
				+ StringEscapeUtils.escapeSql(uri) + "'";
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteOnlineEvaluation(int userId, String uri, String seed, String similaritymethod) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`onlineevaluation` WHERE `lod`.`onlineevaluation`.`uri` = '"
				+ StringEscapeUtils.escapeSql(uri) + "'  and `lod`.`onlineevaluation`.`seed` = '"
				+ StringEscapeUtils.escapeSql(seed) + "'  and" + "`lod`.`onlineevaluation`.`sim` = '" + similaritymethod
				+ "'  and `lod`.`onlineevaluation`.`userid` = " + userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteOnlineEvaluation(int userId, String seed) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`onlineevaluation` WHERE `lod`.`onlineevaluation`.`seed` = '"
				+ StringEscapeUtils.escapeSql(seed) + "'  and `lod`.`onlineevaluation`.`userid` = " + userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteOnlineEvaluationBySeed(int userId, String seed, String similaritymethod, String strategy) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`onlineevaluation` WHERE `lod`.`onlineevaluation`.`seed` = '"
				+ StringEscapeUtils.escapeSql(seed) + "'  and" + "`lod`.`onlineevaluation`.`sim` = '" + similaritymethod
				+ "'  and" + "`lod`.`onlineevaluation`.`strategy` = '" + strategy
				+ "'  and  `lod`.`onlineevaluation`.`userid` = " + userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteOnlineEvaluationBySeed(int userId, String seed, String similaritymethod) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`onlineevaluation` WHERE `lod`.`onlineevaluation`.`seed` = '"
				+ StringEscapeUtils.escapeSql(seed) + "'  and" + "`lod`.`onlineevaluation`.`sim` = '" + similaritymethod
				+ "'  and" + "  `lod`.`onlineevaluation`.`userid` = " + userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	/**
	 * @param userId
	 */
	public void deletePredictionByUserId(int userId) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`prediction` WHERE `lod`.`prediction`.`seed` != 'JUST TO PASS THE MYSQL SAFE MODE'  and  `lod`.`prediction`.`userid` = "
				+ userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deletePredictionByUserIdAndSimilarityMethod(int userId, String sim) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`prediction` WHERE `lod`.`prediction`.`seed` != 'JUST TO PASS THE MYSQL SAFE MODE'  and  `lod`.`prediction`.`sim` = \""
				+ sim + "\" and  `lod`.`prediction`.`userid` = " + userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	/**
	 * @param userId
	 */
	public void deleteSimilarityByUserId(int userId) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `movielens`.`semantic` WHERE `lod`.`semantic`.`uri1` != 'JUST TO PASS THE MYSQL SAFE MODE' `lod`.`semantic`.`uri2` != 'JUST TO PASS THE MYSQL SAFE MODE'  and  `lod`.`semantic`.`userid` = "
				+ userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteSimilarityByUserIdAndMethod(int userId, String sim) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `movielens`.`semantic` WHERE `lod`.`semantic`.`uri1` != 'JUST TO PASS THE MYSQL SAFE MODE' and `lod`.`semantic`.`uri2` != 'JUST TO PASS THE MYSQL SAFE MODE' and  `lod`.`semantic`.`sim` = \""
				+ sim + "\"  and  `movielens`.`semantic`.`userid` = " + userId;
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteBookByUri(String uri) {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`book` WHERE  `lod`.`book`.`uri` = \"" + uri + "\"";
		// NodeUtil.print(query);
		executeAndClose(conn, query);
		NodeUtil.print("Book deleted");
	}

	public void deleteBookLikeByUriAndUserID(String uri, int userId) {
		Node bookNode = getBookByURIAndConvertToNode(uri);
		if (bookNode != null) {
			int bookid = Integer.valueOf(bookNode.getId());
			Connection conn = DBConnection.getConnection();
			String query = "DELETE FROM `lod`.`book_like` WHERE `lod`.`book_like`.`bookid`  = " + bookid
					+ " and  `lod`.`book_like`.`userid` = \"" + userId + "\"";
			// NodeUtil.print(query);
			executeAndClose(conn, query);
			NodeUtil.print("Delete book liked by uri " + uri + " for userId " + userId + "");
		} else {
			NodeUtil.print("There is no book with uri" + uri);
		}
	}

	public void deleteHitRate() {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`hitrate`";
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteEvaluation() {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`evaluation`";
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deletePredictions() {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`prediction`";
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	public void deleteSemantics() {
		Connection conn = DBConnection.getConnection();
		String query = "DELETE FROM `lod`.`prediction`";
		// NodeUtil.print(query);
		executeAndClose(conn, query);
	}

	/**
	 * @param userId
	 * @param similarityMethod
	 */
	public void calculateHitHate(int userId, String similarityMethod) {
		int top1 = 0;
		int top3 = 0;
		int top5 = 0;
		int top10 = 0;
		int top15 = 0;
		int top20 = 0;
		int top25 = 0;
		int top30 = 0;
		int after = 0;
		top1 = top1 + getEvaluationHitRate(1, userId, similarityMethod);
		top3 = top3 + getEvaluationHitRate(3, userId, similarityMethod);
		top5 = top5 + getEvaluationHitRate(5, userId, similarityMethod);
		top10 = top10 + getEvaluationHitRate(10, userId, similarityMethod);
		top15 = top15 + getEvaluationHitRate(15, userId, similarityMethod);
		top20 = top20 + getEvaluationHitRate(20, userId, similarityMethod);
		top25 = top25 + getEvaluationHitRate(25, userId, similarityMethod);
		top30 = top30 + getEvaluationHitRate(30, userId, similarityMethod);
		after = after + getEvaluationHitRate(IConstants.HIT_RATE_TOP_MAX, userId, similarityMethod);
		insertOrUpdateHitRate(top1, top3, top5, top10, top15, top20, top25, top30, after, similarityMethod);
	}

	/**
	 * @param similarityMethod
	 */
	public void updateEvaluationHitRate(String similarityMethod) {
		LinkedList<Integer> userIds = new LinkedList<Integer>();
		userIds = getUserIdsOfEvaluatedItems(similarityMethod);
		int top1 = 0;
		int top3 = 0;
		int top5 = 0;
		int top10 = 0;
		int top15 = 0;
		int top20 = 0;
		int top25 = 0;
		int top30 = 0;
		int after = 0;
		for (Integer userId : userIds) {
			top1 = top1 + getEvaluationHitRate(1, userId, similarityMethod);
			top3 = top3 + getEvaluationHitRate(3, userId, similarityMethod);
			top5 = top5 + getEvaluationHitRate(5, userId, similarityMethod);
			top10 = top10 + getEvaluationHitRate(10, userId, similarityMethod);
			top15 = top15 + getEvaluationHitRate(15, userId, similarityMethod);
			top20 = top20 + getEvaluationHitRate(20, userId, similarityMethod);
			top25 = top25 + getEvaluationHitRate(25, userId, similarityMethod);
			top30 = top30 + getEvaluationHitRate(30, userId, similarityMethod);
			after = after + getEvaluationHitRate(IConstants.HIT_RATE_TOP_MAX, userId, similarityMethod);
		}
		insertOrUpdateHitRate(top1, top3, top5, top10, top15, top20, top25, top30, after, similarityMethod);
		// NodeUtil.print("Hit rates calculated for "+similarityMethod);
	}

	/**
	 * @param top1
	 * @param top3
	 * @param top5
	 * @param top10
	 * @param top15
	 * @param top20
	 * @param top25
	 * @param top30
	 * @param after
	 * @param sim
	 * @return
	 */
	public int insertOrUpdateHitRate(int top1, int top3, int top5, int top10, int top15, int top20, int top25,
			int top30, int after, String sim) {
		Connection conn = DBConnection.getConnection();
		String query = "REPLACE INTO `lod`.`hitrate` (`top1`,`top3`,`top5`, `top10`,`top15`,`top20`,`top25`,`top30`,`after`,`sim`) "
				+ " VALUES (" + top1 + "," + top3 + "," + top5 + "," + top10 + "," + top15 + "," + top20 + "," + top25
				+ "," + top30 + "," + after + ",\"" + sim + "\")";
		// NodeUtil.print(query);
		return executeAndClose(conn, query);
	}

	/**
	 * @param sim
	 * @return
	 */
	public HitRate getHitRate(String sim) {
		HitRate hitRate = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.hitrate where `sim`= ?";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, sim);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				hitRate = new HitRate(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return hitRate;
	}

	/**
	 * @param sim
	 * @return
	 */
	public int getTotalEvaluationsOfLastUser(String sim) {
		int total = 0;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT count(original_candidate) FROM lod.evaluation where `sim`= ? and userid = ( select max(userid)  FROM lod.evaluation where original_candidate=uri )";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, sim);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				total = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return total;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public int getTotalEvaluationsDoneByUserSoFar(String sim, int userid) {
		int total = 0;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT count(original_candidate) FROM lod.prediction where `sim`= ? and seed = 'SEED_EVALUATION' and  `userid`= ?";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, sim);
			ps.setInt(2, userid);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs != null && rs.next()) {
				total = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return total;
	}

	/**
	 * @param sim
	 * @return
	 */
	public int getNextUserIdToEvaluate(String sim) {
		int lastId = 0;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT count(original_candidate),userid FROM lod.prediction where seed = 'SEED_EVALUATION' and userid = (select max(userid)  FROM lod.prediction where seed = 'SEED_EVALUATION' and original_candidate=uri )";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				if (rs.getInt(1) == (IConstants.TOTAL_CANDIDATES_TO_COMPARE_HIT_RATE + 1) * 2) {
					lastId = rs.getInt(2) + 1;
				} else {
					lastId = rs.getInt(2);
				}
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return lastId;
	}

	/**
	 * @param hit
	 * @param userid
	 * @param sim
	 * @return
	 */
	public int getEvaluationHitRate(int hit, int userid, String sim) {

		List<Evaluation> evaluationList = new ArrayList<Evaluation>();

		List<Evaluation> itemsInLastPosition = new ArrayList<Evaluation>();

		int hitRate = 0;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.evaluation where  `userid` = ? and `sim`= ? order by score desc LIMIT ?";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, sim);
			ps.setInt(3, hit);
			ResultSet rs = ps.executeQuery();
			int lowestPosition = Integer.MIN_VALUE;
			while (rs != null && rs.next()) {
				if (rs.getInt(9) > lowestPosition) {
					lowestPosition = rs.getInt(9);
				}
				evaluationList.add(new Evaluation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9)));
			}

			closeQuery(conn, ps);

			// TimeUnit.MILLISECONDS.sleep(100);

			// NodeUtil.printEvaluations(evaluationList);

			for (Evaluation evaluation : evaluationList) {
				if (evaluation.getPosition() == lowestPosition) {
					itemsInLastPosition.add(evaluation);
				}
			}

			// NodeUtil.printEvaluations(auxEval);

			int totalToRetrieve = itemsInLastPosition.size();

			evaluationList.removeAll(itemsInLastPosition);

			// NodeUtil.printEvaluations(evaluationList);

			evaluationList.addAll(getEvaluationByPositionRandom(lowestPosition, userid, sim, totalToRetrieve));

			// NodeUtil.printEvaluations(evaluationList);

			for (Evaluation evaluation : evaluationList) {
				if (evaluation.getUri().equals(evaluation.getOriginalCandidate())) {
					hitRate = 1;
					break;
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hitRate;
	}

	public List<Evaluation> getEvaluationByPositionRandom(int position, int userid, String sim, int limit) {
		List<Evaluation> evaluationList = new ArrayList<Evaluation>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.evaluation where `position` = " + position + " and `userid` = " + userid
					+ " and `sim`= \"" + sim + "\" ORDER BY RAND() LIMIT " + limit;
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				evaluationList.add(new Evaluation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return evaluationList;
	}

	public User getUser(String email, String password) {
		User user = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM `lod`.`user` where `email` = '" + email + "' and `password` = '" + password
					+ "'";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return user;
	}

	public User getUser(String first, String second, String email, String password) {
		User user = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM `lod`.`user` where `first` = '" + first + "' and `second` = '" + second
					+ "' and `email` = '" + email + "' and `password` = '" + password + "'";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return user;
	}

	public List<User> getUser(String email) {
		List<User> userList = new ArrayList<User>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.user where `email` = " + email;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				userList.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return userList;
	}

	public void insertOrUpdateEvaluation(String uri, int correct, int incorrect, boolean usePredictedClassification,
			int userId, double score, Double rr, String originalCandidate, Integer position) {
		String sim = checkSimilarityMethod(usePredictedClassification);
		insertOrUpdateEvaluation(uri, correct, incorrect, sim, userId, score, rr, originalCandidate, position);
	}

	public void insertOrUpdateEvaluation(String uri, int correct, int incorrect, String sim, int userId, double score,
			Double rr, String originalCandidate, Integer position) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				// for (Evaluation evaluation : evaluations) {
				String query = "REPLACE INTO `lod`.`evaluation` (`uri`,`correct`,`incorrect`, `sim`,`userid`,`score`,`rr`,`original_candidate`,`position`) VALUES (?,?,?,?,?,?,?,?,?)";
				// System.out.println(query);
				ps = conn.prepareStatement(query);
				ps.setString(1, uri);
				ps.setInt(2, correct);
				ps.setInt(3, incorrect);
				ps.setString(4, sim);
				ps.setInt(5, userId);
				ps.setDouble(6, score);
				ps.setDouble(7, rr);
				ps.setString(8, originalCandidate);
				ps.setInt(9, position);
				ps.execute();
				// }
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

	// ----- Create by Patrick -----

	public void insertOrUpdateTag(String tag) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO `lod`.`tag` (`tag`) VALUES (?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, tag);
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

	public ArrayList<Tag> getNameOfTagsOfFilms(List<Integer> tagsFilmesAvaliados, int limit) {
		ArrayList<Tag> nameOfTagsFilmsHasRating = new ArrayList<Tag>();
		List<Integer> tagsDosFilmes = DBFunctions.findTagOfDocuments(tagsFilmesAvaliados, limit);
		for (int i = 0; i < tagsDosFilmes.size(); i++) {
			if (tagsDosFilmes.get(i) != null) {
				nameOfTagsFilmsHasRating.add(new Tag(i, DBFunctions.getNameOfTag(tagsDosFilmes.get(i))));
			}
		}
		return nameOfTagsFilmsHasRating;
	}

	public ArrayList<Tag> getNameOfTagsOfFilm(int tagsFilmesAvaliado) {
		ArrayList<Tag> nameOfTagsFilmsHasRating = new ArrayList<Tag>();

		nameOfTagsFilmsHasRating.add(new Tag(tagsFilmesAvaliado, DBFunctions.getNameOfTag(tagsFilmesAvaliado)));

		return nameOfTagsFilmsHasRating;
	}

	public ArrayList<Tag> getNameOfFilm(int idDocument) {
		ArrayList<Tag> nameOfTagsFilmsHasRating = new ArrayList<Tag>();

		nameOfTagsFilmsHasRating.add(new Tag(DBFunctions.getNameOfTag(idDocument)));

		return nameOfTagsFilmsHasRating;
	}

	public void insertOrUpdateDocument(String desc) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO `lod`.`document` (`desc`) VALUES (?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, desc);
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

	public void insertOrUpdateTagging(int idDocument, int idUser, int idTag) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO `lod`.`tagging` (`iddocument`, `iduser`, `idtag`) VALUES (?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, idDocument);
				ps.setInt(2, idUser);
				ps.setInt(3, idTag);
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

	public void insertOrUpdateTagSim(int idTag1, int idTag2, double sim, String tipo) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO `tag_sim` ( `idtag1`, `idtag2`, `sim`, `tipo`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `idtag1` = `idtag1`, `idtag2` = `idtag2`";
				ps = conn.prepareStatement(query);
				ps.setInt(1, idTag1);
				ps.setInt(2, idTag2);
				ps.setDouble(3, sim);
				ps.setString(4, tipo);
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

	public void insertOrUpdateSemanticRaking(int idTag1, int idTag2, String sim, double score, double sumsemantic,
			int iduser) {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO `movielens`.`semantic_raking` (`uri1`, `uri2`, `sim`, `score`, `sumsemantic`, `userid`) VALUES (?,?,?,?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, idTag1);
				ps.setInt(2, idTag2);
				ps.setString(3, sim);
				ps.setDouble(4, score);
				ps.setDouble(5, sumsemantic);
				ps.setInt(6, iduser);
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

	public double findSemantic(String type, int id_user, int id_filme) {

		double valor = 0;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT sumsemantic from `lod`.`semantic_raking` where  `sim`= ? AND `userid`= ? AND `uri2` = ? ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, type);
			ps.setInt(2, id_user);
			ps.setInt(3, id_filme);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				valor = rs.getDouble(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return valor;
	}

	public static int quantidade(String type, int id_user) {

		int valor = 0;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT COUNT(*) from `lod`.`semantic_raking` where `sim`= ? AND `userid`= ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, type);
			ps.setInt(2, id_user);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				valor = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return valor;
	}

	/*
	 * Salva o Resultado dos calculos feito do usuário
	 */
	public void saveResult(int idUser, String userModelList, List<Integer> testSetList, double p10, double p20,
			double p30, double precision, double map, String type, double ap10, double ap20, double ap30) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

				String testSet = "";
				for (Integer integer : testSetList) {
					testSet = Integer.toString(integer);
					testSet = testSet + ", ";
				}
	

		try {

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				String query = "INSERT INTO `movielens`.`result` (`iduser`, `usermodel`, `testset`, `p10`, `p20`, `p30`, `precision`, `map`, `type`, `ap10`, `ap20`, `ap30`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, idUser);
				ps.setString(2, userModelList);
				ps.setString(3, testSet);
				ps.setDouble(4, p10);
				ps.setDouble(5, p20);
				ps.setDouble(6, p30);
				ps.setDouble(7, precision);
				ps.setDouble(8, map);
				ps.setString(9, type);
				ps.setDouble(10, ap10);
				ps.setDouble(11, ap20);
				ps.setDouble(12, ap30);
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

	public double calculeMap(String metric, String type1) {

		PreparedStatement ps = null;
		double resultado = 0.0;

		// String userModel = TaggingFactory.listNameFilmString(userModelList);

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT AVG(" + metric + ") as oi FROM `movielens`.`result` WHERE type = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, metric);
			ps.setString(1, type1);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				resultado = rs.getDouble(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return resultado;
	}

	public void saveMapPrecison(String type, double map_3, double map_5, double map_10, double p_3, double p_5,
			double p_10) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		// String userModel = TaggingFactory.listNameFilmString(userModelList);

		try {

			try {
				String query = "INSERT INTO `movielens`.`map_precision` (`algorithm`, `map_10`,  `map_20`, `map_30`,`p_10`,`p_20`,`p_30`) VALUES (?, ?, ?, ? ,? ,? ,?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, type);
				ps.setDouble(2, map_3);
				ps.setDouble(3, map_5);
				ps.setDouble(4, map_10);
				ps.setDouble(5, p_3);
				ps.setDouble(6, p_5);
				ps.setDouble(7, p_10);
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

	public Tag findTag(String name) {
		Tag tag = null;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`tag` where `tag`= ? ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				tag = new Tag(rs.getInt(1), rs.getString(2));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return tag;
	}

	public static String findTagById(int idTag) {
		String tag = null;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`tag` where `id`= ? ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idTag);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				tag = rs.getString(2);

			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return tag;
	}

	public double isTagSimResult(Tag item1, Tag item2, String similarity) {

		try {

			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`tag_sim` where `idtag1`= ? AND `idtag2`= ? AND `tipo` = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, item1.getId());
			ps.setInt(2, item2.getId());
			ps.setString(3, similarity);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				System.out.println("Encontrada no BANDO DE DADOS -> ID TAG 1 -> " + rs.getInt(1) + " |  ID TAG 2 -> "
						+ rs.getInt(2) + " |  Valor -> " + rs.getDouble(3) + " | sim -> " + rs.getString(4));
				return rs.getDouble(3);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return 0;
	}

	public boolean findTagEquals(int tag1, int tag2) {

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`tag_sim` where `idtag1`= ? AND `idtag2`=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, tag1);
			ps.setInt(2, tag2);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {

				if (rs.getInt(1) == tag1 && rs.getInt(2) == tag2 || rs.getInt(1) == tag2 && rs.getInt(2) == tag1) {
					return false;
				}
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public boolean isFilmsExistSemantic(int uri1, int uri2, int userId, String sim) {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct b.score from `lod`.`semantic_raking` as b where ((b.uri1 =  ? and b.uri2 = ? and b.sim = ?) OR (b.uri1 =  ? and b.uri2 = ?  and b.sim = ?))";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, uri1);
			ps.setInt(2, uri2);
			ps.setString(3, sim);
			ps.setInt(4, uri2);
			ps.setInt(5, uri1);
			ps.setString(6, sim);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				return true;
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	// retorna a lista simplesmente para calculo de ap
	public List<Integer> listByAp(int userid, String type) {
		List<Integer> listforAP = new ArrayList<Integer>();

		try {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT * FROM semantic_raking WHERE userid = ? AND sim = ? AND score != 0 AND score < 1 ORDER BY score DESC ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, type);
			ResultSet rs = ps.executeQuery();
			int cont = 1;

			System.out.println(" \n ---------- Lista de filmes para calculo de ap ordenado por score apenas" + type
					+ "--------------- \n");
			System.out.println(" CLASSIFICAÇÃO |  USUARIO |  FILME RECOMENDADO |  TIPO  | SIMILARIDADE ");
			while (rs != null && rs.next()) {

				listforAP.add(rs.getInt(2));
				cont = cont + 1;

				System.out.println("       " + cont + "           " + rs.getInt(6) + "         " + rs.getInt(3)
						+ "                " + rs.getString(4));/* + "    " + rs.getString(5))*/;
				System.out.println(" ---------------------------------------------------------------------------");

			}

			CBRecommender.end = System.currentTimeMillis();

			System.out
					.println("   Tempo Total do Calculo:    " + String.valueOf(CBRecommender.end - CBRecommender.init));

			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listforAP;
	}

	public static boolean filmesRelevants(int idUser, int idFilm) {

		List<Cenario> cenarios = new ArrayList<Cenario>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM cenario1 WHERE id_user = ? AND id_filme = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idUser);
			ps.setInt(2, idFilm);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {

				Cenario cenario = new Cenario(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getString(6));
				cenarios.add(cenario);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		for (Cenario cenario2 : cenarios) {
			if (cenario2.getRelevance().equals("relevant")) {
				return true;
			}
		}

		return false;
	}
	
	public static double isFilmRelevant(int idUser, int idFilm) {

		List<Rating> ratingsRelevants = new ArrayList<Rating>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT avg(rating) FROM rating WHERE id_movie = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			//ps.setInt(1, idUser);
			ps.setInt(1, idFilm);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {

				if (rs.getDouble(1) > 4) {
					Rating rating  = new Rating(idUser, idFilm, rs.getDouble(1), "relevants");
					ratingsRelevants.add(rating);
					return rs.getDouble(1);
				}
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		
		return 0;
	}


	public static List<Ratings> createTestSetByMax(int iduser, int limit, int max) {

		List<Ratings> relevants = new ArrayList<Ratings>();
		List<Ratings> listRelevants = new ArrayList<Ratings>();
		Random random = new Random();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT id, iduser, iddocument, AVG(rating) FROM rating WHERE iduser != ? and iddocument not in (SELECT iddocument FROM rating where iduser = ?) GROUP BY iddocument";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, iduser);
			ps.setInt(2, iduser);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				Ratings rating = new Ratings(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4));
				if (rs.getDouble(4) <= max) {
					relevants.add(rating);
				}
			}

			for (int i = 0; i < limit;) {

				int index = random.nextInt(relevants.size());
				if (isExistFimlInTag(relevants.get(index).getIddocument())) {
					System.out.println("FILME ->  " + relevants.get(index).getIddocument());

					i++;
					listRelevants.add(relevants.get(index));
					System.out.println(
							"LIMITE: " + i + " TestSet: " + " ID: " + " Rating: " + relevants.get(index).getRating());
				}
			}

			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return listRelevants;
	}

	public static List<Ratings> createTestSet(int iduser, int limit, int limit2, int min) {
		List<Ratings> testSet = new ArrayList<Ratings>();
		List<Ratings> irrelevants = new ArrayList<Ratings>();
		List<Ratings> relevants = new ArrayList<Ratings>();
		List<Ratings> listIrrelevants = new ArrayList<Ratings>();
		List<Ratings> listRelevants = new ArrayList<Ratings>();
		Random random = new Random();

		try {
			Connection conn = DBConnection.getConnection();
			// String query = "SELECT id, iduser, iddocument, AVG(rating) FROM rating WHERE
			// iduser != ? and iddocument not in (SELECT iddocument FROM rating where iduser
			// = ?) GROUP BY iddocument";

			String query = "SELECT t1.iduser, t1.iddocument, d1.rating FROM tagging as t1 "
					+ "	   JOIN rating as d1 on t1.iddocument = d1.iddocument and t1.iduser = d1.iduser "
					+ "      where t1.idUser != ? and t1.idtag in (SELECT distinct(t2.idtag) FROM tagging as t2 "
					+ "   																			           JOIN rating as d2 on t2.iddocument = d2.iddocument and t2.iduser = d2.iduser "
					+ "																							  where t2.idUser = ? and d2.rating = 5)";

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, iduser);
			ps.setInt(2, iduser);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				System.out.println("RS1 " + rs.getInt(1));
				System.out.println("RS2 " + rs.getInt(2));
				System.out.println("RS3 " + rs.getInt(3));
				// System.out.println("RS4 " + rs.getInt(4));

				Ratings rating = new Ratings(rs.getInt(1), rs.getInt(2), (double) rs.getInt(3));

				if (rating.getRating() < 4) {
					irrelevants.add(rating);
				} else {
					relevants.add(rating);
				}
			}

			if (!irrelevants.isEmpty()) {
				for (int i = 0; i < limit;) {
					int index = random.nextInt(irrelevants.size());
					if (isExistFimlInTag(irrelevants.get(index).getIddocument())) {
						i++;
						System.out.println("Valor de i: " + i + " limit: " + limit);
						System.out.println("FILME ->  " + irrelevants.get(index).getIddocument());
						listIrrelevants.add(irrelevants.get(index));
						System.out.println("LIMITE: " + i + " TestSet: " + " ID: " + " Rating: "
								+ irrelevants.get(index).getRating());
					}
				}
			}

			for (int j = 0; j < limit2;) {

				int index = random.nextInt(relevants.size());
				if (isExistFimlInTag(relevants.get(index).getIddocument())) {
					j++;
					System.out.println("Valor de j: " + j + " limit: " + limit2);
					System.out.println("FILME ->  " + relevants.get(index).getIddocument());
					listRelevants.add(relevants.get(index));
					System.out.println(
							"LIMITE: " + j + " TestSet: " + " ID: " + " Rating: " + relevants.get(index).getRating());
				}
			}

			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		testSet.addAll(listIrrelevants);
		testSet.addAll(listRelevants);

		return testSet;
	}

	public static void inrrelevantesFilmes(int iduser) {

		List<Ratings> irrelevants = new ArrayList<Ratings>();
		List<Ratings> relevants = new ArrayList<Ratings>();
		List<Ratings> listIrrelevants = new ArrayList<Ratings>();
		List<Ratings> listRelevants = new ArrayList<Ratings>();
		DBFunctions dbfunctions = new DBFunctions();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT t1.iduser, t1.iddocument, d1.rating FROM tagging as t1 "
					+ "	   JOIN rating as d1 on t1.iddocument = d1.iddocument and t1.iduser = d1.iduser "
					+ "      where t1.idUser != ? and t1.idtag in (SELECT distinct(t2.idtag) FROM tagging as t2 "
					+ "   																			           JOIN rating as d2 on t2.iddocument = d2.iddocument and t2.iduser = d2.iduser "
					+ "																							  where t2.idUser = ? and d2.rating = 5)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, iduser);
			ps.setInt(2, iduser);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				Ratings rating = new Ratings(rs.getInt(1), rs.getInt(2), (double)rs.getInt(3));
				if (rating.getRating() >= 4) {

					// System.out.println("Relevante: " + rs.getDouble(4));

					relevants.add(rating);
				} else {
					//System.out.println("Irrelevante: " + rs.getDouble(4));

					irrelevants.add(rating);
				}
			}

			for (int i = 0; i < irrelevants.size(); i++) {

				System.out.println("FILME ->  " + irrelevants.get(i).getIddocument());
				// listIrrelevants.add(irrelevants.get(i));
				System.out.println("Usuario :" + iduser + " LIMITE: " + i + " TestSet: " + " ID: " + " Rating: "
						+ irrelevants.get(i).getRating());
				dbfunctions.insertOrUpdateRelevante("irrelevant", iduser, irrelevants.get(i).getIddocument());
				// aqui vai ter um metodo para inserir na tabela de cenario no campo
				// inrrelevante os id dos filmes inrrelevantes

			}

			for (int j = 0; j < relevants.size(); j++) {

				System.out.println("FILME ->  " + relevants.get(j).getIddocument());
				// listRelevants.add(relevants.get(j));
				System.out.println("Usuario :" + iduser + "LIMITE: " + j + " TestSet: " + " ID: " + " Rating: "
						+ relevants.get(j).getRating());
				dbfunctions.insertOrUpdateRelevante("relevant", iduser, relevants.get(j).getIddocument());
				// aqui vai ter um metodo para inserir na tabela de cenario no campo relevante
				// os id dos filmes relvantes
			}

			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		// testSet.addAll(listIrrelevants);

		// testSet.addAll(listRelevants);

		// return testSet;
	}

	/*
	 * Busca o nome do filme pelo ID
	 */
	public static String findNameOfFilm(int idDocument) {
		String film = null;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `movielens`.`movie` where `id`= ? ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idDocument);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				film = rs.getString(2);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return film;
	}

	public static String getNameOfTag(int idTag) {
		String tag = null;

		try {
			Thread.sleep(200);
		} catch (InterruptedException ex) {
		}

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`tag` where `id`= ? ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idTag);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				tag = rs.getString(2);

			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return tag;
	}

	public boolean isUserAndDocument(int idUser, int idDocument) {

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`rating` where `iddocument`= ? AND `iduser`=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idUser);
			ps.setInt(2, idDocument);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				return false;
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public void insertOrUpdateRating(int idDocument, int idUser, int rating) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		if (isUserAndDocument(idDocument, idUser)) {

			try {
				try {
					String query = "INSERT INTO `lod`.`rating` (`iddocument`, `iduser`, `rating`) VALUES (?,?,?)";
					ps = conn.prepareStatement(query);
					ps.setInt(1, idDocument);
					ps.setInt(2, idUser);
					ps.setInt(3, rating);
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

	public List<Integer> createUserModel(int idUser, int limit) {
		List<Ratings> list = new ArrayList<Ratings>();
		List<Integer> document = new ArrayList<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.rating WHERE iduser = ? AND rating = 5 ORDER BY RAND()";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idUser);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				System.out.println("avaliados pelo usuario -> " + rs.getString(2) + " Filmes " + rs.getString(3));
				Ratings ratings = new Ratings(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4));
				list.add(ratings);
			}

			int count = 0;
			for (int i = 0; i < list.size(); i++) {

				if (isExistFimlInTag(list.get(i).getIddocument())) {
					if (findTagById(list.get(i).getIddocument()) != null) {
						count++;
						document.add(list.get(i).getIddocument());
						System.out.println("LIMITE: " + i + " TestSet: " + " ID: " + list.get(i).getRating());
					}
				} else {
					System.out.println("Não existe filme em TAGGING");
				}

				if (count == limit) {
					break;
				}
			}

			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return document;
	}

	public static List<Integer> findTagOfDocuments(List<Integer> userModel, int limit) {

		List<Integer> tags = new ArrayList<Integer>();

		for (int i = 0; i < userModel.size(); i++) {
			try {
				Connection conn = DBConnection.getConnection();
				String query = "SELECT * FROM lod.tagging WHERE iddocument = ? LIMIT ?";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, userModel.get(i));
				ps.setInt(2, limit);
				ResultSet rs = ps.executeQuery();

				while (rs != null && rs.next()) {

					if (!isTagOfDocument(tags, rs.getInt(4))) {
						System.out.println(
								"DOCUMENT -> " + userModel.get(i) + " RESULTADO findTagOfDocumentS -> " + rs.getInt(4));
						tags.add(rs.getInt(4));
					}
				}
				closeQuery(conn, ps);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
		return tags;
	}

	public static List<Integer> findUsers() {

		List<Integer> users = new ArrayList<Integer>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT(iduser) AS usuario FROM tagging";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				System.out.println("USER: -> " + rs.getInt(1));
				users.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return users;
	}

	public List<Integer> findTagOfDocument(int filme) {

		List<Integer> tags = new ArrayList<Integer>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT * FROM lod.tagging WHERE iddocument = ? LIMIT 5";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, filme);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {

				if (!isTagOfDocument(tags, rs.getInt(4))) {
					tags.add(rs.getInt(4));
				}
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return tags;
	}

	public List<Integer> findTagOfDocumentsLimitTag(List<Integer> filmes, int limit) {

		List<Integer> tags = new ArrayList<Integer>();

		for (int i = 0; i < filmes.size(); i++) {
			try {
				Connection conn = DBConnection.getConnection();
				String query = "SELECT * FROM lod.tagging WHERE iddocument = ? LIMIT ?";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setInt(1, filmes.get(i));
				ps.setInt(2, limit);
				ResultSet rs = ps.executeQuery();

				while (rs != null && rs.next()) {

					if (!isTagOfDocument(tags, rs.getInt(1))) {
						System.out.println("FILME -> " + filmes.get(i) + " TAG -> " + rs.getInt(1));
						tags.add(rs.getInt(4));
					}
				}
				closeQuery(conn, ps);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
		return tags;
	}

	public void insertOrUpdateCenario(int id_user, String tags_user, String tags_testset, int id_filme) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO `lod`.`cenario1` (`id_user`, `tags_user`,`tags_testset`, `id_filme`) VALUES (?, ?, ? ,?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, id_user);
				ps.setString(2, tags_user);
				ps.setString(3, tags_testset);
				ps.setInt(4, id_filme);
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

	public void insertOrUpdateCenarioSemTag(int id_user, String tags_user) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "INSERT INTO `lod`.`cenario1` (`id_user`, `tags_user`) VALUES (?, ?)";
				ps = conn.prepareStatement(query);
				ps.setInt(1, id_user);
				ps.setString(2, tags_user);
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

	public void insertOrUpdateRelevante(String relevantornot, int id_user, int id_filme) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;

		try {
			try {
				String query = "UPDATE `lod`.`cenario1` SET relevance = ? WHERE id_user = ? AND id_filme = ?";
				ps = conn.prepareStatement(query);
				ps.setString(1, relevantornot);
				ps.setInt(2, id_user);
				ps.setInt(3, id_filme);
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

	public List<Integer> findTagOfDocumentWithLimitTag(int filme, int limit) {

		List<Integer> tags = new ArrayList<Integer>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT * FROM lod.tagging WHERE iddocument = ? LIMIT ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, filme);
			ps.setInt(2, limit);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {

				if (!isTagOfDocument(tags, rs.getInt(4))) {
					tags.add(rs.getInt(4));
				}
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return tags;
	}

	public static List<Cenario> selectCenario(int id_user) {
		Cenario cenario = null;

		List<Cenario> list = new ArrayList<Cenario>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM cenario1 WHERE id_user = ? ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id_user);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				cenario = new Cenario(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6));
				list.add(cenario);

			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return list;
	}

	public List<Integer> findTagOfDocumentNotRating(int filme) {

		List<Integer> tags = new ArrayList<Integer>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.tagging WHERE iddocument = ? GROUP BY idtag";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, filme);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {

				if (!isTagOfDocument(tags, rs.getInt(4))) {
					tags.add(rs.getInt(4));
				}
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return tags;
	}

	public static boolean isTagOfDocument(List<Integer> tags, int i) {
		for (Integer tag : tags) {
			if (tag.equals(i)) {
				return true;
			}
		}
		return false;
	}

	/* RAKING */
	public List<String> rankedItemsByUser(int idUser) {

		List<String> document = new ArrayList<String>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT uri2 FROM lod.semantic where userid = ? ORDER BY SCORE DESC";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idUser);

			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				document.add(findNameOfFilm(rs.getInt(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return document;

	}

	public List<String> rakingCorrectItems(int rating, int limit) {

		List<String> document = new ArrayList<String>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.rating where rating = ? LIMIT ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, rating);
			ps.setInt(2, limit);

			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				document.add(findNameOfFilm(rs.getInt(2)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return document;

	}

	public List<Integer> testFindDocumentUserNotHasRating(int idUser) {
		List<Integer> document = new ArrayList<Integer>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.rating WHERE iduser != ? LIMIT 10";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idUser);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				document.add(rs.getInt(4));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return document;
	}

	public static List<Integer> getTagOfDocument(int idDocument) {
		List<Integer> document = new ArrayList<Integer>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT (idtag), iddocument, idtag, id FROM lod.tagging WHERE idDocument = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idDocument);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				document.add(rs.getInt(4));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return document;
	}

	public static List<String> getNameOfTagOfDocument(int idDocument) {
		List<String> tag = new ArrayList<String>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT (idtag), iddocument, idtag, id FROM lod.tagging WHERE idDocument = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idDocument);
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {

				tag.add(findTagById(rs.getInt(3)));
				System.out.println("Document -> " + rs.getString(2) + " Tag -> " + rs.getString(3));

			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return tag;
	}

	public static boolean quantTagsFilms(int idDocument) {
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT count(*) as quant_tags FROM lod.tagging WHERE iddocument = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idDocument);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {

				if (rs.getInt(1) >= 5) {
					return true;
				}
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return hasBefore;

	}

	public static boolean isExistFimlInTag(int idDocument) {

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * FROM lod.tagging WHERE iddocument = ? ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, idDocument);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				return true;
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	// -------------------------------------------------------------------------------------------------

	/**
	 * @param userid
	 * @param uri
	 * @param seed
	 * @param sim
	 * @param strategy
	 * @param star
	 * @param familiar
	 * @param boring
	 * @param interesting
	 * @param diverse
	 */
	public void insertOrUpdateUser(String first, String second, String email, String password, String sex,
			String agerange, String country) {

		TreeSet<Integer> trees = new TreeSet<Integer>();
		trees.add(getMaxUserIDByBookLikes());
		trees.add(getMaxUserIDByMoovieLikes());
		trees.add(getMaxUserIDByMusicLikes());
		trees.add(getMaxUserId());

		int userid = trees.last();
		userid++;

		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				// for (Evaluation evaluation : evaluations) {
				String query = "REPLACE INTO `lod`.`user` (`userid`,`first`,`second`,`email`,`password`,`sex`,`agerange`,`country`) VALUES (?,?,?,?,?,?,?,?)";
				// System.out.println(query);
				ps = conn.prepareStatement(query);
				ps.setInt(1, userid);
				ps.setString(2, first);
				ps.setString(3, second);
				ps.setString(4, email);
				ps.setString(5, password);
				ps.setString(6, sex);
				ps.setString(7, agerange);
				ps.setString(8, country);
				ps.execute();
				// }
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

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public boolean checkTopNCNN(int userid, String sim, int profilesize, int cnncutsize, String cutstrategy) {
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS (select userid from `lod`.`topncut` where `userid`= ? and sim = ? and  profilesize = ? and `cnncutsize`= ? and `cutstrategy`= ?) ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, sim);
			ps.setInt(3, profilesize);
			ps.setInt(4, cnncutsize);
			ps.setString(5, cutstrategy);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	public void insertOrUpdateTopNCNN(int userid, String sim, int profilesize, int cnncutsize, String cutstrategy,
			int top1, int top3, int top5, int top10, int top15, int top20, int top25, int top30, int after,
			String evaluationtime) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				// for (Evaluation evaluation : evaluations) {
				String query = "REPLACE INTO `lod`.`topncut` (`userid`,`sim`,`profilesize`,`cnncutsize`,`cutstrategy`,`top1`,`top3`,`top5`, `top10`,`top15`,`top20`,`top25`,`top30`,`after`,`evaluationtime`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				// System.out.println(query);
				ps = conn.prepareStatement(query);
				ps.setInt(1, userid);
				ps.setString(2, sim);
				ps.setInt(3, profilesize);
				ps.setInt(4, cnncutsize);
				ps.setString(5, cutstrategy);
				ps.setInt(6, top1);
				ps.setInt(7, top3);
				ps.setInt(8, top5);
				ps.setInt(9, top10);
				ps.setInt(10, top15);
				ps.setInt(11, top20);
				ps.setInt(12, top25);
				ps.setInt(13, top30);
				ps.setInt(14, after);
				ps.setString(15, evaluationtime);
				ps.execute();
				// }
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

	public void insertOrUpdateCNNCUTTest(int userid, String uri, int profilesize, int cnncutsize, int cnnsize,
			int totalpredictedlike, int allrandom, int alltu, int randomtu) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				// for (Evaluation evaluation : evaluations) {
				String query = "REPLACE INTO `lod`.`cnncuttest` (`userid`,`uri`,`profilesize`,`cnncutsize`,`cnnsize`,`totalpredictedlike`,`allrandom`,`alltu`,`randomtu`) VALUES (?,?,?,?,?,?,?,?,?)";
				// System.out.println(query);
				ps = conn.prepareStatement(query);
				ps.setInt(1, userid);
				ps.setString(2, uri);
				ps.setInt(3, profilesize);
				ps.setInt(4, cnncutsize);
				ps.setInt(5, cnnsize);
				ps.setInt(6, totalpredictedlike);
				ps.setInt(7, allrandom);
				ps.setInt(8, alltu);
				ps.setInt(9, randomtu);

				ps.execute();
				// }
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

	public void insertOrUpdateOnlineEvaluation(int userid, String uri, String seed, String sim, String strategy,
			int star, String accuracy, String understand, String statisfaction, String novelty, String addprofile) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				// for (Evaluation evaluation : evaluations) {
				String query = "REPLACE INTO `lod`.`onlineevaluation` (`userid`,`uri`,`seed`,`sim`,`strategy`,`star`,`accuracy`,`understand`,`statisfaction`,`novelty`,`addprofile`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
				// System.out.println(query);
				ps = conn.prepareStatement(query);
				ps.setInt(1, userid);
				ps.setString(2, uri);
				ps.setString(3, seed);
				ps.setString(4, sim);
				ps.setString(5, strategy);
				ps.setInt(6, star);
				ps.setString(7, accuracy);
				ps.setString(8, understand);
				ps.setString(9, statisfaction);
				ps.setString(10, novelty);
				ps.setString(11, addprofile);
				ps.execute();
				// }
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

	public void updateOnlineEvaluationByUserAndRecommendationUriWithAddProfile(int userid, String uri,
			String addprofile) {
		List<OnlineEvaluation> onlineEvaluations = getOnlineEvaluationsByUserAndURI(userid, uri);
		for (OnlineEvaluation onlineEvaluation : onlineEvaluations) {
			insertOrUpdateOnlineEvaluation(onlineEvaluation.getUserid(), onlineEvaluation.getUri(),
					onlineEvaluation.getSeed(), onlineEvaluation.getSimilarityMethod(), onlineEvaluation.getStrategy(),
					onlineEvaluation.getStar(), onlineEvaluation.getAccuracy(), onlineEvaluation.getUnderstand(),
					onlineEvaluation.getSatisfaction(), onlineEvaluation.getNovelty(), addprofile);
		}
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<OnlineEvaluation> getOnlineEvaluationLog(int userid, String uri, String seed, String similarityMethod,
			String strategy, int maxeval) {
		List<OnlineEvaluation> onlineEvaluations = getTotalOnlineEvaluationByUser(userid);
		return onlineEvaluations;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<OnlineEvaluation> getTotalOnlineEvaluationByUser(int userid) {
		List<OnlineEvaluation> onlineEvaluations = new ArrayList<OnlineEvaluation>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluations.add(new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluations;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<OnlineEvaluation> getOnlineEvaluationsByUserAndURI(int userid, String uri) {
		List<OnlineEvaluation> onlineEvaluations = new ArrayList<OnlineEvaluation>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and uri = ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, uri);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluations.add(new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluations;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<OnlineEvaluation> getOnlineEvaluationsByUserAndSeed(int userid, String seed, String similarityMethod) {
		List<OnlineEvaluation> onlineEvaluations = new ArrayList<OnlineEvaluation>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and seed = ? and `sim`= ?  ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluations.add(new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluations;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<String> getOnlineEvaluationURIsByUserAndSeed(int userid, String seed, String similarityMethod) {
		List<String> onlineEvaluationURIs = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and seed = ? and `sim`= ?  ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluationURIs.add(rs.getString(2));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluationURIs;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<OnlineEvaluation> getOnlineEvaluationsByUserAndSeed(int userid, String seed, String similarityMethod,
			String strategy) {
		List<OnlineEvaluation> onlineEvaluations = new ArrayList<OnlineEvaluation>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and seed = ? and `sim`= ? and `strategy`= ?  ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, similarityMethod);
			ps.setString(4, strategy);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluations.add(new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluations;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<OnlineEvaluation> getOnlineEvaluationsAddedToProfileByUserAndSeed(int userid, String seed,
			String similarityMethod, String strategy) {
		List<OnlineEvaluation> onlineEvaluations = new ArrayList<OnlineEvaluation>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and seed = ? and `sim`= ? and `strategy`= ? and addprofile = 'true' ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, similarityMethod);
			ps.setString(4, strategy);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluations.add(new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluations;
	}

	public OnlineEvaluation getOnlineEvaluationsByUserAndUriAndSeed(int userid, String seed, String uri,
			String similarityMethod, String strategy) {
		OnlineEvaluation onlineEvaluation = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and seed = ? and uri = ? and `sim`= ? and `strategy`= ?  ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, uri);
			ps.setString(4, similarityMethod);
			ps.setString(5, strategy);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluation = new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluation;
	}

	public OnlineEvaluation getOnlineEvaluationsByUserAndUriAndSeed(int userid, String seed, String uri,
			String similarityMethod) {
		OnlineEvaluation onlineEvaluation = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and seed = ? and uri = ? and `sim`= ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, uri);
			ps.setString(4, similarityMethod);

			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluation = new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluation;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<String> getSeedsFromOnlineEvaluationByUser(int userid, String similarityMethod) {
		List<String> seeds = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct(seed) from `lod`.`onlineevaluation` where `userid`= ? and `sim`= ?";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				seeds.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return seeds;
	}

	public List<String> getSeedsFromOnlineEvaluationByUser(int userid, String similarityMethod, String strategy) {
		List<String> seeds = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct(seed) from `lod`.`onlineevaluation` where `userid`= ? and `sim`= ? and `strategy`= ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, similarityMethod);
			ps.setString(3, strategy);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				seeds.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return seeds;
	}

	public List<String> getSeedsFromOnlineEvaluationByUserWithSkip(int userid, String similarityMethod,
			String strategy) {
		List<String> seeds = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct(seed) from `lod`.`onlineevaluation` where `userid`= ? and `sim`= ? and `strategy`= ? and uri LIKE '"
					+ IConstants.PICK_ANOTHER_SEED + "%'";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, similarityMethod);
			ps.setString(3, strategy);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				seeds.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return seeds;
	}

	public List<String> getSeedsFromOnlineEvaluationByUserWithSkip(int userid, String similarityMethod) {
		List<String> seeds = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct(seed) from `lod`.`onlineevaluation` where `userid`= ? and `sim`= ? and uri LIKE '"
					+ IConstants.PICK_ANOTHER_SEED + "%'";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, similarityMethod);

			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				seeds.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return seeds;
	}

	public List<Integer> getUsersFromOnlineEvaluation(String similarityMethod) {
		List<Integer> userIds = new ArrayList<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct(userid) from `lod`.`onlineevaluation` where `sim`= ?  ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, similarityMethod);

			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				userIds.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return userIds;
	}

	public List<Integer> getUsersFromOnlineEvaluation(String similarityMethod, String strategy) {
		List<Integer> userIds = new ArrayList<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct(userid) from `lod`.`onlineevaluation` where `sim`= ? and `strategy`= ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, similarityMethod);
			ps.setString(2, strategy);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				userIds.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return userIds;
	}

	public List<Integer> getUsersFromOnlineEvaluatioWithSKIP(String similarityMethod) {
		List<Integer> userIds = new ArrayList<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct(userid) from `lod`.`onlineevaluation` where `sim`= ? and uri LIKE '"
					+ IConstants.PICK_ANOTHER_SEED + "%'";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				userIds.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return userIds;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<String> getSeedsFromOnlineEvaluationByUserWithSKIP(int userid, String similarityMethod) {
		List<String> seeds = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT distinct(seed) from `lod`.`onlineevaluation` where `userid`= ? and `sim`= ? and uri LIKE '"
					+ IConstants.PICK_ANOTHER_SEED + "%'";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				seeds.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return seeds;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<String> getUrisFromOnlineEvaluationByUserAndSeedWithSKIP(int userid, String seed,
			String similarityMethod) {
		List<String> urisOnlineEvaluation = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT uri from `lod`.`onlineevaluation` where `userid`= ? and `seed`= ? and `sim`= ? and uri LIKE '"
					+ IConstants.PICK_ANOTHER_SEED + "%'";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				urisOnlineEvaluation.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return urisOnlineEvaluation;
	}

	public List<String> getUrisFromOnlineEvaluationByUserAndSeedWithSKIP(int userid, String seed) {
		List<String> urisOnlineEvaluation = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT uri from `lod`.`onlineevaluation` where `userid`= ? and `seed`= ? and uri LIKE '"
					+ IConstants.PICK_ANOTHER_SEED + "%'";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				urisOnlineEvaluation.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return urisOnlineEvaluation;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<String> getUrisFromOnlineEvaluationByUserAndSeed(int userid, String seed, String similarityMethod) {
		List<String> urisOnlineEvaluation = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT uri from `lod`.`onlineevaluation` where `userid`= ? and `seed`= ? and `sim`= ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				urisOnlineEvaluation.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return urisOnlineEvaluation;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<String> getUrisFromOnlineEvaluationByUserAndSeedWithoutSKIP(int userid, String seed,
			String similarityMethod) {
		List<String> urisOnlineEvaluation = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT uri from `lod`.`onlineevaluation` where `userid`= ? and `seed`= ? and `sim`= ?  and uri LIKE '"
					+ IConstants.PICK_ANOTHER_SEED + "%' ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, seed);
			ps.setString(3, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				urisOnlineEvaluation.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return urisOnlineEvaluation;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public List<String> getUrisFromOnlineEvaluationByUserAndSeedWithoutSKIP(int userid, String similarityMethod) {
		List<String> urisOnlineEvaluation = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT uri from `lod`.`onlineevaluation` where `userid`= ?  and `sim`= ?  and uri NOT LIKE '"
					+ IConstants.PICK_ANOTHER_SEED + "%' ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				urisOnlineEvaluation.add(new String(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return urisOnlineEvaluation;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public boolean checkCNNCutTest(int userid, String uri, int cnncutsize, int profilesize, int cnnsize) {
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS (select uri from `lod`.`cnncuttest` where `userid`= ? and uri = ? and  profilesize = ? and `cnncutsize`= ? and `cnnsize`= ?) ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, uri);
			ps.setInt(3, profilesize);
			ps.setInt(4, cnncutsize);
			ps.setInt(5, cnnsize);

			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public boolean checkCNNCutTest(int userid, int cnncutsize, int profilesize, int cnnsize) {
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS (select uri from `lod`.`cnncuttest` where `userid`= ? and  profilesize = ? and `cnncutsize`= ? and `cnnsize`= ?) ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);

			ps.setInt(2, profilesize);
			ps.setInt(3, cnncutsize);
			ps.setInt(4, cnnsize);

			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public OnlineEvaluation getOnlineEvaluationByStrategy(int userid, String uri, String seed, String similarityMethod,
			String strategy) {
		OnlineEvaluation onlineEvaluation = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and uri = ? and `seed`= ? and `sim`= ? and `strategy`= ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, uri);
			ps.setString(3, seed);
			ps.setString(4, similarityMethod);
			ps.setString(5, strategy);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluation = new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluation;
	}

	/**
	 * @param sim
	 * @param userid
	 * @return
	 */
	public OnlineEvaluation getOnlineEvaluation(int userid, String uri, String seed, String similarityMethod) {
		OnlineEvaluation onlineEvaluation = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`onlineevaluation` where `userid`= ? and uri = ? and `seed`= ? and `sim`= ?  ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, uri);
			ps.setString(3, seed);
			ps.setString(4, similarityMethod);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				onlineEvaluation = new OnlineEvaluation(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return onlineEvaluation;
	}

	public void insertOrUpdateEvaluations(List<Evaluation> evaluations) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				for (Evaluation evaluation : evaluations) {
					String query = "REPLACE INTO `lod`.`evaluation` (`uri`,`correct`,`incorrect`, `sim`,`userid`,`score`,`rr`,`original_candidate`,`position`) VALUES (?,?,?,?,?,?,?,?,?)";
					// System.out.println(query);
					ps = conn.prepareStatement(query);
					ps.setString(1, evaluation.getUri());
					ps.setInt(2, evaluation.getCorrect());
					ps.setInt(3, evaluation.getIncorrect());
					ps.setString(4, evaluation.getSimilarityMethod());
					ps.setInt(5, evaluation.getUserId());
					ps.setDouble(6, evaluation.getScore());
					ps.setDouble(7, evaluation.getRr());
					ps.setString(8, evaluation.getOriginalCandidate());
					ps.setInt(9, evaluation.getPosition());
					ps.execute();
				}
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

	/**
	 * @return
	 */
	public int insertPrediction(String seed, String uri, String evaluationlabel, String predictedlabel,
			boolean usePredictedClass, Double score, int graphStructure, int userid, String originalCandidate,
			String why) {
		String sim = checkSimilarityMethod(usePredictedClass);
		Connection conn = DBConnection.getConnection();
		String query = "INSERT INTO `lod`.`prediction` (`seed`, `uri`, `evaluationlabel`,`predictedlabel`, `sim`, `score`, `graph_structure`,`userid`, `original_candidate`, `why`) "
				+ " VALUES (\"" + seed + "\" , \"" + uri + "\" , \"" + evaluationlabel + "\", \"" + predictedlabel
				+ "\" ,\"" + sim + "\", " + score + ", " + graphStructure + "," + userid + ",\"" + originalCandidate
				+ "\",\"" + why + "\" )";
		return executeAndClose(conn, query);
	}

	/**
	 * @return
	 */
	public void saveIncomingAndOutgoingLinks(String uri1, List<Resource> resources) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				for (Resource resource : resources) {
					ps = conn.prepareStatement(
							"INSERT INTO `lod`.`link` (`uri1`, `uri2`) VALUES (?,?)  ON DUPLICATE KEY UPDATE `uri1` = `uri1` , `uri2` = `uri2`");
					ps.setString(1, uri1);
					ps.setString(2, resource.getURI());
					ps.execute();
				}
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

	/**
	 * @return
	 */
	public int insertLink(String uri1, String uri2) {
		Connection conn = DBConnection.getConnection();
		String query = "INSERT INTO `lod`.`link` (`uri1`, `uri2`) " + " VALUES (\"" + uri1 + "\" , \"" + uri2 + "\")";
		return executeAndClose(conn, query);
	}

	/**
	 * @param uri1
	 *            - init
	 * @param uri2
	 *            - mid
	 * @param uri3
	 *            - end
	 * @param method
	 * @return
	 */
	public int insertIndirectLink(String uri1, String uri2, String uri3, String method) {
		Connection conn = DBConnection.getConnection();
		String query = "INSERT INTO `movielens`.`indirect_link` (`uri1`,`uri2`,`uri3`,`method`) VALUES (\"" + uri1 + "\" , \""
				+ uri2 + "\", \"" + uri3 + "\",\"" + method + "\")";
		return executeAndClose(conn, query);
	}

	/**
	 * @return
	 */
	public void insertIndirectLinks(String uri1, List<Resource> indirectResources, String uri3, String method) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				for (Resource resource : indirectResources) {
					ps = conn.prepareStatement(
							"INSERT INTO `movielens`.`indirect_link` (`uri1`,`uri2`,`uri3`,`method`) VALUES (?,?,?,?)  ON DUPLICATE KEY UPDATE `uri1` = `uri1`,`uri2` = `uri2`,`uri3` = `uri3`,`method` = `method`");
					ps.setString(1, uri1);
					ps.setString(2, resource.getURI());
					ps.setString(3, uri3);
					ps.setString(4, method);
					ps.execute();
				}
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

	/**
	 * @return
	 */
	public void insertDirectLinks(String uri1, List<Resource> directLinks, String uri2, String method) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				for (Resource resource : directLinks) {
					ps = conn.prepareStatement(
							"INSERT INTO `movielens`.`indirect_link` (`uri1`,`uri2`,`uri3`,`method`) VALUES (?,?,?,?)  ON DUPLICATE KEY UPDATE `uri1` = `uri1`,`uri2` = `uri2`,`uri3` = `uri3`,`method` = `method`");
					ps.setString(1, uri1);
					ps.setString(2, resource.getURI());
					ps.setString(3, uri2);
					ps.setString(4, method);
					ps.execute();
				}
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

	/**
	 * @return
	 */
	public static void insertItem(Item item) {
		insertItem(item.getUri(), item.getImg(), item.getCategory());
	}

	/**
	 * @return
	 */
	public static void insertItem(String uri, String image, String category) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {

				ps = conn.prepareStatement(
						"REPLACE INTO `lod`.`item` (`uri`,`image`,`category`) VALUES (?,?,?)  ON DUPLICATE KEY UPDATE `uri` = `uri`");
				ps.setString(1, uri);
				ps.setString(2, image);
				ps.setString(3, category);
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

	/**
	 * @param uri1
	 * @param uris
	 * @return
	 * 
	 */
	public List<Resource> getIndirectLinksBetween2ResourcesAndLink(String uri1, String link, String uri3,
			String method) {

		List<Resource> indirectLinks = null;

		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder(
					"select distinct uri2 from `movielens`.`indirect_link` as b where b.uri1 = ? and b.uri2 = ? and b.uri3 = ? and b.method = ? ");
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, uri1);
			ps.setString(2, link);
			ps.setString(3, uri3);
			ps.setString(4, method);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				if (indirectLinks == null) {
					indirectLinks = new ArrayList<Resource>();
				}
				indirectLinks.add(new ResourceImpl(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return indirectLinks;
	}

	/**
	 * @param uri1
	 * @param uris
	 * @return
	 * 
	 */
	public List<Resource> getIndirectLinksBetween2Resources(String uri1, String uri3, String method) {

		Set<Resource> indirectLinks = new HashSet<Resource>();

		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder(
					"select uri2 from `movielens`.`indirect_link` as b where b.uri1 = ? and b.uri3 = ? and b.method = ? ");
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, uri1);
			ps.setString(2, uri3);
			ps.setString(3, method);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				indirectLinks.add(new ResourceImpl(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return new ArrayList<Resource>(indirectLinks);
	}

	public List<Resource> getDirectLinksBetween2Resources(String resource1, String resource2, String method) {

		Set<Resource> directLinks = new HashSet<Resource>();

		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder(
					"select uri2 from `movielens`.`indirect_link` as b where b.uri1 = ? and b.uri3 = ? and b.method = ? ");
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, resource1);
			ps.setString(2, resource2);
			ps.setString(3, method);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				directLinks.add(new ResourceImpl(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return new ArrayList<Resource>(directLinks);
	}

	public List<Resource> getDirectLinksBetween1ResourcesAndLink(String resource1, String link, String method) {

		Set<Resource> directLinks = new HashSet<Resource>();

		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder(
					"select uri2 from `movielens`.`indirect_link` as b where b.uri1 = ? and b.uri2 = ? and b.method = ? ");
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, resource1);
			ps.setString(2, link);
			ps.setString(3, method);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				directLinks.add(new ResourceImpl(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return new ArrayList<Resource>(directLinks);
	}

	public void insertNewBookInaSequence(String uri) {
		if (getBookByURI(uri) == null) {
			Integer bookId = getMaxBookId() + 1;
			insertBook(bookId, uri);
		}
	}

	public void insertUserProfile(int userId, String uri) {
		Node node = getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId, uri);
		if (node == null) {
			Integer bookId = null;
			Node book = getBookByURIAndConvertToNode(uri);
			if (book == null) {
				bookId = getMaxBookId() + 1;
				insertBook(bookId, uri);
				book = getBookByURIAndIDConvertToNode(bookId, uri);
				if (book != null) {
					insertBookLike(userId, bookId);
				}
			} else {
				insertBookLike(userId, Integer.valueOf(book.getId()));
			}
		}
	}

	/**
	 * @return
	 */
	public boolean checkBook(String uri) {

		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS(select id from `lod`.`book` as b where b.uri =  ?)";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ps.setString(1, uri);
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	public void updateItemUrlPath(String uri, String urlPath) {
		Item item = this.getItem(uri);
		item.setImg(urlPath);
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				// for (Evaluation evaluation : evaluations) {
				String query = "REPLACE INTO `lod`.`item` (`uri`,`image`,`category`) VALUES (?,?,?)";
				// System.out.println(query);
				ps = conn.prepareStatement(query);
				ps.setString(1, item.getUri());
				ps.setString(2, item.getImg());
				ps.setString(3, item.getCategory());
				ps.execute();
				// }
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

	/**
	 * @return
	 */
	public Item getItem(String uri) {

		Item item = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT * from `lod`.`item` as b where b.uri =  ?";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, uri);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				item = new Item();
				item.setUri(rs.getString(1));
				item.setImg(rs.getString(2));
				item.setCategory(rs.getString(3));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return item;
	}

	public List<String> getImages(String uri) {

		List<String> images = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select image from `lod`.`item` as b where b.uri =  ?";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			ps.setString(1, uri);
			while (rs != null && rs.next()) {
				images.add(rs.getString(1));

			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return images;
	}

	public Integer getMaxUserIDByBookLikes() {
		Integer max = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select max(ml.userid) from book as m, book_like as ml where  m.id = ml.bookid";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				max = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return max;
	}

	public Integer getMaxUserIDByMusicLikes() {
		Integer max = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select max(ml.userid) from music as m, music_like as ml where  m.id = ml.musicid";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				max = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return max;
	}

	public Integer getMaxUserIDByMoovieLikes() {
		Integer max = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select max(ml.userid) from movie as m, movie_like as ml where  m.id = ml.movieid";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				max = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return max;
	}

	public int getMaxBookId() {
		Integer max = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select max(id) from `lod`.`book`";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				max = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return max;
	}

	public int getMaxMusicId() {
		Integer max = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select max(id) from `lod`.`music`";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				max = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return max;
	}

	public int getMaxMovieId() {
		Integer max = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select max(id) from `lod`.`movie`";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				max = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return max;
	}

	public int getMaxUserId() {
		Integer max = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select max(userid) from `lod`.`user`";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				max = rs.getInt(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return max;
	}

	public int insertBook(int id, String uri) {
		Connection conn = DBConnection.getConnection();
		String query = "INSERT INTO `lod`.`book` (`id`, `uri`) VALUES (" + id + " , \"" + uri + "\")";
		return executeAndClose(conn, query);
	}

	public int insertBookLike(int userid, int bookid) {
		Connection conn = DBConnection.getConnection();
		String query = "INSERT INTO `lod`.`book_like` (`userid`, `bookid`) " + " VALUES (" + userid + " , " + bookid
				+ ")";
		return executeAndClose(conn, query);
	}

	/**
	 * @param userId
	 * @return
	 */
	public Node getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(int userId, String uri) {
		Node node = null;
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM ("
					+ "(select distinct m.id, m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid = ? and m.uri = ?)  "
					+ " UNION ALL "
					+ "(select distinct m.id, m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid = ? and m.uri = ?)  "
					+ " UNION ALL "
					+ "(select distinct m.id, m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid = ? and m.uri = ? )  "
					+ ") as x";

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setString(2, uri);
			ps.setInt(3, userId);
			ps.setString(4, uri);
			ps.setInt(5, userId);
			ps.setString(6, uri);
			// System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(null, IConstants.LIKE, rs.getString(2).trim());
				// NodeUtil.print(rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return node;
	}

	/**
	 * @return
	 */
	public void insertLinksOld(String uri1, List<Resource> resources) {
		for (Resource resource : resources) {
			if (!checkLink(uri1, resource.getURI())) {
				insertLink(uri1, resource.getURI());
			}
		}
	}

	/**
	 * @param uri1
	 * @param resources
	 */
	public void checkAndinsertLinksWithSelect(String uri1, List<Resource> resources) {
		boolean noexist = false;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				for (Resource resource : resources) {
					// String query = "SELECT EXISTS(select * from `lod`.`link` as b where b.uri1 =
					// \"" + uri1 + "\" and b.uri2 = \"" + resource.getURI() + "\")";
					String query = "SELECT EXISTS(select uri1 from `lod`.`link` as b where b.uri1 = ? and  b.uri2 = ? )";
					// NodeUtil.print(getConnection().Query);
					ps = conn.prepareStatement(query);
					ps.setString(1, uri1);
					ps.setString(2, resource.getURI());
					ResultSet rs = ps.executeQuery();
					while (rs != null && rs.next()) {
						noexist = rs.getBoolean(1);
					}
					ps.close();
					if (!noexist) {
						query = "INSERT INTO `lod`.`link` (`uri1`, `uri2`) VALUES (?,?)  ON DUPLICATE KEY UPDATE `uri1` = `uri1` , `uri2` = `uri2`";
						ps = conn.prepareStatement(query);
						ps.setString(1, uri1);
						ps.setString(2, resource.getURI());
						ps.execute();
					}
				}

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

	/**
	 * @return
	 */
	public Set<String> getTu(int userId, String uricnn) {
		Set<String> tus = new HashSet<String>();
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {

			String query = "select b.uritu from `lod`.`tu` as b where b.userid =  " + userId + " and b.uricnn =  \""
					+ uricnn + "\")";
			// NodeUtil.print(getConnection().Query);
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				tus.add(rs.getString(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return tus;
	}

	public Set<Node> getTuNodes(int userId, String uricnn, List<Node> cnns) {
		Set<Node> nodeTus = new HashSet<Node>();
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			String query = "select b.uritu from `lod`.`tu` as b where b.userid = ? and b.uricnn = ?)";
			// NodeUtil.print(getConnection().Query);
			ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setString(2, uricnn);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodeTus.add(NodeUtil.getNodeByURI(rs.getString(1), cnns));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return nodeTus;
	}

	/**
	 * fred
	 * 
	 * @return
	 */
	public void insertTu(int userId, String uricnn, List<Resource> resources) {
		boolean noexist = false;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				for (Resource resource : resources) {
					String query = "SELECT EXISTS(select * from `lod`.`tu` as b where b.userid =  " + userId
							+ " and b.uricnn =  \"" + resource.getURI() + "\" and b.uritu =  \"" + resource.getURI()
							+ "\")";
					// NodeUtil.print(getConnection().Query);
					ps = conn.prepareStatement(query);
					ResultSet rs = ps.executeQuery();
					while (rs != null && rs.next()) {
						noexist = rs.getBoolean(1);
					}
					ps.close();
					if (!noexist) {
						query = "INSERT INTO `lod`.`link` (`userid`,`uricnn`, `uritu`) " + " VALUES (" + userId + " ,\""
								+ uricnn + "\" , \"" + resource.getURI() + "\")";
						ps = conn.prepareStatement(query);
						ps.execute();
					}
				}
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

	/*
	 * public int insertOrUpdatePrediction(String seed, String uri, String
	 * evaluationlabel, String predictedlabel, boolean usePredictedClass, Double
	 * score, int userid) { String sim = checkSimilarityMethod(usePredictedClass);
	 * Connection conn = DBConnection.getConnection(); String query =
	 * "REPLACE INTO `lod`.`prediction` (`seed`, `uri`, `evaluationlabel`,`predictedlabel`, `sim`, `score`, `userid`) "
	 * + " VALUES (\"" + seed + "\" , \"" + uri + "\" , \"" + evaluationlabel +
	 * "\", \"" + predictedlabel + "\" ,\"" + sim + "\", "+ score +", "+ userid
	 * +" )"; return executeAndClose(conn,query); }
	 */

	/*	*//**
			 * @param prediction
			 * @return
			 *//*
				 * public int insertOrUpdatePrediction(NodePrediction prediction) { String sim =
				 * checkSimilarityMethod(IConstants.USE_ICA); Connection conn =
				 * DBConnection.getConnection(); String query =
				 * "REPLACE INTO `lod`.`prediction` (`seed`, `uri`, `evaluationlabel`,`predictedlabel`, `sim`, `score`, `userid`) "
				 * + " VALUES (\"" + prediction.getSeed() + "\" , \"" +
				 * prediction.getNode().getURI() + "\" , \"" + prediction.getEvaluationLabel() +
				 * "\", \"" + prediction.getPredictedLabel() + "\" ,\"" + sim + "\", " +
				 * prediction.getPredictionScore() + ", " + prediction.getUserId() + ", \"" +
				 * prediction.getOriginalCandidate() + "\" )"; return executeAndClose(conn,
				 * query); }
				 */

	/**
	 * @return
	 */
	public void insertOrUpdatePredictions(List<NodePrediction> predictions) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				for (NodePrediction prediction : predictions) {
					// String sim = checkSimilarityMethod(IConstants.USE_ICA);
					String query = "REPLACE INTO `lod`.`prediction` (`seed`, `uri`, `evaluationlabel`,`predictedlabel`, `sim`, `score`, `graph_structure`,`userid`, `original_candidate`,`why`) VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? ,? )";
					// System.out.println(query);
					ps = conn.prepareStatement(query);
					ps.setString(1, prediction.getSeed());
					ps.setString(2, prediction.getNode().getURI());
					ps.setString(3, prediction.getEvaluationLabel());
					ps.setString(4, prediction.getPredictedLabel());
					// ps.setString(5, sim);
					ps.setString(5, prediction.getSimilarityMethod());
					ps.setDouble(6, prediction.getPredictionScore());
					ps.setInt(7, prediction.getGraphStructure());
					ps.setInt(8, prediction.getUserId());
					ps.setString(9, prediction.getOriginalCandidate());
					ps.setString(10, prediction.getWhy());
					ps.execute();
					ps.close();
				}
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

	public void insertOrUpdatePrediction(NodePrediction prediction) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {

				// String sim = checkSimilarityMethod(IConstants.USE_ICA);
				/*
				 * String query =
				 * "REPLACE INTO `lod`.`prediction` (`seed`, `uri`, `evaluationlabel`,`predictedlabel`, `sim`, `score`, `graph_structure`,`userid`, `original_candidate`) "
				 * + " VALUES (\"" + prediction.getSeed() + "\" ,\"" +
				 * prediction.getNode().getURI() + "\" , \"" + prediction.getEvaluationLabel() +
				 * "\", \"" + prediction.getPredictedLabel() + "\" ,\"" + sim + "\", " +
				 * prediction.getPredictionScore() + "," + prediction.getGraphStructure() + ","
				 * + prediction.getUserId()+ ",\"" + prediction.getOriginalCandidate() +"\" )";
				 */
				String query = "REPLACE INTO `lod`.`prediction` (`seed`, `uri`, `evaluationlabel`,`predictedlabel`, `sim`, `score`, `graph_structure`,`userid`, `original_candidate`,`why`) VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? ,? )";
				// System.out.println(query);
				ps = conn.prepareStatement(query);
				ps.setString(1, prediction.getSeed());
				ps.setString(2, prediction.getNode().getURI());
				ps.setString(3, prediction.getEvaluationLabel());
				ps.setString(4, prediction.getPredictedLabel());
				// ps.setString(5, sim);
				ps.setString(5, prediction.getSimilarityMethod());
				ps.setDouble(6, prediction.getPredictionScore());
				ps.setInt(7, prediction.getGraphStructure());
				ps.setInt(8, prediction.getUserId());
				ps.setString(9, prediction.getOriginalCandidate());
				ps.setString(10, prediction.getWhy());
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

	/**
	 * @return
	 *//*
		 * public void insertOrUpdateEvaluations(List<Evaluation> evaluations) {
		 * Connection conn = DBConnection.getConnection(); PreparedStatement ps = null;
		 * try { try { for (Evaluation evaluation : evaluations) { String query =
		 * "REPLACE INTO `lod`.`evaluation` (`uri`,`correct`,`incorrect`, `sim`,`userid`,`score`,`rr`,`original_candidate`,`position`) VALUES (\""
		 * + evaluation.getUri() + "\", " + evaluation.getCorrect() + ", " +
		 * evaluation.getIncorrect() + " ,\"" + evaluation.getSimilarityMethod() +
		 * "\", " + evaluation.getUserId() + ", " + evaluation.getScore() + "," +
		 * evaluation.getRr() + ",\"" + evaluation.getOriginalCandidate() + "\", " +
		 * evaluation.getPosition() + ")";
		 * 
		 * //System.out.println(query); ps = conn.prepareStatement(query); ps.execute();
		 * } ps.close(); conn.close(); } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * } finally { if (ps != null) { try { ps.close(); } catch (SQLException e) {
		 * e.printStackTrace(); } } if (conn != null) { try { conn.close(); } catch
		 * (SQLException e) { e.printStackTrace(); } } }
		 * 
		 * }
		 */

	/**
	 * @return
	 *//*
		 * public void insertPredictions(List<NodePrediction> predictions) { Connection
		 * conn = DBConnection.getConnection(); PreparedStatement ps = null; try { try {
		 * for (NodePrediction prediction : predictions) { String sim =
		 * checkSimilarityMethod(IConstants.USE_ICA);
		 * 
		 * String query =
		 * "INSERT INTO `lod`.`prediction` (`seed`, `uri`, `evaluationlabel`,`predictedlabel`, `sim`, `score`, `graph_structure`,`userid`) "
		 * + " VALUES (\"" + prediction.getSeed() + "\" , \"" +
		 * prediction.getNode().getURI() + "\" , \"" + prediction.getEvaluationLabel() +
		 * "\", \"" + prediction.getPredictedLabel() + "\" ,\"" + sim + "\", " +
		 * prediction.getPredictionScore() + "," + prediction.getGraphStructure() + ","
		 * + prediction.getUserId() + "," +prediction.getOriginalCandidate() + " )";
		 * 
		 * ps = conn.prepareStatement(query); ps.execute(); } ps.close(); conn.close();
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * } finally { if (ps != null) { try { ps.close(); } catch (SQLException e) {
		 * e.printStackTrace(); } } if (conn != null) { try { conn.close(); } catch
		 * (SQLException e) { e.printStackTrace(); } } }
		 * 
		 * }
		 */

	/**
	 * @return
	 */
	public int insertDomain(String seed, String uri, String domain, int iteration) {
		Connection conn = DBConnection.getConnection();
		String query = "INSERT INTO `lod`.`domain` (`seed`, `uri`, `domain`,`iteration`) " + " VALUES (\"" + seed
				+ "\" , \"" + uri + "\" , \"" + domain + "\", " + iteration + " )";
		return executeAndClose(conn, query);
	}

	public static String checkSimilarityMethod(boolean usePredictedClass) {
		String sim = IConstants.LDSD;
		if (usePredictedClass) {
			sim = IConstants.LDSD_LOD;
		}
		return sim;
	}

	public static String getOpositeSimilarityMethod(String similarityMethod) {
		String sim = null;
		if (similarityMethod.equals(IConstants.LDSD)) {
			sim = IConstants.LDSD_LOD;
		} else if (similarityMethod.equals(IConstants.LDSD_LOD)) {
			sim = IConstants.LDSD;
		}
		return sim;
	}

	public static boolean isUsingICA(String similarityMethod) {
		boolean isUsingICA = false;
		if (similarityMethod.equals(IConstants.LDSD)) {
			isUsingICA = false;
		} else if (similarityMethod.equals(IConstants.LDSD_LOD)) {
			isUsingICA = true;
		}
		return isUsingICA;
	}

	/**
	 * @return
	 */
	public static int insertRelated(String uri1, String uri2, int userid) {
		Connection conn = DBConnection.getConnection();
		String query = "INSERT INTO `lod`.`related` (`uri1`, `uri2`, `userid`) VALUES ( \"" + uri1 + "\" , \"" + uri2
				+ "\", " + userid + " )";
		return executeAndClose(conn, query);
	}

	/**
	 * @return
	 */
	public int insertSemanticDistance2(String uri1, String uri2, String sim, Double score) {
		Connection conn = DBConnection.getConnection();
		String query = "INSERT INTO `movielens`.`semantic` (`uri1`, `uri2`, `sim`, `score`) VALUES ( \"" + uri1 + "\" , \""
				+ uri2 + "\", \"" + sim + "\", " + score + " )";
		return executeAndClose(conn, query);
	}

	public void insertSemanticDistance(String uri1, String uri2, String sim, Double score, int userid) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				ps = conn.prepareStatement(
						"INSERT INTO `movielens`.`semantic` (`uri1`, `uri2`, `sim`, `score`,`userid`) VALUES (?,?,?,?,?)  ON DUPLICATE KEY UPDATE `uri1` = `uri1`,`uri2` = `uri2`,`sim` = `sim`");
				ps.setString(1, uri1);
				ps.setString(2, uri2);
				ps.setString(3, sim);
				ps.setDouble(4, score);
				ps.setDouble(5, userid);
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

	/**
	 * @return
	 */
	public boolean checkPrediction(String seed, String uri, int userId, String sim) {

		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS(select * from `lod`.`prediction` as b where b.seed =  \"" + seed
					+ "\" and b.uri =  \"" + uri + "\" and b.sim =  \"" + sim + "\" and b.userid =  " + userId + ")";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * @return
	 */
	public boolean checkLink(String uri1, String uri2) {

		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS(select * from `lod`.`link` as b where b.uri1 =  \"" + uri1
					+ "\" and  b.uri2 =  \"" + uri2 + "\")";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * @return
	 */
	public boolean checkPrediction(String seed, String uri, int userId, boolean usePredictedClassification) {
		String sim = checkSimilarityMethod(usePredictedClassification);
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS(select * from `lod`.`prediction` as b where b.seed =  \"" + seed
					+ "\" and b.uri =  \"" + uri + "\" and b.sim =  \"" + sim + "\" and b.userid =  " + userId + ")";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	public Set<Item> extractCoverAndGetItemsByCategory(String category) {
		List<String> uris = new ArrayList<>();
		Set<Item> items = new HashSet<Item>();
		if (category.equals(IConstants.CATEGORY_BOOK)) {
			uris.addAll(getBooks());
		} else if (category.equals(IConstants.CATEGORY_MUSIC)) {
			uris.addAll(getMusics());
		} else if (category.equals(IConstants.CATEGORY_MOVIE)) {
			uris.addAll(getMovies());
		}
		for (String uri : uris) {
			Item item = extractAndSaveNewItemCover(category, uri);
			items.add(item);
		}
		return items;
	}

	public void loadBrazillianDbpedia(String category, Integer limit) {
		Set<Item> items = getItems(category, limit);
		for (Item item : items) {
			String brUri = item.getUri().replace("http://dbpedia.org/", "http://pt.dbpedia.org/");
			SparqlWalk.setService(IConstants.BRAZILIAN_DBPEDIA);
			// System.out.println(brUri);
			if (this.getItem(brUri) == null) {
				if (SparqlWalk.isDbPediaResource(brUri)) {
					System.out.println(brUri);
					this.insertItem(brUri, item.getImg(), category);
				}
			}
		}
	}

	public void getBrazillianItems(String type, String category, Integer limit) {
		SparqlWalk.setService(IConstants.BRAZILIAN_DBPEDIA);
		List<Resource> resources = SparqlWalk.getSubjectsByDbPediaTypeAndCountry(type, "Brazil", limit);
		for (Resource resource : resources) {
			System.out.println(resource.getURI());
			extractAndSaveNewItemCover(category, resource.getURI());
		}
	}

	public Set<Item> extractCoverAndGetItemsByCategoryAndURI(String category, String uriParameter) {
		List<String> uris = new ArrayList<>();
		Set<Item> items = new HashSet<Item>();
		if (category.equals(IConstants.CATEGORY_BOOK)) {
			uris.add(getBookByURI(uriParameter));
		} else if (category.equals(IConstants.CATEGORY_MUSIC)) {
			uris.add(getMusicByURI(uriParameter));
		} else if (category.equals(IConstants.CATEGORY_MOVIE)) {
			uris.add(getMovieByURI(uriParameter));
		}
		for (String uri : uris) {
			Item item = extractAndSaveNewItemCover(category, uri);
			items.add(item);
		}
		return items;
	}

	private Item extractAndSaveNewItemCover(String category, String uri) {
		Item item = getItem(uri);
		if (item == null) {
			item = new Item();
			item.setUri(uri);

			if (item.getImg() == null && (category.equals(IConstants.CATEGORY_MOVIE))) {
				String searchuri = NodeUtil.removeNamespace(uri);
				searchuri = searchuri.replace("_", "*");
				searchuri = "*" + searchuri.replaceAll("\\(.*?\\)", "");
				String path = Parser.getImage(searchuri);

				if (path != null) {
					item.setImg("https://image.tmdb.org/t/p/original" + path);
					if (!StringUtilsNode.isFileAtURL(item.getImg())) {
						item.setImg(IConstants.SORRY_NO_IMAGE);
					}

				}

				if (item.getImg() == null) {
					String img = Parser.getImageCoverFromGoogle("movie+" + searchuri);
					if (img != null) {
						if (StringUtilsNode.isFileAtURL(img)) {
							item.setImg(img);
						} else {
							item.setImg(IConstants.SORRY_NO_IMAGE);
						}
					}
				}

			} else if (item.getImg() == null && (category.equals(IConstants.CATEGORY_BOOK))) {
				String searchuri = NodeUtil.removeNamespace(uri);
				searchuri = searchuri.replaceAll("\\(.*?\\)", "");
				searchuri = searchuri.replace("_", "+");
				String path = Parser.getBook(searchuri);
				if (path != null) {
					if (StringUtilsNode.isFileAtURL(path)) {
						item.setImg(path);
					} else {
						item.setImg(IConstants.SORRY_NO_IMAGE);
					}
				}
				if (item.getImg() == null) {
					String img = Parser.getImageCoverFromGoogle("book+" + searchuri);
					if (img != null) {
						if (StringUtilsNode.isFileAtURL(img)) {
							item.setImg(img);
						} else {
							item.setImg(IConstants.SORRY_NO_IMAGE);
						}
					}
				}
			} else if (item.getImg() == null && (category.equals(IConstants.CATEGORY_MUSIC))) {
				String searchuri = NodeUtil.removeNamespace(uri);
				searchuri = searchuri.replaceAll("\\(.*?\\)", "");
				searchuri = searchuri.replace("_", "+");
				String path = Parser.getMusic(searchuri);
				if (path != null) {
					if (StringUtilsNode.isFileAtURL(path)) {
						item.setImg(path);
					} else {
						item.setImg(IConstants.SORRY_NO_IMAGE);
					}
				}

				if (item.getImg() == null) {
					String img = Parser.getImageCoverFromGoogle("music+" + searchuri);
					if (img != null) {
						if (StringUtilsNode.isFileAtURL(img)) {
							item.setImg(img);
						} else {
							item.setImg(IConstants.SORRY_NO_IMAGE);
						}
					}
				}

			} else if (item.getImg() == null) {
				String img = SparqlWalk.getResourceImage(uri);
				if (img != null) {
					if (StringUtilsNode.isFileAtURL(img)) {
						item.setImg(img);
					} else {
						item.setImg(IConstants.SORRY_NO_IMAGE);
					}
				}
			}

			if (item.getImg() == null) {
				item.setImg(IConstants.SORRY_NO_IMAGE);

			}

			insertItem(uri, item.getImg(), category);
		}
		return item;
	}

	public void updateRealImages(Set<Item> items) {

		for (Item item : items) {
			// System.out.println(item.getImg());
			if (!item.getImg().equals(IConstants.SORRY_NO_IMAGE) && !StringUtilsNode.isFileAtURL(item.getImg())) {
				// System.out.println("PROBLEMAAAAAAAAA "+item.getImg());
				this.updateItemUrlPath(item.getUri(), IConstants.SORRY_NO_IMAGE);
			}
		}
	}

	public Set<Item> getItemsByCategory(String category) {
		Set<Item> items = new HashSet<Item>();
		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder();
			query.append("select * from `lod`.`item` as b where b.category = ?");

			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, category);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Item item = new Item();
				item.setUri(rs.getString(1));
				item.setImg(rs.getString(2));
				item.setCategory(rs.getString(3));
				items.add(item);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return items;
	}

	public Set<Item> searchItemsByTitle(String userQuery) {
		Set<Item> items = new HashSet<Item>();
		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder();
			query.append("select * from `lod`.`item` as b where   ");

			userQuery = StringUtilsNode.removeInvalidCharacteres(userQuery);

			List<String> queries = StringUtilsNode.getListToken(userQuery, " ");
			for (String queryToken : queries) {
				query.append(" ( ");
				query.append(" b.uri LIKE 'http://dbpedia.org/resource/%" + queryToken + "%'  ");
				query.append(" or ");
				query.append(" b.uri LIKE 'http://pt.dbpedia.org/resource/%" + queryToken + "%'  ");
				query.append(" ) ");
				query.append(" and ");
			}

			String finalQuery = query.toString().trim();
			if (finalQuery.contains("and")) {
				finalQuery = StringUtils.removeEnd(finalQuery, "and");
			}

			PreparedStatement ps = conn.prepareStatement(finalQuery);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Item item = new Item();
				item.setUri(rs.getString(1));
				item.setImg(rs.getString(2));
				item.setCategory(rs.getString(3));
				items.add(item);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return items;
	}

	public Set<Item> getItems(String category, Integer limit) {
		Set<Item> items = new HashSet<Item>();
		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder();
			query.append("select * from `lod`.`item` as b where   ");
			if (category.equals(IConstants.CATEGORY_MOVIE)) {
				query.append(" category = '" + IConstants.CATEGORY_MOVIE + "'");
			} else if (category.equals(IConstants.CATEGORY_BOOK)) {
				query.append(" category = '" + IConstants.CATEGORY_BOOK + "'");
			} else if (category.equals(IConstants.CATEGORY_MUSIC)) {
				query.append(" category = '" + IConstants.CATEGORY_MUSIC + "'");
			}
			if (limit != null) {
				query.append(" ORDER BY RAND()  LIMIT " + limit);
			}

			PreparedStatement ps = conn.prepareStatement(query.toString());
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Item item = new Item();
				item.setUri(rs.getString(1));
				item.setImg(rs.getString(2));
				item.setCategory(rs.getString(3));
				items.add(item);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return items;
	}

	public void cleanItems() {
		Set<Item> items = null;

		items = this.getItemsByCategory(IConstants.CATEGORY_MOVIE);
		for (Item item : items) {
			if (SparqlWalk.getLiteralByUri(item.getUri()).isEmpty()) {
				deleteItem(item.getUri());
			} else {
				// NodeUtil.print("Good "+item.getUri());
			}
		}

		items = this.getItemsByCategory(IConstants.CATEGORY_BOOK);
		for (Item item : items) {
			if (SparqlWalk.getLiteralByUri(item.getUri()).isEmpty()) {
				deleteItem(item.getUri());
			} else {
				// NodeUtil.print("Good "+item.getUri());
			}

		}

		items = this.getItemsByCategory(IConstants.CATEGORY_MUSIC);
		for (Item item : items) {
			if (SparqlWalk.getLiteralByUri(item.getUri()).isEmpty()) {
				deleteItem(item.getUri());
			} else {
				// NodeUtil.print("Good "+item.getUri());
			}
		}
	}

	/**
	 * //SELECT count(*) as x, domain FROM lod.domain group by domain order by x
	 * desc
	 * 
	 * @return
	 */
	public boolean checkDomain(String seed, String uri) {
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS(select * from `lod`.`domain` as b where b.seed =  \"" + seed
					+ "\" and b.uri =  \"" + uri + "\")";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * @return
	 */
	public List<String> getSeedUrisByUserAndSimilarity(int userId, String similarityMethod) {
		List<String> uris = new ArrayList<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from `lod`.`prediction` as b where b.seed =  ? and b.sim =  ? and b.userid = ? ";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, IConstants.SEED_EVALUATION);
			ps.setString(2, similarityMethod);
			ps.setInt(3, userId);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uris.add(rs.getString(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return uris;
	}

	/**
	 * @return
	 */
	public NodePrediction getPrediction(String seed, String uri, int userId, boolean usePredictedClassification) {
		String sim = checkSimilarityMethod(usePredictedClassification);
		return getPrediction(seed, uri, userId, sim);
	}

	/**
	 * @return
	 */
	public NodePrediction getPrediction(String seed, String uri, int userId, String similarityMethod) {
		NodePrediction nodePrediction = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`prediction` as b where b.seed =  ? and b.uri = ? and b.sim =  ? and b.userid = ? ";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, seed);
			ps.setString(2, uri);
			ps.setString(3, similarityMethod);
			ps.setInt(4, userId);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node nodeP = new Node();
				nodeP.setURI(rs.getString(2));
				nodePrediction = new NodePrediction(rs.getString(1), nodeP, rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getDouble(6), rs.getInt(7), rs.getInt(8), rs.getString(9),
						rs.getString(10));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return nodePrediction;
	}

	/**
	 * @return
	 */
	public List<NodePrediction> getPredictions(String seed, int userId, String similarityMethod) {
		List<NodePrediction> predictions = new ArrayList<NodePrediction>();
		try {
			Connection conn = DBConnection.getConnection();
			// String query = "select * from `lod`.`prediction` as b where b.seed = \"" +
			// IConstants.SEED_EVALUATION + "\" and b.uri = \"" + uri + "\" and b.sim = \""
			// + sim + "\" and b.userid = " + userId ;
			String query = "select * from `lod`.`prediction` as b where b.seed =  ? and b.sim =  ? and b.userid = ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, seed);
			ps.setString(2, similarityMethod);
			ps.setInt(3, userId);
			// NodeUtil.print(ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node nodeP = new Node();
				nodeP.setURI(rs.getString(2));
				predictions.add(
						new NodePrediction(rs.getString(1), nodeP, rs.getString(3), rs.getString(4), rs.getString(5),
								rs.getDouble(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return predictions;
	}

	/**
	 * @return
	 */
	public List<NodePrediction> getPredictions(String seed, int userId, boolean usePredictedClassification) {
		String sim = checkSimilarityMethod(usePredictedClassification);
		return getPredictions(seed, userId, sim);
	}

	/**
	 * @return
	 */
	public List<NodePrediction> getPredictionsBySeed(String seed, int userId) {
		List<NodePrediction> predictions = new ArrayList<NodePrediction>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`prediction` as b where b.seed =  \"" + seed + "\" and b.userid =  "
					+ userId;
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node nodeP = new Node();
				nodeP.setURI(rs.getString(2));
				predictions.add(
						new NodePrediction(rs.getString(1), nodeP, rs.getString(3), rs.getString(4), rs.getString(5),
								rs.getDouble(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return predictions;
	}

	/**
	 * @return
	 */
	public List<NodePrediction> getPredictionEvaluations(int userId, String sim) {
		List<NodePrediction> predictions = new ArrayList<NodePrediction>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`prediction` as b where b.seed =  ? and b.sim =  ?  and b.userid = ?";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, IConstants.SEED_EVALUATION);
			ps.setString(2, sim);
			ps.setInt(3, userId);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node nodeP = new Node();
				nodeP.setURI(rs.getString(2));
				predictions.add(
						new NodePrediction(rs.getString(1), nodeP, rs.getString(3), rs.getString(4), rs.getString(5),
								rs.getDouble(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return predictions;
	}

	/**
	 * @return
	 */
	public NodePrediction getPredictionEvaluation(String uri, int userId, boolean isICA) {
		String sim = checkSimilarityMethod(isICA);
		NodePrediction prediction = null;
		try {
			Connection conn = DBConnection.getConnection();
			// String query = "select * from `lod`.`prediction` as b where b.seed = \"" +
			// IConstants.SEED_EVALUATION + "\" and b.uri = \"" + uri + "\" and b.sim = \""
			// + sim + "\" and b.userid = " + userId ;
			String query = "select * from `lod`.`prediction` as b where b.seed =  ? and b.uri = ? and b.sim =  ?  and b.userid = ?";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, IConstants.SEED_EVALUATION);
			ps.setString(2, uri);
			ps.setString(3, sim);
			ps.setInt(4, userId);

			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node nodeP = new Node();
				nodeP.setURI(rs.getString(2));
				prediction = new NodePrediction(rs.getString(1), nodeP, rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getDouble(6), rs.getInt(7), rs.getInt(8), rs.getString(9),
						rs.getString(10));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return prediction;
	}

	/**
	 * @return
	 */
	public List<NodePrediction> getPredictions(String uri, int userId) {
		List<NodePrediction> predictions = new ArrayList<NodePrediction>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`prediction` as b where b.uri =  ? and b.userid =  ?";
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, uri);
			ps.setInt(2, userId);

			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node nodeP = new Node();
				nodeP.setURI(rs.getString(2));
				predictions.add(
						new NodePrediction(rs.getString(1), nodeP, rs.getString(3), rs.getString(4), rs.getString(5),
								rs.getDouble(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getString(10)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return predictions;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public List<Resource> getLinksIncomingAndOutcoming(String uri1) throws SQLException {
		List<Resource> links = new ArrayList<Resource>();

		Connection conn = DBConnection.getConnection();
		StringBuilder query = new StringBuilder();
		if (!IConstants.FILTER_CATEGORY) {
			query.append("select uri2 from `lod`.`link` as b where b.uri1 = ?");
		} else {
			query.append(
					"select uri2 from `lod`.`link` as b where b.uri1 = ? and b.uri2 NOT LIKE '%http://dbpedia.org/resource/Category%'");
		}

		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, uri1);
		// NodeUtil.print(ps.toString());
		ps.execute();
		try (ResultSet rs = ps.getResultSet()) {
			while (rs != null && rs.next()) {
				links.add(new ResourceImpl(rs.getString(1)));
			}
		}
		closeQuery(conn, ps);

		return links;
	}

	/**
	 * @return
	 */
	public Set<Resource> getLinksIncomingAndOutcoming(String uri1, String uri2) {
		Set<Resource> links = new HashSet<Resource>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select uri2 from `lod`.`link` as b where b.uri1 = ? and b.uri2 = ? ";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, uri1);
			ps.setString(2, uri2);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs != null && rs.next()) {
				links.add(new ResourceImpl(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return links;
	}

	/**
	 * @param uri1
	 * @param uris
	 * @return
	 */
	public List<Resource> getLinksIncomingAndOutcoming(String uri1, Set<String> uris) {

		Set<Resource> links = new HashSet<Resource>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select uri2 from `lod`.`link` as b where b.uri1 =  \"" + uri1 + "\"  "
					+ buildEqualStrings(true, "b.uri2", uris);
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			// NodeUtil.print(query);

			while (rs != null && rs.next()) {
				links.add(new ResourceImpl(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return new ArrayList<Resource>(links);
	}

	/**
	 * @param uri1
	 * @param uris
	 * @return
	 * @throws SQLException
	 */
	public List<Resource> getLinksIncomingAndOutcomingFinalFromNode(String uri1, Set<Node> nodes) throws SQLException {
		/*
		 * try { //TimeUnit.SECONDS.sleep(100); } catch (InterruptedException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		List<Resource> links = new ArrayList<Resource>();
		int count = 0;

		Connection conn = DBConnection.getConnection();
		StringBuilder query = new StringBuilder();
		query.append("select distinct uri2 from `lod`.`link` as b where ( b.uri1 = ?");
		query.append(buildEqualNodesQuestionTag(true, "b.uri2", nodes));
		query.append(") or ( b.uri2 = ?");
		query.append(buildEqualNodesQuestionTag(true, "b.uri1", nodes));
		query.append(")");
		// System.out.println(query);
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(++count, uri1);
		LinkedList<Node> urisList = new LinkedList<Node>(nodes);
		for (int i = 0; i < urisList.size(); i++) {
			ps.setString(++count, urisList.get(i).getURI());
			// count++;
		}
		ps.setString(++count, uri1);

		for (int i = 0; i < urisList.size(); i++) {
			ps.setString(++count, urisList.get(i).getURI());
			// count++;
		}
		// NodeUtil.print(ps.toString());
		ps.execute();

		ResultSet rs = ps.getResultSet();
		while (rs != null && rs.next()) {
			links.add(new ResourceImpl(rs.getString(1)));
		}
		closeQuery(conn, ps);

		hasBefore = false;
		return links;
	}

	/**
	 * @param uri1
	 * @param uris
	 * @return
	 */
	public List<Resource> getLinksIncomingAndOutcomingFinalFromResource(String uri1, List<Resource> resources) {

		List<Resource> links = new ArrayList<Resource>();
		int count = 0;
		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder();
			query.append("select distinct uri2 from `lod`.`link` as b where ( b.uri1 = ?");
			query.append(buildEqualResourcesQuestionTag(true, "b.uri2", resources));
			query.append(") or ( b.uri2 = ?");
			query.append(buildEqualResourcesQuestionTag(true, "b.uri1", resources));
			query.append(")");
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(++count, uri1);
			LinkedList<Resource> urisList = new LinkedList<Resource>(resources);
			for (int i = 0; i < urisList.size(); i++) {
				ps.setString(++count, urisList.get(i).getURI());
				// count++;
			}
			ps.setString(++count, uri1);

			for (int i = 0; i < urisList.size(); i++) {
				ps.setString(++count, urisList.get(i).getURI());
				// count++;
			}
			// NodeUtil.print(ps.toString());
			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				links.add(new ResourceImpl(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return links;
	}

	/**
	 * @param uri1
	 * @param uris
	 * @return
	 */
	public List<Resource> getLinksIncomingAndOutcomingFinal(String uri1, Set<String> uris) {

		Set<Resource> links = new HashSet<Resource>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select uri2 from `lod`.`link` as b where b.uri1 = ?  "
					+ buildEqualStringsQuestionTag(true, "b.uri2", uris);
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, uri1);
			LinkedList<String> urisList = new LinkedList<String>(uris);
			for (int i = 2; i < urisList.size() + 2; i++) {
				ps.setString(i, urisList.get(i - 2));
			}

			// NodeUtil.print(ps.toString());

			ResultSet rs = ps.executeQuery();

			while (rs != null && rs.next()) {
				links.add(new ResourceImpl(rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return new ArrayList<Resource>(links);
	}

	/**
	 * @return
	 */
	public List<Resource> getLinksIncomingAndOutcoming2(String uri1, Set<String> uris) {
		Set<Resource> links = new HashSet<Resource>();
		for (String uri2 : uris) {
			links.addAll(getLinksIncomingAndOutcoming(uri1, uri2));
		}
		return new ArrayList<Resource>(links);
	}

	/**
	 * @return
	 */
	public Evaluation getEvaluation(String uri, boolean usePredictedClassification, int userId) {
		Evaluation evaluation = null;
		String sim = checkSimilarityMethod(usePredictedClassification);
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`evaluation` as b where b.uri =  \"" + uri + "\"  and b.sim =  \"" + sim
					+ "\" and  b.userid =  " + userId;
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				evaluation = new Evaluation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return evaluation;
	}

	/**
	 * @return
	 */
	public Evaluation getEvaluationByUserUriAndId(String uri, int userId) {
		Evaluation evaluation = null;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`evaluation` as b where b.uri =  \"" + uri + "\" and  b.userid =  "
					+ userId;
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				evaluation = new Evaluation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return evaluation;
	}

	/**
	 * @param userId
	 * @return
	 */
	public boolean existEvaluationByUserId(int userId) {
		boolean result = false;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select count(*) from `lod`.`evaluation` as b where b.userid =  ?";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				result = rs.getInt(1) > 0;
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * @param userId
	 * @return
	 */
	public boolean existCompleteEvaluationByUserId(int userId) {
		boolean result = false;

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select count(original_candidate) from `lod`.`prediction` as b where seed = 'SEED_EVALUATION' and b.userid =  ?";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				result = rs.getInt(1) >= ((IConstants.TOTAL_CANDIDATES_TO_COMPARE_HIT_RATE * 2) + 2);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * @return
	 */
	public List<Evaluation> getEvaluations(int userId) {
		List<Evaluation> evaluationList = new ArrayList<Evaluation>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`evaluation` as b where b.userid =  " + userId;
			// NodeUtil.print(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				evaluationList.add(new Evaluation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return evaluationList;
	}

	public List<Evaluation> getEvaluationsByOriginalCandidateSortedByScore(int userId, String originalCandidate,
			String sim) {
		List<Evaluation> evaluationList = new ArrayList<Evaluation>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`evaluation` as b where b.sim =  \"" + sim
					+ "\" and b.original_candidate =  \"" + originalCandidate + "\" and b.userid =  " + userId
					+ " order by score desc";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				evaluationList.add(new Evaluation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return evaluationList;
	}

	/**
	 * @param sim
	 * @return
	 */
	public LinkedList<Evaluation> getEvaluationsByMethodSortedByScore(String sim, int userId) {
		LinkedList<Evaluation> evaluationList = new LinkedList<Evaluation>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from `lod`.`evaluation` as b where b.sim =  ? and  b.userid =  ? order by score desc";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, sim);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				evaluationList.add(new Evaluation(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return evaluationList;
	}

	/**
	 * @param sim
	 * @return
	 */
	public LinkedList<Integer> getUserIdsOfEvaluatedItems(String sim) {
		LinkedList<Integer> ids = new LinkedList<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select distinct userid from `lod`.`evaluation` as b where b.sim =  ?  and b.original_candidate = b.uri order by userid";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, sim);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				ids.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ids;
	}

	/**
	 * @param sim
	 * @return
	 */
	public LinkedList<Integer> getUserIdsOfPredictedItems(String sim) {
		LinkedList<Integer> ids = new LinkedList<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select distinct userid from `lod`.`prediction` as b where b.sim = ?  and b.original_candidate = b.uri order by userid";
			// NodeUtil.print(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, sim);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				ids.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ids;
	}

	/**
	 * @return
	 */
	public boolean checkEvaluation(int userId, boolean usePredictedClassification) {
		String sim = checkSimilarityMethod(usePredictedClassification);
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS(select * from `lod`.`evaluation` as b where b.sim =  \"" + sim
					+ "\" and  b.userid =  " + userId + ")";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * @return
	 */
	public boolean checkEvaluation(int userId) {
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS(select * from `lod`.`evaluation` as b where b.userid =  " + userId + ")";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * In this we are querying resource and property
	 * 
	 * @return
	 */
	public Double getDistanceFrom2Resources(String uri1, String uri2, String sim) {
		if (uri1.equals(uri2)) {
			return 0d;
		}
		Double score = null;
		try {
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder(
					"SELECT distinct b.score from `movielens`.`semantic` as b where ((b.uri1 =  ? and b.uri2 = ? and b.sim = ?) OR (b.uri1 =  ? and b.uri2 = ?  and b.sim = ?)) ");
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setString(1, uri1);
			ps.setString(2, uri2);
			ps.setString(3, sim);
			ps.setString(4, uri2);
			ps.setString(5, uri1);
			ps.setString(6, sim);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				score = rs.getDouble(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return score;
	}

	/**
	 * @return fred
	 */
	public Double getSimilarityByMethod2(String uri1, String uri2, String sim) {
		if (uri1.equals(uri2)) {
			return 0d;
		}
		Double score = null;
		try {

			Connection conn = DBConnection.getConnection();
			String query = null;
			if (sim.equals(IConstants.LDSD)) {
				query = "SELECT distinct b.score from `movielens`.`semantic` as b where ((b.uri1 =  ? and b.uri2 = ? and b.sim = ?) OR (b.uri1 =  ? and b.uri2 = ?  and b.sim = ?)) ";
			} else if (sim.equals(IConstants.LDSD_LOD)) {
				query = "SELECT distinct b.score from `movielens`.`semantic` as b where ((b.uri1 =  ? and b.uri2 = ? and b.sim = ?) OR (b.uri1 =  ? and b.uri2 = ?  and b.sim = ? and b.userid = ?)) ";
			}
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, uri1);
			ps.setString(2, uri2);
			ps.setString(3, sim);
			ps.setString(4, uri2);
			ps.setString(5, uri1);
			ps.setString(6, sim);
			if (sim.equals(IConstants.LDSD_LOD)) {
				ps.setInt(7, Lodica.userId);
			}
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				score = rs.getDouble(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return score;
	}

	public Double getSimilarityByMethod(String uri1, String uri2, String sim) {
		if (uri1.equals(uri2)) {
			return 0d;
		}
		Double score = null;
		String query = null;
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		try {
			try {
				if (sim.equals(IConstants.LDSD_LOD)) {
					query = "SELECT distinct b.score from `movielens`.`semantic` as b where ((b.uri1 =  ? and b.uri2 = ? and b.sim = ?) OR (b.uri1 =  ? and b.uri2 = ?  and b.sim = ?)) ";
				} else if (sim.equals(IConstants.LDSD_JACCARD)) {
					query = "SELECT distinct b.score from `movielens`.`semantic` as b where ((b.uri1 =  ? and b.uri2 = ? and b.sim = ?) OR (b.uri1 =  ? and b.uri2 = ?  and b.sim = ? and b.userid = ?)) ";
				}
				ps = conn.prepareStatement(query);
				ps.setString(1, uri1);
				ps.setString(2, uri2);
				ps.setString(3, sim);
				ps.setString(4, uri2);
				ps.setString(5, uri1);
				ps.setString(6, sim);
				if (sim.equals(IConstants.LDSD_JACCARD)) {
					ps.setInt(7, Lodica.userId);
				}

				// NodeUtil.print(ps.toString());

				ResultSet rs = ps.executeQuery();
				while (rs != null && rs.next()) {
					score = rs.getDouble(1);
				}

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
		return score;
	}

	/**
	 * @return
	 */
	/*
	 * public Double getLDSD(String uri1, String uri2) { Double score = null; try {
	 * Connection conn = DBConnection.getConnection();
	 * 
	 * String query = "SELECT distinct b.score from `lod`.`semantic` as b where " +
	 * "((b.uri1 =  \"" + uri1 + "\" and b.uri2 =  \"" + uri2 +
	 * "\" and b.sim = \"LDSD\") " + " OR " + "(b.uri1 =  \"" + uri2 +
	 * "\"  and b.uri2 =  \"" + uri1 + "\" and b.sim = \"LDSD\")) ";
	 * 
	 * 
	 * String query = "SELECT distinct b.score from `lod`.`semantic` as b where " +
	 * "((b.uri1 =  ? and b.uri2 = ? and b.sim = \"LDSD\") " + " OR " +
	 * "(b.uri1 =  ? and b.uri2 = ?  and b.sim = \"LDSD\")) ";
	 * 
	 * 
	 * 
	 * PreparedStatement ps = conn.prepareStatement(query);
	 * 
	 * ps.setString(1, uri1); ps.setString(2, uri2); ps.setString(3, uri2);
	 * ps.setString(4, uri1);
	 * 
	 * 
	 * ResultSet rs = ps.executeQuery(); while (rs != null && rs.next()) { score =
	 * rs.getDouble(1); } closeQuery(conn,ps); } catch (SQLException ex) {
	 * ex.printStackTrace(); } return score; }
	 * 
	 *//**
		 * @return
		 *//*
			 * public Double getLDSD_LOD(String uri1, String uri2) { Double score = null;
			 * try { Connection conn = DBConnection.getConnection(); String query =
			 * "SELECT distinct b.score from `lod`.`semantic` as b where " +
			 * "((b.uri1 =  \"" + uri1 + "\" and b.uri2 =  \"" + uri2 +
			 * "\" and b.sim = \"LDSD_LOD\") " + " OR " + "(b.uri1 =  \"" + uri2 +
			 * "\"  and b.uri2 =  \"" + uri1 + "\" and b.sim = \"LDSD_LOD\")) ";
			 * 
			 * 
			 * String query = "SELECT distinct b.score from `lod`.`semantic` as b where " +
			 * "((b.uri1 =  ? and b.uri2 = ? and b.sim = \"LDSD_LOD\") " + " OR " +
			 * "(b.uri1 =  ? and b.uri2 = ?  and b.sim = \"LDSD_LOD\")) ";
			 * 
			 * PreparedStatement ps = conn.prepareStatement(query); ps.setString(1, uri1);
			 * ps.setString(2, uri2); ps.setString(3, uri2); ps.setString(4, uri1);
			 * 
			 * ResultSet rs = ps.executeQuery(); while (rs != null && rs.next()) { score =
			 * rs.getDouble(1); } closeQuery(conn,ps); } catch (SQLException ex) {
			 * ex.printStackTrace(); } return score; }
			 */

	/**
	 * @return
	 *//*
		 * public boolean checkSemantics(String uri1,String uri2, String sim) { boolean
		 * noexist = false; try { Connection conn = DBConnection.getConnection(); String
		 * query = "SELECT EXISTS(select * from `lod`.`semantic` as b where" +
		 * " (b.uri1 =  \"" + uri1 + "\" and b.uri2 =  \"" + uri2 + "\" and b.sim =  \""
		 * + sim + "\") " + "  OR   " + "(b.uri1 =  \"" + uri2 + "\" and b.uri2 =  \"" +
		 * uri1 + "\" and b.sim =  \"" + sim + "\"))  "; PreparedStatement ps =
		 * conn.prepareStatement(query);ResultSet rs = ps.executeQuery(); while (rs !=
		 * null && rs.next()) { noexist = rs.getBoolean(1); } closeQuery(conn,ps); }
		 * catch (SQLException ex) { ex.printStackTrace(); } return noexist; }
		 */

	/**
	 * @return
	 */
	public boolean checkRelated(String uri1, String uri2, int userid) {
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS(select * from `lod`.`related` as b where" + " (b.uri1 =  \"" + uri1
					+ "\" and b.uri2 =  \"" + uri2 + "\" and b.userid =  " + userid + ") " + "  OR   "
					+ " (b.uri1 =  \"" + uri2 + "\" and b.uri2 =  \"" + uri1 + "\" and b.userid =  " + userid + "))  ";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * @return
	 */
	public boolean checkRelatedByUserId(int userid) {
		boolean noexist = false;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT EXISTS (select * from `lod`.`related` as b where (b.userid =  " + userid + " ))";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				noexist = rs.getBoolean(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return noexist;
	}

	/**
	 * @return
	 */
	public static Set<String> getMusics() {
		Set<String> bookURIs = new HashSet<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from music as b";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				bookURIs.add(rs.getString(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bookURIs;
	}

	/**
	 * @return
	 */
	public static Set<String> getMovies() {
		Set<String> bookURIs = new HashSet<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from movie as b";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				bookURIs.add(rs.getString(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bookURIs;
	}

	/**
	 * @return
	 */
	public static Set<String> getBooks() {
		Set<String> bookURIs = new HashSet<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from book as b";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				String bookuri = rs.getString(1);
				if (!bookuri.trim().isEmpty()) {
					bookURIs.add(bookuri);
				}

			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bookURIs;
	}

	/**
	 * @return
	 */
	public String getMusicURIByID(int id) {
		String uri = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from music as b where b.id =  " + id;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uri = rs.getString(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return uri;
	}

	/**
	 * @return
	 */
	public String getMovieURIByID(int id) {
		String uri = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from movie as b where b.id =  " + id;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uri = rs.getString(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return uri;
	}

	/**
	 * @return
	 */
	public String getBookURIByID(int id) {
		String uri = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from book as b where b.id =  " + id;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uri = rs.getString(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return uri;
	}

	/**
	 * @return
	 */
	public String getBookByURI(String uri) {
		String uriFinal = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from book as b where b.uri = '" + uri + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uriFinal = rs.getString(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return uriFinal;
	}

	public String getMovieByURI(String uri) {
		String uriFinal = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from movie as b where b.uri = '" + uri + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uriFinal = rs.getString(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return uriFinal;
	}

	public String getMusicByURI(String uri) {
		String uriFinal = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from music as b where b.uri = '" + uri + "'";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uriFinal = rs.getString(1);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return uriFinal;
	}

	/**
	 * @return
	 */
	public Set<String> getBooksByURI(String uri) {
		Set<String> bookURIs = new HashSet<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from book as b where b.uri =  \"" + uri + "\"";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				bookURIs.add(rs.getString(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bookURIs;
	}

	/**
	 * @return
	 */
	public Node getBookByURIAndConvertToNode(String uri) {
		Node node = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select id, uri from book where uri = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, uri);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return node;
	}

	public Node getBookByURIAndIDConvertToNode(int bookid, String uri) {
		Node node = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select id, uri from book where id =  ? and uri = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, bookid);
			ps.setString(2, uri);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return node;
	}

	/**
	 * @return
	 */
	public Node getMusicByURIAndConvertToNode(String uri) {
		Node node = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.id, b.uri from music as b where b.uri =  \"" + uri + "\"";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return node;
	}

	/**
	 * @return
	 */
	public Node getMovieByURIAndConvertToNode(String uri) {
		Node node = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.id, b.uri from movie as b where b.uri =  \"" + uri + "\"";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return node;
	}

	public static String buildEqual(boolean addAND, String field, List<Resource> resources) {
		StringBuilder stringBuilder = new StringBuilder();
		int count = 0;
		if (resources != null && !resources.isEmpty() && addAND) {
			stringBuilder.append(" and (");
		}
		for (Resource resource : resources) {
			stringBuilder.append("  " + field + " = ");
			stringBuilder.append("\"" + resource.getURI() + "\"");
			count++;
			if (count != resources.size()) {
				stringBuilder.append(" OR ");
			}
		}

		if (resources != null && !resources.isEmpty() && addAND) {
			stringBuilder.append(")");
		}

		if (!stringBuilder.toString().isEmpty()) {
			hasBefore = true;
		}

		return stringBuilder.toString();
	}

	public static String buildEqualStrings(boolean addAND, String field, Set<String> resources) {
		StringBuilder stringBuilder = new StringBuilder();
		int count = 0;
		if (resources != null && !resources.isEmpty() && addAND) {
			stringBuilder.append(" and (");
		}
		for (String resource : resources) {
			stringBuilder.append("  " + field + " = ");
			stringBuilder.append("\"" + resource + "\"");
			count++;
			if (count != resources.size()) {
				stringBuilder.append(" OR ");
			}
		}

		if (resources != null && !resources.isEmpty() && addAND) {
			stringBuilder.append(")");
		}

		if (!stringBuilder.toString().isEmpty()) {
			hasBefore = true;
		}

		return stringBuilder.toString();
	}

	public static String buildEqualStringsQuestionTag(boolean addAND, String field, Set<String> resources) {
		StringBuilder stringBuilder = new StringBuilder();
		int count = 0;
		if (resources != null && !resources.isEmpty() && addAND) {
			stringBuilder.append(" and (");
		}
		for (String resource : resources) {
			stringBuilder.append("  " + field + " = ");
			stringBuilder.append("?");
			count++;
			if (count != resources.size()) {
				stringBuilder.append(" OR ");
			}
		}

		if (resources != null && !resources.isEmpty() && addAND) {
			stringBuilder.append(")");
		}

		if (!stringBuilder.toString().isEmpty()) {
			hasBefore = true;
		}

		return stringBuilder.toString();
	}

	public static String buildEqualNodesQuestionTag(boolean addAND, String field, Set<Node> nodes) {
		StringBuilder stringBuilder = new StringBuilder();
		int count = 0;
		if (nodes != null && !nodes.isEmpty() && addAND) {
			stringBuilder.append(" and (");
		}
		for (Node node : nodes) {
			stringBuilder.append("  " + field + " = ?");
			count++;
			if (count != nodes.size()) {
				stringBuilder.append(" OR ");
			}
		}

		if (nodes != null && !nodes.isEmpty() && addAND) {
			stringBuilder.append(")");
		}

		if (!stringBuilder.toString().isEmpty()) {
			hasBefore = true;
		}

		return stringBuilder.toString();
	}

	public static String buildEqualResourcesQuestionTag(boolean addAND, String field, List<Resource> resources) {
		StringBuilder stringBuilder = new StringBuilder();
		int count = 0;
		if (resources != null && !resources.isEmpty() && addAND) {
			stringBuilder.append(" and (");
		}
		for (Resource resource : resources) {
			stringBuilder.append("  " + field + " = ?");
			count++;
			if (count != resources.size()) {
				stringBuilder.append(" OR ");
			}
		}

		if (resources != null && !resources.isEmpty() && addAND) {
			stringBuilder.append(")");
		}

		if (!stringBuilder.toString().isEmpty()) {
			hasBefore = true;
		}

		return stringBuilder.toString();
	}

	public static String buildEqual(boolean addAND, String field, String uri) {
		StringBuilder stringBuilder = new StringBuilder();

		if (uri != null && !uri.isEmpty() && addAND) {
			stringBuilder.append(" and (");
		}

		stringBuilder.append("  " + field + " = ");
		stringBuilder.append("\"" + uri + "\"");

		if (uri != null && !uri.isEmpty() && addAND) {
			stringBuilder.append(")");
		}

		if (!stringBuilder.toString().isEmpty()) {
			hasBefore = true;
		}

		return stringBuilder.toString();
	}

	public static String buildNOT_IN(boolean addAND, String field, Set<Node> nodesNotIn) {
		StringBuilder stringBuilder = new StringBuilder();
		if (nodesNotIn == null || nodesNotIn.isEmpty()) {
			return "";
		}
		int count = 0;
		if (nodesNotIn != null && !nodesNotIn.isEmpty() && addAND) {
			stringBuilder.append(" and ");
		}
		if (nodesNotIn != null && !nodesNotIn.isEmpty()) {
			stringBuilder.append(field + " NOT IN ( ");
		}
		for (Node node : nodesNotIn) {
			stringBuilder.append(node.getId().trim());
			// stringBuilder.append("\"" + node.getUri() + "%\"");
			count++;
			if (count != nodesNotIn.size()) {
				stringBuilder.append(",");
			}
		}
		if (nodesNotIn != null && !nodesNotIn.isEmpty()) {
			stringBuilder.append(")");
		}

		return stringBuilder.toString();
	}

	public Set<Node> getMusicsNotInConvertToNode(List<Resource> resources, Set<Node> nodesNotIn) {
		if (resources.isEmpty() && nodesNotIn.isEmpty()) {
			return new HashSet<Node>();
		}
		Set<Node> nodes = new HashSet<Node>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from music as m where  " + buildEqual(false, "m.uri", resources) + " "
					+ buildNOT_IN(hasBefore, "m.id", nodesNotIn);

			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Node> getBooksNotInConvertToNode(List<Resource> resources, Set<Node> nodesNotIn) {
		if (resources.isEmpty() && nodesNotIn.isEmpty()) {
			return new HashSet<Node>();
		}
		Set<Node> nodes = new HashSet<Node>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from book as m where  " + buildEqual(false, "m.uri", resources) + " "
					+ buildNOT_IN(hasBefore, "m.id", nodesNotIn);
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Node> getUnionMoviesMusicsBooksNotInConvertToNode(List<Resource> resources, Set<Node> nodesNotIn) {
		if (resources.isEmpty() && nodesNotIn.isEmpty()) {
			return new HashSet<Node>();
		}
		Set<Node> nodes = new HashSet<Node>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM (" + "(select distinct m.id, m.uri from movie as m where  "
					+ buildEqual(false, "m.uri", resources) + " " + buildNOT_IN(hasBefore, "m.id", nodesNotIn) + ")  "
					+ " UNION ALL " + "(select distinct m.id, m.uri from music as m where  "
					+ buildEqual(false, "m.uri", resources) + " " + buildNOT_IN(hasBefore, "m.id", nodesNotIn) + ")  "
					+ " UNION ALL " + "(select distinct m.id, m.uri from book as m where   "
					+ buildEqual(false, "m.uri", resources) + " " + buildNOT_IN(hasBefore, "m.id", nodesNotIn) + ")  "
					+ ") as x";
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Node> getUnionMoviesMusicsBooksNotInConvertToNode(String uri) {

		Set<Node> nodes = new HashSet<Node>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM (" + "(select distinct m.id, m.uri from movie as m where  "
					+ buildEqual(false, "m.uri", uri) + " )  " + " UNION ALL "
					+ "(select distinct m.id, m.uri from music as m where  " + buildEqual(false, "m.uri", uri) + " )  "
					+ " UNION ALL " + "(select distinct m.id, m.uri from book as m where   "
					+ buildEqual(false, "m.uri", uri) + " )  " + ") as x";
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Node> getMoviesNotInConvertToNode(List<Resource> resources, Set<Node> nodesNotIn) {
		if (resources.isEmpty() && nodesNotIn.isEmpty()) {
			return new HashSet<Node>();
		}
		Set<Node> nodes = new HashSet<Node>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from movie as m where  " + buildEqual(false, "m.uri", resources) + " "
					+ buildNOT_IN(hasBefore, "m.id", nodesNotIn);
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Node> getMusicsConvertToNode(List<Resource> resources) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from music as m where  " + buildEqual(false, "m.uri", resources);
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	/**
	 * @return
	 */
	public Set<Node> getMoviesConvertToNode(List<Resource> resources) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from movie as m where " + buildEqual(false, "m.uri", resources);
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	/**
	 * @return
	 */
	public Set<Node> getBooksConvertToNode(List<Resource> resources) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.id, b.uri from book as b where " + buildEqual(false, "b.uri", resources);
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Node> getUnionLikedMoviesMusicsBooksUserIdAndConvertToNode(int userId, List<Resource> resources) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM ("
					+ "(select distinct m.id, m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid = "
					+ userId + " " + buildEqual(true, "m.uri", resources) + ")  " + " UNION ALL "
					+ "(select distinct m.id, m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid = "
					+ userId + " " + buildEqual(true, "m.uri", resources) + ")  " + " UNION ALL "
					+ "(select distinct m.id, m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid = "
					+ userId + " " + buildEqual(true, "m.uri", resources) + ")  " + ") as x";
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	/**
	 * @param userId
	 * @return
	 */
	public Set<Node> getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(int userId) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			StringBuilder query = new StringBuilder(
					"select distinct *  FROM ((select distinct m.id, m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid = ? )   UNION ALL "
							+ "(select distinct m.id, m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid = ? )  UNION ALL "
							+ "(select distinct m.id, m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid = ? )  ) as x");
			PreparedStatement ps = conn.prepareStatement(query.toString());
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.setInt(3, userId);

			// System.out.println(ps);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs != null && rs.next()) {
				// nodes.add(new
				// Node(String.valueOf(rs.getInt(1)),IConstants.LIKE,rs.getString(2).trim()));
				nodes.add(new Node(null, IConstants.LIKE, rs.getString(2).trim()));
				// NodeUtil.print(rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return nodes;
	}

	/**
	 * @param userId
	 * @return
	 */
	public Set<String> getUnionLikedMoviesMusicsBooksByUserIdAndConvertToString(int userId) {
		Set<String> likedItems = new HashSet<String>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM ("
					+ "(select distinct m.id, m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid = ? )  "
					+ " UNION ALL "
					+ "(select distinct m.id, m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid = ? )  "
					+ " UNION ALL "
					+ "(select distinct m.id, m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid = ? )  "
					+ ") as x";

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.setInt(3, userId);

			// System.out.println(ps);

			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				// nodes.add(new
				// Node(String.valueOf(rs.getInt(1)),IConstants.LIKE,rs.getString(2).trim()));
				likedItems.add(rs.getString(2).trim());
				// NodeUtil.print(rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return likedItems;
	}

	/**
	 * @param userId
	 * @param amountOfRandomItems
	 * @return
	 */
	public Set<Node> getRandomUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(int userId, int amountOfRandomItems) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM ("
					+ "(select distinct m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid = ? )  "
					+ " UNION ALL "
					+ "(select distinct m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid = ? )  "
					+ " UNION ALL "
					+ "(select distinct m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid = ? )  "
					+ ") as x ORDER BY RAND() LIMIT ?";
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.setInt(3, userId);
			ps.setInt(4, amountOfRandomItems);
			// System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				// nodes.add(new
				// Node(String.valueOf(rs.getInt(1)),IConstants.LIKE,rs.getString(2).trim()));
				nodes.add(new Node(null, IConstants.LIKE, rs.getString(1).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return nodes;
	}

	/**
	 * @param userId
	 * @param amountOfRandomItems
	 * @return
	 */
	public Set<Node> getRandomUnionNonLikedMoviesMusicsBooksByUserIdAndConvertToNode(int userId,
			int amountOfRandomItems) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM ("
					+ "(select distinct m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid <> ? )  "
					+ " UNION ALL "
					+ "(select distinct m.uri from movie as m, movie_like as ml where  m.id = ml.movieid and ml.userid <> ? )  "
					+ " UNION ALL "
					+ "(select distinct m.uri from book  as m, book_like  as ml where  m.id = ml.bookid  and ml.userid <> ? )  "
					+ ") as x ORDER BY RAND() LIMIT ?";

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.setInt(3, userId);
			ps.setInt(4, amountOfRandomItems);
			// System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				// nodes.add(new
				// Node(String.valueOf(rs.getInt(1)),IConstants.LIKE,rs.getString(2).trim()));
				nodes.add(new Node(null, IConstants.NO_LABEL, rs.getString(1).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return nodes;
	}

	/**
	 * @param userId
	 * @param amountOfRandomItems
	 * @return
	 */
	public Set<Node> getItemsByCNNSizeAndConvertToNode(int rangeInit, int rangeEnd, int limit) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select uri1, COUNT(uri2) as x from link group by uri1 HAVING x > ? and x < ? LIMIT ?";

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, rangeInit);
			ps.setInt(2, rangeEnd);
			ps.setInt(3, limit);
			// System.out.println(ps);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs != null && rs.next()) {
				// nodes.add(new
				// Node(String.valueOf(rs.getInt(1)),IConstants.LIKE,rs.getString(2).trim()));
				nodes.add(new Node(null, IConstants.NO_LABEL, rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return nodes;
	}

	/**
	 * @param userId
	 * @param amountOfRandomItems
	 * @return
	 */
	public Set<Node> getItemsByURIAndCNNSizeAndConvertToNode(String uri, int rangeInit, int rangeEnd) {
		Set<Node> nodes = new HashSet<Node>();
		try {

			Connection conn = DBConnection.getConnection();
			String query = "select uri1, COUNT(uri2) as x from link group by uri1 HAVING x > ? and x < ? and uri1 = ?";

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(3, uri);
			ps.setInt(1, rangeInit);
			ps.setInt(2, rangeEnd);
			// System.out.println(ps);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs != null && rs.next()) {
				// nodes.add(new
				// Node(String.valueOf(rs.getInt(1)),IConstants.LIKE,rs.getString(2).trim()));
				nodes.add(new Node(null, IConstants.NO_LABEL, rs.getString(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return nodes;
	}

	/**
	 * @param startRange
	 * @param endRange
	 * @return
	 */
	public Set<Integer> getUserIdsWithLikedItemsByRange(int startRange, int endRange) {
		Set<Integer> userIds = new HashSet<Integer>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct userid  FROM ("
					+ "(select distinct count(mu.id) as total, userid  from music as mu, music_like as mul where  mu.id = mul.musicid  group by userid)  "
					+ " UNION ALL "
					+ "(select distinct count(mo.id) as total, userid from movie as mo, movie_like as mol where  mo.id = mol.movieid group by userid)  "
					+ " UNION ALL "
					+ "(select distinct count(bo.id) as total, userid from book  as bo, book_like  as bol where  bo.id = bol.bookid  group by userid)"
					+ ") as x group by userid  having sum(total) > " + startRange + " and sum(total) < " + endRange
					+ " ";
			// System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				// nodes.add(new
				// Node(String.valueOf(rs.getInt(1)),IConstants.LIKE,rs.getString(2).trim()));
				userIds.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return userIds;
	}

	public Set<Node> getUnionLikedMoviesMusicsBooksAndConvertToNode() {
		Set<Node> nodes = new HashSet<Node>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM ("
					+ "(select distinct m.id, m.uri, ml.userid from music as m, music_like as ml where  m.id = ml.musicid  )  "
					+ " UNION ALL "
					+ "(select distinct m.id, m.uri, ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid  )  "
					+ " UNION ALL "
					+ "(select distinct m.id, m.uri, ml.userid from book  as m, book_like  as ml where  m.id = ml.bookid   )  "
					+ ") as x";

			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(
						new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim(), rs.getInt(1)));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Integer> getUserIdsForLikedMoviesMusicsBooks() {
		Set<Integer> nodes = new HashSet<Integer>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM ("
					+ "(select distinct ml.userid from music as m, music_like as ml where  m.id = ml.musicid  )  "
					+ " UNION ALL "
					+ "(select distinct ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid  )  "
					+ " UNION ALL "
					+ "(select distinct ml.userid from book  as m, book_like  as ml where  m.id = ml.bookid   )  "
					+ ") as x";

			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Node> getUnionLikedMoviesMusicsBooksUserIdAndConvertToNode(String uri) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			// Connection conn = BDConexao.getConexao(); String query = "select m.id as id,
			// m.uri as uri FROM ("+
			Connection conn = DBConnection.getConnection();
			String query = "select distinct *  FROM ("
					+ "(select distinct m.id, m.uri from music as m, music_like as ml where  m.uri = \"" + uri
					+ "\" )  " + " UNION ALL "
					+ "(select distinct m.id, m.uri from movie as m, movie_like as ml where  m.uri = \"" + uri + "\"  "
					+ " UNION ALL " + "(select distinct m.id, m.uri from book  as m, book_like  as ml where  m.uri = \""
					+ uri + "\"  " + ") as x";
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.NO_LABEL, rs.getString(2).trim().trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Node> getLikedMusicsUserIdAndConvertToNode(String userId, List<Resource> resources) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from music as m, music_like as ml where  m.id = ml.musicid and ml.userid = "
					+ userId + " " + buildEqual(true, "m.uri", resources);
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim().trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	/**
	 * @return
	 */
	public Set<Node> getLikedMoviesUserIdAndConvertToNode(String userId, List<Resource> resources) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from movie as m, movie_like as ml where m.id = ml.movieid and ml.userid = "
					+ userId + " " + buildEqual(true, "m.uri", resources);
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	/**
	 * @return
	 */
	public Set<Node> getLikedBooksUserIdAndConvertToNode(String userId, List<Resource> resources) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.id, b.uri from book as b, book_like as bl where b.id = bl.bookid and bl.userid = "
					+ userId + " " + buildEqual(true, "b.uri", resources);
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim()));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Node getLikedMusicUserIdAndConvertToNode(String uri, String userId, Set<Resource> resources) {
		Node node = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from music as m, music_like as ml where m.id = ml.musicid and ml.userid = "
					+ userId + " and m.uri =  \"" + uri + "\"";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return node;
	}

	/**
	 * @return
	 */
	public Node getLikedBookUserIdAndConvertToNode(String uri, String userId) {
		Node node = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.id, b.uri from book as b, book_like as bl where b.id = bl.bookid and bl.userid = "
					+ userId + " and b.uri =  \"" + uri + "\"";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return node;
	}

	/**
	 * @return
	 */
	public Node getLikedMusicUserIdAndConvertToNode(String uri, String userId) {
		Node node = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from music as m, music_like as ml where m.id = ml.musicid and ml.userid = "
					+ userId + " and m.uri =  \"" + uri + "\"";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return node;
	}

	/**
	 * @return
	 */
	public Node getLikedMovieUserIdAndConvertToNode(String uri, String userId) {
		Node node = null;
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from movie as m, movie_like as ml where m.id = ml.movieid and ml.userid = "
					+ userId + " and m.uri =  \"" + uri + "\"";
			// System.out.println(getConnection().Query);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				node = new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return node;
	}

	/**
	 * @return
	 */
	public static Set<String> getBooksURI() {
		Set<String> bookURIs = new HashSet<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select * from book";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				bookURIs.add(rs.getString(2).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return bookURIs;
	}

	/**
	 * @return
	 */
	public Set<String> getBooksURILikedByUser(String userId) {
		Set<String> bookURIs = new HashSet<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.uri from book as b, book_like as bl where b.id = bl.bookid and bl.userid = "
					+ userId;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				bookURIs.add(rs.getString(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return bookURIs;
	}

	/**
	 * @return
	 */
	public Set<Node> getBooksLikedByUserAsNodes(String userId) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select b.id, b.uri from book as b, book_like as bl where b.id = bl.bookid and bl.userid = "
					+ userId;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node node = new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim());
				nodes.add(node);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return nodes;
	}

	/**
	 * @return
	 */
	public Set<Node> getMusicsLikedByUserAsNodes(String userId) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from music as m, music_like as ml where m.id = ml.musicid and ml.userid = "
					+ userId;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node node = new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim());
				nodes.add(node);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return nodes;
	}

	/**
	 * @return
	 */
	public Set<Node> getMoviesURILikedByUserAsNodes(String userId) {
		Set<Node> nodes = new HashSet<Node>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.id, m.uri from movie as m, movie_like as ml where m.id = ml.movieid and ml.userid = "
					+ userId;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				Node node = new Node(String.valueOf(rs.getInt(1)), IConstants.LIKE, rs.getString(2).trim());
				nodes.add(node);
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return nodes;
	}

	/**
	 * @return
	 */
	public Set<String> getMusicsURILikedByUser(String userId) {
		Set<String> uris = new HashSet<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select m.uri from music as m, music_like as ml where m.id = ml.musicid and ml.userid = "
					+ userId;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uris.add(rs.getString(1).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return uris;
	}

	/**
	 * @return
	 */
	public Set<String> getMoviesURILikedByUser(String userId) {
		Set<String> uris = new HashSet<String>();
		try {

			Connection conn = DBConnection.getConnection();
			String query = "select m.uri from movie as m, movie_like as ml where m.id = ml.movieid and ml.userid = "
					+ userId;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uris.add(rs.getString(1).trim());
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return uris;
	}

	/**
	 * @return
	 */
	public Set<String> getMoviesFilmsBooksURILikedByUser(String userId) {
		Set<String> uris = new HashSet<String>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = " select distinct mo.uri from movie as mo , movie_like as mol, book as b, book_like as bl, "
					+ " music as mu, music_like as mul where mo.id = mol.movieid and b.id = bl.bookid and mu.id = mul.musicid "
					+ " and mol.userid = " + userId + " and  bl.userid  = " + userId + " and mul.userid = " + userId;
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				uris.add(rs.getString(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return uris;
	}

	/*
	 * public void checkDistance(int limitIteration) throws Exception {
	 * 
	 * Set<Integer> ids = getUserIdsForLikedMoviesMusicsBooks();
	 * 
	 * for (Integer id : ids) {
	 * 
	 * Set<Node> userProfile = new HashSet<Node>();
	 * 
	 * userProfile.addAll(getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(id)
	 * );
	 * 
	 * List<String> control = new ArrayList<String>(); int limit = limitIteration;
	 * 
	 * for (Node node1 : userProfile) { for (Node node2 : userProfile) { if
	 * (node1!=node2 && (!control.contains(node1.getURI()+node2.getURI()+id) ||
	 * !control.contains(node2.getURI()+node1.getURI()+id)) &&
	 * !checkRelated(node1.getURI(), node2.getURI(),id)) {
	 * NodeUtil.print("Testing uiser id"+
	 * id+"  for  "+node1.getURI()+" versus "+node2.getURI()); int dist =
	 * SparqlWalk.findPathsBetween2Resources(node1.getURI(),node2.getURI(),limit);
	 * //int dist = SparqlWalk.findPathsBetween2Resources(
	 * "http://dbpedia.org/resource/Katy_Perry",
	 * "http://dbpedia.org/resource/Jones_Leandro",limit); //if (dist==0 &&
	 * (!(control.contains(node1.getURI()+node2.getURI())||control.contains(node2.
	 * getURI()+node1.getURI())))) { //print("User id"+
	 * id+" with  "+node1.getURI()+" is distant of "+node2.getURI()+" at least "+
	 * limit); //}else if (dist!=0) { insertRelated(node1.getURI(),
	 * node2.getURI(),id); NodeUtil.print("User id"+
	 * id+" with  "+node1.getURI()+" is distant of "+node2.getURI()+" = "+ dist); }
	 * else if
	 * (SparqlWalk.countIndirectIncomingLinksFromResourceAndLink(node1.getURI(),
	 * node2.getURI())>0) { insertRelated(node1.getURI(), node2.getURI(),id);
	 * NodeUtil.print("User id"+
	 * id+" with  "+node1.getURI()+" is distant of "+node2.getURI()+" = "+ dist); }
	 * else if
	 * (SparqlWalk.countIndirectOutgoingLinksFromResourceAndLink(node1.getURI(),
	 * node2.getURI())>0) { insertRelated(node1.getURI(), node2.getURI(),id);
	 * NodeUtil.print("User id"+
	 * id+" with  "+node1.getURI()+" is distant of "+node2.getURI()+" = "+ dist); }
	 * 
	 * control.add(node1.getURI()+node2.getURI()+id);
	 * control.add(node2.getURI()+node1.getURI()+id);
	 * 
	 * } } }
	 * 
	 * NodeUtil.print("User id"+ id+" items are not related"); } }
	 */

	/**
	 * @param userId
	 * @param uri
	 * @return
	 */
	public Set<Node> getLikedItemsFromDatabase(int userId, String uri) {
		List<Resource> resources = new ArrayList<Resource>();
		Resource resource = new ResourceImpl(uri);
		resources.add(resource);
		return getNodesFromDatabase(userId, resources);
	}

	/**
	 * @param userId
	 * @param uri
	 * @return
	 */
	public Set<Node> getNodesFromDatabase(int userId, String uri) {
		List<Resource> resources = new ArrayList<Resource>();
		Resource resource = new ResourceImpl(uri);
		resources.add(resource);
		return getNodesFromDatabase(userId, resources);
	}

	/**
	 * @param userId
	 * @param resources
	 * @return
	 */
	public Set<Node> getNodesFromDatabase(int userId, List<Resource> resources) {
		Set<Node> databaseNodes = new HashSet<Node>();
		// resources = resources.subList(0,1);
		Set<Node> nodes = getUnionLikedMoviesMusicsBooksUserIdAndConvertToNode(userId, resources);
		if (!nodes.isEmpty()) {
			databaseNodes.addAll(nodes);
		}
		nodes = getUnionMoviesMusicsBooksNotInConvertToNode(resources, nodes);
		if (!nodes.isEmpty()) {
			databaseNodes.addAll(nodes);
		}
		return databaseNodes;
	}

	/**
	 * @param nodesDB
	 * @param nodesToGraph
	 */
	private void normalizeBDtoGraph(Set<Node> nodesDB, Set<Node> nodesToGraph) {
		for (Node node : nodesDB) {
			Node nodeUpdate = NodeUtil.getNodeByURI(node.getURI(), nodesToGraph);
			if (nodeUpdate != null) {
				nodeUpdate.setLabel(node.getLabel());
				NodeUtil.printNode(nodeUpdate);
			}
		}
	}

	/**
	 * It gets user profiles from database
	 * 
	 * @param userId
	 * @param resources
	 * @return
	 */
	public Set<Node> getLikedNodesFromDatabase(int userId, List<Resource> resources) {
		Set<Node> databaseNodes = new HashSet<Node>();
		// resources = resources.subList(0,1);
		Set<Node> nodes = getUnionLikedMoviesMusicsBooksUserIdAndConvertToNode(userId, resources);
		if (!nodes.isEmpty()) {
			databaseNodes.addAll(nodes);
		}
		return databaseNodes;
	}

	public Set<Integer> getUserIdsForLikedMovies() {
		Set<Integer> nodes = new HashSet<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select distinct ml.userid from movie as m, movie_like as ml where  m.id = ml.movieid";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Integer> getUserIdsForLikedMusics() {
		Set<Integer> nodes = new HashSet<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select distinct ml.userid from music as m, music_like as ml where  m.id = ml.musicid";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public Set<Integer> getUserIdsForLikedBooks() {
		Set<Integer> nodes = new HashSet<Integer>();
		try {
			Connection conn = DBConnection.getConnection();
			String query = "select distinct ml.userid from book as m, book_like as ml where  m.id = ml.bookid";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs != null && rs.next()) {
				nodes.add(rs.getInt(1));
			}
			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		hasBefore = false;
		return nodes;
	}

	public void collectDomainStatitics() {
		for (Integer userId : getUserIdsForLikedMoviesMusicsBooks()) {
			for (Node node : getUnionLikedMoviesMusicsBooksByUserIdAndConvertToNode(userId)) {
				List<Resource> resources = new ArrayList<Resource>();
				resources = SparqlWalk.getDBpediaObjecstBySubject(node.getURI());
				resources.addAll(SparqlWalk.getDBpediaSubjectsByObjects(node.getURI()));
				getStatisticsDomain(node, 1, resources);
			}
		}
	}

	/**
	 * Collects domain of neighbours
	 * 
	 * @param nodeOriginal
	 * @param iteration
	 * @param resources
	 */
	public void getStatisticsDomain(Node nodeOriginal, int iteration, List<Resource> resources) {
		// SELECT count(*) as total, domain FROM lod.domain group by domain order by
		// total desc
		for (Resource resource : resources) {
			if (nodeOriginal.getURI().length() > 255 || resource.getURI().length() > 255) {
				NodeUtil.print(nodeOriginal.getURI());
				NodeUtil.print(resource.getURI());
				continue;
			}
			if (!checkDomain(nodeOriginal.getURI(), resource.getURI())) {
				String domain = SparqlWalk.getMostSpecificSubclasseOfDbpediaResource(resource.getURI()).getURI();
				insertDomain(nodeOriginal.getURI(), resource.getURI(), domain, (iteration + 1));
			}
		}
	}

	/**
	 * @param conn
	 * @param query
	 * @return
	 */
	public static int executeAndClose(Connection conn, String query) {
		boolean exec = false;
		PreparedStatement ps = null;
		try {
			try {
				ps = conn.prepareStatement(query);
				exec = ps.execute();
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

		if (exec) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * @param conn
	 * @param query
	 * @return
	 */
	public static void closeQuery(Connection conn, PreparedStatement ps) {
		try {
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param conn
	 * @param query
	 * @return
	 */
	public void closeConnections() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calculateRRByScalingForAllMethods() {
		calculateRRByScaling(IConstants.LDSD_LOD);
		calculateRRByScaling(IConstants.LDSD);
	}

	/**
	 * @param method
	 */
	private void calculateRRByScaling(String method) {
		LinkedList<Integer> userIds = getUserIdsOfEvaluatedItems(method);
		for (Integer userId : userIds) {
			LinkedList<Evaluation> evaluations = new LinkedList<Evaluation>(
					getEvaluationsByMethodSortedByScore(method, userId));
			// NodeUtil.printEvaluations(evaluations);
			for (Evaluation evaluation : evaluations) {
				double position = Math
						.round(StringUtilsNode.scale(evaluation.getScore(), -1, 1, evaluations.size(), 1));
				double rr = 1d / position;
				// NodeUtil.print("Position is "+position);
				// NodeUtil.print("RR is "+rr);
				evaluation.setRr(rr);
				evaluation.setPosition((int) position);
				// NodeUtil.printEvaluation(evaluation);
			}
			// NodeUtil.printEvaluations(evaluations);
			// insertOrUpdateEvaluations(evaluations);
		}
	}

	/**
	 * @param userid
	 * @param originalCandidate
	 * @param method
	 */
	private void calculateRR(int userid, String originalCandidate, String method) {

		LinkedList<Evaluation> evaluations = new LinkedList<Evaluation>(
				getEvaluationsByOriginalCandidateSortedByScore(userid, originalCandidate, method));
		// NodeUtil.printEvaluations(evaluations);
		Lodica.calculateRR(evaluations);
		// NodeUtil.printEvaluations(evaluations);
		insertOrUpdateEvaluations(evaluations);
	}

	// Mysql export utilities Running: mysqldump.exe
	// http://giantdorks.org/alain/export-mysql-data-into-csv-or-psv-files/
	public static void mysqlExport(String pathToMySqlExecutables, String host, String port, String user,
			String password, String dbname, String table, String folder) {

		System.out
				.println("------ Exporting " + dbname + "." + table + " at " + folder + "---------------------------");
		try {
			String command = pathToMySqlExecutables + "mysqldump --host=" + host + " --port=" + port + " --user=" + user
					+ " --password=" + password + " " + dbname + " " + table + "   > " + folder + table + ".sql";
			System.out.println(command);
			int returnValue = executeCommand(Arrays.asList(pathToMySqlExecutables + "mysqldump", "--host=" + host,
					"--port=" + port, "--user=" + user, "--password=" + password, dbname, table),
					folder + table + ".sql");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void mysqlImport(String pathToMysqldump, String host, String port, String user, String password,
			String dbname, String table, String folder, String filename) {

		System.out
				.println("------ Importing " + dbname + "." + table + " at " + folder + "---------------------------");
		try {
			String command = pathToMysqldump + "mysql --host=" + host + " --port=" + port + " --user=" + user
					+ " --password=" + password + " " + dbname + " " + table + " < " + folder + filename + ".sql";
			System.out.println(command);
			int returnValue = executeCommand(
					new String[] { pathToMysqldump + "mysql", "--host=" + host, "--port=" + port, "--user=" + user,
							"--password=" + password, dbname, "-e", "source " + folder + filename + ".csv" });
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void mysqlExport(String pathToMysqldump, String host, String port, String user, String password,
			String dbname, String table, String folder, String query) {

		System.out
				.println("------ Exporting " + dbname + "." + table + " at " + folder + "---------------------------");
		try {
			String command = pathToMysqldump + "mysqldump --host=" + host + " --port=" + port + " --user=" + user
					+ " --password=" + password + " " + dbname + " " + table + " --where=\"" + query + "\" > " + folder
					+ table + ".sql";
			// System.out.println(command);
			int returnValue = executeCommand(
					Arrays.asList(pathToMysqldump + "mysqldump", "--add-drop-table", "--host=" + host, "--port=" + port,
							"--user=" + user, "--password=" + password, dbname, table, "--where=" + query),
					folder + table + ".sql");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static int executeCommand(String[] commands) throws IOException {
		// System.out.println(commands.toString());
		Process process = Runtime.getRuntime().exec(commands);
		return dumpProcess(process);
	}

	public static int executeCommand(List<String> commands, String folder) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(commands);
		// System.out.println(builder.command());
		builder.redirectOutput(new File(folder));
		Process process = builder.start();
		return dumpProcess(process);
	}

	public static int dumpProcess(Process process) throws IOException {
		int returnValue = -1;
		try {
			String s = null;
			process.waitFor();
			returnValue = process.exitValue();
			if (returnValue == 0) {
				System.out.println("Command successful !!");
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
				/*
				 * System.out.println("Here is the standard output of the command:\n"); while
				 * ((s = stdInput.readLine()) != null) { System.out.println(s); }
				 */
			} else {
				System.out.println("Command failed. Exist Status code = " + returnValue);
				BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				/*
				 * System.out.println("Here is the standard error of the command (if any):\n");
				 * while ((s = stdError.readLine()) != null) { System.out.println(s); }
				 */
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * If the security problem issues a problem, please refer to:
	 * http://stackoverflow.com/questions/14597884/mysql-my-ini-location
	 * 
	 * @param filename
	 * @param table
	 */
	public static void exportDataByINFILE(String filename, String table) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement ps = null;
		String query;
		try {
			query = " LOAD DATA LOCAL INFILE '" + filename
					+ "' INTO TABLE `LOD`.`PREDICTION`  CHARACTER SET 'utf8' FIELDS  OPTIONALLY  ENCLOSED BY '\"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;";
			ps = conn.prepareStatement(query);
			ps.execute();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exportDataBySELECT(String filename, String table) {
		Connection conn = DBConnection.getConnection();
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.executeQuery("SELECT * INTO OUTFILE \"'" + filename + "\"   FROM " + table);
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * Exibe o resultado da RecomendaÃ§Ã£o e retorna uma lista com os filmes com
	 * similaridade
	 */
	public List<Document> resultRecommendation(int userid, String type) {
		List<Document> rankedItems = new ArrayList<Document>();

		try {
			Connection conn = DBConnection.getConnection();
			String query = "SELECT DISTINCT * FROM semantic_raking WHERE userid = ? AND sim = ? AND score != 0 AND score < 1 ORDER BY score DESC ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userid);
			ps.setString(2, type);
			ResultSet rs = ps.executeQuery();
			int cont = 1;

			System.out.println(" \n ---------- RAKING DOS FILME UTILIZANDO UTILIZANDO " + type + "--------------- \n");
			System.out.println(" CLASSIFICAÃ‡ÃƒO |  USUARIO |  FILME RECOMENDADO |  TIPO  | SIMILARIDADE ");
			while (rs != null && rs.next()) {

				System.out.println(
						"       " + cont + "           " + rs.getInt(6) + "         " + findNameOfFilm(rs.getInt(3))
								/*+ "                " + rs.getString(3) + "    " + rs.getDouble(4)*/);
				System.out.println(" ---------------------------------------------------------------------------");

				Document document = new Document(rs.getInt(3), findNameOfFilm(rs.getInt(3)));
				
				rankedItems.add(document);

				cont = cont + 1;
			}

			CBRecommender.end = System.currentTimeMillis();

			System.out.println("   Tempo Total do Calculo:    " + String.valueOf(CBRecommender.end - CBRecommender.init));

			closeQuery(conn, ps);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rankedItems;
	}

	

	/**
	 * @throws Exception
	 *//*
		 * private void calculateAndSaveEvaluationMetrics() throws Exception {
		 * 
		 * //double rr = calculateRR();
		 * 
		 * int correctPrediction = 0;
		 * 
		 * int incorrectPrediction = 0;
		 * 
		 * NodePrediction predictedLeftOut =
		 * getDatabaseConnection().getPredictionEvaluation(nodeUnderEvaluation.getURI(),
		 * userId,IConstants.USE_ICA); //NodeUtil.printPrediction(predictedLeftOut); if
		 * (!NodeUtil.isDistinctURI(predictedLeftOut.getNode(), nodeUnderEvaluation)) {
		 * if (predictedLeftOut.getPredictedLabel().equals(IConstants.LIKE)) {
		 * correctPrediction++; }else{ incorrectPrediction++; }
		 * getDatabaseConnection().insertOrUpdateEvaluation(predictedLeftOut.getNode().
		 * getURI(), correctPrediction, incorrectPrediction, IConstants.USE_ICA,
		 * predictedLeftOut.getUserId(),predictedLeftOut.getPredictionScore(),null,
		 * predictedLeftOut.getOriginalCandidate(),null); }else{
		 * NodeUtil.print("Something went wrong..."); }
		 * 
		 * //Evaluation evaluation =
		 * getDatabaseConnection().getEvaluation(predictedLeftOut.getNode().getURI(),
		 * IConstants.USE_ICA, predictedLeftOut.getUserId());
		 * //NodeUtil.print("\nEVALUATION ONE LEAVE OUT...\n");
		 * //NodeUtil.printEvaluation(evaluation); }
		 */

}