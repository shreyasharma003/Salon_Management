package com.salon.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResponse {
    private Long id;
    private String name;

    private Long categoryId;
    private String categoryName;

    private BigDecimal price;
}
