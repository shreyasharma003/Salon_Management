package com.salon.inventory_service.dto.inDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockInRequest {

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Stock-in quantity must be at least 1")
    private Integer quantity;

    @Size(max = 255, message = "Reason must not exceed 255 characters")
    private String reason;
}