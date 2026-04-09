package com.nikhil.educonnect.utils;

import com.nikhil.educonnect.services.LocationVerificationService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Location Verification Service Tests")
class LocationVerificationServiceTest {

    private LocationVerificationService service;

    @BeforeEach
    void setUp() {
        service = new LocationVerificationService();
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Student within 200m — verified")
    void isWithinRange_Close_ReturnsTrue() {

        // Two points ~35 metres apart in Pune
        boolean result = service.isWithinRange(
                18.5204, 73.8567,
                18.5205, 73.8569
        );

        assertTrue(result);
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("❌ Student too far — not verified")
    void isWithinRange_Far_ReturnsFalse() {

        // Pune vs Mumbai — ~150km apart
        boolean result = service.isWithinRange(
                18.5204, 73.8567,
                19.0760, 72.8777
        );

        assertFalse(result);
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Same location — 0 distance")
    void calculateDistance_SamePoint_ReturnsZero() {

        int distance = service.calculateDistance(
                18.5204, 73.8567,
                18.5204, 73.8567
        );

        assertEquals(0, distance);
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Distance calculation is accurate")
    void calculateDistance_KnownPoints_Accurate() {

        // Known distance between two Pune landmarks ~1km
        int distance = service.calculateDistance(
                18.5204, 73.8567,
                18.5104, 73.8567
        );

        // Should be approximately 1112 metres
        assertTrue(distance > 1000 && distance < 1200);
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Verification message — verified")
    void getVerificationMessage_Verified() {

        String msg =
                service.getVerificationMessage(true, 50);

        assertTrue(msg.contains("✅"));
        assertTrue(msg.contains("50"));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Verification message — not verified")
    void getVerificationMessage_NotVerified() {

        String msg =
                service.getVerificationMessage(false, 500);

        assertTrue(msg.contains("⚠️"));
        assertTrue(msg.contains("500"));
    }

    // ─────────────────────────────────────────────────────
    @Test
    @DisplayName("✅ Travel time estimate is positive")
    void estimateTravelMinutes_Positive() {

        int minutes =
                service.estimateTravelMinutes(3000);

        assertTrue(minutes > 0);
        // 3km at 15km/h = 12min + 5 buffer = 17min
        assertEquals(17, minutes);
    }
}