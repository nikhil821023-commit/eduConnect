package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Who wrote the review
    @Column(name = "reviewer_id", nullable = false)
    private String reviewerId;

    @Column(name = "reviewer_name")
    private String reviewerName;

    // Who is being reviewed
    @Column(name = "target_id", nullable = false)
    private String targetId;

    // TEACHER or SCHOOL
    @Column(name = "target_type")
    private String targetType;

    @Column(name = "target_name")
    private String targetName;

    // 1 to 5
    @Column(nullable = false)
    private int rating;

    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructor
    public Review() {}

    public Review(String reviewerId, String reviewerName,
                  String targetId, String targetType,
                  String targetName, int rating, String comment) {
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.targetId = targetId;
        this.targetType = targetType;
        this.targetName = targetName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getReviewerId() { return reviewerId; }
    public String getReviewerName() { return reviewerName; }
    public String getTargetId() { return targetId; }
    public String getTargetType() { return targetType; }
    public String getTargetName() { return targetName; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setReviewerId(String reviewerId) { this.reviewerId = reviewerId; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
    public void setTargetId(String targetId) { this.targetId = targetId; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public void setTargetName(String targetName) { this.targetName = targetName; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}