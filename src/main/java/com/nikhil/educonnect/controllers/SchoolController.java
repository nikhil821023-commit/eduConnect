package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.ApiResponse;
import com.nikhil.educonnect.dto.SchoolRegisterRequest;
import com.nikhil.educonnect.dto.SchoolResponse;
import com.nikhil.educonnect.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    // POST /api/schools/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SchoolResponse>> register(
            @RequestBody SchoolRegisterRequest request) {

        SchoolResponse school = schoolService.register(request);
        return ResponseEntity.ok(
                ApiResponse.success(school, "School registered successfully")
        );
    }

    // GET /api/schools/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolResponse>> getById(
            @PathVariable String id) {

        SchoolResponse school = schoolService.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success(school, "School found")
        );
    }

    // GET /api/schools
    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolResponse>>> getAll() {

        List<SchoolResponse> schools = schoolService.getAll();
        return ResponseEntity.ok(
                ApiResponse.success(schools,
                        "Found " + schools.size() + " schools")
        );
    }

    // GET /api/schools/search?city=Pune
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SchoolResponse>>> search(
            @RequestParam String city) {

        List<SchoolResponse> schools = schoolService.getByCity(city);
        return ResponseEntity.ok(
                ApiResponse.success(schools,
                        "Found " + schools.size() + " schools in " + city)
        );
    }
}