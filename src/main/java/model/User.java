package model;

import java.sql.Timestamp;

public class User {
	private int userId;
	private String username;
	private String email;
	private String password;
	private String bio;
	private Timestamp createdAt;
//	
	public User() {
		
	}
	
	public User(String username,String email,String password) {
		this.username=username;
		this.email=email;
		this.password=password;
	}
	
	public User(int userId,String username,String email,String password,String bio,Timestamp createdAt) {
		this.userId=userId;
		this.username=username;
		this.email=email;
		this.password=password;
		this.bio=bio;
		this.createdAt=createdAt;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
