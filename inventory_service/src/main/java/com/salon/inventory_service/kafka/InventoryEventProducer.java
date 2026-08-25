package com.salon.inventory_service.kafka;

import com.salon.inventory_service.dto.eventDto.InventoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventProducer {

    private final KafkaTemplate<String, InventoryEvent> kafkaTemplate;

    private static final String INVENTORY_TOPIC = "inventory-events";

    public void publishInventoryEvent(InventoryEvent event) {

        kafkaTemplate.send(
                INVENTORY_TOPIC,
                event.getSku(),
                event
        );
    }
}