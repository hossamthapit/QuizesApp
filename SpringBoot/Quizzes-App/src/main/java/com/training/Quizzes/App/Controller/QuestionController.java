package com.training.Quizzes.App.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.Question;
import com.training.Quizzes.App.repository.ExamRepository;
import com.training.Quizzes.App.repository.QuestionRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class QuestionController {

	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	ExamRepository examRepository;

	@GetMapping("/questions")
	public ResponseEntity<Page<Question>> getAllExams(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

		Page<Question> examsPage;

		examsPage = questionRepository.findAll(PageRequest.of(page, size));

		if (examsPage.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(examsPage, HttpStatus.OK);
	}

	@GetMapping("/questions/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable("id") int id) {
		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Question with id = " + id));

		return new ResponseEntity<>(question, HttpStatus.OK);
	}

	@PostMapping("/questions")
	public ResponseEntity<Question> createExam(@RequestBody Question question) {
		Question tempQuestion = questionRepository
				.save(new Question(question.getDescription(), question.getAnswer(), question.getScore()));
		return new ResponseEntity<>(tempQuestion, HttpStatus.CREATED);
	}

	@PutMapping("/questions/{id}")
	public ResponseEntity<Question> updateQuestion(@PathVariable("id") int id, @RequestBody Question question) {
		Question tempQuestion = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Question with id = " + id));

		tempQuestion.setDescription(question.getDescription());
		tempQuestion.setAnswer(question.getAnswer());
		tempQuestion.setScore(question.getScore());

		return new ResponseEntity<>(questionRepository.save(tempQuestion), HttpStatus.OK);
	}

	@DeleteMapping("/questions/{id}")
	public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("id") int id) {
		questionRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/questions")
	public ResponseEntity<HttpStatus> deleteAllQuestions() {
		questionRepository.deleteAll();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

	@GetMapping("/exams/{examId}/questions")
	public ResponseEntity<List<Question>> getAllQuestionsByExamId(
			@PathVariable(value = "examId") int examId) {
		
		if (!examRepository.existsById(examId)) {
			throw new ResourceNotFoundException("Not found Exam with id = " + examId);
		}
		List<Question> questions = questionRepository.findByExamId(examId);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	@PostMapping("/exams/{examId}/questions")
	public ResponseEntity<Question> createComment(@PathVariable(value = "examId") int examId,
			@RequestBody Question questionRequest) {
		System.out.println(examId);
		System.out.println(questionRequest);
		Question question = examRepository.findById(examId).map(exam -> {
			questionRequest.setExams(exam);
			return questionRepository.save(questionRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Exam with id = " + examId));

		return new ResponseEntity<>(question, HttpStatus.CREATED);
	}

	@DeleteMapping("/exams/{examId}/questions")
	public ResponseEntity<List<Question>> deleteAllQuestionsOfExam(
			@PathVariable(value = "examId") int examId) {
		if (!examRepository.existsById(examId)) {
			throw new ResourceNotFoundException("Not found Exam with id = " + examId);
		}

		questionRepository.deleteByExamId(examId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	

}
