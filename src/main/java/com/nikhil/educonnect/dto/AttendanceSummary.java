package com.nikhil.educonnect.dto;

public class AttendanceSummary {

    private String studentId;
    private String studentName;
    private int totalClasses;
    private int presentCount;
    private int absentCount;
    private int lateCount;
    private double attendancePercentage;
    private int month;
    private int year;

    // Getters
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public int getTotalClasses() { return totalClasses; }
    public int getPresentCount() { return presentCount; }
    public int getAbsentCount() { return absentCount; }
    public int getLateCount() { return lateCount; }
    public double getAttendancePercentage() { return attendancePercentage; }
    public int getMonth() { return month; }
    public int getYear() { return year; }

    // Setters
    public void setStudentId(String s) { this.studentId = s; }
    public void setStudentName(String s) { this.studentName = s; }
    public void setTotalClasses(int t) { this.totalClasses = t; }
    public void setPresentCount(int p) { this.presentCount = p; }
    public void setAbsentCount(int a) { this.absentCount = a; }
    public void setLateCount(int l) { this.lateCount = l; }
    public void setAttendancePercentage(double a) { this.attendancePercentage = a; }
    public void setMonth(int m) { this.month = m; }
    public void setYear(int y) { this.year = y; }
}