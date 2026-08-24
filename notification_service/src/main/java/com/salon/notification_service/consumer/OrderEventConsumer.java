package com.salon.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order_events",
            groupId = "notificationservice"
    )
    public void consume(String event) {

        System.out.println("Order event received: " + event);
    }
}