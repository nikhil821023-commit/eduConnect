package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String subject;

    @Column(name = "experience_years")
    private int experienceYears;

    private String city;

    private double rating;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    private String bio;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Role is always TEACHER
    private String role = "TEACHER";

    // Constructor

    public Teacher(String name, String email, String password,
                   String subject, int experienceYears, String city) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.subject = subject;
        this.experienceYears = experienceYears;
        this.city = city;
        this.rating = 0.0;
        this.isAvailable = true;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getSubject() { return subject; }
    public int getExperienceYears() { return experienceYears; }
    public String getCity() { return city; }
    public double getRating() { return rating; }
    public boolean isAvailable() { return isAvailable; }
    public String getProfilePhotoUrl() { return profilePhotoUrl; }
    public String getBio() { return bio; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getRole() { return role; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public void setCity(String city) { this.city = city; }
    public void setRating(double rating) { this.rating = rating; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setProfilePhotoUrl(String profilePhotoUrl) { this.profilePhotoUrl = profilePhotoUrl; }
    public void setBio(String bio) { this.bio = bio; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setRole(String role) { this.role = role; }
}