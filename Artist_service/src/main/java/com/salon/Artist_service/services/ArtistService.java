package com.salon.Artist_service.services;

import com.salon.Artist_service.entity.Artist;
import java.util.List;

public interface ArtistService {
    Artist createArtist(Artist artist);
    List<Artist> getAllArtists();
    Artist getArtistById(Long id);
    Artist updateArtist(Long id, Artist artist);
    void deleteArtist(Long id);
}

