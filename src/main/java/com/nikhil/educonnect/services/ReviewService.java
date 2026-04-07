package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.ReviewRequest;
import com.nikhil.educonnect.dto.ReviewResponse;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.models.Review;
import com.nikhil.educonnect.models.Teacher;
import com.nikhil.educonnect.models.School;
import com.nikhil.educonnect.repositories.ReviewRepository;
import com.nikhil.educonnect.repositories.TeacherRepository;
import com.nikhil.educonnect.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    // ─── CREATE REVIEW ────────────────────────────────────
    public ReviewResponse createReview(ReviewRequest request) {

        // Validate rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException(
                    "Rating must be between 1 and 5"
            );
        }

        // Check already reviewed
        if (reviewRepository.existsByReviewerIdAndTargetId(
                request.getReviewerId(), request.getTargetId())) {
            throw new RuntimeException(
                    "You have already reviewed this " +
                            request.getTargetType().toLowerCase()
            );
        }

        String targetName = "";
        String targetType = request.getTargetType().toUpperCase();

        // Verify target exists and get name
        if (targetType.equals("TEACHER")) {
            Teacher teacher = teacherRepository
                    .findById(request.getTargetId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Teacher", request.getTargetId()
                            )
                    );
            targetName = teacher.getName();

        } else if (targetType.equals("SCHOOL")) {
            School school = schoolRepository
                    .findById(request.getTargetId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "School", request.getTargetId()
                            )
                    );
            targetName = school.getSchoolName();
        } else {
            throw new RuntimeException(
                    "Invalid target type. Use: TEACHER or SCHOOL"
            );
        }

        // Save review
        Review review = new Review(
                request.getReviewerId(),
                request.getReviewerName(),
                request.getTargetId(),
                targetType,
                targetName,
                request.getRating(),
                request.getComment()
        );

        Review saved = reviewRepository.save(review);

        // Recalculate average rating
        updateRating(request.getTargetId(), targetType);

        return toResponse(saved);
    }

    // ─── GET REVIEWS FOR TEACHER ──────────────────────────
    public List<ReviewResponse> getTeacherReviews(String teacherId) {
        return reviewRepository
                .findByTargetIdAndTargetType(teacherId, "TEACHER")
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── GET REVIEWS FOR SCHOOL ───────────────────────────
    public List<ReviewResponse> getSchoolReviews(String schoolId) {
        return reviewRepository
                .findByTargetIdAndTargetType(schoolId, "SCHOOL")
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── RECALCULATE AVERAGE RATING ───────────────────────
    private void updateRating(String targetId, String targetType) {

        List<Review> reviews = reviewRepository
                .findByTargetIdAndTargetType(targetId, targetType);

        double avg = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        // Round to 1 decimal place
        double rounded = Math.round(avg * 10.0) / 10.0;

        if (targetType.equals("TEACHER")) {
            teacherRepository.findById(targetId).ifPresent(t -> {
                t.setRating(rounded);
                teacherRepository.save(t);
            });
        } else if (targetType.equals("SCHOOL")) {
            schoolRepository.findById(targetId).ifPresent(s -> {
                s.setRating(rounded);
                schoolRepository.save(s);
            });
        }
    }

    // ─── CONVERTER ────────────────────────────────────────
    private ReviewResponse toResponse(Review review) {
        ReviewResponse r = new ReviewResponse();
        r.setId(review.getId());
        r.setReviewerId(review.getReviewerId());
        r.setReviewerName(review.getReviewerName());
        r.setTargetId(review.getTargetId());
        r.setTargetType(review.getTargetType());
        r.setTargetName(review.getTargetName());
        r.setRating(review.getRating());
        r.setComment(review.getComment());
        if (review.getCreatedAt() != null)
            r.setCreatedAt(review.getCreatedAt().toString());
        return r;
    }
}