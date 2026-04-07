package com.nikhil.educonnect.dto;

public class LocationStatus {

    private String studentId;
    private String studentName;

    // AT_SCHOOL / AT_TUITION / HEADING_HOME / HOME / UNKNOWN
    private String currentStatus;

    private String lastLocation;
    private String lastCheckinTime;
    private boolean isSafe;
    private String safetyMessage;

    // Getters
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getCurrentStatus() { return currentStatus; }
    public String getLastLocation() { return lastLocation; }
    public String getLastCheckinTime() { return lastCheckinTime; }
    public boolean isSafe() { return isSafe; }
    public String getSafetyMessage() { return safetyMessage; }

    // Setters
    public void setStudentId(String s) { this.studentId = s; }
    public void setStudentName(String s) { this.studentName = s; }
    public void setCurrentStatus(String c) { this.currentStatus = c; }
    public void setLastLocation(String l) { this.lastLocation = l; }
    public void setLastCheckinTime(String l) { this.lastCheckinTime = l; }
    public void setSafe(boolean safe) { isSafe = safe; }
    public void setSafetyMessage(String s) { this.safetyMessage = s; }
}