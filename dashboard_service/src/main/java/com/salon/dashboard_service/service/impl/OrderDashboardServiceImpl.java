package com.salon.dashboard_service.service.impl;

import com.salon.dashboard_service.dto.enums.OrderEventType;
import com.salon.dashboard_service.dto.eventDto.OrderEvent;
import com.salon.dashboard_service.dto.outDto.OrderDashboardResponse;
import com.salon.dashboard_service.entity.OrderDashboardItem;
import com.salon.dashboard_service.repository.OrderDashboardItemRepository;
import com.salon.dashboard_service.service.OrderDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDashboardServiceImpl implements OrderDashboardService {

    private final OrderDashboardItemRepository repository;

    @Override
    public void processOrderEvent(OrderEvent event) {

        OrderDashboardItem item =
                repository.findById(event.getOrderId())
                        .orElseGet(() -> {

                            OrderDashboardItem newItem =
                                    new OrderDashboardItem();

                            newItem.setOrderId(event.getOrderId());

                            return newItem;
                        });

        item.setEventType(event.getEventType());
        item.setStatus(event.getStatus());
        item.setLastUpdated(event.getTimestamp());

        repository.save(item);
    }

    @Override
    public OrderDashboardResponse getOrderDashboard() {

        List<OrderDashboardItem> items =
                repository.findAll();

        long pendingOrders =
                items.stream()
                        .filter(item ->
                                item.getEventType() == OrderEventType.ORDER_PENDING)
                        .count();

        long completedOrders =
                items.stream()
                        .filter(item ->
                                item.getEventType() == OrderEventType.ORDER_COMPLETED)
                        .count();

        return new OrderDashboardResponse(
                pendingOrders,
                completedOrders
        );
    }
}