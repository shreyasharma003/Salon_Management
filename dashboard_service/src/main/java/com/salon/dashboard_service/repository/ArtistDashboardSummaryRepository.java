package com.salon.dashboard_service.repository;

import com.salon.dashboard_service.entity.ArtistDashboardSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistDashboardSummaryRepository
        extends JpaRepository<ArtistDashboardSummary, Long> {
}