package com.training.Quizzes.App.Controller;

import java.io.Console;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.auth.JwtUtil;
import com.training.Quizzes.App.entity.Student;
import com.training.Quizzes.App.entity.User;
import com.training.Quizzes.App.model.IUser.Roles;
import com.training.Quizzes.App.model.request.LoginReq;
import com.training.Quizzes.App.model.response.ErrorRes;
import com.training.Quizzes.App.model.response.LoginRes;
import com.training.Quizzes.App.repository.StudentRepository;
import com.training.Quizzes.App.service.UserService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class AuthController {
	
    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private final UserService userService;
    @Autowired
    private final StudentRepository studentRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService,
    		StudentRepository studentRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.studentRepository = studentRepository;
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {

        try {
        	Optional<User> existingUser = userService.findByEmail(loginReq.getEmail());

        	User user = existingUser.orElseThrow(() -> new Exception("No such email exist"));
        	
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            
//          User user = new User(email,"");
            // cast the existingUser to user 
            String token = jwtUtil.createToken(user);
            user.setPassword("******");
            LoginRes loginRes = new LoginRes(email,token,user);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody Student student) {
        try {
            // Check if the user exist 
        	// user service has method :    Optional<User> findByEmail(String email);
        	System.out.println(student);
        	Optional<User> existingUser = userService.findByEmail(student.getEmail());

        	if (existingUser.isPresent()) {
        	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        	            .body(new ErrorRes(HttpStatus.BAD_REQUEST, "Email is already registered"));
        	}

            // Register the user
        	student.setRoles(Roles.ROLE_STUDENT);
            User registeredUser = studentRepository.save(student);

            // Optionally, return registeredUser details
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


}
