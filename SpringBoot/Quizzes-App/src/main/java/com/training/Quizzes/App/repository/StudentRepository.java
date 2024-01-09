package com.training.Quizzes.App.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Student;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface  StudentRepository extends JpaRepository<Student, Integer>   {
	
    List<Student> findStudentsByGroupsId(int groupId);
    
    List<Student> deleteStudentsByGroupsId(int groupId);

	Page<Student> findByFirstNameContaining(String name, PageRequest of);	

}
