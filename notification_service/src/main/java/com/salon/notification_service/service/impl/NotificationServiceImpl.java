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
import com.salon.notification_service.dto.eventDto.InventoryEvent;

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

    @Override
    public void createNotificationsForInventoryEvent(InventoryEvent event) {

        String message;

        if (event.getEventType() == EventType.INVENTORY_LOW) {
            message = "Low inventory: " + event.getProductName()
                    + " (SKU: " + event.getSku() + ")"
                    + ". Current quantity: " + event.getQuantity();

        } else if (event.getEventType() == EventType.INVENTORY_OUT_OF_STOCK) {
            message = "Out of stock: " + event.getProductName()
                    + " (SKU: " + event.getSku() + ")"
                    + ". Current quantity: " + event.getQuantity();

        } else {
            return;
        }

        Notification adminNotification = new Notification();
        adminNotification.setEventType(event.getEventType());
        adminNotification.setMessage(message);
        adminNotification.setRecipientRole(RecipientRole.ADMIN);
        adminNotification.setStatus(Status.UNREAD);
        adminNotification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(adminNotification);

        Notification frontDeskNotification = new Notification();
        frontDeskNotification.setEventType(event.getEventType());
        frontDeskNotification.setMessage(message);
        frontDeskNotification.setRecipientRole(RecipientRole.FRONT_DESK);
        frontDeskNotification.setStatus(Status.UNREAD);
        frontDeskNotification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(frontDeskNotification);
    }
}