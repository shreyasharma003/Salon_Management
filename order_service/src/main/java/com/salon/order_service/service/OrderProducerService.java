package com.salon.order_service.service;

import com.salon.order_service.dto.OrderResponse;
import com.salon.order_service.entity.Order;
import com.salon.order_service.event.OrderEvent;
import com.salon.order_service.event.OrderEventType;

public interface OrderProducerService {

    void sendOrderCreatedEvent(OrderResponse orderResponse);

    void sendOrderPendingEvent(OrderResponse orderResponse);

    void sendOrderCancelledEvent(OrderResponse orderResponse);

    void sendOrderEvent(OrderEventType eventType, OrderResponse orderResponse);
}
