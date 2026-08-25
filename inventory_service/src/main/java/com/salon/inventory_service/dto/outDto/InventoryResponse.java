package com.salon.inventory_service.dto.outDto;

import com.salon.inventory_service.entity.enums.InventoryStatus;
import com.salon.inventory_service.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private Long id;

    private String name;

    private String sku;

    private Integer quantity;

    private Integer minimumStock;

    private Unit unit;

    private BigDecimal unitPrice;

    private InventoryStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}