package com.salon.inventory_service.mapper;

import com.salon.inventory_service.dto.inDto.InventoryRequest;
import com.salon.inventory_service.dto.outDto.InventoryResponse;
import com.salon.inventory_service.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toEntity(InventoryRequest request);

    InventoryResponse toResponse(Inventory inventory);
}