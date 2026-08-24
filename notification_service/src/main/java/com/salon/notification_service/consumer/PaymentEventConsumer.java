package com.salon.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {

    @KafkaListener(
            topics = "payment-events",
            groupId = "notificationservice"
    )
    public void consume(String event) {

        System.out.println("Payment event received: " + event);
    }
}