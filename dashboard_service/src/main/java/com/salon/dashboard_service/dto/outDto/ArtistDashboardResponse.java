package com.salon.dashboard_service.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDashboardResponse {

    private long totalArtists;
    private long availableArtists;
    private long unavailableArtists;
}