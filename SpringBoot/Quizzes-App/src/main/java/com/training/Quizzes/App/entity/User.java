package com.training.Quizzes.App.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.training.Quizzes.App.model.IUser;

import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User implements IUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Length(min = 2, message = "The firstName must be at least 5 characters")
	@Length(max = 50, message = "The firstName must be at maximum 50 characters")
	@Column(name = "firstName")
	private String firstName;
	
	@NotNull
	@Length(min = 2, message = "The lastName must be at least 5 characters")
	@Length(max = 50, message = "The lastName must be at maximum 50 characters")
	@Column(name = "lastName")
	private String lastName;
	
	@NotNull
	@Email(message="Please provide a valid email address")
	@Length(min = 3, message = "The email must be at least 3 characters")
	@Length(max = 50, message = "The email must be at maximum 50 characters")
	@Column(name = "email")
	private String email;
	
	@NotNull
	@Length(min = 8, message = "The password must be at least 5 characters")
	@Length(max = 50, message = "The password must be at maximum 50 characters")
	@Column(name = "password")
	private String password;
	
	@NotNull
	@Column(name = "role")
	private Roles roles;
	
	@Column(name = "pictureUrl")
	private String pictureUrl;
	
	public User() {

	}

	public User(String firstName, String lastName, String email, String password,String pictureUrl,Roles roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.pictureUrl = pictureUrl;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	} 
}
