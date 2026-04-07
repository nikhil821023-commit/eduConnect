package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.NotificationResponse;
import com.nikhil.educonnect.models.Notification;
import com.nikhil.educonnect.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // ─── SEND ARRIVAL NOTIFICATION ───────────────────────
    public void sendArrivalNotification(
            String parentId, String studentName,
            String destinationName, String time,
            String checkinId) {

        Notification notification = new Notification(
                parentId,
                "PARENT",
                "ARRIVAL",
                "✅ " + studentName + " has arrived",
                studentName + " reached " + destinationName +
                        " at " + time + " ✅ Verified",
                checkinId
        );
        notificationRepository.save(notification);
    }

    // ─── SEND DEPARTURE NOTIFICATION ─────────────────────
    public void sendDepartureNotification(
            String parentId, String studentName,
            String destinationName, String time,
            String expectedHomeTime, String checkinId) {

        Notification notification = new Notification(
                parentId,
                "PARENT",
                "DEPARTURE",
                "🏠 " + studentName + " is heading home",
                studentName + " left " + destinationName +
                        " at " + time +
                        ". Expected home by: " + expectedHomeTime,
                checkinId
        );
        notificationRepository.save(notification);
    }

    // ─── SEND SAFETY ALERT ────────────────────────────────
    public void sendSafetyAlert(
            String parentId, String studentName,
            String lastLocation, String checkinId) {

        Notification notification = new Notification(
                parentId,
                "PARENT",
                "SAFETY_ALERT",
                "⚠️ Safety Alert — " + studentName,
                studentName +
                        " hasn't reached home yet. Last seen: " +
                        lastLocation + ". Please check on them.",
                checkinId
        );
        notificationRepository.save(notification);
    }

    // ─── SEND ABSENCE ALERT ───────────────────────────────
    public void sendAbsenceNotification(
            String parentId, String studentName,
            String subject, String teacherName) {

        Notification notification = new Notification(
                parentId,
                "PARENT",
                "ABSENT",
                "⚠️ " + studentName + " marked absent",
                studentName + " was marked absent from " +
                        subject + " class by " + teacherName + " today.",
                null
        );
        notificationRepository.save(notification);
    }

    // ─── SEND JOB ALERT TO TEACHER ───────────────────────
    public void sendJobAlert(
            String teacherId, String schoolName,
            String subject, String city, String jobId) {

        Notification notification = new Notification(
                teacherId,
                "TEACHER",
                "JOB_ALERT",
                "💼 New Job in " + city,
                schoolName + " is hiring a " + subject +
                        " teacher in " + city + ". Apply now!",
                jobId
        );
        notificationRepository.save(notification);
    }

    // ─── GET ALL NOTIFICATIONS ────────────────────────────
    public List<NotificationResponse> getAll(String userId) {
        return notificationRepository
                .findByRecipientIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── GET UNREAD COUNT ─────────────────────────────────
    public int getUnreadCount(String userId) {
        return notificationRepository
                .countByRecipientIdAndIsReadFalse(userId);
    }

    // ─── MARK ONE AS READ ─────────────────────────────────
    public void markAsRead(String notificationId) {
        notificationRepository.findById(notificationId)
                .ifPresent(n -> {
                    n.setRead(true);
                    notificationRepository.save(n);
                });
    }

    // ─── MARK ALL AS READ ─────────────────────────────────
    public void markAllAsRead(String userId) {
        List<Notification> unread = notificationRepository
                .findByRecipientIdAndIsReadFalse(userId);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }

    // ─── CONVERTER ────────────────────────────────────────
    private NotificationResponse toResponse(Notification n) {
        NotificationResponse r = new NotificationResponse();
        r.setId(n.getId());
        r.setRecipientId(n.getRecipientId());
        r.setType(n.getType());
        r.setTitle(n.getTitle());
        r.setMessage(n.getMessage());
        r.setRead(n.isRead());
        r.setReferenceId(n.getReferenceId());
        if (n.getCreatedAt() != null)
            r.setCreatedAt(n.getCreatedAt().toString());
        return r;
    }
}