package com.training.Quizzes.App.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Teacher;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface  TeacherRepository extends JpaRepository<Teacher, Integer>   {
	
	  List<Teacher> findTeachersByGroupsId(int groupId);

}
