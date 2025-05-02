package com.example.demo.payload;

public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String employerName;

    public JobResponse(Long id, String title, String description, String location, String employerName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.employerName = employerName;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getEmployerName() { return employerName; }
}
