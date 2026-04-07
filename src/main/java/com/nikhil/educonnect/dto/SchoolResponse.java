package com.nikhil.educonnect.dto;

public class SchoolResponse {

    private String id;
    private String schoolName;
    private String email;
    private String city;
    private String board;
    private String address;
    private double rating;
    private boolean isVerified;
    private String createdAt;

    // Getters
    public String getId() { return id; }
    public String getSchoolName() { return schoolName; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public String getBoard() { return board; }
    public String getAddress() { return address; }
    public double getRating() { return rating; }
    public boolean isVerified() { return isVerified; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public void setEmail(String email) { this.email = email; }
    public void setCity(String city) { this.city = city; }
    public void setBoard(String board) { this.board = board; }
    public void setAddress(String address) { this.address = address; }
    public void setRating(double rating) { this.rating = rating; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}