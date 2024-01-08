package com.training.Quizzes.App.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.entity.Group;
import com.training.Quizzes.App.entity.Student;
import com.training.Quizzes.App.repository.GroupRepository;
import com.training.Quizzes.App.repository.StudentRepository;

@CrossOrigin(value = "http://localhost:4200/")
@RestController
@RequestMapping("/api")
public class StudentController {

	  @Autowired
	  private StudentRepository studentRepository;

	  @Autowired
	  private GroupRepository groupRepository;

	  @GetMapping("/students")
	public ResponseEntity<Page<Student>> getAllStudents(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "20") int size) {

		Page<Student> studentPage;

		if (name == null || name.isEmpty()) {
			studentPage = studentRepository.findAll(PageRequest.of(page, size));
		} else {
			studentPage = studentRepository.findByFirstNameContaining(name, PageRequest.of(page, size));
		}

		if (studentPage.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(studentPage, HttpStatus.OK);
	}
	  
	  @PostMapping("/students")
	  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		  System.out.println(student);
	    Student tempStudent = studentRepository.save(new Student(
	    		student.getFirstName(), student.getLastName(),student.getNationalId(),student.getAge(),student.getPictureUrl()
	    		));
	    
	    return new ResponseEntity<>(tempStudent, HttpStatus.CREATED);
	  }
	  
	  
	  @GetMapping("/groups/{groupId}/students")
	  public ResponseEntity<List<Student>> getAllTagsByTutorialId(@PathVariable(value = "groupId") int groupId) {
	    if (!groupRepository.existsById((int) groupId)) {
	      throw new ResourceNotFoundException("Not found Group with id = " + groupId);
	    }

	    List<Student> students = studentRepository.findStudentsByGroupsId(groupId);
	    return new ResponseEntity<>(students, HttpStatus.OK);
	  }

	  @GetMapping("/students/{id}")
	  public ResponseEntity<Student> getStudentsById(@PathVariable(value = "id") int id) {
	    Student student = studentRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + id));

	    return new ResponseEntity<>(student, HttpStatus.OK);
	  }
	  
	  @GetMapping("/students/{studentId}/groups")
	  public ResponseEntity<List<Group>> getAllGroupssByStudentId(@PathVariable(value = "studentId") int studentId) {
	    if (!studentRepository.existsById(studentId)) {
	      throw new ResourceNotFoundException("Not found Student  with id = " + studentId);
	    }

	    List<Group> groups = groupRepository.findGroupsByStudentsId(studentId);
	    return new ResponseEntity<>(groups, HttpStatus.OK);
	  }

	  @PostMapping("/groups/{groupId}/students")
	  public ResponseEntity<Student> addTag(@PathVariable(value = "groupId") int groupId, @RequestBody Student studentRequest) {
	    Student stu = groupRepository.findById(groupId).map(group -> {
	      int stuId = studentRequest.getId();
	      
	      // tag is existed
	      if (stuId != 0L) {
	    	  Student tempStudent = studentRepository.findById(stuId)
	            .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + stuId));
	        group.addStudent(studentRequest);
	        groupRepository.save(group);
	        return tempStudent;
	      }
	      
	      // add and create new Tag
	      group.addStudent(studentRequest);
	      return studentRepository.save(studentRequest);
	    }).orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));

	    return new ResponseEntity<>(stu, HttpStatus.CREATED);
	  }

	  @PutMapping("/students/{id}")
	  public ResponseEntity<Student> updateStudent(@PathVariable("id") int id, @RequestBody Student studentRequest) {
	    Student stu = studentRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("studentId " + id + "not found"));

	    System.out.println("hello");
	    System.out.println(studentRequest);
		stu.setFirstName(studentRequest.getFirstName());
		stu.setLastName(studentRequest.getLastName());
		stu.setNationalId(studentRequest.getNationalId());
		stu.setAge(studentRequest.getAge());
		stu.setPictureUrl(studentRequest.getPictureUrl());

	    return new ResponseEntity<>(studentRepository.save(stu), HttpStatus.OK);
	  }
	 
	  @DeleteMapping("/groups/{groupId}/students/{studentId}")
	  public ResponseEntity<HttpStatus> deleteTagFromTutorial(@PathVariable(value = "groupId") int groupId, @PathVariable(value = "studentId") int studentId) {
	    Group group = groupRepository.findById(groupId)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));
	    
	    group.removeStudent(studentId);
	    groupRepository.save(group);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	  
	  @PostMapping("/groups/{groupId}/students/{studentId}")
	  public ResponseEntity<HttpStatus> addStudentToGroup(@PathVariable(value = "groupId") int groupId, @PathVariable(value = "studentId") int studentId) {
	      Group group = groupRepository.findById(groupId)
	              .orElseThrow(() -> new ResourceNotFoundException("Not found Group with id = " + groupId));
	      
	      Student student = studentRepository.findById(studentId)
	              .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + studentId));

	      // Add the student to the group if not already present
	      if (!group.getStudents().contains(student)) {
	          group.addStudent(student);
	          groupRepository.save(group);
	          return new ResponseEntity<>(HttpStatus.CREATED);
	      } else {
	          return new ResponseEntity<>(HttpStatus.CONFLICT); // Or any other appropriate status
	      }
	  }

	  
	  @DeleteMapping("/students/{id}")
	  public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") int id) {
		  
      Student student = studentRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + id));
      
      Set<Group> groups = student.getGroups();

      for (Group group : groups) 
          group.getStudents().remove(student);

      student.getGroups().clear();
      groupRepository.saveAll(groups);
      studentRepository.delete(student);
		
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
}

