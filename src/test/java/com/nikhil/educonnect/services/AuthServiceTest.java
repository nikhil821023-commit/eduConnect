package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.exceptions.UserAlreadyExistsException;
import com.nikhil.educonnect.models.*;
import com.nikhil.educonnect.repositories.*;
import com.nikhil.educonnect.security.JwtUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Service Tests")
class AuthServiceTest {

    @Mock private TeacherRepository teacherRepository;
    @Mock private SchoolRepository schoolRepository;
    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private JwtUtil jwtUtil;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        // Inject refresh expiration value
        ReflectionTestUtils.setField(
                authService,
                "refreshExpiration",
                604800000L
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Register teacher successfully")
    void registerTeacher_Success() {

        RegisterRequest request = new RegisterRequest();
        request.setName("Nikhil Sharma");
        request.setEmail("nikhil@gmail.com");
        request.setPassword("pass123");
        request.setRole("TEACHER");
        request.setSubject("Mathematics");
        request.setExperienceYears(5);
        request.setCity("Pune");

        Teacher savedTeacher = new Teacher(
                "Nikhil Sharma", "nikhil@gmail.com",
                "hashed", "Mathematics", 5, "Pune"
        );
        savedTeacher.setId("T001");

        when(teacherRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(passwordEncoder.encode(anyString()))
                .thenReturn("hashed_password");
        when(teacherRepository.save(any()))
                .thenReturn(savedTeacher);
        when(jwtUtil.generateAccessToken(anyString(), anyString()))
                .thenReturn("mock_access_token");
        when(refreshTokenRepository.save(any()))
                .thenReturn(new RefreshToken());

        LoginResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("T001", response.getUserId());
        assertEquals("TEACHER", response.getRole());
        assertNotNull(response.getAccessToken());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Register fails — duplicate email")
    void registerTeacher_DuplicateEmail() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("nikhil@gmail.com");
        request.setRole("TEACHER");

        when(teacherRepository.existsByEmail("nikhil@gmail.com"))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(request)
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Login teacher successfully")
    void loginTeacher_Success() {

        Teacher teacher = new Teacher(
                "Nikhil Sharma", "nikhil@gmail.com",
                "hashed_pass", "Mathematics", 5, "Pune"
        );
        teacher.setId("T001");

        LoginRequest request = new LoginRequest();
        request.setEmail("nikhil@gmail.com");
        request.setPassword("pass123");
        request.setRole("TEACHER");

        when(teacherRepository.findByEmail("nikhil@gmail.com"))
                .thenReturn(Optional.of(teacher));
        when(passwordEncoder.matches("pass123", "hashed_pass"))
                .thenReturn(true);
        when(jwtUtil.generateAccessToken(anyString(), anyString()))
                .thenReturn("mock_token");
        when(refreshTokenRepository.save(any()))
                .thenReturn(new RefreshToken());

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("T001", response.getUserId());
        assertEquals("TEACHER", response.getRole());
        assertEquals("mock_token", response.getAccessToken());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Login fails — wrong password")
    void loginTeacher_WrongPassword() {

        Teacher teacher = new Teacher(
                "Nikhil", "nikhil@gmail.com",
                "hashed_pass", "Maths", 5, "Pune"
        );

        LoginRequest request = new LoginRequest();
        request.setEmail("nikhil@gmail.com");
        request.setPassword("wrong_password");
        request.setRole("TEACHER");

        when(teacherRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(teacher));
        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Login fails — email not found")
    void loginTeacher_EmailNotFound() {

        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@gmail.com");
        request.setPassword("pass123");
        request.setRole("TEACHER");

        when(teacherRepository.findByEmail("wrong@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> authService.login(request)
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Refresh fails — token revoked")
    void refresh_RevokedToken() {

        RefreshToken token = new RefreshToken(
                "T001", "old_token", "TEACHER",
                LocalDateTime.now().plusDays(7)
        );
        token.setRevoked(true);

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("old_token");

        when(refreshTokenRepository.findByToken("old_token"))
                .thenReturn(Optional.of(token));

        assertThrows(
                RuntimeException.class,
                () -> authService.refresh(request)
        );
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Refresh fails — token expired")
    void refresh_ExpiredToken() {

        RefreshToken token = new RefreshToken(
                "T001", "expired_token", "TEACHER",
                LocalDateTime.now().minusDays(1) // already expired
        );
        token.setRevoked(false);

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("expired_token");

        when(refreshTokenRepository
                .findByToken("expired_token"))
                .thenReturn(Optional.of(token));

        assertThrows(
                RuntimeException.class,
                () -> authService.refresh(request)
        );
    }
}