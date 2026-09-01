package com.salon.dashboard_service.consumer;

import com.salon.dashboard_service.dto.eventDto.CustomerCreatedEvent;
import com.salon.dashboard_service.service.CustomerDashboardService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventConsumer {

    private final CustomerDashboardService customerDashboardService;

    public CustomerEventConsumer(
            CustomerDashboardService customerDashboardService
    ) {
        this.customerDashboardService = customerDashboardService;
    }

    @KafkaListener(
            topics = "customer-events",
            groupId = "dashboard-service"
    )
    public void consumeCustomerCreatedEvent(
            CustomerCreatedEvent event
    ) {

        customerDashboardService.processCustomerCreatedEvent(event);
    }
}