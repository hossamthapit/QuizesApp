package com.training.Quizzes.App.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Group;

@CrossOrigin(value = "http://localhost:4200/")
public interface GroupRepository extends JpaRepository<Group, Integer> {

	List<Group> findGroupsByStudentsId(int studentId);
			
	List<Group> findGroupsByTeachersId(int teacherId);
			
	Page<Group> findByTitleContaining(String title, PageRequest of);

}
