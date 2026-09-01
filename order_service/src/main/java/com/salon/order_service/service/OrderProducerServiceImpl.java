package com.salon.order_service.service;

import com.salon.order_service.dto.OrderResponse;
import com.salon.order_service.event.OrderEvent;
import com.salon.order_service.event.OrderEventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducerServiceImpl implements OrderProducerService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.topic.order-events:order-events}")
    private String topicName;

    @Override
    public void sendOrderCreatedEvent(OrderResponse orderResponse) {
        sendOrderEvent(OrderEventType.ORDER_CREATED, orderResponse);
    }

    @Override
    public void sendOrderPendingEvent(OrderResponse orderResponse) {
        sendOrderEvent(OrderEventType.ORDER_PENDING, orderResponse);
    }

    @Override
    public void sendOrderCancelledEvent(OrderResponse orderResponse) {
        sendOrderEvent(OrderEventType.ORDER_CANCELLED, orderResponse);
    }

    @Override
    public void sendOrderEvent(OrderEventType eventType, OrderResponse orderResponse) {
        if (orderResponse == null) {
            log.warn("Cannot send Kafka event for null OrderResponse");
            return;
        }

        OrderEvent event = OrderEvent.builder()
                .eventType(eventType)
                .orderId(orderResponse.getId())
                .customerId(orderResponse.getCustomerId())
                .customerName(orderResponse.getCustomerName())
                .artistId(orderResponse.getArtistId())
                .artistName(orderResponse.getArtistName())
                .serviceName(orderResponse.getServiceName())
                .quantity(orderResponse.getQuantity())
                .price(orderResponse.getPrice())
                .totalAmount(orderResponse.getTotalAmount())
                .status(orderResponse.getStatus() != null ? orderResponse.getStatus().name() : null)
                .timestamp(LocalDateTime.now())
                .build();

        String key = String.valueOf(orderResponse.getId());

        log.info("Publishing {} event to topic '{}' for orderId: {}", eventType, topicName, key);

        kafkaTemplate.send(topicName, key, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully published {} event for orderId: {} to partition: {} with offset: {}",
                                eventType, key, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to publish {} event for orderId: {}", eventType, key, ex);
                    }
                });
    }
}
