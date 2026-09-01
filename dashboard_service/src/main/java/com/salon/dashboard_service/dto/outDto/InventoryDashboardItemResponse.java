package com.salon.dashboard_service.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryDashboardItemResponse {

    private String sku;
    private String productName;
    private Integer quantity;
    private String status;
}