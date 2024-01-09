package com.training.Quizzes.App.Controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.training.Quizzes.App.auth.JwtUtil;
import com.training.Quizzes.App.model.User;
import com.training.Quizzes.App.model.request.LoginReq;
import com.training.Quizzes.App.model.response.ErrorRes;
import com.training.Quizzes.App.model.response.LoginRes;
import com.training.Quizzes.App.service.UserService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class AuthController {
	
    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) {

        try {
        	Optional<User> existingUser = userService.findByEmail(loginReq.getEmail());

        	if(!existingUser.isPresent())throw new Exception("No such email exist");
        	
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            User user = new User(email,"");
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Check if the user exist 
        	// user service has method :    Optional<User> findByEmail(String email);

        	Optional<User> existingUser = userService.findByEmail(user.getEmail());

        	if (existingUser.isPresent()) {
        	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        	            .body(new ErrorRes(HttpStatus.BAD_REQUEST, "Email is already registered"));
        	}

            // Register the user
            User registeredUser = userService.registerUser(user);

            // Optionally, return registeredUser details
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


}
