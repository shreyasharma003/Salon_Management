package com.salon.inventory_service.dto.inDto;

import com.salon.inventory_service.entity.enums.Unit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateRequest {

    @NotBlank(message = "Inventory name is required")
    @Size(max = 100, message = "Inventory name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "SKU is required")
    @Size(max = 50, message = "SKU must not exceed 50 characters")
    private String sku;

    @NotNull(message = "Minimum stock is required")
    private Integer minimumStock;

    @NotNull(message = "Unit is required")
    private Unit unit;

    @NotNull(message = "Unit price is required")
    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Unit price cannot be negative"
    )
    private BigDecimal unitPrice;
}