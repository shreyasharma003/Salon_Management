package com.salon.Artist_service.services;

import com.salon.Artist_service.dto.ArtistRequestDto;
import com.salon.Artist_service.dto.ArtistResponseDto;
import java.util.List;

public interface ArtistService {
    ArtistResponseDto createArtist(ArtistRequestDto artistRequestDto);
    List<ArtistResponseDto> getAllArtists();
    ArtistResponseDto getArtistById(Long id);
    ArtistResponseDto updateArtist(Long id, ArtistRequestDto artistRequestDto);
    void deleteArtist(Long id);

}

