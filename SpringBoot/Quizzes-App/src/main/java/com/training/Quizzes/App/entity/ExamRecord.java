package com.training.Quizzes.App.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ExamRecords")
public class ExamRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@NotNull
	@Min(value = 1) 
	@Max(value = 10000) 
	@Column(name = "score")
	private int score;
	
	@Column(name = "examDate")
	private Date examDate;
			
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "student_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Student student ;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "exam_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Exam exam ;
	
	public ExamRecord() {
		
	}

	public ExamRecord(int score, Student student, Exam exam, Date examDate) {
		this.score = score;
		this.student = student;
		this.exam = exam;
		this.examDate = examDate;
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

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	@Override
	public String toString() {
		return "ExamRecord [id=" + id + ", score=" + score + ", examDate=" + examDate + ", student=" + student
				+ ", exam=" + exam + "]";
	}
	
	
	
	
	
}
