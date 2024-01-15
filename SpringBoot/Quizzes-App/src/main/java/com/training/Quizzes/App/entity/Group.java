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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "groups")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Length(min = 3, message = "The title must be at least 3 characters")
	@Length(min = 60, message = "The title must be at maximum 60 characters")
	@Column(name = "title")
	private String title;

	@NotNull
	@Length(min = 3, message = "The description must be at least 3 characters")
	@Length(min = 60, message = "The description must be at maximum 60 characters")
	@Column(name = "description")
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "group_students", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = {
			@JoinColumn(name = "student_id") })
	private Set<Student> students = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "group_teacher", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = {
			@JoinColumn(name = "teacher_id") })
	private Set<Teacher> teachers = new HashSet<>();
		
	public Group() {

	}

	public Group(String title, String description) {
		this.title = title;
		this.description = description;
	}

	// getters and setters

	public void addStudent(Student student) {
		this.students.add(student);
		student.getGroups().add(this);
	}

	public void removeStudent(long studentId) {
		Student student = this.students.stream().filter(t -> t.getId() == studentId).findFirst().orElse(null);
		if (student != null) {
			this.students.remove(student);
			student.getGroups().remove(this);
		}
	}
	
	public void addTeacher(Teacher teacher) {
		this.teachers.add(teacher);
		teacher.getGroups().add(this);
	}

	public void removeTracher(long teacherId) {
		Teacher teacher = this.teachers.stream().filter(t -> t.getId() == teacherId).findFirst().orElse(null);
		if (teacher != null) {
			this.teachers.remove(teacher);
			teacher.getGroups().remove(this);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	

}
