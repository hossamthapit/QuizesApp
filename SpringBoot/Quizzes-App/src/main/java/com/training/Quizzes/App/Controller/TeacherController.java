package com.training.Quizzes.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.Group;
import com.training.Quizzes.App.entity.Teacher;
import com.training.Quizzes.App.repository.GroupRepository;
import com.training.Quizzes.App.repository.TeacherRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private GroupRepository groupRepository;

	@GetMapping("/teachers")
	public ResponseEntity<Page<Teacher>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<Teacher> teacherPage;
		teacherPage = teacherRepository.findAll(PageRequest.of(page, size));
		if (teacherPage.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(teacherPage, HttpStatus.OK);
	}

	@GetMapping("/teachers/{id}")
	public ResponseEntity<Teacher> getOne(@PathVariable(value = "id") int id) {

		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + id));
		return new ResponseEntity<>(teacher, HttpStatus.OK);
	}

	@PostMapping("/teachers")
	public ResponseEntity<Teacher> post(@RequestBody Teacher teacherRequest) {
		Teacher teacher = new Teacher(teacherRequest);
		teacher = teacherRepository.save(teacher);
		return new ResponseEntity<>(teacher, HttpStatus.CREATED);
	}

	@PutMapping("/teachers/{id}")
	public ResponseEntity<Teacher> put(@PathVariable("id") int id, @RequestBody Teacher teacherRequest) {

		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id: " + id));

		teacher.update(teacherRequest);
		return new ResponseEntity<>(teacherRepository.save(teacher), HttpStatus.OK);
	}

	@DeleteMapping("/teachers/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id: " + id));
		teacherRepository.delete(teacher);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/* Groups */

	@GetMapping("/teachers/{teacherId}/groups")
	public ResponseEntity<Page<Group>> getTeacherGroups(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @PathVariable(value = "teacherId") int teacherId) {

		if (!teacherRepository.existsById(teacherId))
			throw new ResourceNotFoundException("Not found Teacher  with id = " + teacherId);

		Page<Group> groups = groupRepository.findByTeachersId(teacherId, PageRequest.of(page, size));
		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

}
