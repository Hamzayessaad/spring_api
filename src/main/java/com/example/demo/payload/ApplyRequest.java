package com.example.demo.payload;

public class ApplyRequest {
    private String message;
    private String resumeLink; // optional

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getResumeLink() { return resumeLink; }
    public void setResumeLink(String resumeLink) { this.resumeLink = resumeLink; }
}
