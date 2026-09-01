package com.salon.dashboard_service.controller;

import com.salon.dashboard_service.dto.outDto.OrderDashboardResponse;
import com.salon.dashboard_service.service.OrderDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/admin/orders")
@RequiredArgsConstructor
public class OrderDashboardController {

    private final OrderDashboardService orderDashboardService;

    @GetMapping
    public OrderDashboardResponse getOrderDashboard() {

        return orderDashboardService.getOrderDashboard();
    }
}