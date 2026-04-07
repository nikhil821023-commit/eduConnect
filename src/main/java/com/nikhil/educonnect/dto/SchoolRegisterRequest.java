package com.nikhil.educonnect.dto;

public class SchoolRegisterRequest {

    private String schoolName;
    private String email;
    private String password;
    private String address;
    private String city;
    private String board;

    // Getters
    public String getSchoolName() { return schoolName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getBoard() { return board; }

    // Setters
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setBoard(String board) { this.board = board; }
}