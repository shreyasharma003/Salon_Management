package com.salon.dashboard_service.consumer;

import com.salon.dashboard_service.dto.eventDto.ArtistEvent;
import com.salon.dashboard_service.service.ArtistDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArtistEventConsumer {

    private final ArtistDashboardService artistDashboardService;

    @KafkaListener(
            topics = "artist-events",
            groupId = "dashboard-service"
    )
    public void consumeArtistEvent(ArtistEvent event) {

        artistDashboardService.processArtistEvent(event);
    }
}