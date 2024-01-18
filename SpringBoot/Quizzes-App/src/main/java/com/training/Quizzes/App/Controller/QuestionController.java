package com.training.Quizzes.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<Page<Question>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<Question> questionPage;
		questionPage = questionRepository.findAll(PageRequest.of(page, size));
		if (questionPage.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(questionPage, HttpStatus.OK);
	}

	@GetMapping("/questions/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<Question> getOne(@PathVariable("id") int id) {

		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Question with id = " + id));
		return new ResponseEntity<>(question, HttpStatus.OK);
	}

	@PostMapping("/questions")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<Question> post(@RequestBody Question requestQuestion) {

		Question question = new Question(requestQuestion);
		question = questionRepository.save(question);
		return new ResponseEntity<>(requestQuestion, HttpStatus.CREATED);
	}

	@PutMapping("/questions/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<Question> put(@PathVariable("id") int id, @RequestBody Question requestQuestion) {

		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Question with id = " + id));

		question.update(requestQuestion);
		return new ResponseEntity<>(questionRepository.save(question), HttpStatus.OK);
	}

	@DeleteMapping("/questions/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Question with id: " + id));
		questionRepository.delete(question);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
