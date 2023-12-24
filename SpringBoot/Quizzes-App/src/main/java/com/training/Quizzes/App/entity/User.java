package com.training.Quizzes.App.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	
	public enum UserRoles{
		Admin,
		User,
		Guest
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	
	private String username;
	
	private String password;
	
	private UserRoles roles ;
		
	private int score ;
	
	public User() {
		
	}
		
	public User(String name, String username) {
		this.name = name;
		this.username = username;
	}
	
	public User(String name, String username ,String password ) {
		this(name,username);
		this.password =password;
	}	
	
	public User(String name, String username , String password , UserRoles roles) {
		this(name,username,password);
		this.roles = roles;
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRoles getRoles() {
		return roles;
	}

	public void setRoles(UserRoles roles) {
		this.roles = roles;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", roles="
				+ roles + ", score=" + score + "]";
	}	
	
		
}
