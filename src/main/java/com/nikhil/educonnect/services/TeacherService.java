package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.TeacherRegisterRequest;
import com.nikhil.educonnect.dto.TeacherResponse;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.exceptions.UserAlreadyExistsException;
import com.nikhil.educonnect.models.Teacher;
import com.nikhil.educonnect.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // Register new teacher
    public TeacherResponse register(TeacherRegisterRequest request) {

        // Check if email already taken
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        // Create teacher object
        Teacher teacher = new Teacher(
                request.getName(),
                request.getEmail(),
                request.getPassword(), // plain text for now — Phase 5 adds encryption
                request.getSubject(),
                request.getExperienceYears(),
                request.getCity()
        );

        if (request.getBio() != null) {
            teacher.setBio(request.getBio());
        }

        // Save to database
        Teacher saved = teacherRepository.save(teacher);

        return toResponse(saved);
    }

    // Get teacher by ID
    public TeacherResponse getById(String id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher", id)
                );
        return toResponse(teacher);
    }

    // Get all teachers
    public List<TeacherResponse> getAll() {
        return teacherRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Search by city and/or subject
    public List<TeacherResponse> search(String city, String subject) {

        List<Teacher> results;

        if (city != null && subject != null) {
            results = teacherRepository
                    .findByCityAndSubjectAndIsAvailableTrue(city, subject);
        } else if (city != null) {
            results = teacherRepository.findByCity(city);
        } else if (subject != null) {
            results = teacherRepository.findAll()
                    .stream()
                    .filter(t -> subject.equalsIgnoreCase(t.getSubject()))
                    .collect(Collectors.toList());
        } else {
            results = teacherRepository.findAll();
        }

        return results.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Convert Teacher entity → TeacherResponse DTO
    // We never send password back to frontend
    private TeacherResponse toResponse(Teacher teacher) {
        TeacherResponse response = new TeacherResponse();
        response.setId(teacher.getId());
        response.setName(teacher.getName());
        response.setEmail(teacher.getEmail());
        response.setSubject(teacher.getSubject());
        response.setExperienceYears(teacher.getExperienceYears());
        response.setCity(teacher.getCity());
        response.setRating(teacher.getRating());
        response.setAvailable(teacher.isAvailable());
        response.setBio(teacher.getBio());
        if (teacher.getCreatedAt() != null) {
            response.setCreatedAt(teacher.getCreatedAt().toString());
        }
        return response;
    }
}