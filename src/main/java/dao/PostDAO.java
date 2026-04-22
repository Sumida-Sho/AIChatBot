package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Post;

public class PostDAO {
	public boolean insert(Post post) throws SQLException {
		String sql = "INSERT INTO posts (user_id,content,ai_response) VALUES (?,?,?)";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			statement.setInt(1, post.getUserId());
			statement.setString(2, post.getContent());
			statement.setString(3, post.getAiResponse());

			int affectedRows = statement.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						post.setPostId(generatedKeys.getInt(1));
					}
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("DBのinsert処理エラー発生");
			return false;
		}
		return false;
	}

	public List<Post> findAll() throws SQLException {
		List<Post> posts = new ArrayList<>();
		String sql = "SELECT " +
				" p.post_id, " +
				" p.content, " +
				" p.ai_response, " +
				" p.created_at, " +
				" p.user_id, " +
				" u.username " +
				" FROM posts AS p JOIN users AS u ON p.user_id = u.user_id" +
				" ORDER BY p.created_at DESC";

		try (Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				Post post = new Post();
				post.setPostId(resultSet.getInt("post_id"));
				post.setUsername(resultSet.getString("username"));
				post.setContent(resultSet.getString("content"));
				post.setAiResponse(resultSet.getString("ai_response"));
				post.setCreatedAt(resultSet.getTimestamp("created_at"));
				posts.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("findAll処理エラー発生");
		}
		return posts;
	}

	public List<Post> findByUserId(int userId) throws SQLException {
		List<Post> myPosts = new ArrayList<>();
		String sql = "SELECT " +
				" p.post_id, " +
				" p.content, " +
				" p.ai_response, " +
				" p.created_at, " +
				" p.user_id, " +
				" u.username " +
				" FROM posts AS p JOIN users AS u ON p.user_id = u.user_id " +
				" WHERE p.user_id = ? " +
				" ORDER BY p.created_at DESC";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, userId);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Post post = new Post();
					post.setPostId(resultSet.getInt("post_id"));
					post.setUsername(resultSet.getString("username"));
					post.setContent(resultSet.getString("content"));
					post.setAiResponse(resultSet.getString("ai_response"));
					post.setCreatedAt(resultSet.getTimestamp("created_at"));
					myPosts.add(post);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("DBのSELECT処理エラー発生");
		}
		return myPosts;
	}

	public boolean delete(int postId, int userId) throws SQLException {
		String sql = "DELETE FROM posts WHERE post_id = ? AND user_id = ?";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, postId);
			statement.setInt(2, userId);

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("DBのdelete処理エラー発生");
			return false;
		}
	}

}
