package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // POST /api/jobs/create
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<JobResponse>> createJob(
            @RequestBody JobCreateRequest request) {

        JobResponse job = jobService.createJob(request);
        return ResponseEntity.ok(
                ApiResponse.success(job, "Job posted successfully")
        );
    }

    // GET /api/jobs
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobResponse>>> getAll() {

        List<JobResponse> jobs = jobService.getAll();
        return ResponseEntity.ok(
                ApiResponse.success(jobs,
                        "Found " + jobs.size() + " jobs")
        );
    }

    // GET /api/jobs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobResponse>> getById(
            @PathVariable String id) {

        JobResponse job = jobService.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success(job, "Job found")
        );
    }

    // GET /api/jobs/search?city=Pune&subject=Maths
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<JobResponse>>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String subject) {

        List<JobResponse> jobs = jobService.search(city, subject);
        return ResponseEntity.ok(
                ApiResponse.success(jobs,
                        "Found " + jobs.size() + " open jobs")
        );
    }

    // GET /api/jobs/school/{schoolId}
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<ApiResponse<List<JobResponse>>> getBySchool(
            @PathVariable String schoolId) {

        List<JobResponse> jobs = jobService.getBySchool(schoolId);
        return ResponseEntity.ok(
                ApiResponse.success(jobs,
                        "Found " + jobs.size() + " jobs by school")
        );
    }

    // POST /api/jobs/{jobId}/apply
    @PostMapping("/{jobId}/apply")
    public ResponseEntity<ApiResponse<ApplicationResponse>> apply(
            @PathVariable String jobId,
            @RequestBody ApplicationRequest request) {

        ApplicationResponse application =
                jobService.applyForJob(jobId, request);
        return ResponseEntity.ok(
                ApiResponse.success(application,
                        "Application submitted successfully")
        );
    }

    // GET /api/jobs/{jobId}/applications
    @GetMapping("/{jobId}/applications")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>>
    getApplications(@PathVariable String jobId) {

        List<ApplicationResponse> applications =
                jobService.getApplications(jobId);
        return ResponseEntity.ok(
                ApiResponse.success(applications,
                        "Found " + applications.size() + " applications")
        );
    }

    // GET /api/jobs/applications/teacher/{teacherId}
    @GetMapping("/applications/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>>
    getByTeacher(@PathVariable String teacherId) {

        List<ApplicationResponse> applications =
                jobService.getByTeacher(teacherId);
        return ResponseEntity.ok(
                ApiResponse.success(applications,
                        "Found " + applications.size() + " applications")
        );
    }

    // PATCH /api/jobs/applications/{applicationId}/status
    @PatchMapping("/applications/{applicationId}/status")
    public ResponseEntity<ApiResponse<ApplicationResponse>> updateStatus(
            @PathVariable String applicationId,
            @RequestBody StatusUpdateRequest request) {

        ApplicationResponse application =
                jobService.updateStatus(applicationId, request);
        return ResponseEntity.ok(
                ApiResponse.success(application,
                        "Application status updated to: " + request.getStatus())
        );
    }
}