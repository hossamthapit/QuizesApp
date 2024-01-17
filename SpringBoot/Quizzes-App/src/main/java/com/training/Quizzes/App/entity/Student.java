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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "students")
public class Student extends User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Length(min = 14, message = "The password must 14 characters")
	@Length(max = 14, message = "The password must 14 characters")
	@Column(name = "nationalId")
	private String nationalId;

	@Column(name = "phoneNumber")
	private String phoneNumber;

	@Length(min = 3, message = "The address must be at least 3 characters")
	@Length(max = 60, message = "The address must be at maximum 60 characters")
	@Column(name = "address")
	private String address;

	@Min(value = 4) 
	@Max(value = 85) 
	@Column(name = "age")
	private int age;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "students")
	@JsonIgnore
	private Set<Group> groups = new HashSet<>();
	
	public Student() {
		
	}
	
	public Student(String firstName,String lastName,String email,String password,String pictureUrl,Roles roles) {
		super(firstName,lastName,email,password,pictureUrl,roles);
	}

	public Student(String firstName,String lastName,String email,String password,String pictureUrl,Roles roles,
			String nationalId,String phoneNumber,String address,int age) {
		this(firstName,lastName,email,password,pictureUrl,roles);
		this.nationalId = nationalId;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.age = age;
	}
	
	public Student(Student student) {
		this.setFirstName(student.getFirstName());
		this.setLastName(student.getLastName());
		this.setEmail(student.getEmail());
		this.setPictureUrl(student.getPictureUrl());
		this.setPassword(student.getPassword());
		this.setRoles(student.getRoles());
		
		this.setNationalId(student.getNationalId());
		this.setPhoneNumber(student.getPhoneNumber());
		this.setAddress(student.getAddress());
		this.setAge(student.getAge());

	}
	
	public void update(Student student) {
		this.setFirstName(student.getFirstName());
		this.setLastName(student.getLastName());
		this.setEmail(student.getEmail());
		this.setPictureUrl(student.getPictureUrl());
		this.setRoles(Roles.ROLE_STUDENT);
		
		this.setNationalId(student.getNationalId());
		this.setPhoneNumber(student.getPhoneNumber());
		this.setAddress(student.getAddress());
		this.setAge(student.getAge());

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
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

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", nationalId=" + nationalId + ", phoneNumber=" + phoneNumber + ", address="
				+ address + ", age=" + age + ", groups=" + groups + "]";
	}
	
	

}
