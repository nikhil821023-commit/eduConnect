package com.nikhil.educonnect.dto;

public class TeacherResponse {

    private String id;
    private String name;
    private String email;
    private String subject;
    private int experienceYears;
    private String city;
    private double rating;
    private boolean isAvailable;
    private String bio;
    private String createdAt;

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSubject() { return subject; }
    public int getExperienceYears() { return experienceYears; }
    public String getCity() { return city; }
    public double getRating() { return rating; }
    public boolean isAvailable() { return isAvailable; }
    public String getBio() { return bio; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setExperienceYears(int e) { this.experienceYears = e; }
    public void setCity(String city) { this.city = city; }
    public void setRating(double rating) { this.rating = rating; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setBio(String bio) { this.bio = bio; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}