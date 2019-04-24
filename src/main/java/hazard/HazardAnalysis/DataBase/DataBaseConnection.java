package hazard.HazardAnalysis.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
import javafx.collections.ObservableList;

public class DataBaseConnection {

	private static String database;

	private static Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:" + database;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	@SuppressWarnings("unchecked")
	public static <E> void selectAll(String table, ObservableList<E> list) {
		String sql = "SELECT id, name FROM " + table;

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				if (table.contentEquals("kind")) {
					list.add((E) new Kind(rs.getInt("id"), rs.getString("name")));

				} else {
					list.add((E) new Role(rs.getInt("id"), rs.getString("name")));
					System.out.println(rs.getInt("id") + rs.getString("name"));

				}

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insert(String table, String name) {
		String sql = "INSERT INTO " + table + "(name) VALUES(?)";

		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void delete(String table, int id) {
		String sql = "DELETE FROM " + table + " WHERE id = ?";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// set the corresponding param
			pstmt.setInt(1, id);
			// execute the delete statement
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String getDatabase() {
		return database;
	}

	public static void setDatabase(String database) {
		DataBaseConnection.database = database;
	}
}
