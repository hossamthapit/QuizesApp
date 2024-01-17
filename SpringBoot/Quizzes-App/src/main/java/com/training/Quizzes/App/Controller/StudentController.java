package com.training.Quizzes.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.ExamRecord;
import com.training.Quizzes.App.entity.Group;
import com.training.Quizzes.App.entity.Student;
import com.training.Quizzes.App.repository.ExamRecordRepository;
import com.training.Quizzes.App.repository.GroupRepository;
import com.training.Quizzes.App.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private ExamRecordRepository examRecordRepository;

	@GetMapping("/students")
	public ResponseEntity<Page<Student>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<Student> studentPage;
		studentPage = studentRepository.findAll(PageRequest.of(page, size));
		if (studentPage.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(studentPage, HttpStatus.OK);
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getOne(@PathVariable(value = "id") int id) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + id));
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@PostMapping("/students")
	public ResponseEntity<Student> post(@RequestBody Student studentRequest) {

		Student student = new Student(studentRequest);
		student = studentRepository.save(student);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> put(@PathVariable("id") int id, @RequestBody Student studentRequest) {

		System.out.println(studentRequest + " " + id);

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with id: " + id));

		student.update(studentRequest);
		return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with id: " + id));
		studentRepository.delete(student);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/* Exam Records */

	@GetMapping("/students/{studentId}/examRecords")
	public ResponseEntity<Page<ExamRecord>> getAllExamRecordsByGroupId(@PathVariable(value = "studentId") int studentId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

		studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with id: " + studentId));

		Page<ExamRecord> examsRecords = examRecordRepository.findByStudentId(studentId, PageRequest.of(page, size));
		return new ResponseEntity<>(examsRecords, HttpStatus.OK);
	}

	/* Groups */

	@GetMapping("/students/{studentId}/groups")
	public ResponseEntity<Page<Group>> getStudentGroups(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @PathVariable(value = "studentId") int studentId) {

		System.out.println(studentId);

		if (!studentRepository.existsById(studentId))
			throw new ResourceNotFoundException("Not found Student  with id = " + studentId);

		Page<Group> groups = groupRepository.findByStudentsId(studentId, PageRequest.of(page, size));
		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

}
