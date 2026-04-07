package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "checkins")
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "parent_id")
    private String parentId;

    // Teacher or school they checked into
    @Column(name = "destination_id")
    private String destinationId;

    @Column(name = "destination_name")
    private String destinationName;

    // TUITION / SCHOOL / HOME
    @Column(name = "destination_type")
    private String destinationType;

    // ARRIVAL / DEPARTURE
    private String type;

    // Student GPS coordinates
    private double lat;
    private double lng;

    // Destination GPS coordinates
    @Column(name = "dest_lat")
    private double destLat;

    @Column(name = "dest_lng")
    private double destLng;

    // Distance between student and destination in metres
    @Column(name = "distance_metres")
    private int distanceMetres;

    // Is student within 200m of destination?
    @Column(name = "is_verified")
    private boolean isVerified;

    private LocalDateTime timestamp;

    // Expected home arrival (set on departure)
    @Column(name = "expected_home_at")
    private LocalDateTime expectedHomeAt;

    // Constructor
    public Checkin() {}

    public Checkin(String studentId, String studentName,
                   String parentId, String destinationId,
                   String destinationName, String destinationType,
                   String type, double lat, double lng,
                   double destLat, double destLng,
                   int distanceMetres, boolean isVerified) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.parentId = parentId;
        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.destinationType = destinationType;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.destLat = destLat;
        this.destLng = destLng;
        this.distanceMetres = distanceMetres;
        this.isVerified = isVerified;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getParentId() { return parentId; }
    public String getDestinationId() { return destinationId; }
    public String getDestinationName() { return destinationName; }
    public String getDestinationType() { return destinationType; }
    public String getType() { return type; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public double getDestLat() { return destLat; }
    public double getDestLng() { return destLng; }
    public int getDistanceMetres() { return distanceMetres; }
    public boolean isVerified() { return isVerified; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public LocalDateTime getExpectedHomeAt() { return expectedHomeAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setStudentId(String s) { this.studentId = s; }
    public void setStudentName(String s) { this.studentName = s; }
    public void setParentId(String p) { this.parentId = p; }
    public void setDestinationId(String d) { this.destinationId = d; }
    public void setDestinationName(String d) { this.destinationName = d; }
    public void setDestinationType(String d) { this.destinationType = d; }
    public void setType(String t) { this.type = t; }
    public void setLat(double lat) { this.lat = lat; }
    public void setLng(double lng) { this.lng = lng; }
    public void setDestLat(double d) { this.destLat = d; }
    public void setDestLng(double d) { this.destLng = d; }
    public void setDistanceMetres(int d) { this.distanceMetres = d; }
    public void setVerified(boolean v) { this.isVerified = v; }
    public void setTimestamp(LocalDateTime t) { this.timestamp = t; }
    public void setExpectedHomeAt(LocalDateTime e) { this.expectedHomeAt = e; }
}