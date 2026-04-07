package com.nikhil.educonnect.dto;

public class CheckinResponse {

    private String id;
    private String studentId;
    private String studentName;
    private String destinationName;
    private String destinationType;
    private String type;
    private double lat;
    private double lng;
    private int distanceMetres;
    private boolean verified;
    private String timestamp;
    private String expectedHomeAt;
    private String verificationMessage;
    private String notificationSent;

    // Getters
    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getDestinationName() { return destinationName; }
    public String getDestinationType() { return destinationType; }
    public String getType() { return type; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public int getDistanceMetres() { return distanceMetres; }
    public boolean isVerified() { return verified; }
    public String getTimestamp() { return timestamp; }
    public String getExpectedHomeAt() { return expectedHomeAt; }
    public String getVerificationMessage() { return verificationMessage; }
    public String getNotificationSent() { return notificationSent; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setStudentId(String s) { this.studentId = s; }
    public void setStudentName(String s) { this.studentName = s; }
    public void setDestinationName(String d) { this.destinationName = d; }
    public void setDestinationType(String d) { this.destinationType = d; }
    public void setType(String t) { this.type = t; }
    public void setLat(double lat) { this.lat = lat; }
    public void setLng(double lng) { this.lng = lng; }
    public void setDistanceMetres(int d) { this.distanceMetres = d; }
    public void setVerified(boolean v) { this.verified = v; }
    public void setTimestamp(String t) { this.timestamp = t; }
    public void setExpectedHomeAt(String e) { this.expectedHomeAt = e; }
    public void setVerificationMessage(String v) { this.verificationMessage = v; }
    public void setNotificationSent(String n) { this.notificationSent = n; }
}