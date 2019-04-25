package hazard.HazardAnalysis.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Relator;
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

	public static void delete(String table, int id) {
		String sql = "DELETE FROM " + table + " WHERE id = ?";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteARoleToPlay(String table, String id1, String id2) {
		String sql = "Delete FROM " + table + " WHERE roleid=? AND kindid=?;";

		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id1);
			pstmt.setString(2, id2);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void deleteRelatorToRole(String table, String id1, String id2) {
		String sql = "Delete FROM " + table + " WHERE roleid=? AND relatorid=?;";

		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id1);
			pstmt.setString(2, id2);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static String getDatabase() {
		return database;
	}

	public static void insert(String sql) {

		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

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

	@SuppressWarnings("unchecked")
	public static <E> void selectAll(String table, ObservableList<E> list) {
		String sql = "SELECT id, name FROM " + table;

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			list.clear();
			while (rs.next()) {
				if (table.contentEquals("kind")) {
					list.add((E) new Kind(rs.getInt("id"), rs.getString("name")));

				} else if (table.contentEquals("role")) {
					list.add((E) new Role(rs.getInt("id"), rs.getString("name")));

				} else if (table.contentEquals("relator")) {
					list.add((E) new Relator(rs.getInt("id"), rs.getString("name")));
				}

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void setDatabase(String database) {
		DataBaseConnection.database = database;
	}

	@SuppressWarnings("unchecked")
	public static <E> void sql(String sql, String table, ObservableList<E> list) {
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			list.clear();
			while (rs.next()) {
				if (table.contentEquals("kind")) {
					list.add((E) new Kind(rs.getInt("id"), rs.getString("name")));
				} else if (table.contentEquals("kindtorole")) {
					list.add((E) new Kind(rs.getInt("kindid"), rs.getString("kind")));
				} else if (table.contentEquals("role")) {
					list.add((E) new Role(rs.getInt("id"), rs.getString("name")));
				} else if (table.contentEquals("roletoplay")) {
					list.add((E) new Role(rs.getInt("roleid"), rs.getString("role")));
				} else if (table.contentEquals("relator")) {
					list.add((E) new Relator(rs.getInt("id"), rs.getString("name")));
				} else if (table.contentEquals("relatortorole")) {
					list.add((E) new Relator(rs.getInt("relatorid"), rs.getString("relator")));
				}

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
