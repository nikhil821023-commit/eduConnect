package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {

    // Find school by email
    Optional<School> findByEmail(String email);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find all schools in a city
    List<School> findByCity(String city);

    // Find by city and board
    List<School> findByCityAndBoard(String city, String board);
}