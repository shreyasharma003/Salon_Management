package com.salon.order_service.service;

import com.salon.order_service.dto.ArtistResponse;
import com.salon.order_service.dto.CustomerResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "artistservice")
public interface ArtistClient {

    @GetMapping("/artist/{id}")
    ArtistResponse getArtistById(@PathVariable("id") Long id);
}


