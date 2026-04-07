package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.Earning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EarningRepository
        extends JpaRepository<Earning, String> {

    List<Earning> findByTeacherId(String teacherId);

    List<Earning> findByTeacherIdAndMonthAndYear(
            String teacherId, int month, int year
    );

    List<Earning> findByTeacherIdAndStatus(
            String teacherId, String status
    );

    List<Earning> findByStudentId(String studentId);
}