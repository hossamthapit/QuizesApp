package com.training.Quizzes.App.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Question;

//@RepositoryRestResource(path = "questions")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	Page<Question> findByExamId(int questionId, PageRequest of);

	@Transactional
	void deleteByExamId(int examId);

	List<Question> findQuestionsByExamId(int groupId);

}
