package com.salon.dashboard_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "artist_dashboard_summary")
@Data
public class ArtistDashboardSummary {

    @Id
    private Long id;

    private int totalArtists;

    private int availableArtists;

    private int unavailableArtists;
}