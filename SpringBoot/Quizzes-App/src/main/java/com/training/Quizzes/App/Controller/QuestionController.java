package com.training.Quizzes.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.Quizzes.App.entity.Question;
import com.training.Quizzes.App.repository.QuestionRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:4200/")
public class QuestionController {
	
	@Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions/")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
    	question.getQuiz();
        return ResponseEntity.ok(questionRepository.saveQuestion(question));
    }


}


