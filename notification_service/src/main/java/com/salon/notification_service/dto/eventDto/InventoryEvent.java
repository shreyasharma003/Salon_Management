package com.salon.notification_service.dto.eventDto;

import com.salon.notification_service.entity.enums.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class InventoryEvent {

    private EventType eventType;
    private String sku;
    private String productName;
    private Integer quantity;
    private LocalDateTime timestamp;
}
