package com.nikhil.educonnect.dto;

public class AttendanceResponse {

    private String id;
    private String studentId;
    private String studentName;
    private String teacherId;
    private String teacherName;
    private String subject;
    private String date;
    private String classTime;
    private String status;
    private String notes;
    private boolean parentNotified;
    private String markedAt;

    // Getters
    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getTeacherId() { return teacherId; }
    public String getTeacherName() { return teacherName; }
    public String getSubject() { return subject; }
    public String getDate() { return date; }
    public String getClassTime() { return classTime; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public boolean isParentNotified() { return parentNotified; }
    public String getMarkedAt() { return markedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setStudentId(String s) { this.studentId = s; }
    public void setStudentName(String s) { this.studentName = s; }
    public void setTeacherId(String t) { this.teacherId = t; }
    public void setTeacherName(String t) { this.teacherName = t; }
    public void setSubject(String s) { this.subject = s; }
    public void setDate(String d) { this.date = d; }
    public void setClassTime(String c) { this.classTime = c; }
    public void setStatus(String s) { this.status = s; }
    public void setNotes(String n) { this.notes = n; }
    public void setParentNotified(boolean p) { this.parentNotified = p; }
    public void setMarkedAt(String m) { this.markedAt = m; }
}