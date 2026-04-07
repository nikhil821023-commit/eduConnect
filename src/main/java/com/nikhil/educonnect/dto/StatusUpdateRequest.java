package com.nikhil.educonnect.dto;

public class StatusUpdateRequest {

    // SHORTLISTED / INTERVIEW / HIRED / REJECTED
    private String status;
    private String rejectionReason;

    // Getters
    public String getStatus() { return status; }
    public String getRejectionReason() { return rejectionReason; }

    // Setters
    public void setStatus(String status) { this.status = status; }
    public void setRejectionReason(String r) { this.rejectionReason = r; }
}