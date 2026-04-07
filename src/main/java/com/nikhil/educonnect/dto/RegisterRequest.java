package com.nikhil.educonnect.dto;

public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    // TEACHER / SCHOOL / PARENT / STUDENT
    private String role;

    // Extra fields for teacher
    private String subject;
    private int experienceYears;

    // Extra fields for school
    private String schoolName;
    private String board;
    private String address;

    // Common
    private String city;

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getSubject() { return subject; }
    public int getExperienceYears() { return experienceYears; }
    public String getSchoolName() { return schoolName; }
    public String getBoard() { return board; }
    public String getAddress() { return address; }
    public String getCity() { return city; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setExperienceYears(int e) { this.experienceYears = e; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public void setBoard(String board) { this.board = board; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
}