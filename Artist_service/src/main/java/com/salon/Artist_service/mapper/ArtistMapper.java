package com.salon.Artist_service.mapper;

import com.salon.Artist_service.dto.ArtistRequestDto;
import com.salon.Artist_service.dto.ArtistResponseDto;
import com.salon.Artist_service.entity.Artist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    Artist toEntity(ArtistRequestDto dto);

    ArtistResponseDto toResponseDto(Artist artist);

}
