package com.example.demo.controller;

import com.example.demo.model.JobPosting;
import com.example.demo.model.User;
import com.example.demo.payload.JobRequest;
import com.example.demo.payload.JobResponse;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobController {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobController(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/jobs")
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream()
            .map(job -> new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getEmployer().getFullname() // make sure you're calling .getFullname()
            ))
            .toList();
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        JobPosting job = jobRepository.findById(id).orElse(null);
    
        if (job == null) {
            return ResponseEntity.status(404).body("Job not found.");
        }
    
        JobResponse response = new JobResponse(
            job.getId(),
            job.getTitle(),
            job.getDescription(),
            job.getLocation(),
            job.getEmployer().getFullname()
        );
    
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employer/jobs")
    public ResponseEntity<?> getJobsByEmployer(HttpServletRequest request) {
        User employer = extractUserFromToken(request);
        return ResponseEntity.ok(jobRepository.findByEmployerId(employer.getId()));
    }

    @PostMapping("/jobs")
    public ResponseEntity<?> createJob(@RequestBody JobRequest jobRequest, HttpServletRequest request) {
        System.out.println("Job Title: " + jobRequest.getTitle());
        String authHeader = request.getHeader("Authorization");
        System.out.println("Token: " + authHeader);
        User employer = extractUserFromToken(request);

        if (!"employer".equalsIgnoreCase(employer.getRole())) {
            return ResponseEntity.status(403).body("Only employers can post jobs.");
        }

        JobPosting job = new JobPosting(
                jobRequest.getTitle(),
                jobRequest.getDescription(),
                jobRequest.getLocation(),
                employer
        );

        jobRepository.save(job);
        return ResponseEntity.ok("Job posted successfully.");
    }

    private User extractUserFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String email = Jwts.parserBuilder()
                .setSigningKey(JwtUtil.getKey())  // you need to expose `getKey()` in JwtUtil
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow();
    }
    @DeleteMapping("/employer/jobs/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id, HttpServletRequest request) {
        User employer = extractUserFromToken(request);

        JobPosting job = jobRepository.findById(id).orElse(null);

        if (job == null) {
            return ResponseEntity.status(404).body("Job not found.");
        }

        if (!job.getEmployer().getId().equals(employer.getId())) {
            return ResponseEntity.status(403).body("You are not allowed to delete this job.");
        }

        jobRepository.delete(job);
        return ResponseEntity.ok("Job deleted successfully.");
    }

}
