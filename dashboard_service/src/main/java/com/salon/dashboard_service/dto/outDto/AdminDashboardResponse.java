package com.salon.dashboard_service.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDashboardResponse {

    private CustomerDashboardResponse customer;
    private InventoryDashboardResponse inventory;
    private ArtistDashboardResponse artist;
    private OrderDashboardResponse order;
}