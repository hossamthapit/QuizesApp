package com.training.Quizzes.App.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<List<Teacher>> getAllTeachers() {
		List<Teacher> teachers = new ArrayList<Teacher>();

		teacherRepository.findAll().forEach(teachers::add);

		if (teachers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(teachers, HttpStatus.OK);
	}

	@PostMapping("/teachers")
	public ResponseEntity<Teacher> createStudent(@RequestBody Teacher teacherRequest) {
		
		Teacher tempTeacher = new Teacher();
		tempTeacher.setFirstName(teacherRequest.getFirstName());
		tempTeacher.setLastName(teacherRequest.getLastName());
		tempTeacher.setEmail(teacherRequest.getEmail());
		tempTeacher.setPictureUrl(teacherRequest.getPictureUrl());
		tempTeacher.setPassword(teacherRequest.getPassword());
		tempTeacher.setRoles(teacherRequest.getRoles());
		
		tempTeacher.setNationalId(teacherRequest.getNationalId());
		tempTeacher.setPhoneNumber(teacherRequest.getPhoneNumber());
		tempTeacher.setAddress(teacherRequest.getAddress());
		tempTeacher.setAge(teacherRequest.getAge());
		teacherRepository.save(tempTeacher);

		return new ResponseEntity<>(tempTeacher, HttpStatus.CREATED);
	}

	@GetMapping("/groups/{groupId}/teachers")
	public ResponseEntity<List<Teacher>> getAllTeachersByGroupId(@PathVariable(value = "groupId") int groupId) {
		if (!groupRepository.existsById(groupId)) {
			throw new ResourceNotFoundException("Not found Group with id = " + groupId);
		}

		List<Teacher> teachers = teacherRepository.findTeachersByGroupsId(groupId);
		return new ResponseEntity<>(teachers, HttpStatus.OK);
	}

	@GetMapping("/teachers/{id}")
	public ResponseEntity<Teacher> getTeacherssById(@PathVariable(value = "id") int id) {
		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + id));

		return new ResponseEntity<>(teacher, HttpStatus.OK);
	}

	@GetMapping("/teachers/{teacherId}/groups")
	public ResponseEntity<List<Group>> getAllGroupssByTeacherId(@PathVariable(value = "teacherId") int teacherId) {
		if (!teacherRepository.existsById((int) teacherId)) {
			throw new ResourceNotFoundException("Not found Teacher  with id = " + teacherId);
		}

		List<Group> groups = groupRepository.findGroupsByTeachersId(teacherId);
		return new ResponseEntity<>(groups, HttpStatus.OK);
	}

	@PostMapping("/groups/{groupId}/teachers")
	public ResponseEntity<Teacher> addTag(@PathVariable(value = "groupId") int groupId,
			@RequestBody Teacher teacherRequest) {
		Teacher teacher = groupRepository.findById(groupId).map(group -> {
			int teacherId = teacherRequest.getId();

			System.out.println(groupId);
			// tag is existed
			if (teacherId != 0L) {
				Teacher tempTeacher = teacherRepository.findById(teacherId)
						.orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + teacherId));
				group.addTeacher(teacherRequest);
				groupRepository.save(group);
				return tempTeacher;
			}

			// add and create new Tag
			group.addTeacher(teacherRequest);
			return teacherRepository.save(teacherRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));

		return new ResponseEntity<>(teacher, HttpStatus.CREATED);
	}

	@PutMapping("/teachers/{id}")
	public ResponseEntity<Teacher> updateStudent(@PathVariable("id") int id, @RequestBody Teacher teacherRequest) {
		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("teacherId " + id + "not found"));

		teacher.setFirstName(teacherRequest.getFirstName());
		teacher.setLastName(teacherRequest.getLastName());
		teacher.setEmail(teacherRequest.getEmail());
		teacher.setPictureUrl(teacherRequest.getPictureUrl());
		teacher.setRoles(teacherRequest.getRoles());
		
		teacher.setNationalId(teacherRequest.getNationalId());
		teacher.setPhoneNumber(teacherRequest.getPhoneNumber());
		teacher.setAddress(teacherRequest.getAddress());
		teacher.setAge(teacherRequest.getAge());

		return new ResponseEntity<>(teacherRepository.save(teacher), HttpStatus.OK);
	}

	@PostMapping("/groups/{groupId}/teachers/{teacherId}")
	public ResponseEntity<HttpStatus> addStudentToGroup(@PathVariable(value = "groupId") int groupId,
			@PathVariable(value = "teacherId") int teacherId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));

		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + teacherId));

		// Add the student to the group if not already present
		if (!group.getStudents().contains(teacher)) {
			group.addTeacher(teacher);
			groupRepository.save(group);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT); // Or any other appropriate status
		}
	}

	@DeleteMapping("/groups/{groupId}/teachers/{teacherId}")
	public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "groupId") int groupId,
			@PathVariable(value = "teacherId") int teacherId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));

		System.out.println(groupId + " " + teacherId);
		group.removeTracher(teacherId);
		groupRepository.save(group);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/teachers/{id}")
	public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable("id") int id) {
		teacherRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
