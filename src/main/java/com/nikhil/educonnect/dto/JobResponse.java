package com.nikhil.educonnect.dto;

public class JobResponse {

    private String id;
    private String schoolId;
    private String schoolName;
    private String subject;
    private String gradeLevel;
    private int salaryMin;
    private int salaryMax;
    private String jobType;
    private String status;
    private String city;
    private String description;
    private int experienceRequired;
    private int applicantCount;
    private String postedAt;
    private String expiryDate;

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
    public String getPostedAt() { return postedAt; }
    public String getExpiryDate() { return expiryDate; }

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
    public void setApplicantCount(int a) { this.applicantCount = a; }
    public void setPostedAt(String postedAt) { this.postedAt = postedAt; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
}