package com.nikhil.educonnect.repositories;

import com.nikhil.educonnect.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, String> {

    // All notifications for a user newest first
    List<Notification> findByRecipientIdOrderByCreatedAtDesc(
            String recipientId
    );

    // Unread only
    List<Notification> findByRecipientIdAndIsReadFalse(
            String recipientId
    );

    // Count unread
    int countByRecipientIdAndIsReadFalse(String recipientId);
}