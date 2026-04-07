package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Entity
@Table(name = "schools")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String address;
    private String city;
    private String board;

    @Column(name = "student_count")
    private int studentCount;

    private double rating;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String role = "SCHOOL";

    // Constructor
    public School(String s001, String dps, String email, String pass123, String newtown, String kolkata, String icse) {}

    public School(String schoolName, String email, String password,
                  String address, String city, String board) {
        this.schoolName = schoolName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.city = city;
        this.board = board;
        this.rating = 0.0;
        this.isVerified = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getSchoolName() { return schoolName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getBoard() { return board; }
    public int getStudentCount() { return studentCount; }
    public double getRating() { return rating; }
    public String getLogoUrl() { return logoUrl; }
    public boolean isVerified() { return isVerified; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getRole() { return role; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setBoard(String board) { this.board = board; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }
    public void setRating(double rating) { this.rating = rating; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setRole(String role) { this.role = role; }
}