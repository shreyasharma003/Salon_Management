package com.salon.dashboard_service.service;

import com.salon.dashboard_service.dto.eventDto.InventoryEvent;
import com.salon.dashboard_service.dto.outDto.InventoryDashboardResponse;

public interface InventoryDashboardService {

    void processInventoryEvent(InventoryEvent event);

    InventoryDashboardResponse getInventoryDashboard();
}