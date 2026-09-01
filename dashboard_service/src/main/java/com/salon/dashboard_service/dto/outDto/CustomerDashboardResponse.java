package com.salon.dashboard_service.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDashboardResponse {

    private long totalCustomers;
    private long customersToday;
    private long customersThisMonth;
}