package com.salon.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BillingEventConsumer {

    @KafkaListener(
            topics = "billing-events",
            groupId = "notificationservice"
    )
    public void consume(String event) {

        System.out.println("Billing event received: " + event);
    }
}