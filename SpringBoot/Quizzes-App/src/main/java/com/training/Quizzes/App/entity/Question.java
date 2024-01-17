package com.training.Quizzes.App.entity;

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
import org.hibernate.validator.constraints.Length;


@Entity
@Table(name = "Questions")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Length(min = 3, message = "The address must be at least 3 characters")
	@Length(max = 300, message = "The address must be at maximum 60 characters")
	private String description;

	@NotNull
	private int answer;

	@NotNull
	@Min(value = 1) 
	@Max(value = 10000) 
	private int score;
	
	@NotNull
	@Min(value = 5) 
	@Max(value = 60 * 60) 
	private int seconds;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "exam_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Exam exam ;

	public Question() {

	}

	public Question(String description, int answer, int score) {
		this.description = description;
		this.answer = answer;
		this.score = score;
	}
	public Question(String description, int answer, int score, int seconds) {
		this(description,answer,score);
		this.seconds = seconds;
	}
	
	public Question(Question question) {
		this.description = question.getDescription();
		this.answer = question.getAnswer();
		this.score = question.getScore();
		this.seconds = question.getSeconds();		
		this.exam = question.getExam();
	}
	
	public void update(Question question) {
		this.setDescription(question.getDescription());
		this.setAnswer(question.getAnswer());
		this.setScore(question.getScore());
		this.setSeconds(question.getSeconds());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int checkAnswer(String response) {
		return ((response.equals(answer)) ? score : 0);
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", answer=" + answer + ", score=" + score
				+ ", seconds=" + seconds + ", exam=" + exam + "]";
	}
	
	
	

}
