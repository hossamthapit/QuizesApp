package com.training.Quizzes.App.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User {
	
	public enum Roles{
		ROLE_STUDENT,
		ROLE_TEACHER,
		ROLE_ADMIN
	}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Roles roles;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    
    public User() {
    	setRoles(Roles.ROLE_STUDENT);
    	setFirstName("Hossam");
    	setLastName("Ahmed");
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
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

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", roles=" + roles + ", email=" + email + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + "]";
	}

    
    
    
    
}
