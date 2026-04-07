package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.models.Application;
import com.nikhil.educonnect.models.Job;
import com.nikhil.educonnect.models.Teacher;
import com.nikhil.educonnect.repositories.ApplicationRepository;
import com.nikhil.educonnect.repositories.JobRepository;
import com.nikhil.educonnect.repositories.SchoolRepository;
import com.nikhil.educonnect.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    // ─── CREATE JOB ───────────────────────────────────────
    public JobResponse createJob(JobCreateRequest request) {

        // Verify school exists
        var school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("School", request.getSchoolId())
                );

        Job job = new Job(
                request.getSchoolId(),
                school.getSchoolName(),
                request.getSubject(),
                request.getGradeLevel(),
                request.getSalaryMin(),
                request.getSalaryMax(),
                request.getJobType(),
                request.getCity(),
                request.getDescription(),
                request.getExperienceRequired()
        );

        Job saved = jobRepository.save(job);
        return toJobResponse(saved);
    }

    // ─── GET JOB BY ID ────────────────────────────────────
    public JobResponse getById(String id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job", id)
                );
        return toJobResponse(job);
    }

    // ─── GET ALL JOBS ─────────────────────────────────────
    public List<JobResponse> getAll() {
        return jobRepository.findAll()
                .stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
    }

    // ─── SEARCH JOBS ──────────────────────────────────────
    public List<JobResponse> search(String city, String subject) {

        List<Job> results;

        if (city != null && subject != null) {
            results = jobRepository
                    .findByCityAndSubjectAndStatus(city, subject, "OPEN");
        } else if (city != null) {
            results = jobRepository.findByCityAndStatus(city, "OPEN");
        } else if (subject != null) {
            results = jobRepository.findBySubjectAndStatus(subject, "OPEN");
        } else {
            results = jobRepository.findByStatus("OPEN");
        }

        return results.stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
    }

    // ─── GET JOBS BY SCHOOL ───────────────────────────────
    public List<JobResponse> getBySchool(String schoolId) {
        return jobRepository.findBySchoolId(schoolId)
                .stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
    }

    // ─── APPLY FOR JOB ────────────────────────────────────
    public ApplicationResponse applyForJob(
            String jobId, ApplicationRequest request) {

        // Check job exists and is open
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job", jobId)
                );

        if (!job.getStatus().equals("OPEN")) {
            throw new RuntimeException(
                    "This job is no longer accepting applications"
            );
        }

        // Check teacher exists
        Teacher teacher = teacherRepository
                .findById(request.getTeacherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher", request.getTeacherId()
                        )
                );

        // Check teacher hasn't already applied
        if (applicationRepository.existsByJobIdAndTeacherId(
                jobId, request.getTeacherId())) {
            throw new RuntimeException(
                    "You have already applied for this job"
            );
        }

        // Create application
        Application application = new Application(
                jobId,
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getSubject(),
                teacher.getExperienceYears(),
                teacher.getRating(),
                request.getCoverNote()
        );

        Application saved = applicationRepository.save(application);

        // Increment applicant count on job
        job.setApplicantCount(job.getApplicantCount() + 1);
        jobRepository.save(job);

        return toApplicationResponse(saved);
    }

    // ─── GET ALL APPLICATIONS FOR A JOB ──────────────────
    public List<ApplicationResponse> getApplications(String jobId) {

        // Verify job exists
        jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job", jobId)
                );

        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(this::toApplicationResponse)
                .collect(Collectors.toList());
    }

    // ─── GET APPLICATIONS BY TEACHER ─────────────────────
    public List<ApplicationResponse> getByTeacher(String teacherId) {
        return applicationRepository.findByTeacherId(teacherId)
                .stream()
                .map(this::toApplicationResponse)
                .collect(Collectors.toList());
    }

    // ─── UPDATE APPLICATION STATUS ────────────────────────
    public ApplicationResponse updateStatus(
            String applicationId, StatusUpdateRequest request) {

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application", applicationId
                        )
                );

        // Validate status value
        String newStatus = request.getStatus().toUpperCase();
        if (!List.of("SHORTLISTED", "INTERVIEW",
                "HIRED", "REJECTED").contains(newStatus)) {
            throw new RuntimeException(
                    "Invalid status. Use: SHORTLISTED, INTERVIEW, HIRED, REJECTED"
            );
        }

        application.setStatus(newStatus);
        application.setUpdatedAt(LocalDateTime.now());

        // If hired → close the job
        if (newStatus.equals("HIRED")) {
            Job job = jobRepository
                    .findById(application.getJobId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Job", application.getJobId()
                            )
                    );
            job.setStatus("FILLED");
            jobRepository.save(job);
        }

        // If rejected → save reason
        if (newStatus.equals("REJECTED") &&
                request.getRejectionReason() != null) {
            application.setRejectionReason(
                    request.getRejectionReason()
            );
        }

        Application updated = applicationRepository.save(application);
        return toApplicationResponse(updated);
    }

    // ─── CONVERTERS ───────────────────────────────────────
    private JobResponse toJobResponse(Job job) {
        JobResponse r = new JobResponse();
        r.setId(job.getId());
        r.setSchoolId(job.getSchoolId());
        r.setSchoolName(job.getSchoolName());
        r.setSubject(job.getSubject());
        r.setGradeLevel(job.getGradeLevel());
        r.setSalaryMin(job.getSalaryMin());
        r.setSalaryMax(job.getSalaryMax());
        r.setJobType(job.getJobType());
        r.setStatus(job.getStatus());
        r.setCity(job.getCity());
        r.setDescription(job.getDescription());
        r.setExperienceRequired(job.getExperienceRequired());
        r.setApplicantCount(job.getApplicantCount());
        if (job.getPostedAt() != null)
            r.setPostedAt(job.getPostedAt().toString());
        if (job.getExpiryDate() != null)
            r.setExpiryDate(job.getExpiryDate().toString());
        return r;
    }

    private ApplicationResponse toApplicationResponse(Application a) {
        ApplicationResponse r = new ApplicationResponse();
        r.setId(a.getId());
        r.setJobId(a.getJobId());
        r.setTeacherId(a.getTeacherId());
        r.setTeacherName(a.getTeacherName());
        r.setTeacherEmail(a.getTeacherEmail());
        r.setTeacherSubject(a.getTeacherSubject());
        r.setTeacherExperience(a.getTeacherExperience());
        r.setTeacherRating(a.getTeacherRating());
        r.setStatus(a.getStatus());
        r.setCoverNote(a.getCoverNote());
        r.setRejectionReason(a.getRejectionReason());
        if (a.getAppliedAt() != null)
            r.setAppliedAt(a.getAppliedAt().toString());
        if (a.getUpdatedAt() != null)
            r.setUpdatedAt(a.getUpdatedAt().toString());
        return r;
    }
}