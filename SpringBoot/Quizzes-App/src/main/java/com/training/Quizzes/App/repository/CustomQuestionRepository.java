package com.training.Quizzes.App.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.training.Quizzes.App.entity.Question;

@RepositoryRestResource(path = "questions")
public interface CustomQuestionRepository {
    Question saveQuestion(Question question);
}
