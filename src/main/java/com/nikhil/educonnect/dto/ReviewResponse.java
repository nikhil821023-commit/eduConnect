package com.nikhil.educonnect.dto;

public class ReviewResponse {

    private String id;
    private String reviewerId;
    private String reviewerName;
    private String targetId;
    private String targetType;
    private String targetName;
    private int rating;
    private String comment;
    private String createdAt;

    // Getters
    public String getId() { return id; }
    public String getReviewerId() { return reviewerId; }
    public String getReviewerName() { return reviewerName; }
    public String getTargetId() { return targetId; }
    public String getTargetType() { return targetType; }
    public String getTargetName() { return targetName; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setReviewerId(String r) { this.reviewerId = r; }
    public void setReviewerName(String r) { this.reviewerName = r; }
    public void setTargetId(String t) { this.targetId = t; }
    public void setTargetType(String t) { this.targetType = t; }
    public void setTargetName(String t) { this.targetName = t; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}