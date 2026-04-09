package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.models.*;
import com.nikhil.educonnect.repositories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Review Service Tests")
class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private TeacherRepository teacherRepository;
    @Mock private SchoolRepository schoolRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Teacher mockTeacher;
    private ReviewRequest request;

    @BeforeEach
    void setUp() {
        mockTeacher = new Teacher(
                "Nikhil Sharma", "nikhil@gmail.com",
                "pass", "Mathematics", 5, "Pune"
        );
        mockTeacher.setId("T001");
        mockTeacher.setRating(0.0);

        request = new ReviewRequest();
        request.setReviewerId("P001");
        request.setReviewerName("Rajesh Kumar");
        request.setTargetId("T001");
        request.setTargetType("TEACHER");
        request.setRating(5);
        request.setComment("Excellent teacher!");
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Create review successfully")
    void createReview_Success() {

        when(reviewRepository
                .existsByReviewerIdAndTargetId("P001", "T001"))
                .thenReturn(false);
        when(teacherRepository.findById("T001"))
                .thenReturn(Optional.of(mockTeacher));

        Review savedReview = new Review(
                "P001", "Rajesh Kumar", "T001",
                "TEACHER", "Nikhil Sharma", 5,
                "Excellent teacher!"
        );
        savedReview.setId("R001");

        when(reviewRepository.save(any()))
                .thenReturn(savedReview);
        when(reviewRepository
                .findByTargetIdAndTargetType("T001", "TEACHER"))
                .thenReturn(List.of(savedReview));
        when(teacherRepository.save(any()))
                .thenReturn(mockTeacher);

        ReviewResponse response =
                reviewService.createReview(request);

        assertNotNull(response);
        assertEquals(5, response.getRating());
        assertEquals("Excellent teacher!",
                response.getComment());
        assertEquals("Rajesh Kumar",
                response.getReviewerName());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Create review fails — already reviewed")
    void createReview_AlreadyReviewed() {

        when(reviewRepository
                .existsByReviewerIdAndTargetId("P001", "T001"))
                .thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> reviewService.createReview(request)
        );

        verify(reviewRepository, never()).save(any());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Create review fails — rating below 1")
    void createReview_InvalidRatingLow() {

        request.setRating(0);

        assertThrows(
                RuntimeException.class,
                () -> reviewService.createReview(request)
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Create review fails — rating above 5")
    void createReview_InvalidRatingHigh() {

        request.setRating(6);

        assertThrows(
                RuntimeException.class,
                () -> reviewService.createReview(request)
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Rating recalculated correctly after review")
    void createReview_RatingRecalculated() {

        // Teacher has 2 reviews — avg should be 4.5
        Review review1 = new Review(
                "P001", "Parent1", "T001", "TEACHER",
                "Nikhil", 4, "Good"
        );
        Review review2 = new Review(
                "P002", "Parent2", "T001", "TEACHER",
                "Nikhil", 5, "Excellent"
        );

        when(reviewRepository
                .existsByReviewerIdAndTargetId(anyString(), anyString()))
                .thenReturn(false);
        when(teacherRepository.findById("T001"))
                .thenReturn(Optional.of(mockTeacher));
        when(reviewRepository.save(any()))
                .thenReturn(review2);
        when(reviewRepository
                .findByTargetIdAndTargetType("T001", "TEACHER"))
                .thenReturn(List.of(review1, review2));
        when(teacherRepository.findById("T001"))
                .thenReturn(Optional.of(mockTeacher));
        when(teacherRepository.save(any()))
                .thenReturn(mockTeacher);

        reviewService.createReview(request);

        // Verify teacher rating was updated
        verify(teacherRepository, atLeastOnce())
                .save(any(Teacher.class));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Get all reviews for teacher")
    void getTeacherReviews_ReturnsList() {

        Review r1 = new Review("P001", "Parent1",
                "T001", "TEACHER", "Nikhil", 5, "Great");
        Review r2 = new Review("P002", "Parent2",
                "T001", "TEACHER", "Nikhil", 4, "Good");

        when(reviewRepository
                .findByTargetIdAndTargetType("T001", "TEACHER"))
                .thenReturn(List.of(r1, r2));

        List<ReviewResponse> reviews =
                reviewService.getTeacherReviews("T001");

        assertEquals(2, reviews.size());
        assertEquals(5, reviews.get(0).getRating());
        assertEquals(4, reviews.get(1).getRating());
    }
}