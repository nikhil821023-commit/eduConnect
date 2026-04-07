package com.nikhil.educonnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Which school posted this job
    @Column(name = "school_id", nullable = false)
    private String schoolId;

    // School name — stored directly for easy display
    @Column(name = "school_name")
    private String schoolName;

    @Column(nullable = false)
    private String subject;

    @Column(name = "grade_level")
    private String gradeLevel;

    @Column(name = "salary_min")
    private int salaryMin;

    @Column(name = "salary_max")
    private int salaryMax;

    // FULLTIME / PARTTIME / SUBSTITUTE
    @Column(name = "job_type")
    private String jobType;

    // OPEN / CLOSED / FILLED
    private String status;

    private String city;

    private String description;

    @Column(name = "experience_required")
    private int experienceRequired;

    @Column(name = "applicant_count")
    private int applicantCount;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    // Constructor
    public Job() {}

    public Job(String schoolId, String schoolName, String subject,
               String gradeLevel, int salaryMin, int salaryMax,
               String jobType, String city, String description,
               int experienceRequired) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.subject = subject;
        this.gradeLevel = gradeLevel;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.jobType = jobType;
        this.city = city;
        this.description = description;
        this.experienceRequired = experienceRequired;
        this.status = "OPEN";
        this.applicantCount = 0;
        this.postedAt = LocalDateTime.now();
        this.expiryDate = LocalDateTime.now().plusDays(30);
    }

    // Getters
    public String getId() { return id; }
    public String getSchoolId() { return schoolId; }
    public String getSchoolName() { return schoolName; }
    public String getSubject() { return subject; }
    public String getGradeLevel() { return gradeLevel; }
    public int getSalaryMin() { return salaryMin; }
    public int getSalaryMax() { return salaryMax; }
    public String getJobType() { return jobType; }
    public String getStatus() { return status; }
    public String getCity() { return city; }
    public String getDescription() { return description; }
    public int getExperienceRequired() { return experienceRequired; }
    public int getApplicantCount() { return applicantCount; }
    public LocalDateTime getPostedAt() { return postedAt; }
    public LocalDateTime getExpiryDate() { return expiryDate; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }
    public void setSalaryMin(int salaryMin) { this.salaryMin = salaryMin; }
    public void setSalaryMax(int salaryMax) { this.salaryMax = salaryMax; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    public void setStatus(String status) { this.status = status; }
    public void setCity(String city) { this.city = city; }
    public void setDescription(String description) { this.description = description; }
    public void setExperienceRequired(int e) { this.experienceRequired = e; }
    public void setApplicantCount(int applicantCount) { this.applicantCount = applicantCount; }
    public void setPostedAt(LocalDateTime postedAt) { this.postedAt = postedAt; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
}