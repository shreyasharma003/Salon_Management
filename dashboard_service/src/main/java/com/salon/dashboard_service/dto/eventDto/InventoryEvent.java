package com.salon.dashboard_service.dto.eventDto;

import com.salon.dashboard_service.dto.enums.InventoryEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEvent {

    private InventoryEventType eventType;
    private String sku;
    private String productName;
    private Integer quantity;
    private LocalDateTime timestamp;
}