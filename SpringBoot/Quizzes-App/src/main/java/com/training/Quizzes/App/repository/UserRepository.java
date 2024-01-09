package com.training.Quizzes.App.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.training.Quizzes.App.model.User;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
