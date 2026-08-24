package com.salon.notification_service.controller;

import com.salon.notification_service.entity.Notification;
import com.salon.notification_service.entity.enums.RecipientRole;
import com.salon.notification_service.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NoificationController {
    private final NotificationService notificationService;

    public NoificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/role/{recipientRole}")
    public List<Notification> getNotificationsByRole(@PathVariable RecipientRole recipientRole){
        return notificationService.getNotificationByRecipientRole(recipientRole);
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable long id){
        return notificationService.getNotificationById(id);
    }
}
