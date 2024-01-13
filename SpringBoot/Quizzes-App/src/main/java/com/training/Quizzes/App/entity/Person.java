package com.training.Quizzes.App.entity;

public interface Person {

	String getNationalId();
	void setNationalId(String nationalId);

	String getFirstName();
	void setFirstName(String firstName);
	
	String getLastName();
	void setLastName(String lastName);
	
	int getAge();
	void setAge(int age);
	
	String getPictureUrl();
	void setPictureUrl(String pictureUrl);
	
	String getEmail();
	void setEmail(String email);
	
	String getPhoneNumber();
	void setPhoneNumber(String phoneNumber);

	String getAddress();
	void setAddress(String address);

}
