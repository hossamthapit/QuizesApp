package com.training.Quizzes.App.entity;

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
@Table(name = "Exams")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	private String title;
	private String description;

//	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//	@JoinTable(name = "exam_questions", joinColumns = { @JoinColumn(name = "exam_id") }, inverseJoinColumns = {
//			@JoinColumn(name = "question_id") })
//	private Set<Question> questions = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "group_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Group group;
	
//	public void addQuestion(Question question) {
//		this.questions.add(question);
//		question.getExams().add(this);
//	}
//
//	public void removeQuestion(long questionId) {
//		Question question = this.questions.stream().filter(t -> t.getId() == questionId).findFirst().orElse(null);
//		if (question != null) {
//			this.questions.remove(question);
//			question.getExams().remove(this);
//		}
//	}

	public Exam() {
	}

	public Exam(String title) {
		this.title = title;
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Set<Question> getQuestions() {
//		return questions;
//	}
//
//	public void setQuestions(Set<Question> questions) {
//		this.questions = questions;
//	}

	
}
