package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.services.EarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/earnings")
public class EarningController {

    @Autowired
    private EarningService earningService;

    // POST /api/earnings/add
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<EarningResponse>> add(
            @RequestBody EarningRequest request) {

        EarningResponse earning = earningService.addEarning(request);
        return ResponseEntity.ok(
                ApiResponse.success(earning,
                        "Earning record added successfully")
        );
    }

    // GET /api/earnings/teacher/{teacherId}
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<EarningResponse>>>
    getTeacherEarnings(
            @PathVariable String teacherId) {

        List<EarningResponse> earnings =
                earningService.getTeacherEarnings(teacherId);
        return ResponseEntity.ok(
                ApiResponse.success(earnings,
                        "Found " + earnings.size() + " records")
        );
    }

    // GET /api/earnings/summary/{teacherId}?month=4&year=2026
    @GetMapping("/summary/{teacherId}")
    public ResponseEntity<ApiResponse<EarningsSummary>>
    getMonthlySummary(
            @PathVariable String teacherId,
            @RequestParam int month,
            @RequestParam int year) {

        EarningsSummary summary =
                earningService.getMonthlySummary(teacherId, month, year);
        return ResponseEntity.ok(
                ApiResponse.success(summary,
                        "Earnings summary for month " + month)
        );
    }

    // PATCH /api/earnings/{earningId}/pay
    @PatchMapping("/{earningId}/pay")
    public ResponseEntity<ApiResponse<EarningResponse>> markAsPaid(
            @PathVariable String earningId,
            @RequestBody PaymentRequest request) {

        EarningResponse earning =
                earningService.markAsPaid(earningId, request);
        return ResponseEntity.ok(
                ApiResponse.success(earning, "Payment recorded ✅")
        );
    }
}