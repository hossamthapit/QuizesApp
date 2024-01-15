package com.training.Quizzes.App.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Student;
import com.training.Quizzes.App.model.IUser;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface  StudentRepository extends JpaRepository<Student, Integer>   {
	
    List<Student> findStudentsByGroupsId(int groupId);
    
    List<Student> deleteStudentsByGroupsId(int groupId);

	Page<Student> findByFirstNameContaining(String name, PageRequest of);

	Optional<Student> findByEmail(String email);

	Optional<Student> save(IUser user);	

}
