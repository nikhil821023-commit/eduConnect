package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.models.*;
import com.nikhil.educonnect.repositories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Attendance Service Tests")
class AttendanceServiceTest {

    @Mock private AttendanceRepository attendanceRepository;
    @Mock private TeacherRepository teacherRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    private Teacher mockTeacher;

    @BeforeEach
    void setUp() {
        mockTeacher = new Teacher(
                "Nikhil Sharma", "nikhil@gmail.com",
                "pass", "Mathematics", 5, "Pune"
        );
        mockTeacher.setId("T001");
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Mark attendance for multiple students")
    void markAttendance_Success() {

        AttendanceMarkRequest request =
                new AttendanceMarkRequest();
        request.setTeacherId("T001");
        request.setSubject("Mathematics");
        request.setDate("2026-04-08");
        request.setClassTime("17:00");

        AttendanceMarkRequest.StudentAttendance s1 =
                new AttendanceMarkRequest.StudentAttendance();
        s1.setStudentId("ST001");
        s1.setStudentName("Aryan");
        s1.setStatus("PRESENT");

        AttendanceMarkRequest.StudentAttendance s2 =
                new AttendanceMarkRequest.StudentAttendance();
        s2.setStudentId("ST002");
        s2.setStudentName("Priya");
        s2.setStatus("ABSENT");

        request.setStudents(List.of(s1, s2));

        when(teacherRepository.findById("T001"))
                .thenReturn(java.util.Optional.of(mockTeacher));

        Attendance a1 = new Attendance(
                "ST001", "Aryan", "T001", "Nikhil",
                "Mathematics", LocalDate.of(2026, 4, 8),
                java.time.LocalTime.of(17, 0), "PRESENT", ""
        );
        Attendance a2 = new Attendance(
                "ST002", "Priya", "T001", "Nikhil",
                "Mathematics", LocalDate.of(2026, 4, 8),
                java.time.LocalTime.of(17, 0), "ABSENT", ""
        );

        when(attendanceRepository.save(any()))
                .thenReturn(a1)
                .thenReturn(a2);

        List<AttendanceResponse> responses =
                attendanceService.markAttendance(request);

        assertEquals(2, responses.size());
        verify(attendanceRepository, times(2))
                .save(any(Attendance.class));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Monthly summary calculates correctly")
    void getMonthlySummary_Correct() {

        Attendance present = new Attendance(
                "ST001", "Aryan", "T001", "Nikhil",
                "Maths", LocalDate.of(2026, 4, 1),
                java.time.LocalTime.of(17, 0),
                "PRESENT", ""
        );
        Attendance absent = new Attendance(
                "ST001", "Aryan", "T001", "Nikhil",
                "Maths", LocalDate.of(2026, 4, 2),
                java.time.LocalTime.of(17, 0),
                "ABSENT", ""
        );

        when(attendanceRepository
                .findByStudentIdAndDateBetween(
                        eq("ST001"),
                        any(LocalDate.class),
                        any(LocalDate.class)
                ))
                .thenReturn(List.of(present, absent));

        AttendanceSummary summary =
                attendanceService.getMonthlySummary(
                        "ST001", 4, 2026
                );

        assertEquals(2, summary.getTotalClasses());
        assertEquals(1, summary.getPresentCount());
        assertEquals(1, summary.getAbsentCount());
        assertEquals(50.0,
                summary.getAttendancePercentage());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Empty month returns zero summary")
    void getMonthlySummary_Empty() {

        when(attendanceRepository
                .findByStudentIdAndDateBetween(
                        anyString(),
                        any(LocalDate.class),
                        any(LocalDate.class)
                ))
                .thenReturn(List.of());

        AttendanceSummary summary =
                attendanceService.getMonthlySummary(
                        "ST001", 4, 2026
                );

        assertEquals(0, summary.getTotalClasses());
        assertEquals(0.0,
                summary.getAttendancePercentage());
    }
}