package com.training.Quizzes.App.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.Group;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface GroupRepository extends JpaRepository<Group, Integer> {

			
	List<Group> findGroupsByTeachersId(int teacherId);
			
	Page<Group> findByTitleContaining(String title, PageRequest of);

	Page<Group> findByStudentsId(int studentId, PageRequest of);

	Page<Group> findByTeachersId(int teacherId, PageRequest of);


}
