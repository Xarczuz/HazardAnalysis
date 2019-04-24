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

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
			stmt.execute(sql2);
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
