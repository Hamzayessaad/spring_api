package com.example.demo.repository;

import com.example.demo.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByEmployerId(Long employerId);
}
