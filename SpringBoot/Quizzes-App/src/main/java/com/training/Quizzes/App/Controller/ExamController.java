package com.training.Quizzes.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.Exam;
import com.training.Quizzes.App.entity.Question;
import com.training.Quizzes.App.repository.ExamRepository;
import com.training.Quizzes.App.repository.QuestionRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class ExamController {

	@Autowired
	ExamRepository examRepository;

	@Autowired
	QuestionRepository questionRepository;

	@GetMapping("/exams")
	public ResponseEntity<Page<Exam>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<Exam> examsPage;
		examsPage = examRepository.findAll(PageRequest.of(page, size));
		if (examsPage.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(examsPage, HttpStatus.OK);
	}

	@GetMapping("/exams/{id}")
	public ResponseEntity<Exam> getOne(@PathVariable("id") int id) {

		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Exam with id = " + id));
		return new ResponseEntity<>(exam, HttpStatus.OK);
	}

	@PostMapping("/exams")
	public ResponseEntity<Exam> post(@RequestBody Exam requestExam) {

		Exam exam = new Exam(requestExam);
		exam = examRepository.save(exam);
		return new ResponseEntity<>(exam, HttpStatus.CREATED);
	}

	@PutMapping("/exams/{id}")
	public ResponseEntity<Exam> put(@PathVariable("id") int id, @RequestBody Exam requestExam) {

		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Exam with id = " + id));
		exam.setTitle(requestExam.getTitle());
		exam.setDescription(requestExam.getDescription());
		return new ResponseEntity<>(examRepository.save(exam), HttpStatus.OK);
	}

	@DeleteMapping("/exams/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Exam with id: " + id));
		examRepository.delete(exam);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/* Questions */

	@GetMapping("/exams/{examId}/questions")
	public ResponseEntity<Page<Question>> getExamQuestions(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @PathVariable(value = "examId") int examId) {

		if (!examRepository.existsById(examId)) {
			throw new ResourceNotFoundException("Not found Exam with id = " + examId);
		}
		Page<Question> questions = questionRepository.findByExamId(examId, PageRequest.of(page, size));
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	@PostMapping("/exams/{examId}/questions")
	public ResponseEntity<Question> postExamQuestion(@PathVariable(value = "examId") int examId,
			@RequestBody Question questionRequest) {

		Exam exam = examRepository.findById(examId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Exam with id: " + examId));

		questionRequest.setExam(exam);
		Question question = new Question(questionRequest);
		question = questionRepository.save(question);
		return new ResponseEntity<>(question, HttpStatus.CREATED);
	}

}
