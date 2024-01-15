package com.training.Quizzes.App.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Teacher;
import com.training.Quizzes.App.model.IUser;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface  TeacherRepository extends JpaRepository<Teacher, Integer>   {
	
	  List<Teacher> findTeachersByGroupsId(int groupId);

	Optional<Teacher> findByEmail(String email);

	Optional<Teacher> save(IUser user);

}
