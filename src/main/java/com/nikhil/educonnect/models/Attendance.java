package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "teacher_id", nullable = false)
    private String teacherId;

    @Column(name = "teacher_name")
    private String teacherName;

    private String subject;

    // Date of class
    private LocalDate date;

    // Class start time
    @Column(name = "class_time")
    private LocalTime classTime;

    // PRESENT / ABSENT / LATE
    private String status;

    // Optional note from teacher
    private String notes;

    // Has parent been notified?
    @Column(name = "parent_notified")
    private boolean parentNotified;

    @Column(name = "marked_at")
    private LocalDateTime markedAt;

    // Constructor
    public Attendance() {}

    public Attendance(String studentId, String studentName,
                      String teacherId, String teacherName,
                      String subject, LocalDate date,
                      LocalTime classTime, String status,
                      String notes) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.subject = subject;
        this.date = date;
        this.classTime = classTime;
        this.status = status;
        this.notes = notes;
        this.parentNotified = false;
        this.markedAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getTeacherId() { return teacherId; }
    public String getTeacherName() { return teacherName; }
    public String getSubject() { return subject; }
    public LocalDate getDate() { return date; }
    public LocalTime getClassTime() { return classTime; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public boolean isParentNotified() { return parentNotified; }
    public LocalDateTime getMarkedAt() { return markedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setClassTime(LocalTime classTime) { this.classTime = classTime; }
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setParentNotified(boolean parentNotified) { this.parentNotified = parentNotified; }
    public void setMarkedAt(LocalDateTime markedAt) { this.markedAt = markedAt; }
}