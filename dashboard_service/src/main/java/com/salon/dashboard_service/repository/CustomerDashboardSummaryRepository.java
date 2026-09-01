package com.salon.dashboard_service.repository;

import com.salon.dashboard_service.entity.CustomerDashboardSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDashboardSummaryRepository
        extends JpaRepository<CustomerDashboardSummary, Long> {
}