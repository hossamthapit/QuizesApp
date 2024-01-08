package com.training.Quizzes.App.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Question;

//@RepositoryRestResource(path = "questions")
@CrossOrigin(value = "http://localhost:4200/")
public interface QuestionRepository extends JpaRepository<Question, Integer>  {
	
	  List<Question> findByExamId(int questionId);
	  
	  @Transactional
	  void deleteByExamId(int examId);
	  
    List<Question> findQuestionsByExamId(int groupId);

}
