package com.salon.dashboard_service.consumer;

import com.salon.dashboard_service.dto.eventDto.OrderEvent;
import com.salon.dashboard_service.service.OrderDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final OrderDashboardService orderDashboardService;

    @KafkaListener(
            topics = "order-events",
            groupId = "dashboard-service"
    )
    public void consumeOrderEvent(OrderEvent event) {

        orderDashboardService.processOrderEvent(event);
    }
}