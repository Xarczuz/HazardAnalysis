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

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS kind (\n" + "	id integer PRIMARY KEY,\n" + "	name text NOT NULL);";
		String sql2 = "CREATE TABLE IF NOT EXISTS role (\n" + "	id integer PRIMARY KEY,\n" + "	name text NOT NULL);";
		String sql3 = "CREATE TABLE IF NOT EXISTS roletoplay (\n" + "	id integer PRIMARY KEY,\n"
				+ "	kind text NOT NULL, kindid integer NOT NULL, role text NOT NULL, roleid integer NOT NULL);";
		String sql4 = "CREATE TABLE IF NOT EXISTS relator (\n" + "	id integer PRIMARY KEY,\n"
				+ "	realator text NOT NULL, role text NOT NULL, roleid integer NOT NULL);";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
			stmt.execute(sql2);
			stmt.execute(sql3);
			stmt.execute(sql4);

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
