package com.training.Quizzes.App.model.response;

import com.training.Quizzes.App.entity.User;

public class LoginRes {
	
    private User user;
    private String email;
    private String token;

    public LoginRes(String email, String token) {
        this.email = email;
        this.token = token;
    }
    public LoginRes(String email, String token,User user) {
    	this(email, token);
        this.user = user;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    

}
