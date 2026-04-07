package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Who receives this notification
    @Column(name = "recipient_id", nullable = false)
    private String recipientId;

    @Column(name = "recipient_role")
    private String recipientRole;

    // ARRIVAL / DEPARTURE / ABSENT /
    // SAFETY_ALERT / JOB_ALERT / REVIEW / FEE_DUE
    private String type;

    private String title;

    private String message;

    @Column(name = "is_read")
    private boolean isRead;

    // Extra data — student ID, location etc
    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructor
    public Notification() {}

    public Notification(String recipientId, String recipientRole,
                        String type, String title,
                        String message, String referenceId) {
        this.recipientId = recipientId;
        this.recipientRole = recipientRole;
        this.type = type;
        this.title = title;
        this.message = message;
        this.referenceId = referenceId;
        this.isRead = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getRecipientId() { return recipientId; }
    public String getRecipientRole() { return recipientRole; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public String getReferenceId() { return referenceId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setRecipientId(String r) { this.recipientId = r; }
    public void setRecipientRole(String r) { this.recipientRole = r; }
    public void setType(String type) { this.type = type; }
    public void setTitle(String title) { this.title = title; }
    public void setMessage(String message) { this.message = message; }
    public void setRead(boolean read) { isRead = read; }
    public void setReferenceId(String r) { this.referenceId = r; }
    public void setCreatedAt(LocalDateTime c) { this.createdAt = c; }
}