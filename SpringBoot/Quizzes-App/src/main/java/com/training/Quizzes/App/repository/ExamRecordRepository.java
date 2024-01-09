package com.training.Quizzes.App.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.ExamRecord;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface ExamRecordRepository extends JpaRepository<ExamRecord, Integer> {

	List<ExamRecord> findByStudentId(int studentId);

	@Transactional
	void deleteByStudentId(int studentId);

	List<ExamRecord> findByExamId(int examId);

	@Transactional
	void deleteByExamId(int examId);

	List<ExamRecord> findExamRecordsByExamId(int groupId);

}
