package com.training.Quizzes.App.Controller;

import java.io.Console;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.ExamRecord;
import com.training.Quizzes.App.entity.Question;
import com.training.Quizzes.App.entity.Student;
import com.training.Quizzes.App.repository.ExamRecordRepository;
import com.training.Quizzes.App.repository.ExamRepository;
import com.training.Quizzes.App.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class ExamRecordController {

	@Autowired
	ExamRecordRepository examRecordRepository ;
	@Autowired
	StudentRepository studentRepository ;
	@Autowired
	ExamRepository examRepository;

	@GetMapping("examRecords")
	public ResponseEntity<Page<ExamRecord>> getAllExamRecords(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

		Page<ExamRecord> examRecordPage;

		examRecordPage = examRecordRepository.findAll(PageRequest.of(page, size));

		if (examRecordPage.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(examRecordPage, HttpStatus.OK);
	}

	@GetMapping("/examRecords/{id}")
	public ResponseEntity<List<ExamRecord>> getExamRecordById(@PathVariable("id") int id) {
		List<ExamRecord> examRecord = examRecordRepository.findByStudentId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Exam Record with id = " + id));
		System.out.println("id is " + id + "records" + examRecord.size());
		return new ResponseEntity<>(examRecord, HttpStatus.OK);
	}

	@PostMapping("/examRecords")
	public ResponseEntity<ExamRecord> createExamRecord(@RequestBody ExamRecord examRecord) {
		System.out.println(examRecord);
		System.out.println(examRecord.getExam());
		ExamRecord tempRecord = examRecordRepository
				.save(new ExamRecord(examRecord.getScore(), examRecord.getStudent(), examRecord.getExam(),examRecord.getExamDate()));
		return new ResponseEntity<>(tempRecord, HttpStatus.CREATED);
	}

	@PutMapping("/examRecords/{id}")
	public ResponseEntity<ExamRecord> updateExamRecord(@PathVariable("id") int id, @RequestBody ExamRecord examRecord) {
		ExamRecord tempRecord = examRecordRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Exam Record with id = " + id));

		tempRecord = new ExamRecord(examRecord.getScore(), examRecord.getStudent(), examRecord.getExam(),examRecord.getExamDate());

		return new ResponseEntity<>(examRecordRepository.save(tempRecord), HttpStatus.OK);
	}

	@DeleteMapping("/examRecords/{id}")
	public ResponseEntity<HttpStatus> deleteExamRecord(@PathVariable("id") int id) {
		examRecordRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/examRecords")
	public ResponseEntity<HttpStatus> deleteAllExamRecords() {
		examRecordRepository.deleteAll();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

		@DeleteMapping("/students/{studentId}/examRecords")
		public ResponseEntity<List<Question>> deleteAllExamRecordsOfStudent(
				@PathVariable(value = "studentId") int studentId) {
			if (!studentRepository.existsById(studentId)) {
				throw new ResourceNotFoundException("Not found Student with id = " + studentId);
			}
	
			examRecordRepository.deleteByStudentId(studentId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		@GetMapping("/exams/{examId}/examRecords")
		public ResponseEntity<List<ExamRecord>> getAllExamRecordsByGroupId(
				@PathVariable(value = "examId") int examId) {

			if (!examRepository.existsById(examId)) {
				throw new ResourceNotFoundException("Not found Exam with id = " + examId);
			}

			List<ExamRecord> examsRecords = examRecordRepository.findExamRecordsByExamId(examId);
			return new ResponseEntity<>(examsRecords, HttpStatus.OK);
		}

}
