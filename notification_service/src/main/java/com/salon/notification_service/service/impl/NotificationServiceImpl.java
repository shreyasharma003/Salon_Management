package com.salon.notification_service.service.impl;

import com.salon.notification_service.dto.eventDto.CustomerCreatedEvent;
import com.salon.notification_service.entity.Notification;
import com.salon.notification_service.entity.enums.EventType;
import com.salon.notification_service.entity.enums.RecipientRole;
import com.salon.notification_service.entity.enums.Status;
import com.salon.notification_service.exception.NotificationNotFoundException;
import com.salon.notification_service.repository.NotificationRepository;
import com.salon.notification_service.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl (NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void createNotificationsForCustomerEvent(CustomerCreatedEvent event){
        String message = event.getCustomerName()+" got added as a new Customer";

        Notification adminNotification =  new Notification();
        adminNotification.setEventType(EventType.CUSTOMER_CREATED);
        adminNotification.setMessage(message);
        adminNotification.setRecipientRole(RecipientRole.ADMIN);
        adminNotification.setStatus(Status.UNREAD);
        adminNotification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(adminNotification);

        Notification frontDeskNotification = new Notification();
        frontDeskNotification.setEventType(EventType.CUSTOMER_CREATED);
        frontDeskNotification.setMessage(message);
        frontDeskNotification.setRecipientRole(RecipientRole.FRONT_DESK);
        frontDeskNotification.setStatus(Status.UNREAD);
        frontDeskNotification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(frontDeskNotification);
    }

    @Override
    public List<Notification> getAllNotifications(){
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getNotificationByRecipientRole(RecipientRole recipientRole){
        return notificationRepository.findByRecipientRole(recipientRole);
    }

    @Override
    public List<Notification> getUnreadNotifications(){
        return notificationRepository.findByStatus(Status.UNREAD);
    }

    @Override
    public Notification getNotificationById(Long id){
        return notificationRepository.findById(id).orElseThrow(()-> new NotificationNotFoundException(
                "Notification not found with id: " +id
        ));
    }
}