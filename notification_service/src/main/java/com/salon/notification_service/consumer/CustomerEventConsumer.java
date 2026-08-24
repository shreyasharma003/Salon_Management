package com.salon.notification_service.consumer;

import com.salon.notification_service.dto.eventDto.CustomerCreatedEvent;
import com.salon.notification_service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventConsumer {
    private final NotificationService notificationService;

    public CustomerEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "customer-events",
            groupId = "notificationservice"
    )
    public void consume(CustomerCreatedEvent event) {
        notificationService.createNotificationsForCustomerEvent(event);
    }
}