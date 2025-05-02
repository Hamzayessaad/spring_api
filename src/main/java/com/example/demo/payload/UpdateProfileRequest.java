package com.example.demo.payload;

public class UpdateProfileRequest {
    private String fullname;
    private String password;

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
