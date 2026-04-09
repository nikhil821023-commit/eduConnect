package com.nikhil.educonnect.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.security.JwtUtil;
import com.nikhil.educonnect.services.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@DisplayName("Auth Controller Integration Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private LoginResponse mockLoginResponse;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        mockLoginResponse = new LoginResponse();
        mockLoginResponse.setUserId("T001");
        mockLoginResponse.setName("Nikhil Sharma");
        mockLoginResponse.setEmail("nikhil@gmail.com");
        mockLoginResponse.setRole("TEACHER");
        mockLoginResponse.setAccessToken("mock_token");
        mockLoginResponse.setRefreshToken("mock_refresh");
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ POST /api/auth/register returns 200")
    void register_Returns200() throws Exception {

        when(authService.register(any()))
                .thenReturn(mockLoginResponse);

        RegisterRequest request = new RegisterRequest();
        request.setName("Nikhil Sharma");
        request.setEmail("nikhil@gmail.com");
        request.setPassword("pass123");
        request.setRole("TEACHER");
        request.setSubject("Mathematics");
        request.setExperienceYears(5);
        request.setCity("Pune");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.role")
                        .value("TEACHER"))
                .andExpect(jsonPath("$.data.accessToken")
                        .value("mock_token"));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ POST /api/auth/login returns token")
    void login_Returns200WithToken() throws Exception {

        when(authService.login(any()))
                .thenReturn(mockLoginResponse);

        LoginRequest request = new LoginRequest();
        request.setEmail("nikhil@gmail.com");
        request.setPassword("pass123");
        request.setRole("TEACHER");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken")
                        .exists())
                .andExpect(jsonPath("$.data.refreshToken")
                        .exists());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ POST /api/auth/login returns 500 on error")
    void login_Returns500OnError() throws Exception {

        when(authService.login(any()))
                .thenThrow(new RuntimeException(
                        "Invalid email or password"
                ));

        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@gmail.com");
        request.setPassword("wrong");
        request.setRole("TEACHER");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode")
                        .value("INTERNAL_ERROR"));
    }
}