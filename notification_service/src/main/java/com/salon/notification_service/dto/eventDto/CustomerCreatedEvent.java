package com.salon.notification_service.dto.eventDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreatedEvent {
    private String eventType;
    private Long customerId;
    private String customerName;
}
