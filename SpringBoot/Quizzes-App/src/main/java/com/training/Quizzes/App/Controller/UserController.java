package com.training.Quizzes.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.ExamRecord;
import com.training.Quizzes.App.entity.Group;
import com.training.Quizzes.App.entity.Student;
import com.training.Quizzes.App.entity.User;
import com.training.Quizzes.App.repository.ExamRecordRepository;
import com.training.Quizzes.App.repository.GroupRepository;
import com.training.Quizzes.App.repository.StudentRepository;
import com.training.Quizzes.App.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/users")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<Page<User>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<User> userPage;
		userPage = userRepository.findAll(PageRequest.of(page, size));
		if (userPage.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(userPage, HttpStatus.OK);
	}

	@GetMapping("/users/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<User> getOne(@PathVariable(value = "id") int id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + id));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<User> post(@RequestBody User userRequest) {

		User user = new User(userRequest);
		user = userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PutMapping("/users/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<User> put(@PathVariable("id") int id, @RequestBody User userRequest) {


		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found User with id: " + id));

		user.update(userRequest);
		return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found User with id: " + id));
		userRepository.delete(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
