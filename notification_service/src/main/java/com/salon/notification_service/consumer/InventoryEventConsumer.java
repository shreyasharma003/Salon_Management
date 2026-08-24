package com.salon.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

    @KafkaListener(
            topics = "inventory-events",
            groupId = "notificationservice"
    )
    public void consume(String event) {

        System.out.println("Inventory event received: " + event);
    }
}