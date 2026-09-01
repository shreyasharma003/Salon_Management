package com.salon.dashboard_service.repository;

import com.salon.dashboard_service.entity.InventoryDashboardItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryDashboardItemRepository
        extends JpaRepository<InventoryDashboardItem, Long> {

    Optional<InventoryDashboardItem> findBySku(String sku);
}