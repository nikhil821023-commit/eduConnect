package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository
        extends JpaRepository<Review, String> {

    List<Review> findByTargetId(String targetId);

    List<Review> findByTargetIdAndTargetType(
            String targetId, String targetType
    );

    // Has this reviewer already reviewed this target?
    boolean existsByReviewerIdAndTargetId(
            String reviewerId, String targetId
    );
}