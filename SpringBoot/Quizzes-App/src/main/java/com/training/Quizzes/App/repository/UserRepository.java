package com.training.Quizzes.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.entity.User;


@RepositoryRestResource(path = "users")
@CrossOrigin(value = "http://localhost:4200/")
public interface UserRepository extends JpaRepository<User, Integer> {
	
}
