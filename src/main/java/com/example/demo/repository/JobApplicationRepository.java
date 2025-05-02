package com.example.demo.repository;

import com.example.demo.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
    List<JobApplication> findByCandidateId(Long candidateId);
    List<JobApplication> findByJob_EmployerId(Long employerId);

}