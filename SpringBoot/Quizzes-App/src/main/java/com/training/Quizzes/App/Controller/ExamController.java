package com.training.Quizzes.App.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.Exam;
import com.training.Quizzes.App.repository.ExamRepository;
import com.training.Quizzes.App.repository.GroupRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class ExamController {

	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@GetMapping("/exams")
	public ResponseEntity<Page<Exam>> getAllExams(
			@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "20") int size) {

		Page<Exam> examsPage;
		
		System.out.println("test ");

		if (title == null || title.isEmpty()) {
			examsPage = examRepository.findAll(PageRequest.of(page, size));
		} else {
			examsPage = examRepository.findByTitleContaining(title, PageRequest.of(page, size));
		}

		if (examsPage.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(examsPage, HttpStatus.OK);
	}

	@GetMapping("/exams/{id}")
	public ResponseEntity<Exam> getGroupById(@PathVariable("id") int id) {
		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Exam with id = " + id));

		return new ResponseEntity<>(exam, HttpStatus.OK);
	}

	@PostMapping("/exams")
	public ResponseEntity<Exam> createExam(@RequestBody Exam exam) {
		Exam tempExam = examRepository.save(new Exam(exam.getTitle()));
		return new ResponseEntity<>(tempExam, HttpStatus.CREATED);
	}

	@PutMapping("/exams/{id}")
	public ResponseEntity<Exam> updateExam(@PathVariable("id") int id, @RequestBody Exam exam) {
		Exam tempExam = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Exam with id = " + id));

		tempExam.setTitle(exam.getTitle());

		return new ResponseEntity<>(examRepository.save(tempExam), HttpStatus.OK);
	}

	@DeleteMapping("/exams/{id}")
	public ResponseEntity<HttpStatus> deleteGroup(@PathVariable("id") int id) {
		examRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/exams")
	public ResponseEntity<HttpStatus> deleteAllExams() {
		examRepository.deleteAll();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/groups/{groupId}/exams")
	public ResponseEntity<List<Exam>> getAllExamRecordsByStudentsId(
			@PathVariable(value = "groupId") int groupId) {

		if (!groupRepository.existsById(groupId)) {
			throw new ResourceNotFoundException("Not found Group with id = " + groupId);
		}

		List<Exam> exams = examRepository.findExamsByGroupId(groupId);
		return new ResponseEntity<>(exams, HttpStatus.OK);
	}
	


//	@DeleteMapping("/groups/{groupId}/examRecords")
//	public ResponseEntity<List<Question>> deleteAllExamRecordsOfStudent(
//			@PathVariable(value = "groupId") int groupId) {
//		if (!studentRepository.existsById((int) groupId)) {
//			throw new ResourceNotFoundException("Not found Student with id = " + groupId);
//		}
//
//		examRecordRepository.deleteByStudentId(groupId);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}

}
