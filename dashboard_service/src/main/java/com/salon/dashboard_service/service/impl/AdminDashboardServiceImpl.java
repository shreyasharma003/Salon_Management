package com.salon.dashboard_service.service.impl;

import com.salon.dashboard_service.dto.outDto.AdminDashboardResponse;
import com.salon.dashboard_service.dto.outDto.ArtistDashboardResponse;
import com.salon.dashboard_service.dto.outDto.CustomerDashboardResponse;
import com.salon.dashboard_service.dto.outDto.InventoryDashboardResponse;
import com.salon.dashboard_service.dto.outDto.OrderDashboardResponse;
import com.salon.dashboard_service.service.AdminDashboardService;
import com.salon.dashboard_service.service.ArtistDashboardService;
import com.salon.dashboard_service.service.CustomerDashboardService;
import com.salon.dashboard_service.service.InventoryDashboardService;
import com.salon.dashboard_service.service.OrderDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final CustomerDashboardService customerDashboardService;
    private final InventoryDashboardService inventoryDashboardService;
    private final ArtistDashboardService artistDashboardService;
    private final OrderDashboardService orderDashboardService;

    @Override
    public AdminDashboardResponse getAdminDashboard() {

        CustomerDashboardResponse customer =
                customerDashboardService.getCustomerDashboard();

        InventoryDashboardResponse inventory =
                inventoryDashboardService.getInventoryDashboard();

        ArtistDashboardResponse artist =
                artistDashboardService.getArtistDashboard();

        OrderDashboardResponse order =
                orderDashboardService.getOrderDashboard();

        return new AdminDashboardResponse(
                customer,
                inventory,
                artist,
                order
        );
    }
}