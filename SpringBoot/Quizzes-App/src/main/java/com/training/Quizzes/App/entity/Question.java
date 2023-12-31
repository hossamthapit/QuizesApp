package com.training.Quizzes.App.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Questions")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;
	
	private String answer;
		
	private int score;
	
	@ManyToOne
	@JoinColumn(name = "quizid")
	private Quiz quiz;
			
	public Question() {

	}

	public Question(String description, String answer, int score) {
		super();
		this.description = description;
		this.answer = answer;
		this.score = score;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
		
	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@Override
    public String toString() {
        return "Question [id=" + id + ", description=" + description + ", answer=" + answer + ", score=" + score + "]";
    }
	
	public int checkAnswer(String response) {
		return ((response.equals(answer))? score : 0);
	}

}
