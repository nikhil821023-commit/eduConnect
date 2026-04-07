package com.nikhil.educonnect.dto;

public class JobCreateRequest {

    private String schoolId;
    private String subject;
    private String gradeLevel;
    private int salaryMin;
    private int salaryMax;
    private String jobType;
    private String city;
    private String description;
    private int experienceRequired;

    // Getters
    public String getSchoolId() { return schoolId; }
    public String getSubject() { return subject; }
    public String getGradeLevel() { return gradeLevel; }
    public int getSalaryMin() { return salaryMin; }
    public int getSalaryMax() { return salaryMax; }
    public String getJobType() { return jobType; }
    public String getCity() { return city; }
    public String getDescription() { return description; }
    public int getExperienceRequired() { return experienceRequired; }

    // Setters
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }
    public void setSalaryMin(int salaryMin) { this.salaryMin = salaryMin; }
    public void setSalaryMax(int salaryMax) { this.salaryMax = salaryMax; }
    public void setJobType(String jobType) { this.jobType = jobType; }
    public void setCity(String city) { this.city = city; }
    public void setDescription(String description) { this.description = description; }
    public void setExperienceRequired(int e) { this.experienceRequired = e; }
}