package com.salon.Artist_service.services;

import com.salon.Artist_service.dto.ArtistRequestDto;
import com.salon.Artist_service.dto.ArtistResponseDto;
import com.salon.Artist_service.entity.Artist;
import com.salon.Artist_service.exception.ResourceNotFoundException;
import com.salon.Artist_service.mapper.ArtistMapper;
import com.salon.Artist_service.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtistMapper artistMapper;

    @Override
    public ArtistResponseDto createArtist(ArtistRequestDto artistRequestDto) {
        Artist artist = artistMapper.toEntity(artistRequestDto);
        Artist savedArtist = artistRepository.save(artist);
        return artistMapper.toResponseDto(savedArtist);
    }

    @Override
    public List<ArtistResponseDto> getAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(artistMapper::toResponseDto)
                .toList();
    }

    @Override
    public ArtistResponseDto getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", id));
        return artistMapper.toResponseDto(artist);
    }

    @Override
    public ArtistResponseDto updateArtist(Long id, ArtistRequestDto artistRequestDto) {
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", id));
        Artist updatedArtist = artistMapper.toEntity(artistRequestDto);
        updatedArtist.setId(existingArtist.getId());
        Artist savedArtist = artistRepository.save(updatedArtist);
        return artistMapper.toResponseDto(savedArtist);
    }

    @Override
    public void deleteArtist(Long id) {
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", id));
        artistRepository.delete(existingArtist);
    }
}

