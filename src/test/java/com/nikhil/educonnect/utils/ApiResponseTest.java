package com.nikhil.educonnect.utils;

import com.nikhil.educonnect.dto.ApiResponse;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ApiResponse DTO Tests")
class ApiResponseTest {

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Success response has correct fields")
    void successResponse_HasCorrectFields() {

        ApiResponse<String> response =
                ApiResponse.success("test data", "All good");

        assertTrue(response.isSuccess());
        assertEquals("All good", response.getMessage());
        assertEquals("test data", response.getData());
        assertNull(response.getErrorCode());
        assertNotNull(response.getTimestamp());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Error response has correct fields")
    void errorResponse_HasCorrectFields() {

        ApiResponse<Object> response =
                ApiResponse.error(
                        "Something failed", "NOT_FOUND"
                );

        assertFalse(response.isSuccess());
        assertEquals("Something failed",
                response.getMessage());
        assertNull(response.getData());
        assertEquals("NOT_FOUND", response.getErrorCode());
        assertNotNull(response.getTimestamp());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Success with null data is allowed")
    void successResponse_NullData_Allowed() {

        ApiResponse<Object> response =
                ApiResponse.success(null, "OK");

        assertTrue(response.isSuccess());
        assertNull(response.getData());
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Timestamp is always set")
    void response_TimestampAlwaysSet() {

        ApiResponse<String> success =
                ApiResponse.success("data", "msg");
        ApiResponse<Object> error =
                ApiResponse.error("msg", "CODE");

        assertNotNull(success.getTimestamp());
        assertNotNull(error.getTimestamp());
    }
}