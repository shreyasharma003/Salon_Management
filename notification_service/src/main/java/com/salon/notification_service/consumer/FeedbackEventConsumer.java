package com.salon.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FeedbackEventConsumer {

    @KafkaListener(
            topics = "feedback-events",
            groupId = "notificationservice"
    )
    public void consume(String event) {

        System.out.println("Feedback event received: " + event);
    }
}