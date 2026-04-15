package model;
import java.sql.Timestamp;

public class Post {
	private int postId;
	private String userId;
	private String content;
	private String aiResponse;
	private Timestamp createdAt;
	private String username;
	
	public Post() {	
	}
	
	public Post(String content,String aiResponse,int postId) {
		this.content=content;
		this.aiResponse=aiResponse;
		this.postId=postId;
	}
	
	public Post(int postId,String userId,String content,String  aiResponse,Timestamp createdAt,String username) {
		this.postId=postId;
		this.userId=userId;
		this.content=content;
		this.aiResponse=aiResponse;
		this.createdAt=createdAt;
		this.username=username;
	}
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
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
}