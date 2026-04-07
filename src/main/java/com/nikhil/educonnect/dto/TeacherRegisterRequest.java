package com.nikhil.educonnect.dto;

public class TeacherRegisterRequest {

    private String name;
    private String email;
    private String password;
    private String subject;
    private int experienceYears;
    private String city;
    private String bio;

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getSubject() { return subject; }
    public int getExperienceYears() { return experienceYears; }
    public String getCity() { return city; }
    public String getBio() { return bio; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public void setCity(String city) { this.city = city; }
    public void setBio(String bio) { this.bio = bio; }
}