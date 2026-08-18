package com.salon.Artist_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistRequestDto {
    private String name;
    private String email;
    private String contactNumber;
    private String specialization;
    private boolean isAvailable;
}

