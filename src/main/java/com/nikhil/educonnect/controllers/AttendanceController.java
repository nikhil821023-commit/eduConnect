package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // POST /api/attendance/mark
    @PostMapping("/mark")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>>
    markAttendance(
            @RequestBody AttendanceMarkRequest request) {

        List<AttendanceResponse> records =
                attendanceService.markAttendance(request);
        return ResponseEntity.ok(
                ApiResponse.success(records,
                        "Attendance marked for " +
                                records.size() + " students")
        );
    }

    // GET /api/attendance/student/{studentId}
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>>
    getStudentAttendance(
            @PathVariable String studentId) {

        List<AttendanceResponse> records =
                attendanceService.getStudentAttendance(studentId);
        return ResponseEntity.ok(
                ApiResponse.success(records,
                        "Found " + records.size() + " records")
        );
    }

    // GET /api/attendance/summary/{studentId}?month=4&year=2026
    @GetMapping("/summary/{studentId}")
    public ResponseEntity<ApiResponse<AttendanceSummary>>
    getMonthlySummary(
            @PathVariable String studentId,
            @RequestParam int month,
            @RequestParam int year) {

        AttendanceSummary summary =
                attendanceService.getMonthlySummary(
                        studentId, month, year
                );
        return ResponseEntity.ok(
                ApiResponse.success(summary,
                        "Attendance summary for month " + month)
        );
    }
}