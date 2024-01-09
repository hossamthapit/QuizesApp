package com.training.Quizzes.App.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "teachers")
public class Teacher implements Person{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "nationalId")
	private String nationalId;

	@Column(name = "age")
	private int age;
	
	@Column(name = "pictureUrl")
	private String pictureUrl;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "teachers")
	@JsonIgnore
	private Set<Group> groups = new HashSet<>();
	
	public Teacher() {

	}
	
	public Teacher(String firstName, String lastName, String nationalId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationalId = nationalId;
	}
	
	public Teacher(String firstName, String lastName, String nationalId, int age) {
		this (firstName,lastName,nationalId);
		this.age = age;
	}
	public Teacher(String firstName, String lastName, String nationalId, int age, String pictureUrl) {
		this (firstName,lastName,nationalId,age);
		this.pictureUrl = pictureUrl;
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

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	


}
