package com.salon.Artist_service.repository;

import com.salon.Artist_service.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
