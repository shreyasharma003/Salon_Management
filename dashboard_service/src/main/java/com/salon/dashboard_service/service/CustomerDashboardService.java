package com.salon.dashboard_service.service;

import com.salon.dashboard_service.dto.eventDto.CustomerCreatedEvent;
import com.salon.dashboard_service.dto.outDto.CustomerDashboardResponse;

public interface CustomerDashboardService {

    void processCustomerCreatedEvent(CustomerCreatedEvent event);

    CustomerDashboardResponse getCustomerDashboard();
}