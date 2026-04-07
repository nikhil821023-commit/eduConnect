package com.nikhil.educonnect.dto;

public class EarningResponse {

    private String id;
    private String teacherId;
    private String teacherName;
    private String studentId;
    private String studentName;
    private int amount;
    private int month;
    private int year;
    private String status;
    private String paidAt;
    private String paymentMethod;
    private String createdAt;

    // Getters
    public String getId() { return id; }
    public String getTeacherId() { return teacherId; }
    public String getTeacherName() { return teacherName; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public int getAmount() { return amount; }
    public int getMonth() { return month; }
    public int getYear() { return year; }
    public String getStatus() { return status; }
    public String getPaidAt() { return paidAt; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTeacherId(String t) { this.teacherId = t; }
    public void setTeacherName(String t) { this.teacherName = t; }
    public void setStudentId(String s) { this.studentId = s; }
    public void setStudentName(String s) { this.studentName = s; }
    public void setAmount(int a) { this.amount = a; }
    public void setMonth(int m) { this.month = m; }
    public void setYear(int y) { this.year = y; }
    public void setStatus(String s) { this.status = s; }
    public void setPaidAt(String p) { this.paidAt = p; }
    public void setPaymentMethod(String p) { this.paymentMethod = p; }
    public void setCreatedAt(String c) { this.createdAt = c; }
}