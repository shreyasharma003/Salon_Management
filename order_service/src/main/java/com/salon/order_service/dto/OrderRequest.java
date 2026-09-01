package com.salon.order_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private Long artistId;

//    @NotNull
    private Long serviceId;

    @NotNull
    @Min(1)
    private Integer quantity;

}
