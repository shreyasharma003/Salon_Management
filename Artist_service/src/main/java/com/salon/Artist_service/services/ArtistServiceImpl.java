package com.salon.Artist_service.services;

import com.salon.Artist_service.entity.Artist;
import com.salon.Artist_service.exception.ResourceNotFoundException;
import com.salon.Artist_service.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", id));
    }

    @Override
    public Artist updateArtist(Long id, Artist artistDetails) {
        Artist existingArtist = getArtistById(id);
        existingArtist.setName(artistDetails.getName());
        existingArtist.setContactNumber(artistDetails.getContactNumber());
        existingArtist.setEmail(artistDetails.getEmail());
        existingArtist.setSpecialization(artistDetails.getSpecialization());
        existingArtist.setAvailable(artistDetails.isAvailable());
        return artistRepository.save(existingArtist);
    }

    @Override
    public void deleteArtist(Long id) {
        Artist existingArtist = getArtistById(id);
        artistRepository.delete(existingArtist);
    }
}

