package com.training.Quizzes.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.Exam;
import com.training.Quizzes.App.entity.Group;
import com.training.Quizzes.App.entity.Student;
import com.training.Quizzes.App.entity.Teacher;
import com.training.Quizzes.App.repository.ExamRepository;
import com.training.Quizzes.App.repository.GroupRepository;
import com.training.Quizzes.App.repository.StudentRepository;
import com.training.Quizzes.App.repository.TeacherRepository;
import com.training.Quizzes.App.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api")
//@PreAuthorize("hasRole('ROLE_STUDENT')")

public class GroupController {

	@Autowired
	GroupRepository groupRepository;
	@Autowired
	ExamRepository examRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TeacherRepository teacherRepository;

	
	@GetMapping("/groups")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<Page<Group>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<Group> groupPage;
		groupPage = groupRepository.findAll(PageRequest.of(page, size));

		if (groupPage.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(groupPage, HttpStatus.OK);
	}

	@GetMapping("/groups/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<Group> getOne(@PathVariable("id") int id) {
		Group group = groupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + id));
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@PostMapping("/groups")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<Group> post(@RequestBody Group requestGroup) {
		Group group = groupRepository.save(new Group(requestGroup));
		return new ResponseEntity<>(group, HttpStatus.CREATED);
	}

	@PutMapping("/groups/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<Group> put(@PathVariable("id") int id, @RequestBody Group requestGroup) {
		Group group = groupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + id));

		group.setTitle(requestGroup.getTitle());
		group.setDescription(requestGroup.getDescription());

		return new ResponseEntity<>(groupRepository.save(group), HttpStatus.OK);
	}
	
	@DeleteMapping("/groups/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
		Group group = groupRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id: " + id));
		groupRepository.delete(group);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/* Exams */

	@GetMapping("/groups/{groupId}/exams")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<Page<Exam>> getGroupExams(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @PathVariable(value = "groupId") int groupId) {

		if (!groupRepository.existsById(groupId)) {
			throw new ResourceNotFoundException("Not found Group with id = " + groupId);
		}
		Page<Exam> exams = examRepository.findByGroupId(groupId, PageRequest.of(page, size));
		return new ResponseEntity<>(exams, HttpStatus.OK);
	}

	@PostMapping("/groups/{groupId}/exams")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<Exam> postGroupExam(@PathVariable(value = "groupId") int groupId,
			@RequestBody Exam examRequest) {

		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id: " + groupId));

		System.out.println("here we are : ");
		System.out.println(group);
		Exam exam = new Exam(examRequest);
		System.out.println(exam);

		exam.setGroup(group);

		exam = examRepository.save(exam);
		return new ResponseEntity<>(examRequest, HttpStatus.CREATED);
	}

	/* Students */

	@GetMapping("/groups/{groupId}/students")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<Page<Student>> getGroupStudents(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @PathVariable(value = "groupId") int groupId) {

		groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id: " + groupId));

		Page<Student> students = studentRepository.findByGroupsId(groupId, PageRequest.of(page, size));
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@PostMapping("/groups/{groupId}/students/{studentId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<HttpStatus> addStudentToGroup(@PathVariable(value = "groupId") int groupId,
			@PathVariable(value = "studentId") int studentId) {

		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + studentId));

		if (!group.getStudents().contains(student)) {
			group.addStudent(student);
			groupRepository.save(group);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/groups/{groupId}/students/{studentId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<HttpStatus> deleteStudentFromGroup(@PathVariable(value = "groupId") int groupId,
			@PathVariable(value = "studentId") int studentId) {

		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));

		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + studentId));

		if (group.getStudents().contains(student)) {
			group.removeStudent(studentId);
			groupRepository.save(group);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	/* Teachers */

	@GetMapping("/groups/{groupId}/teachers")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER') or hasRole('ROLE_STUDENT')")
	public ResponseEntity<Page<Teacher>> getGroupTeachers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @PathVariable(value = "groupId") int groupId) {

		groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id: " + groupId));

		Page<Teacher> teachers = teacherRepository.findByGroupsId(groupId, PageRequest.of(page, size));
		return new ResponseEntity<>(teachers, HttpStatus.OK);
	}

	@PostMapping("/groups/{groupId}/teachers/{teacherId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<HttpStatus> addTeacherToGroup(@PathVariable(value = "groupId") int groupId,
			@PathVariable(value = "teacherId") int teacherId) {

		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));

		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + teacherId));

		if (!group.getTeachers().contains(teacher)) {
			group.addTeacher(teacher);
			groupRepository.save(group);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/groups/{groupId}/teachers/{teacherId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')")
	public ResponseEntity<HttpStatus> deleteTeacherFromGroup(@PathVariable(value = "groupId") int groupId,
			@PathVariable(value = "teacherId") int teacherId) {

		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));

		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + teacherId));

		if (group.getTeachers().contains(teacher)) {
			group.removeTracher(teacherId);
			groupRepository.save(group);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

}
