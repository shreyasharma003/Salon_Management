package com.salon.dashboard_service.service;

import com.salon.dashboard_service.dto.eventDto.OrderEvent;
import com.salon.dashboard_service.dto.outDto.OrderDashboardResponse;

public interface OrderDashboardService {

    void processOrderEvent(OrderEvent event);

    OrderDashboardResponse getOrderDashboard();
}