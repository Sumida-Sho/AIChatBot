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
		String sql = "INSERT INTO Post (userId,content,aiResponse) VALUES (?,?,?)";

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
		}
		return false;
	}

	public List<Post> findAll() throws SQLException {
		List<Post> posts = new ArrayList<>();
		String sql = "SELECT " +
				"p.postId, " +
				"p.content, " +
				"p.aiResponse, " +
				"p.createdAt, " +
				"p.userId, " +
				"u.username " +
				"FROM Post AS p JOIN User AS u ON p.userId = u.userId" +
				"ORDER BY p.createdAt DESC";

		try (Connection connection = ConnectionManager.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				Post post = new Post();
				post.setPostId(resultSet.getInt("postId"));
				post.setUsername(resultSet.getString("username"));
				post.setContent(resultSet.getString("content"));
				post.setAiResponse(resultSet.getString("aiResponse"));
				post.setCreatedAt(resultSet.getTimestamp("createdAt"));
				posts.add(post);
			}
		}
		return posts;
	}

	public List<Post> findByUserId(int userId) throws SQLException {
		List<Post> posts = new ArrayList<>();
		String sql = "SELECT " +
				"p.postId, " +
				"p.content, " +
				"p.aiResponse, " +
				"p.createdAt, " +
				"p.userId, " +
				"u.username " +
				"FROM Post AS p JOIN User AS u ON p.userId = u.userId " +
				"WHERE p.userId = ? " +
				"ORDER BY p.createdAt DESC";

		try (Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Post post = new Post();
					post.setPostId(resultSet.getInt("postId"));
					post.setUsername(resultSet.getString("username"));
					post.setContent(resultSet.getString("content"));
					post.setAiResponse(resultSet.getString("aiResponse"));
					post.setCreatedAt(resultSet.getTimestamp("createdAt"));
					posts.add(post);
				}
			}
		}
		return posts;
	}

	public Post 

}
