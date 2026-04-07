package com.nikhil.educonnect.services;

import org.springframework.stereotype.Service;

@Service
public class LocationVerificationService {

    // Max distance in metres to be considered "arrived"
    private static final int VERIFICATION_RADIUS = 200;

    // Earth radius in metres
    private static final double EARTH_RADIUS = 6371000;

    // ─── HAVERSINE FORMULA ────────────────────────────────
    // Calculates real distance between two GPS points
    public int calculateDistance(
            double lat1, double lng1,
            double lat2, double lng2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(
                Math.sqrt(a), Math.sqrt(1 - a)
        );

        return (int) (EARTH_RADIUS * c);
    }

    // ─── IS WITHIN RANGE ──────────────────────────────────
    public boolean isWithinRange(
            double studentLat, double studentLng,
            double destLat, double destLng) {

        int distance = calculateDistance(
                studentLat, studentLng, destLat, destLng
        );
        return distance <= VERIFICATION_RADIUS;
    }

    // ─── GET VERIFICATION MESSAGE ─────────────────────────
    public String getVerificationMessage(
            boolean verified, int distanceMetres) {

        if (verified) {
            return "✅ Arrival verified — within " +
                    distanceMetres + "m of destination";
        } else {
            return "⚠️ Location not verified — " +
                    distanceMetres +
                    "m away from destination (max 200m)";
        }
    }

    // ─── CALCULATE EXPECTED HOME TIME ─────────────────────
    // Estimates travel time based on distance
    public int estimateTravelMinutes(int distanceMetres) {

        // Assume average speed 15km/h (auto/bike in city)
        // distance(m) / speed(m/min) = time(min)
        double speedMetresPerMin = 15000.0 / 60.0;
        int minutes = (int) Math.ceil(
                distanceMetres / speedMetresPerMin
        );

        // Add 5 mins buffer
        return minutes + 5;
    }
}