package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.CheckinRequest;
import com.nikhil.educonnect.dto.CheckinResponse;
import com.nikhil.educonnect.dto.LocationStatus;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.models.Checkin;
import com.nikhil.educonnect.models.Teacher;
import com.nikhil.educonnect.repositories.CheckinRepository;
import com.nikhil.educonnect.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private LocationVerificationService locationService;

    @Autowired
    private NotificationService notificationService;

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a");

    // ─── STUDENT ARRIVES ──────────────────────────────────
    public CheckinResponse arrive(CheckinRequest request) {

        // Get destination coordinates
        double destLat = 0.0;
        double destLng = 0.0;
        String destinationName = "";

        if (request.getDestinationType()
                .equalsIgnoreCase("TUITION")) {

            Teacher teacher = teacherRepository
                    .findById(request.getDestinationId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Teacher", request.getDestinationId()
                            )
                    );

            // For now use teacher city as placeholder
            // In production these would be real coordinates
            destLat = 18.5204;
            destLng = 73.8567;
            destinationName = teacher.getName() +
                    "'s Tuition";
        }

        // Calculate distance using Haversine
        int distance = locationService.calculateDistance(
                request.getLat(), request.getLng(),
                destLat, destLng
        );

        boolean verified = locationService.isWithinRange(
                request.getLat(), request.getLng(),
                destLat, destLng
        );

        // Save checkin
        Checkin checkin = new Checkin(
                request.getStudentId(),
                request.getStudentName(),
                request.getParentId(),
                request.getDestinationId(),
                destinationName,
                request.getDestinationType().toUpperCase(),
                "ARRIVAL",
                request.getLat(),
                request.getLng(),
                destLat,
                destLng,
                distance,
                verified
        );

        Checkin saved = checkinRepository.save(checkin);

        // Send notification to parent
        String time = LocalDateTime.now().format(TIME_FORMAT);
        notificationService.sendArrivalNotification(
                request.getParentId(),
                request.getStudentName(),
                destinationName,
                time,
                saved.getId()
        );

        return toResponse(saved, locationService
                .getVerificationMessage(verified, distance));
    }

    // ─── STUDENT LEAVES ───────────────────────────────────
    public CheckinResponse leave(CheckinRequest request) {

        String destinationName = "Tuition";

        if (request.getDestinationType()
                .equalsIgnoreCase("TUITION")) {
            teacherRepository.findById(
                            request.getDestinationId())
                    .ifPresent(t -> {});
            destinationName = "Tuition";
        }

        // Estimate travel time to home
        // Using placeholder home distance of 3km
        int homeDistanceMetres = 3000;
        int travelMins = locationService
                .estimateTravelMinutes(homeDistanceMetres);
        LocalDateTime expectedHome = LocalDateTime
                .now().plusMinutes(travelMins);

        Checkin checkin = new Checkin(
                request.getStudentId(),
                request.getStudentName(),
                request.getParentId(),
                request.getDestinationId(),
                destinationName,
                request.getDestinationType().toUpperCase(),
                "DEPARTURE",
                request.getLat(),
                request.getLng(),
                0.0, 0.0, 0, true
        );
        checkin.setExpectedHomeAt(expectedHome);

        Checkin saved = checkinRepository.save(checkin);

        // Send departure notification
        String time = LocalDateTime.now().format(TIME_FORMAT);
        String expectedTime = expectedHome.format(TIME_FORMAT);

        notificationService.sendDepartureNotification(
                request.getParentId(),
                request.getStudentName(),
                destinationName,
                time,
                expectedTime,
                saved.getId()
        );

        return toResponse(saved,
                "🏠 Heading home. Expected arrival: " +
                        expectedTime);
    }

    // ─── GET TODAY'S JOURNEY ──────────────────────────────
    public List<CheckinResponse> getTodayCheckins(
            String studentId) {

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return checkinRepository
                .findByStudentIdAndTimestampBetween(
                        studentId, startOfDay, endOfDay
                )
                .stream()
                .map(c -> toResponse(c, ""))
                .collect(Collectors.toList());
    }

    // ─── GET FULL HISTORY ─────────────────────────────────
    public List<CheckinResponse> getHistory(String studentId) {
        return checkinRepository
                .findByStudentIdOrderByTimestampDesc(studentId)
                .stream()
                .map(c -> toResponse(c, ""))
                .collect(Collectors.toList());
    }

    // ─── GET CURRENT STATUS ───────────────────────────────
    public LocationStatus getCurrentStatus(String studentId) {

        Optional<Checkin> latest = checkinRepository
                .findFirstByStudentIdOrderByTimestampDesc(studentId);

        LocationStatus status = new LocationStatus();
        status.setStudentId(studentId);

        if (latest.isEmpty()) {
            status.setCurrentStatus("UNKNOWN");
            status.setSafe(true);
            status.setSafetyMessage(
                    "No check-in data available yet"
            );
            return status;
        }

        Checkin last = latest.get();
        status.setStudentName(last.getStudentName());
        status.setLastCheckinTime(
                last.getTimestamp().format(TIME_FORMAT)
        );
        status.setLastLocation(last.getDestinationName());

        // Determine current status
        if (last.getType().equals("ARRIVAL")) {
            status.setCurrentStatus(
                    "AT_" + last.getDestinationType()
            );
            status.setSafe(true);
            status.setSafetyMessage(
                    "✅ " + last.getStudentName() +
                            " is at " + last.getDestinationName()
            );
        } else if (last.getType().equals("DEPARTURE")) {
            status.setCurrentStatus("HEADING_HOME");
            status.setSafe(true);

            // Check if overdue
            if (last.getExpectedHomeAt() != null &&
                    LocalDateTime.now()
                            .isAfter(last.getExpectedHomeAt()
                                    .plusMinutes(15))) {

                status.setSafe(false);
                status.setCurrentStatus("OVERDUE");
                status.setSafetyMessage(
                        "⚠️ " + last.getStudentName() +
                                " should have arrived home by " +
                                last.getExpectedHomeAt()
                                        .format(TIME_FORMAT)
                );

                // Fire safety alert to parent
                notificationService.sendSafetyAlert(
                        last.getParentId(),
                        last.getStudentName(),
                        last.getDestinationName(),
                        last.getId()
                );
            } else {
                status.setSafetyMessage(
                        "🏠 Heading home from " +
                                last.getDestinationName()
                );
            }
        }

        return status;
    }

    // ─── CONVERTER ────────────────────────────────────────
    private CheckinResponse toResponse(
            Checkin c, String verificationMessage) {

        CheckinResponse r = new CheckinResponse();
        r.setId(c.getId());
        r.setStudentId(c.getStudentId());
        r.setStudentName(c.getStudentName());
        r.setDestinationName(c.getDestinationName());
        r.setDestinationType(c.getDestinationType());
        r.setType(c.getType());
        r.setLat(c.getLat());
        r.setLng(c.getLng());
        r.setDistanceMetres(c.getDistanceMetres());
        r.setVerified(c.isVerified());
        r.setVerificationMessage(verificationMessage);
        if (c.getTimestamp() != null)
            r.setTimestamp(c.getTimestamp().toString());
        if (c.getExpectedHomeAt() != null)
            r.setExpectedHomeAt(
                    c.getExpectedHomeAt().format(TIME_FORMAT)
            );
        r.setNotificationSent("Parent notified ✅");
        return r;
    }
}