package com.salon.dashboard_service.dto.eventDto;

import com.salon.dashboard_service.dto.enums.ArtistEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistEvent {

    private ArtistEventType eventType;
    private Long artistId;
    private String artistName;
    private boolean available;
    private LocalDateTime timestamp;
}