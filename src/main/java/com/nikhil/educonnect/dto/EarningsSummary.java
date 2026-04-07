package com.nikhil.educonnect.dto;

public class EarningsSummary {

    private String teacherId;
    private int month;
    private int year;
    private int totalEarned;
    private int pendingAmount;
    private int overdueAmount;
    private int totalStudents;

    // Getters
    public String getTeacherId() { return teacherId; }
    public int getMonth() { return month; }
    public int getYear() { return year; }
    public int getTotalEarned() { return totalEarned; }
    public int getPendingAmount() { return pendingAmount; }
    public int getOverdueAmount() { return overdueAmount; }
    public int getTotalStudents() { return totalStudents; }

    // Setters
    public void setTeacherId(String t) { this.teacherId = t; }
    public void setMonth(int m) { this.month = m; }
    public void setYear(int y) { this.year = y; }
    public void setTotalEarned(int t) { this.totalEarned = t; }
    public void setPendingAmount(int p) { this.pendingAmount = p; }
    public void setOverdueAmount(int o) { this.overdueAmount = o; }
    public void setTotalStudents(int t) { this.totalStudents = t; }
}