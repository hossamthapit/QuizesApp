package com.training.Quizzes.App.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Exam;

@CrossOrigin(value = "http://localhost:4200/")
public interface ExamRepository extends JpaRepository<Exam, Integer> {

	Page<Exam> findByTitleContaining(String title, PageRequest pageRequest);
	
	List<Exam> findExamsByGroupId(int groupId);
				
	@Transactional
	void deleteByGroupId(int groupId);

}
