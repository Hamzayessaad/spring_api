package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private String resumeLink;

    private LocalDateTime appliedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobPosting job;

    public JobApplication() {}

    public JobApplication(String message, String resumeLink, User candidate, JobPosting job) {
        this.message = message;
        this.resumeLink = resumeLink;
        this.candidate = candidate;
        this.job = job;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getResumeLink() {
        return resumeLink;
    }
    
    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }
    
    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
    
    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
    
    public User getCandidate() {
        return candidate;
    }
    
    public void setCandidate(User candidate) {
        this.candidate = candidate;
    }
    
    public JobPosting getJob() {
        return job;
    }
    
    public void setJob(JobPosting job) {
        this.job = job;
    }
    
}
