package com.training.Quizzes.App.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Quizes")
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;
			
	@OneToMany
	private List<Question> questionList ;
	
	public void addQuestion(Question question) {
		questionList.add(question);
	}

	public void deleteQuestion(Question question) {
		questionList.remove(question);
	}
	
	public void deleteQuestion(int id) {
		for(int i = 0 ; i < questionList.size();i++)
			if(questionList.get(i).getId() == id)
				deleteQuestion(questionList.get(i));
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

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", title=" + title + "]";
	}

}
