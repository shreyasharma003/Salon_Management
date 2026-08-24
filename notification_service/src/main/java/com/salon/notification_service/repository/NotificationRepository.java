package com.salon.notification_service.repository;

import com.salon.notification_service.entity.enums.RecipientRole;
import com.salon.notification_service.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.salon.notification_service.entity.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientRole(RecipientRole recipientRole);
    List<Notification> findByStatus(Status status);
}
