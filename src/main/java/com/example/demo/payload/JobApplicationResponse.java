package com.example.demo.payload;

import java.time.LocalDateTime;

public class JobApplicationResponse {
    private Long applicationId;
    private String jobTitle;
    private String employerName;
    private String message;
    private String resumeLink;
    private LocalDateTime appliedAt;

    public JobApplicationResponse(Long applicationId, String jobTitle, String employerName,
                                  String message, String resumeLink, LocalDateTime appliedAt) {
        this.applicationId = applicationId;
        this.jobTitle = jobTitle;
        this.employerName = employerName;
        this.message = message;
        this.resumeLink = resumeLink;
        this.appliedAt = appliedAt;
    }

    public Long getApplicationId() { return applicationId; }
    public String getJobTitle() { return jobTitle; }
    public String getEmployerName() { return employerName; }
    public String getMessage() { return message; }
    public String getResumeLink() { return resumeLink; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
}
