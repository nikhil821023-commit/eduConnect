package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.TeacherRegisterRequest;
import com.nikhil.educonnect.dto.TeacherResponse;
import com.nikhil.educonnect.exceptions.UserAlreadyExistsException;
import com.nikhil.educonnect.models.Teacher;
import com.nikhil.educonnect.repositories.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Teacher Service Tests")
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher mockTeacher;
    private TeacherRegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        // Create a fake teacher for testing
        mockTeacher = new Teacher(
                "Nikhil Sharma",
                "nikhil@gmail.com",
                "hashedpassword",
                "Mathematics",
                5,
                "Pune"
        );
        mockTeacher.setId("T001");

        // Create a fake registration request
        registerRequest = new TeacherRegisterRequest();
        registerRequest.setName("Nikhil Sharma");
        registerRequest.setEmail("nikhil@gmail.com");
        registerRequest.setPassword("pass123");
        registerRequest.setSubject("Mathematics");
        registerRequest.setExperienceYears(5);
        registerRequest.setCity("Pune");
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Register teacher successfully")
    void registerTeacher_Success() {

        // ARRANGE — setup fake behaviour
        when(teacherRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(teacherRepository.save(any(Teacher.class)))
                .thenReturn(mockTeacher);

        // ACT — call the method
        TeacherResponse response =
                teacherService.register(registerRequest);

        // ASSERT — check the result
        assertNotNull(response);
        assertEquals("Nikhil Sharma", response.getName());
        assertEquals("nikhil@gmail.com", response.getEmail());
        assertEquals("Mathematics", response.getSubject());
        assertEquals("Pune", response.getCity());
        assertEquals(5, response.getExperienceYears());

        // Verify save was called exactly once
        verify(teacherRepository, times(1))
                .save(any(Teacher.class));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Register fails — email already exists")
    void registerTeacher_EmailAlreadyExists() {

        // ARRANGE — email is already taken
        when(teacherRepository.existsByEmail(anyString()))
                .thenReturn(true);

        // ACT + ASSERT — expect exception
        assertThrows(
                UserAlreadyExistsException.class,
                () -> teacherService.register(registerRequest)
        );

        // Verify save was NEVER called
        verify(teacherRepository, never())
                .save(any(Teacher.class));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Get teacher by ID successfully")
    void getTeacherById_Success() {

        when(teacherRepository.findById("T001"))
                .thenReturn(Optional.of(mockTeacher));

        TeacherResponse response =
                teacherService.getById("T001");

        assertNotNull(response);
        assertEquals("T001", response.getId());
        assertEquals("Nikhil Sharma", response.getName());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Get teacher by ID — not found")
    void getTeacherById_NotFound() {

        when(teacherRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> teacherService.getById("INVALID_ID")
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Get all teachers returns list")
    void getAllTeachers_ReturnsList() {

        Teacher teacher2 = new Teacher(
                "Ravi Kumar", "ravi@gmail.com",
                "pass", "Science", 3, "Pune"
        );

        when(teacherRepository.findAll())
                .thenReturn(List.of(mockTeacher, teacher2));

        List<TeacherResponse> result =
                teacherService.getAll();

        assertEquals(2, result.size());
        assertEquals("Nikhil Sharma", result.get(0).getName());
        assertEquals("Ravi Kumar", result.get(1).getName());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Search teachers by city and subject")
    void searchTeachers_ByCityAndSubject() {

        when(teacherRepository
                .findByCityAndSubjectAndIsAvailableTrue(
                        "Pune", "Mathematics"
                ))
                .thenReturn(List.of(mockTeacher));

        List<TeacherResponse> result =
                teacherService.search("Pune", "Mathematics");

        assertEquals(1, result.size());
        assertEquals("Pune", result.get(0).getCity());
        assertEquals("Mathematics",
                result.get(0).getSubject());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Search returns empty list when no match")
    void searchTeachers_NoResults() {

        when(teacherRepository
                .findByCityAndSubjectAndIsAvailableTrue(
                        "Mumbai", "Arts"
                ))
                .thenReturn(List.of());

        List<TeacherResponse> result =
                teacherService.search("Mumbai", "Arts");

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}