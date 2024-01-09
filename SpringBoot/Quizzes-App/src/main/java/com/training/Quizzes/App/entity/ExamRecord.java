package com.training.Quizzes.App.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ExamRecords")
public class ExamRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@Column(name = "score")
	private int score;
			
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "student_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	//@JsonIgnore
	private Student student ;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "exam_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	//@JsonIgnore
	private Exam exam ;
	
	public ExamRecord() {

	}

	public ExamRecord(int score, Student student, Exam exam) {
		this.score = score;
		this.student = student;
		this.exam = exam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
	
	
	
	
}
