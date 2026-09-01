package com.salon.dashboard_service.repository;

import com.salon.dashboard_service.entity.OrderDashboardItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDashboardItemRepository
        extends JpaRepository<OrderDashboardItem, Long> {
}