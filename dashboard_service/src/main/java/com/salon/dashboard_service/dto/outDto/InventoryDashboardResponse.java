package com.salon.dashboard_service.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InventoryDashboardResponse {

    private long lowStockItems;
    private long outOfStockItems;
    private List<InventoryDashboardItemResponse> items;
}