package com.salon.dashboard_service.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDashboardResponse {

    private long pendingOrders;
    private long completedOrders;
}