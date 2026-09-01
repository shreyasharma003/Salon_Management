package com.salon.dashboard_service.consumer;

import com.salon.dashboard_service.dto.eventDto.InventoryEvent;
import com.salon.dashboard_service.service.InventoryDashboardService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventConsumer {

    private final InventoryDashboardService inventoryDashboardService;

    public InventoryEventConsumer(
            InventoryDashboardService inventoryDashboardService
    ) {
        this.inventoryDashboardService = inventoryDashboardService;
    }

    @KafkaListener(
            topics = "inventory-events",
            groupId = "dashboard-service"
    )
    public void consumeInventoryEvent(InventoryEvent event) {

        inventoryDashboardService.processInventoryEvent(event);
    }
}