package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.ApiResponse;
import com.nikhil.educonnect.dto.TeacherRegisterRequest;
import com.nikhil.educonnect.dto.TeacherResponse;
import com.nikhil.educonnect.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // POST /api/teachers/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<TeacherResponse>> register(
            @RequestBody TeacherRegisterRequest request) {

        TeacherResponse teacher = teacherService.register(request);
        return ResponseEntity.ok(
                ApiResponse.success(teacher, "Teacher registered successfully")
        );
    }

    // GET /api/teachers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponse>> getById(
            @PathVariable String id) {

        TeacherResponse teacher = teacherService.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success(teacher, "Teacher found")
        );
    }

    // GET /api/teachers
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherResponse>>> getAll() {

        List<TeacherResponse> teachers = teacherService.getAll();
        return ResponseEntity.ok(
                ApiResponse.success(teachers,
                        "Found " + teachers.size() + " teachers")
        );
    }

    // GET /api/teachers/search?city=Pune&subject=Maths
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TeacherResponse>>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String subject) {

        List<TeacherResponse> teachers = teacherService.search(city, subject);
        return ResponseEntity.ok(
                ApiResponse.success(teachers,
                        "Found " + teachers.size() + " teachers")
        );
    }
}