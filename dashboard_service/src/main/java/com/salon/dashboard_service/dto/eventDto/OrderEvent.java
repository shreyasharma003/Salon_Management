package com.salon.dashboard_service.dto.eventDto;

import com.salon.dashboard_service.dto.enums.OrderEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

    private OrderEventType eventType;

    private Long orderId;

    private Long customerId;

    private String customerName;

    private Long artistId;

    private String artistName;

    private String serviceName;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime timestamp;
}