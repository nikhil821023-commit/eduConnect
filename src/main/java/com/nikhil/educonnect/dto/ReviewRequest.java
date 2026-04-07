package com.nikhil.educonnect.dto;

public class ReviewRequest {

    private String reviewerId;
    private String reviewerName;
    private String targetId;
    // TEACHER or SCHOOL
    private String targetType;
    private int rating;
    private String comment;

    // Getters
    public String getReviewerId() { return reviewerId; }
    public String getReviewerName() { return reviewerName; }
    public String getTargetId() { return targetId; }
    public String getTargetType() { return targetType; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    // Setters
    public void setReviewerId(String reviewerId) { this.reviewerId = reviewerId; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
    public void setTargetId(String targetId) { this.targetId = targetId; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
}