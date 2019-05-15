package hazard.HazardAnalysis.DataBase;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.MishapVictim;
import hazard.HazardClasses.PossibleVictim;
import hazard.HazardClasses.Relator;
import hazard.HazardClasses.Role;
import javafx.collections.ObservableList;

public class DataBaseConnection {
	private static String database;

	private static Connection connect() {
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
		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
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

	public static void exportData(File file) {
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * from Hazard;";
			ResultSet rs = stmt.executeQuery(sql);
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Hazards");
			int rowIndex = 0;
			int index = 1;
			while (rs.next()) {
				Row header = sheet.createRow(rowIndex);
				rowIndex++;
				CellStyle headerStyle = workbook.createCellStyle();
				headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
				headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				headerStyle.setBorderTop(BorderStyle.THIN);
				headerStyle.setBorderRight(BorderStyle.THIN);
				headerStyle.setBorderBottom(BorderStyle.THIN);
				headerStyle.setBorderLeft(BorderStyle.THIN);
				XSSFFont font = ((XSSFWorkbook) workbook).createFont();
				font.setFontName("Liberation Sans");
				font.setFontHeightInPoints((short) 16);
				font.setBold(true);
				headerStyle.setFont(font);
				Cell headerCell = header.createCell(0);
				headerCell.setCellValue("No.");
				headerCell.setCellStyle(headerStyle);
				headerCell = header.createCell(1);
				headerCell.setCellValue("HML-Style Hazard Description");
				headerCell.setCellStyle(headerStyle);
				CellStyle style = workbook.createCellStyle();
				style.setWrapText(false);
				style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				style.setBorderTop(BorderStyle.THIN);
				style.setBorderRight(BorderStyle.THIN);
				style.setBorderBottom(BorderStyle.THIN);
				style.setBorderLeft(BorderStyle.THIN);
				font = ((XSSFWorkbook) workbook).createFont();
				font.setFontName("Liberation Sans");
				font.setFontHeightInPoints((short) 12);
				style.setFont(font);
				Row row = sheet.createRow(rowIndex);
				rowIndex++;
				Cell cell = row.createCell(0);
				cell.setCellValue("H" + index);
				index++;
				cell.setCellStyle(style);
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("hazard").trim());
				cell.setCellStyle(style);
				header = sheet.createRow(rowIndex);
				rowIndex++;
				headerCell = header.createCell(0);
				headerCell.setCellValue("");
				headerCell.setCellStyle(headerStyle);
				headerCell = header.createCell(1);
				headerCell.setCellValue("Natural Language Hazard Description");
				headerCell.setCellStyle(headerStyle);
				row = sheet.createRow(rowIndex);
				rowIndex++;
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(style);
				cell = row.createCell(1);
				cell.setCellValue(rs.getString("harm").trim());
				cell.setCellStyle(style);
				Row headerCause = sheet.createRow(rowIndex);
				rowIndex++;
				Cell headerCellCause = headerCause.createCell(0);
				headerCellCause.setCellValue("No.");
				headerCellCause.setCellStyle(headerStyle);
				headerCellCause = headerCause.createCell(1);
				headerCellCause.setCellValue("Pre-Initiating event for H" + (index - 1));
				headerCellCause.setCellStyle(headerStyle);
				headerCellCause = headerCause.createCell(2);
				headerCellCause.setCellValue("Probability");
				headerCellCause.setCellStyle(headerStyle);
				headerCellCause = headerCause.createCell(3);
				headerCellCause.setCellValue("Severity");
				headerCellCause.setCellStyle(headerStyle);
				headerCellCause = headerCause.createCell(4);
				headerCellCause.setCellValue("RPN");
				headerCellCause.setCellStyle(headerStyle);
				headerCellCause = headerCause.createCell(5);
				headerCellCause.setCellValue("Risk");
				headerCellCause.setCellStyle(headerStyle);
				headerCellCause = headerCause.createCell(6);
				headerCellCause.setCellValue("Rq.");
				headerCellCause.setCellStyle(headerStyle);
				headerCellCause = headerCause.createCell(7);
				headerCellCause.setCellValue("Mitigation");
				headerCellCause.setCellStyle(headerStyle);
				Statement stmt2 = conn.createStatement();
				String sql2 = "select *  from hazard,cause where cause.hazardid=" + rs.getInt("id") + " AND hazard.id="
						+ rs.getInt("id") + ";";
				ResultSet rs2 = stmt2.executeQuery(sql2);
				int i = 1;
				while (rs2.next()) {
					Row causes = sheet.createRow(rowIndex);
					rowIndex++;
					Cell cellCauses = causes.createCell(0);
					cellCauses.setCellValue("Cause " + i);
					cellCauses.setCellStyle(style);
					cellCauses = causes.createCell(1);
					cellCauses.setCellValue(rs2.getString("cause"));
					cellCauses.setCellStyle(style);
					cellCauses = causes.createCell(2);
					cellCauses.setCellValue(rs2.getString("probability"));
					cellCauses.setCellStyle(style);
					cellCauses = causes.createCell(3);
					cellCauses.setCellValue(rs2.getString("severity"));
					cellCauses.setCellStyle(style);
					cellCauses = causes.createCell(4);
					cellCauses.setCellValue(rs2.getString("riskevaluation"));
					cellCauses.setCellStyle(style);
					cellCauses = causes.createCell(5);
					String risk = "Denied";
					CellStyle styleRisk = workbook.createCellStyle();
					styleRisk.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					if (rs2.getBoolean("risk")) {
						risk = "Accept";
						styleRisk.setFillForegroundColor(IndexedColors.GREEN.getIndex());
					} else {
						styleRisk.setFillForegroundColor(IndexedColors.RED.getIndex());
					}
					cellCauses.setCellValue(risk);
					cellCauses.setCellStyle(styleRisk);
					cellCauses = causes.createCell(6);
					cellCauses.setCellValue("M " + i);
					i++;
					cellCauses.setCellStyle(style);
					cellCauses = causes.createCell(7);
					cellCauses.setCellValue(rs2.getString("mitigation"));
					cellCauses.setCellStyle(style);
				}
//				Row headerMitigation = sheet.createRow(rowIndex);
//				rowIndex++;
//				Cell headerCellMitigation = headerMitigation.createCell(0);
//				headerCellMitigation.setCellValue("Rq.");
//				headerCellMitigation.setCellStyle(headerStyle);
//				headerCellMitigation = headerMitigation.createCell(1);
//				headerCellMitigation.setCellValue("Mitigation for H" + (index - 1));
//				headerCellMitigation.setCellStyle(headerStyle);
//				Statement stmt4 = conn.createStatement();
//				String sql4 = "select *  from hazard,mitigation where mitigation.hazardid=" + rs.getInt("id")
//						+ " AND hazard.id=" + rs.getInt("id") + ";";
//				ResultSet rs4 = stmt4.executeQuery(sql4);
//				i = 1;
//				while (rs4.next()) {
//					Row mitigation = sheet.createRow(rowIndex);
//					rowIndex++;
//					Cell cellMitigation = mitigation.createCell(0);
//					cellMitigation.setCellValue("Mitigation " + i);
//					i++;
//					cellMitigation.setCellStyle(style);
//					cellMitigation = mitigation.createCell(1);
//					cellMitigation.setCellValue(rs4.getString("mitigation"));
//					cellMitigation.setCellStyle(style);
//				}
				rowIndex++;
			}
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			sheet.autoSizeColumn(7);
			PrintSetup ps = sheet.getPrintSetup();
			ps.setFitWidth((short) 1);
			ps.setFitHeight((short) 1);
			sheet.setAutobreaks(true);
			sheet.setFitToPage(true);
			String fileLocation = file.getPath();
			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
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

	public static void insertCause(String cause, int hazardID) {
		String sql = "INSERT INTO " + "cause" + "(cause,hazardid) VALUES(?,?)";
		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cause);
			pstmt.setInt(2, hazardID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertHazard(String hazard, String harm) {
		try {
			String sql = "INSERT INTO hazard (hazard,harm) VALUES(?,?)";
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hazard);
			pstmt.setString(2, harm);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertMishapVictim(int roleID, String role, int kindID, String kind, int relatorID,
			String relator) {
		try {
			String sql = "INSERT INTO mishapvictim (roleid,role,kindid,kind,relator,relatorid) VALUES(?,?,?,?,?,?)";
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roleID);
			pstmt.setString(2, role);
			pstmt.setInt(3, kindID);
			pstmt.setString(4, kind);
			pstmt.setInt(5, relatorID);
			pstmt.setString(6, relator);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertMitigationToCause(String mitigation, int causeID) {
		String sql = "UPDATE cause SET mitigation=? WHERE id = ?";
		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mitigation);
			pstmt.setInt(2, causeID);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertRelator(String table, String relator) {
		String sql = "INSERT INTO " + "relator" + "(relator) VALUES(?)";
		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, relator);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertRoloeOrKind(String table, String name, Boolean start, boolean runtime, boolean shutdown) {
		String sql = "INSERT INTO " + table + "(" + table + ",start,runtime,shutdown) VALUES(?,?,?,?)";
		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setBoolean(2, start);
			pstmt.setBoolean(3, runtime);
			pstmt.setBoolean(4, shutdown);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static <E> void selectAll(String table, ObservableList<E> list) {
		String sql = "SELECT * FROM " + table;
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list.clear();
			while (rs.next()) {
				if (table.contentEquals("kind")) {
					list.add((E) new Kind(rs.getInt("id"), rs.getString("kind"), rs.getBoolean("start"),
							rs.getBoolean("runtime"), rs.getBoolean("shutdown")));
				} else if (table.contentEquals("role")) {
					list.add((E) new Role(rs.getInt("id"), rs.getString("role"), rs.getBoolean("start"),
							rs.getBoolean("runtime"), rs.getBoolean("shutdown")));
				} else if (table.contentEquals("relator")) {
					list.add((E) new Relator(rs.getInt("id"), rs.getString("relator")));
				} else if (table.contentEquals("cause")) {
					list.add((E) new Cause(rs.getInt("id"), rs.getString("cause"), rs.getInt("hazardid")));
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void setDatabase(String database) {
		DataBaseConnection.database = database;
	}

	public static ResultSet sql(String sql) {
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <E> void sql(String sql, String table, ObservableList<E> list) {
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list.clear();
			while (rs.next()) {
				if (table.contentEquals("kind")) {
					list.add((E) new Kind(rs.getInt("id"), rs.getString("kind"), rs.getBoolean("start"),
							rs.getBoolean("runtime"), rs.getBoolean("shutdown")));
				} else if (table.contentEquals("kindtorole")) {
					list.add((E) new Kind(rs.getInt("kindid"), rs.getString("kind")));
				} else if (table.contentEquals("role")) {
					list.add((E) new Role(rs.getInt("id"), rs.getString("role"), rs.getBoolean("start"),
							rs.getBoolean("runtime"), rs.getBoolean("shutdown")));
				} else if (table.contentEquals("roletoplay")) {
					list.add((E) new Role(rs.getInt("roleid"), rs.getString("role")));
				} else if (table.contentEquals("relator")) {
					list.add((E) new Relator(rs.getInt("id"), rs.getString("relator")));
				} else if (table.contentEquals("relatortorole")) {
					list.add((E) new Relator(rs.getInt("relatorid"), rs.getString("relator")));
				} else if (table.contentEquals("possiblevictim")) {
					list.add((E) new PossibleVictim(rs.getInt("roleid"), rs.getString("role"),
							rs.getInt("kindid"), rs.getString("kind"), rs.getInt("relatorid"),
							rs.getString("relator")));
				} else if (table.contentEquals("mishapvictim")) {
					list.add((E) new MishapVictim(rs.getInt("id"), rs.getInt("roleid"), rs.getString("role"),
							rs.getInt("kindid"), rs.getString("kind"), rs.getInt("relatorid"),
							rs.getString("relator")));
				} else if (table.contentEquals("hazard")) {
					Hazard hz = new Hazard(rs.getInt("id"), rs.getString("hazard"), rs.getString("harm"));
					list.add((E) hz);
				} else if (table.contentEquals("cause")) {
					Cause c = new Cause(rs.getInt("id"), rs.getString("cause"), rs.getInt("hazardid"));
					Double d = rs.getDouble("riskevaluation");
					if (d != 0) {
						c.setRisk(String.valueOf(rs.getBoolean("risk")));
					}
					String m = rs.getString("mitigation");
					if (m != null) {
						c.setMitigation(m);
					}
					list.add((E) c);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void sqlUpdate(String sql) {
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "??");
		}
	}
}
