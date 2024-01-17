package com.training.Quizzes.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.ExamRecord;
import com.training.Quizzes.App.repository.ExamRecordRepository;
import com.training.Quizzes.App.repository.ExamRepository;
import com.training.Quizzes.App.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class ExamRecordController {

	@Autowired
	ExamRecordRepository examRecordRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	ExamRepository examRepository;

	@GetMapping("/examRecords")
	public ResponseEntity<Page<ExamRecord>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<ExamRecord> examRecordPage;
		examRecordPage = examRecordRepository.findAll(PageRequest.of(page, size));
		if (examRecordPage.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(examRecordPage, HttpStatus.OK);
	}

	@GetMapping("/examRecords/{id}")
	public ResponseEntity<ExamRecord> getOne(@PathVariable("id") int id) {

		ExamRecord examRecord = examRecordRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found ExamRecord with id = " + id));
		return new ResponseEntity<>(examRecord, HttpStatus.OK);
	}

	@PostMapping("/examRecords")
	public ResponseEntity<ExamRecord> post(@RequestBody ExamRecord requestExamRecord) {

		ExamRecord examRecord = new ExamRecord(requestExamRecord);
		examRecord = examRecordRepository.save(examRecord);
		return new ResponseEntity<>(examRecord, HttpStatus.CREATED);
	}

	@PutMapping("/examRecords/{id}")
	public ResponseEntity<ExamRecord> put(@PathVariable("id") int id, @RequestBody ExamRecord requestExamRecord) {

		ExamRecord examRecord = examRecordRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found ExamRecord with id = " + id));
		examRecord.update(requestExamRecord);
		return new ResponseEntity<>(examRecordRepository.save(examRecord), HttpStatus.OK);
	}

	@DeleteMapping("/examRecords/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

		ExamRecord examRecord = examRecordRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found ExamRecord with id: " + id));
		examRecordRepository.delete(examRecord);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
