package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.payload.LoginRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }
    
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("user"); // default role
        }
    
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.getEmail()) && u.getPassword().equals(request.getPassword()))
                .findFirst()
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }

        String token = JwtUtil.generateToken(user.getEmail(), user.getFullname(), user.getRole());

        return ResponseEntity.ok("JWT Token: " + token);
    }

}
