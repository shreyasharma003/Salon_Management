package com.salon.dashboard_service.dto.eventDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreatedEvent {

    private String eventType;
    private Long customerId;
    private String customerName;
}