package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.payload.LoginRequest;
import com.example.demo.payload.UpdateProfileRequest;
import com.example.demo.payload.UserProfileResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔐 Signup
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("user");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    // 🔐 Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.getEmail()) &&
                             u.getPassword().equals(request.getPassword()))
                .findFirst()
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }

        String token = JwtUtil.generateToken(user.getEmail(), user.getFullname(), user.getRole());
        return ResponseEntity.ok("JWT Token: " + token);
    }

    // 👤 Get profile
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        User user = extractUserFromToken(request);

        UserProfileResponse profile = new UserProfileResponse(
                user.getFullname(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(profile);
    }

    // ✏️ Update profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest req, HttpServletRequest request) {
        User user = extractUserFromToken(request);

        if (req.getFullname() != null && !req.getFullname().isBlank()) {
            user.setFullname(req.getFullname());
        }

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(req.getPassword()); // 🔐 Hash in production
        }

        userRepository.save(user);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    // 🔐 Extract user from JWT
    private User extractUserFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // remove "Bearer "

        String email = Jwts.parserBuilder()
                .setSigningKey(JwtUtil.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
