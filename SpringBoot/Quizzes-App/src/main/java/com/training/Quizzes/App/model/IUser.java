package com.training.Quizzes.App.model;


public interface IUser  {
	
	public enum Roles{
		ROLE_STUDENT,
		ROLE_TEACHER,
		ROLE_ADMIN
	}
	public String getFirstName() ;
	
	public void setFirstName(String firstName) ;
	
	public String getLastName() ;
	
	public void setLastName(String lastName) ;
	
	public String getEmail() ;
	
	public void setEmail(String email);
	
	public String getPassword() ;
	
	public void setPassword(String password);
	
	public Roles getRoles() ;
	
	public void setRoles(Roles roles);
    
	public String getPictureUrl();

	public void setPictureUrl(String pictureUrl);
	

}
