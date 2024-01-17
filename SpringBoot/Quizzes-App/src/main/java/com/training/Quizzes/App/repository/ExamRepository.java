package com.training.Quizzes.App.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Exam;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface ExamRepository extends JpaRepository<Exam, Integer> {

	Page<Exam> findByTitleContaining(String title, PageRequest pageRequest);
	
	Page<Exam> findByGroupId(int groupId, PageRequest pageRequest);
				
	@Transactional
	void deleteByGroupId(int groupId);

}
