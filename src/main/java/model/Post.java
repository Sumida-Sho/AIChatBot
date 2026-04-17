package model;

import java.sql.Timestamp;

public class Post {
	private int postId;
	private int userId;
	private String content;
	private String aiResponse;
	private Timestamp createdAt;
	private String username;

	//コンストラクタ
	public Post() {
	}

	public Post(String content, String aiResponse, int postId) {
		this.content = content;
		this.aiResponse = aiResponse;
		this.postId = postId;
	}

	public Post(int postId, int userId, String content, String aiResponse, Timestamp createdAt, String username) {
		this.postId = postId;
		this.userId = userId;
		this.content = content;
		this.aiResponse = aiResponse;
		this.createdAt = createdAt;
		this.username = username;
	}

	//Getter,Setter
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAiResponse() {
		return aiResponse;
	}

	public void setAiResponse(String aiResponse) {
		this.aiResponse = aiResponse;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	//便利メソッド
	public String getSummary() {
		if (content == null) {
			return "";
		}
		return content.length() > 100 ? content.substring(0, 100) + "..." : content;
	}

	public String getSummaryAi() {
		if (aiResponse == null) {
			return "回答準備中...";
		}
		return aiResponse.length() > 100 ? aiResponse.substring(0, 100) + "..." : aiResponse;
	}
}