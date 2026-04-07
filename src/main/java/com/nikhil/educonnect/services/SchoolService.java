package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.SchoolRegisterRequest;
import com.nikhil.educonnect.dto.SchoolResponse;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.exceptions.UserAlreadyExistsException;
import com.nikhil.educonnect.models.School;
import com.nikhil.educonnect.repositories.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    // Register new school
    public SchoolResponse register(SchoolRegisterRequest request) {

        if (schoolRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        School school = new School(
                request.getSchoolName(),
                request.getEmail(),
                request.getPassword(),
                request.getAddress(),
                request.getCity(),
                request.getBoard()
        );

        School saved = schoolRepository.save(school);
        return toResponse(saved);
    }

    // Get school by ID
    public SchoolResponse getById(String id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("School", id)
                );
        return toResponse(school);
    }

    // Get all schools
    public List<SchoolResponse> getAll() {
        return schoolRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get schools by city
    public List<SchoolResponse> getByCity(String city) {
        return schoolRepository.findByCity(city)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Convert School → SchoolResponse
    private SchoolResponse toResponse(School school) {
        SchoolResponse response = new SchoolResponse();
        response.setId(school.getId());
        response.setSchoolName(school.getSchoolName());
        response.setEmail(school.getEmail());
        response.setCity(school.getCity());
        response.setBoard(school.getBoard());
        response.setAddress(school.getAddress());
        response.setRating(school.getRating());
        response.setVerified(school.isVerified());
        if (school.getCreatedAt() != null) {
            response.setCreatedAt(school.getCreatedAt().toString());
        }
        return response;
    }
}