package com.nikhil.educonnect.dto;

public class ApplicationRequest {

    private String teacherId;
    private String coverNote;

    // Getters
    public String getTeacherId() { return teacherId; }
    public String getCoverNote() { return coverNote; }

    // Setters
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }
    public void setCoverNote(String coverNote) { this.coverNote = coverNote; }
}