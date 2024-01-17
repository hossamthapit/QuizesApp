package com.training.Quizzes.App.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Teacher;
import com.training.Quizzes.App.model.IUser;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface  TeacherRepository extends JpaRepository<Teacher, Integer>   {
	
	Optional<Teacher> findByEmail(String email);

	Optional<Teacher> save(IUser user);

	Page<Teacher> findByGroupsId(int groupId, PageRequest of);

}
