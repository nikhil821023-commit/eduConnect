package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {

    // All jobs by a specific school
    List<Job> findBySchoolId(String schoolId);

    // All open jobs in a city
    List<Job> findByCityAndStatus(String city, String status);

    // All open jobs for a subject
    List<Job> findBySubjectAndStatus(String subject, String status);

    // All open jobs in a city for a subject
    List<Job> findByCityAndSubjectAndStatus(
            String city, String subject, String status
    );

    // All currently open jobs
    List<Job> findByStatus(String status);
}