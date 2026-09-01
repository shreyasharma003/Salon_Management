package com.salon.dashboard_service.service;

import com.salon.dashboard_service.dto.eventDto.ArtistEvent;
import com.salon.dashboard_service.dto.outDto.ArtistDashboardResponse;

public interface ArtistDashboardService {

    void processArtistEvent(ArtistEvent event);

    ArtistDashboardResponse getArtistDashboard();
}