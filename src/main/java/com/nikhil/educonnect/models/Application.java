package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "job_id", nullable = false)
    private String jobId;

    @Column(name = "teacher_id", nullable = false)
    private String teacherId;

    // Store name directly for easy display
    @Column(name = "teacher_name")
    private String teacherName;

    @Column(name = "teacher_email")
    private String teacherEmail;

    @Column(name = "teacher_subject")
    private String teacherSubject;

    @Column(name = "teacher_experience")
    private int teacherExperience;

    @Column(name = "teacher_rating")
    private double teacherRating;

    // APPLIED → SHORTLISTED → INTERVIEW → HIRED → REJECTED
    private String status;

    @Column(name = "cover_note")
    private String coverNote;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    // Constructor
    public Application() {}

    public Application(String jobId, String teacherId,
                       String teacherName, String teacherEmail,
                       String teacherSubject, int teacherExperience,
                       double teacherRating, String coverNote) {
        this.jobId = jobId;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherSubject = teacherSubject;
        this.teacherExperience = teacherExperience;
        this.teacherRating = teacherRating;
        this.coverNote = coverNote;
        this.status = "APPLIED";
        this.appliedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getJobId() { return jobId; }
    public String getTeacherId() { return teacherId; }
    public String getTeacherName() { return teacherName; }
    public String getTeacherEmail() { return teacherEmail; }
    public String getTeacherSubject() { return teacherSubject; }
    public int getTeacherExperience() { return teacherExperience; }
    public double getTeacherRating() { return teacherRating; }
    public String getStatus() { return status; }
    public String getCoverNote() { return coverNote; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getRejectionReason() { return rejectionReason; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setJobId(String jobId) { this.jobId = jobId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void setTeacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; }
    public void setTeacherSubject(String s) { this.teacherSubject = s; }
    public void setTeacherExperience(int e) { this.teacherExperience = e; }
    public void setTeacherRating(double r) { this.teacherRating = r; }
    public void setStatus(String status) { this.status = status; }
    public void setCoverNote(String coverNote) { this.coverNote = coverNote; }
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setRejectionReason(String r) { this.rejectionReason = r; }
}