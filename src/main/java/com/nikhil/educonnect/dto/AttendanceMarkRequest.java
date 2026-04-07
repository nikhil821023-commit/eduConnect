package com.nikhil.educonnect.dto;

import java.util.List;

public class AttendanceMarkRequest {

    private String teacherId;
    private String subject;
    private String date;
    private String classTime;
    private List<StudentAttendance> students;

    public static class StudentAttendance {
        private String studentId;
        private String studentName;
        // PRESENT / ABSENT / LATE
        private String status;
        private String notes;

        public String getStudentId() { return studentId; }
        public String getStudentName() { return studentName; }
        public String getStatus() { return status; }
        public String getNotes() { return notes; }
        public void setStudentId(String s) { this.studentId = s; }
        public void setStudentName(String s) { this.studentName = s; }
        public void setStatus(String s) { this.status = s; }
        public void setNotes(String n) { this.notes = n; }
    }

    // Getters
    public String getTeacherId() { return teacherId; }
    public String getSubject() { return subject; }
    public String getDate() { return date; }
    public String getClassTime() { return classTime; }
    public List<StudentAttendance> getStudents() { return students; }

    // Setters
    public void setTeacherId(String t) { this.teacherId = t; }
    public void setSubject(String s) { this.subject = s; }
    public void setDate(String d) { this.date = d; }
    public void setClassTime(String c) { this.classTime = c; }
    public void setStudents(List<StudentAttendance> s) { this.students = s; }
}