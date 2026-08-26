package com.salon.order_service.dto;

import com.salon.order_service.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {

    private Long id;

    private Long customerId;

    private Long artistId;

    private String serviceName;

    private Integer quantity;

    private BigDecimal price;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private LocalDate createdAt;

    private LocalDateTime updatedAt;
}
