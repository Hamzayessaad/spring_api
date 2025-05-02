package com.example.demo.payload;

public class UserProfileResponse {
    private String fullname;
    private String email;
    private String role;

    public UserProfileResponse(String fullname, String email, String role) {
        this.fullname = fullname;
        this.email = email;
        this.role = role;
    }

    public String getFullname() { return fullname; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
