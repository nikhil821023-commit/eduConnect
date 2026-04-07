package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

    // Find teacher by email — used during registration check
    Optional<Teacher> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Find all teachers in a city
    List<Teacher> findByCity(String city);

    // Find by city and subject — parent search
    List<Teacher> findByCityAndSubject(String city, String subject);

    // Find available teachers only
    List<Teacher> findByIsAvailableTrue();

    // Find by city, subject and available
    List<Teacher> findByCityAndSubjectAndIsAvailableTrue(
            String city, String subject
    );
}