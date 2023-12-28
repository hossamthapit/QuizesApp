package com.training.Quizzes.App.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.training.Quizzes.App.entity.Question;

@Repository
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository{
    
	private final EntityManager entityManager;

    public CustomQuestionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Question saveQuestion(Question question) {
    	question.getQuiz();
    	System.out.println(question.getQuiz());
        entityManager.persist(question);
        return question;
    }

}
