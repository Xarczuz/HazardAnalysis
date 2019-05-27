package hazard.HazardAnalysis.DataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author chjunchi
 *
 */
public class CreateDataBase {
	private static String database;

	public static void createNewDatabase() {
		String url = "jdbc:sqlite:" + database;
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createNewTable() {
		// SQLite connection string
		String url = "jdbc:sqlite:" + database;
		String sql1 = "CREATE TABLE IF NOT EXISTS kind (id integer PRIMARY KEY, kind text NOT NULL, start bit NOT NULL, runtime bit NOT NULL, shutdown bit NOT NULL,unique(kind));";
		String sql2 = "CREATE TABLE IF NOT EXISTS role (id integer PRIMARY KEY, role text NOT NULL, start bit NOT NULL, runtime bit NOT NULL, shutdown bit NOT NULL,unique(role));";
		String sql3 = "CREATE TABLE IF NOT EXISTS roletoplay (id integer PRIMARY KEY,kind text NOT NULL, kindid integer NOT NULL, role text NOT NULL, roleid integer NOT NULL);";
		String sql4 = "CREATE TABLE IF NOT EXISTS relator (id integer PRIMARY KEY,relator text NOT NULL,unique(relator));";
		String sql5 = "CREATE TABLE IF NOT EXISTS relatortorole (id integer PRIMARY KEY, relator text NOT NULL, relatorid integer NOT NULL, role TEXT NOT NULL, roleid INTEGER NOT NULL);";
		String sql6 = "CREATE TABLE IF NOT EXISTS hazard (id INTEGER PRIMARY KEY, hazard TEXT NOT NULL, harm TEXT NOT NULL);";
		String sql7 = "CREATE TABLE IF NOT EXISTS cause (id INTEGER PRIMARY KEY, cause TEXT NOT NULL, mitigation TEXT, hazardid INTEGER NOT NULL, severity REAL, probability REAL, riskevaluation REAL, risk BIT, postseverity REAL, postprobability REAL, postriskevaluation REAL, postrisk BIT);";
		String sql8 = "CREATE TABLE IF NOT EXISTS mishapvictim (id INTEGER PRIMARY KEY, kind TEXT NOT NULL,role TEXT NOT NULL,relator TEXT NOT NULL, kindid INTEGER NOT NULL,roleid INTEGER NOT NULL,relatorid INTEGER NOT NULL);";
		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql1);
			stmt.execute(sql2);
			stmt.execute(sql3);
			stmt.execute(sql4);
			stmt.execute(sql5);
			stmt.execute(sql6);
			stmt.execute(sql7);
			stmt.execute(sql8);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getDatabase() {
		return database;
	}

	public static void setDatabase(String database) {
		CreateDataBase.database = database;
	}
}
