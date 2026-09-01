package com.salon.dashboard_service.controller;

import com.salon.dashboard_service.dto.outDto.InventoryDashboardResponse;
import com.salon.dashboard_service.service.InventoryDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/admin/inventory")
@RequiredArgsConstructor
public class InventoryDashboardController {

    private final InventoryDashboardService inventoryDashboardService;

    @GetMapping
    public ResponseEntity<InventoryDashboardResponse> getInventoryDashboard() {

        return ResponseEntity.ok(
                inventoryDashboardService.getInventoryDashboard()
        );
    }
}