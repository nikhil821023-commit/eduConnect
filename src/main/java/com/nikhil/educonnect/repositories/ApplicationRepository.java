package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    // All applications for a job — school uses this
    List<Application> findByJobId(String jobId);

    // All applications by a teacher
    List<Application> findByTeacherId(String teacherId);

    // Check if teacher already applied to this job
    boolean existsByJobIdAndTeacherId(String jobId, String teacherId);

    // Applications by status for a job
    List<Application> findByJobIdAndStatus(String jobId, String status);
}