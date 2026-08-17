package com.salon.Artist_service.controller;

import com.salon.Artist_service.dto.ApiResponse;
import com.salon.Artist_service.entity.Artist;
import com.salon.Artist_service.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @PostMapping
    public ResponseEntity<ApiResponse<Artist>> createArtist(@RequestBody Artist artist) {
        Artist createdArtist = artistService.createArtist(artist);
        ApiResponse<Artist> response = ApiResponse.<Artist>builder()
                .message("Artist created successfully")
                .data(createdArtist)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getAllArtists")
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Artist artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Artist>> updateArtist(@PathVariable Long id, @RequestBody Artist artistDetails) {
        Artist updatedArtist = artistService.updateArtist(id, artistDetails);
        ApiResponse<Artist> response = ApiResponse.<Artist>builder()
                .message("Artist updated successfully")
                .data(updatedArtist)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Artist deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
