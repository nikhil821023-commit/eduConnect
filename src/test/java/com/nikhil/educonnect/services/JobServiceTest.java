package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.models.*;
import com.nikhil.educonnect.repositories.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Job Service Tests")
class JobServiceTest {

    @Mock private JobRepository jobRepository;
    @Mock private ApplicationRepository applicationRepository;
    @Mock private TeacherRepository teacherRepository;
    @Mock private SchoolRepository schoolRepository;

    @InjectMocks
    private JobService jobService;

    private Job mockJob;
    private Teacher mockTeacher;
    private School mockSchool;
    private JobCreateRequest createRequest;
    private ApplicationRequest applyRequest;

    @BeforeEach
    void setUp() {
        mockSchool = new School(
                "DPS Pune", "dps@gmail.com", "pass",
                "Sector 12", "Pune", "CBSE"
        );
        mockSchool.setId("S001");

        mockTeacher = new Teacher(
                "Nikhil Sharma", "nikhil@gmail.com",
                "pass", "Mathematics", 5, "Pune"
        );
        mockTeacher.setId("T001");

        mockJob = new Job(
                "S001", "DPS Pune", "Mathematics",
                "Grade 9-10", 20000, 30000,
                "FULLTIME", "Pune",
                "Looking for maths teacher", 3
        );
        mockJob.setId("J001");

        createRequest = new JobCreateRequest();
        createRequest.setSchoolId("S001");
        createRequest.setSubject("Mathematics");
        createRequest.setGradeLevel("Grade 9-10");
        createRequest.setSalaryMin(20000);
        createRequest.setSalaryMax(30000);
        createRequest.setJobType("FULLTIME");
        createRequest.setCity("Pune");
        createRequest.setDescription("Need teacher");
        createRequest.setExperienceRequired(3);

        applyRequest = new ApplicationRequest();
        applyRequest.setTeacherId("T001");
        applyRequest.setCoverNote("I am interested");
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ School creates job successfully")
    void createJob_Success() {

        when(schoolRepository.findById("S001"))
                .thenReturn(Optional.of(mockSchool));
        when(jobRepository.save(any(Job.class)))
                .thenReturn(mockJob);

        JobResponse response =
                jobService.createJob(createRequest);

        assertNotNull(response);
        assertEquals("Mathematics", response.getSubject());
        assertEquals("DPS Pune", response.getSchoolName());
        assertEquals("OPEN", response.getStatus());
        assertEquals(20000, response.getSalaryMin());

        verify(jobRepository, times(1))
                .save(any(Job.class));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Create job fails — school not found")
    void createJob_SchoolNotFound() {

        when(schoolRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> jobService.createJob(createRequest)
        );

        verify(jobRepository, never())
                .save(any(Job.class));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Teacher applies for job successfully")
    void applyForJob_Success() {

        when(jobRepository.findById("J001"))
                .thenReturn(Optional.of(mockJob));
        when(teacherRepository.findById("T001"))
                .thenReturn(Optional.of(mockTeacher));
        when(applicationRepository
                .existsByJobIdAndTeacherId("J001", "T001"))
                .thenReturn(false);

        Application mockApp = new Application(
                "J001", "T001", "Nikhil Sharma",
                "nikhil@gmail.com", "Mathematics",
                5, 0.0, "I am interested"
        );
        mockApp.setId("A001");

        when(applicationRepository.save(any()))
                .thenReturn(mockApp);
        when(jobRepository.save(any())).thenReturn(mockJob);

        ApplicationResponse response =
                jobService.applyForJob("J001", applyRequest);

        assertNotNull(response);
        assertEquals("T001", response.getTeacherId());
        assertEquals("APPLIED", response.getStatus());
        assertEquals("Nikhil Sharma",
                response.getTeacherName());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Apply fails — already applied")
    void applyForJob_AlreadyApplied() {

        when(jobRepository.findById("J001"))
                .thenReturn(Optional.of(mockJob));
        when(teacherRepository.findById("T001"))
                .thenReturn(Optional.of(mockTeacher));
        when(applicationRepository
                .existsByJobIdAndTeacherId("J001", "T001"))
                .thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> jobService.applyForJob("J001", applyRequest)
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Apply fails — job is closed")
    void applyForJob_JobClosed() {

        mockJob.setStatus("FILLED");

        when(jobRepository.findById("J001"))
                .thenReturn(Optional.of(mockJob));

        assertThrows(
                RuntimeException.class,
                () -> jobService.applyForJob("J001", applyRequest)
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Update status to HIRED closes job")
    void updateStatus_HiredClosesJob() {

        Application mockApp = new Application(
                "J001", "T001", "Nikhil Sharma",
                "nikhil@gmail.com", "Mathematics",
                5, 4.5, "note"
        );
        mockApp.setId("A001");

        when(applicationRepository.findById("A001"))
                .thenReturn(Optional.of(mockApp));
        when(jobRepository.findById("J001"))
                .thenReturn(Optional.of(mockJob));
        when(applicationRepository.save(any()))
                .thenReturn(mockApp);
        when(jobRepository.save(any()))
                .thenReturn(mockJob);

        StatusUpdateRequest req = new StatusUpdateRequest();
        req.setStatus("HIRED");

        ApplicationResponse response =
                jobService.updateStatus("A001", req);

        assertNotNull(response);
        // Verify job was saved (status changed to FILLED)
        verify(jobRepository, times(1))
                .save(any(Job.class));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Invalid status throws exception")
    void updateStatus_InvalidStatus() {

        Application mockApp = new Application(
                "J001", "T001", "Nikhil",
                "n@gmail.com", "Maths", 5, 4.0, ""
        );
        mockApp.setId("A001");

        when(applicationRepository.findById("A001"))
                .thenReturn(Optional.of(mockApp));

        StatusUpdateRequest req = new StatusUpdateRequest();
        req.setStatus("INVALID_STATUS");

        assertThrows(
                RuntimeException.class,
                () -> jobService.updateStatus("A001", req)
        );
    }
}