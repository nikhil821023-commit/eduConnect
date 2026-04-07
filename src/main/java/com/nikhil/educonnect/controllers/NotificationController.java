package com.nikhil.educonnect.controllers;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // GET /api/notifications/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>>
    getAll(@PathVariable String userId) {

        List<NotificationResponse> notifications =
                notificationService.getAll(userId);
        return ResponseEntity.ok(
                ApiResponse.success(notifications,
                        "Found " + notifications.size() +
                                " notifications")
        );
    }

    // GET /api/notifications/unread/{userId}
    @GetMapping("/unread/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Integer>>>
    getUnreadCount(@PathVariable String userId) {

        int count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        Map.of("unreadCount", count),
                        "Unread notifications: " + count
                )
        );
    }

    // PATCH /api/notifications/{id}/read
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<String>> markAsRead(
            @PathVariable String id) {

        notificationService.markAsRead(id);
        return ResponseEntity.ok(
                ApiResponse.success("OK",
                        "Notification marked as read")
        );
    }

    // PATCH /api/notifications/read-all/{userId}
    @PatchMapping("/read-all/{userId}")
    public ResponseEntity<ApiResponse<String>> markAllAsRead(
            @PathVariable String userId) {

        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(
                ApiResponse.success("OK",
                        "All notifications marked as read ✅")
        );
    }
}