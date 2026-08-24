package com.salon.notification_service.service;

import com.salon.notification_service.dto.eventDto.CustomerCreatedEvent;
import com.salon.notification_service.entity.Notification;
import com.salon.notification_service.entity.enums.RecipientRole;
import com.salon.notification_service.entity.enums.Status;

import java.util.List;

public interface NotificationService {
    void createNotificationsForCustomerEvent(CustomerCreatedEvent event);
    List<Notification> getAllNotifications();
    List<Notification> getNotificationByRecipientRole(RecipientRole recipientRole);
    List<Notification> getUnreadNotifications();
    Notification getNotificationById(Long id);
}
