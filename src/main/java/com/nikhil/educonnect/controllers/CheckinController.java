package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.services.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    // POST /api/checkin/arrive
    @PostMapping("/arrive")
    public ResponseEntity<ApiResponse<CheckinResponse>> arrive(
            @RequestBody CheckinRequest request) {

        CheckinResponse checkin = checkinService.arrive(request);
        return ResponseEntity.ok(
                ApiResponse.success(checkin,
                        "Arrival recorded. Parent notified ✅")
        );
    }

    // POST /api/checkin/leave
    @PostMapping("/leave")
    public ResponseEntity<ApiResponse<CheckinResponse>> leave(
            @RequestBody CheckinRequest request) {

        CheckinResponse checkin = checkinService.leave(request);
        return ResponseEntity.ok(
                ApiResponse.success(checkin,
                        "Departure recorded. Parent notified 🏠")
        );
    }

    // GET /api/checkin/today/{studentId}
    @GetMapping("/today/{studentId}")
    public ResponseEntity<ApiResponse<List<CheckinResponse>>>
    getToday(@PathVariable String studentId) {

        List<CheckinResponse> checkins =
                checkinService.getTodayCheckins(studentId);
        return ResponseEntity.ok(
                ApiResponse.success(checkins,
                        "Today's journey — " +
                                checkins.size() + " events")
        );
    }

    // GET /api/checkin/history/{studentId}
    @GetMapping("/history/{studentId}")
    public ResponseEntity<ApiResponse<List<CheckinResponse>>>
    getHistory(@PathVariable String studentId) {

        List<CheckinResponse> checkins =
                checkinService.getHistory(studentId);
        return ResponseEntity.ok(
                ApiResponse.success(checkins,
                        "Found " + checkins.size() +
                                " checkin records")
        );
    }

    // GET /api/checkin/status/{studentId}
    @GetMapping("/status/{studentId}")
    public ResponseEntity<ApiResponse<LocationStatus>>
    getStatus(@PathVariable String studentId) {

        LocationStatus status =
                checkinService.getCurrentStatus(studentId);
        return ResponseEntity.ok(
                ApiResponse.success(status,
                        "Current status: " +
                                status.getCurrentStatus())
        );
    }
}