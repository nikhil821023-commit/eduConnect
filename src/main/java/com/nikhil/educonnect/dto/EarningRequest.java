package com.nikhil.educonnect.dto;

public class EarningRequest {

    private String teacherId;
    private String teacherName;
    private String studentId;
    private String studentName;
    private int amount;
    private int month;
    private int year;

    // Getters
    public String getTeacherId() { return teacherId; }
    public String getTeacherName() { return teacherName; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public int getAmount() { return amount; }
    public int getMonth() { return month; }
    public int getYear() { return year; }

    // Setters
    public void setTeacherId(String t) { this.teacherId = t; }
    public void setTeacherName(String t) { this.teacherName = t; }
    public void setStudentId(String s) { this.studentId = s; }
    public void setStudentName(String s) { this.studentName = s; }
    public void setAmount(int a) { this.amount = a; }
    public void setMonth(int m) { this.month = m; }
    public void setYear(int y) { this.year = y; }
}