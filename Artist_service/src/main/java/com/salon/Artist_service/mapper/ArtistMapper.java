package com.salon.Artist_service.mapper;

import com.salon.Artist_service.dto.ArtistRequestDto;
import com.salon.Artist_service.dto.ArtistResponseDto;
import com.salon.Artist_service.entity.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {

    public Artist toEntity(ArtistRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Artist artist = new Artist();
        artist.setName(dto.getName());
        artist.setEmail(dto.getEmail());
        artist.setContactNumber(dto.getContactNumber());
        artist.setSpecialization(dto.getSpecialization());
        artist.setAvailable(dto.isAvailable());
        return artist;
    }

    public ArtistResponseDto toResponseDto(Artist artist) {
        if (artist == null) {
            return null;
        }
        return ArtistResponseDto.builder()
                .id(artist.getId())
                .name(artist.getName())
                .email(artist.getEmail())
                .contactNumber(artist.getContactNumber())
                .specialization(artist.getSpecialization())
                .isAvailable(artist.isAvailable())
                .build();
    }

    public void updateEntityFromDto(ArtistRequestDto dto, Artist artist) {
        if (dto == null || artist == null) {
            return;
        }
        artist.setName(dto.getName());
        artist.setEmail(dto.getEmail());
        artist.setContactNumber(dto.getContactNumber());
        artist.setSpecialization(dto.getSpecialization());
        artist.setAvailable(dto.isAvailable());
    }
}
