package com.salon.dashboard_service.controller;

import com.salon.dashboard_service.dto.outDto.ArtistDashboardResponse;
import com.salon.dashboard_service.service.ArtistDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/admin/artists")
@RequiredArgsConstructor
public class ArtistDashboardController {

    private final ArtistDashboardService artistDashboardService;

    @GetMapping
    public ResponseEntity<ArtistDashboardResponse> getArtistDashboard() {

        return ResponseEntity.ok(
                artistDashboardService.getArtistDashboard()
        );
    }
}