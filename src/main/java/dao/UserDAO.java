package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import model.User;

public class UserDAO {

	public boolean insert(User user) throws SQLException {
		String sql = "INSERT INTO users (username,email,password) VALUES (?,?,?)";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());

			if (user.getUserId() == 0) {
				statement.setNull(4, Types.INTEGER);
			} else {
				statement.setInt(4, user.getUserId());
			}

			int affectedRows = statement.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						user.setUserId(generatedKeys.getInt(1));
					}
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("DBのINSERT処理エラー発生");
		}
		return false;
	}

	public User login(String email, String password) throws SQLException {
		String hash = hPassword(password);

		String sql = "SELECT username,email,password,userId FROM users WHERE email=? AND password=?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, email);
			statement.setString(2, hash);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ログインエラー発生");
		}

		return null;
	}

	//ハッシュ化
	private String hPassword(String password) {
		try {
			MessageDigest message = MessageDigest.getInstance("SHA-256");
			byte[] hash = message.digest(password.getBytes());
			return hash.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.err.println("パスワードのハッシュ化でエラー発生");
			return null;
		}
	}

	public User findById(int userId) throws SQLException {
		String sql = "SELECT userId,username FROM users WHERE userId=?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					User user = new User();
					user.setUserId(resultSet.getInt("userId"));
					user.setUsername(resultSet.getString("name"));
					return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ユーザー特定エラー発生");
		}
		return null;
	}

	public boolean update(User user) throws SQLException {
		String sql = "UPDATE users SET username=?, bio=? WHERE userId=?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getBio());
			statement.setInt(3, user.getUserId());

			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ユーザー情報更新エラー発生");
			return false;
		}
	}

}
