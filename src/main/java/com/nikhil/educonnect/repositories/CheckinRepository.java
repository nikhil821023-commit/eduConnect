package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckinRepository
        extends JpaRepository<Checkin, String> {

    List<Checkin> findByStudentIdOrderByTimestampDesc(
            String studentId
    );

    List<Checkin> findByStudentIdAndTimestampBetween(
            String studentId,
            LocalDateTime start,
            LocalDateTime end
    );

    // Latest checkin for a student
    Optional<Checkin> findFirstByStudentIdOrderByTimestampDesc(
            String studentId
    );
}