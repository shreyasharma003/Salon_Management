package com.salon.attendanceservice.service;

import com.salon.attendanceservice.dto.ArtistResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "artist-service", url = "${artist.service.url:http://localhost:8083}")
public interface ArtistClient {

    @GetMapping("/artist/{id}")
    ArtistResponseDto getArtistById(@PathVariable Long id);

}
