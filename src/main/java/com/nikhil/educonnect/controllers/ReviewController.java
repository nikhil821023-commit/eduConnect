package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // POST /api/reviews/create
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReviewResponse>> create(
            @RequestBody ReviewRequest request) {

        ReviewResponse review = reviewService.createReview(request);
        return ResponseEntity.ok(
                ApiResponse.success(review, "Review submitted successfully")
        );
    }

    // GET /api/reviews/teacher/{teacherId}
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>>
    getTeacherReviews(@PathVariable String teacherId) {

        List<ReviewResponse> reviews =
                reviewService.getTeacherReviews(teacherId);
        return ResponseEntity.ok(
                ApiResponse.success(reviews,
                        "Found " + reviews.size() + " reviews")
        );
    }

    // GET /api/reviews/school/{schoolId}
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>>
    getSchoolReviews(@PathVariable String schoolId) {

        List<ReviewResponse> reviews =
                reviewService.getSchoolReviews(schoolId);
        return ResponseEntity.ok(
                ApiResponse.success(reviews,
                        "Found " + reviews.size() + " reviews")
        );
    }
}