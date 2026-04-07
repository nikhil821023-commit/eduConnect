package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, String> {

    List<Attendance> findByStudentId(String studentId);

    List<Attendance> findByTeacherId(String teacherId);

    List<Attendance> findByStudentIdAndDateBetween(
            String studentId, LocalDate start, LocalDate end
    );

    List<Attendance> findByTeacherIdAndDate(
            String teacherId, LocalDate date
    );

    // Count by status for summary
    int countByStudentIdAndStatus(String studentId, String status);

    int countByStudentIdAndDateBetweenAndStatus(
            String studentId, LocalDate start,
            LocalDate end, String status
    );
}