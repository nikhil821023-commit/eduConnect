package com.nikhil.educonnect.dto;

public class CheckinRequest {

    private String studentId;
    private String studentName;
    private String parentId;
    private String destinationId;
    // TUITION / SCHOOL / HOME
    private String destinationType;

    // Student's current GPS coordinates
    private double lat;
    private double lng;

    // Getters
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getParentId() { return parentId; }
    public String getDestinationId() { return destinationId; }
    public String getDestinationType() { return destinationType; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }

    // Setters
    public void setStudentId(String s) { this.studentId = s; }
    public void setStudentName(String s) { this.studentName = s; }
    public void setParentId(String p) { this.parentId = p; }
    public void setDestinationId(String d) { this.destinationId = d; }
    public void setDestinationType(String d) { this.destinationType = d; }
    public void setLat(double lat) { this.lat = lat; }
    public void setLng(double lng) { this.lng = lng; }
}