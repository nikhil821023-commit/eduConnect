package com.nikhil.educonnect.dto;

public class NotificationResponse {

    private String id;
    private String recipientId;
    private String type;
    private String title;
    private String message;
    private boolean isRead;
    private String referenceId;
    private String createdAt;

    // Getters
    public String getId() { return id; }
    public String getRecipientId() { return recipientId; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public String getReferenceId() { return referenceId; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setRecipientId(String r) { this.recipientId = r; }
    public void setType(String type) { this.type = type; }
    public void setTitle(String title) { this.title = title; }
    public void setMessage(String message) { this.message = message; }
    public void setRead(boolean read) { isRead = read; }
    public void setReferenceId(String r) { this.referenceId = r; }
    public void setCreatedAt(String c) { this.createdAt = c; }
}