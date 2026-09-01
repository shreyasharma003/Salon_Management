package com.salon.dashboard_service.service.impl;

import com.salon.dashboard_service.dto.eventDto.ArtistEvent;
import com.salon.dashboard_service.dto.enums.ArtistEventType;
import com.salon.dashboard_service.dto.outDto.ArtistDashboardResponse;
import com.salon.dashboard_service.entity.ArtistDashboardSummary;
import com.salon.dashboard_service.repository.ArtistDashboardSummaryRepository;
import com.salon.dashboard_service.service.ArtistDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistDashboardServiceImpl implements ArtistDashboardService {

    private final ArtistDashboardSummaryRepository repository;

    @Override
    public void processArtistEvent(ArtistEvent event) {

        ArtistDashboardSummary summary =
                repository.findById(1L)
                        .orElseGet(() -> {

                            ArtistDashboardSummary newSummary =
                                    new ArtistDashboardSummary();

                            newSummary.setId(1L);
                            newSummary.setTotalArtists(0);
                            newSummary.setAvailableArtists(0);
                            newSummary.setUnavailableArtists(0);

                            return newSummary;
                        });

        if (event.getEventType() == ArtistEventType.ARTIST_CREATED) {

            summary.setTotalArtists(
                    summary.getTotalArtists() + 1
            );

            if (event.isAvailable()) {
                summary.setAvailableArtists(
                        summary.getAvailableArtists() + 1
                );
            } else {
                summary.setUnavailableArtists(
                        summary.getUnavailableArtists() + 1
                );
            }
        }

        else if (event.getEventType() == ArtistEventType.ARTIST_UPDATED) {

            /*
             * For now, we will handle availability changes here.
             * We will improve this logic later if needed.
             */
        }

        else if (event.getEventType() == ArtistEventType.ARTIST_DELETED) {

            summary.setTotalArtists(
                    Math.max(0, summary.getTotalArtists() - 1)
            );

            if (event.isAvailable()) {
                summary.setAvailableArtists(
                        Math.max(0, summary.getAvailableArtists() - 1)
                );
            } else {
                summary.setUnavailableArtists(
                        Math.max(0, summary.getUnavailableArtists() - 1)
                );
            }
        }

        repository.save(summary);
    }

    @Override
    public ArtistDashboardResponse getArtistDashboard() {

        ArtistDashboardSummary summary =
                repository.findById(1L)
                        .orElseGet(() -> {

                            ArtistDashboardSummary newSummary =
                                    new ArtistDashboardSummary();

                            newSummary.setId(1L);
                            newSummary.setTotalArtists(0);
                            newSummary.setAvailableArtists(0);
                            newSummary.setUnavailableArtists(0);

                            return newSummary;
                        });

        return new ArtistDashboardResponse(
                summary.getTotalArtists(),
                summary.getAvailableArtists(),
                summary.getUnavailableArtists()
        );
    }
}