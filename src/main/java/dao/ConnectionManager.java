package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String URL = "jdbc:mysql://localhost:3306/ai_chatbot";
	private static final String USER = "root";
	private static final String PASSWORD = "38711426StLgs";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("JDBCドライバのロードに失敗");
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static boolean testConnection() {
		try (Connection connection = getConnection()) {
			return connection != null && !connection.isClosed();
		} catch (SQLException e) {
			System.err.println("データベース接続失敗");
			return false;
		}
	}
}
