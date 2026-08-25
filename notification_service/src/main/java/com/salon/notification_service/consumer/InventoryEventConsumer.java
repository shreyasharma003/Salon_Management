package com.salon.notification_service.consumer;

import com.salon.notification_service.dto.eventDto.InventoryEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

    @KafkaListener(
            topics = "inventory-events",
            groupId = "notificationservice"
    )
    public void consume(InventoryEvent event) {

        System.out.println("Inventory event received:");
        System.out.println("Event Type: " + event.getEventType());
        System.out.println("SKU: " + event.getSku());
        System.out.println("Product Name: " + event.getProductName());
        System.out.println("Quantity: " + event.getQuantity());
        System.out.println("Timestamp: " + event.getTimestamp());
    }
}