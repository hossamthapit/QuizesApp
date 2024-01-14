package com.training.Quizzes.App.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.training.Quizzes.App.entity.User;
import com.training.Quizzes.App.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Implement validation, password hashing, etc., before saving
        return userRepository.save(user);
    }

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
