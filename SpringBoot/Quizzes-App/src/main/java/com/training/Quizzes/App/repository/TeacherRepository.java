package com.training.Quizzes.App.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Teacher;

@CrossOrigin(value = "http://localhost:4200/")
public interface  TeacherRepository extends JpaRepository<Teacher, Integer>   {
	
	  List<Teacher> findTeachersByGroupsId(int groupId);

}
