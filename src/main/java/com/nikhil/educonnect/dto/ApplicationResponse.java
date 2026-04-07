package com.nikhil.educonnect.dto;

public class ApplicationResponse {

    private String id;
    private String jobId;
    private String teacherId;
    private String teacherName;
    private String teacherEmail;
    private String teacherSubject;
    private int teacherExperience;
    private double teacherRating;
    private String status;
    private String coverNote;
    private String appliedAt;
    private String updatedAt;
    private String rejectionReason;

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
    public String getAppliedAt() { return appliedAt; }
    public String getUpdatedAt() { return updatedAt; }
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
    public void setAppliedAt(String appliedAt) { this.appliedAt = appliedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    public void setRejectionReason(String r) { this.rejectionReason = r; }
}