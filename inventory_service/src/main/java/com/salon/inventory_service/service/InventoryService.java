package com.salon.inventory_service.service;

import com.salon.inventory_service.dto.inDto.InventoryRequest;
import com.salon.inventory_service.dto.inDto.InventoryUpdateRequest;
import com.salon.inventory_service.dto.inDto.StockInRequest;
import com.salon.inventory_service.dto.inDto.StockOutRequest;
import com.salon.inventory_service.dto.outDto.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);
    List<InventoryResponse> getAllInventory();
    InventoryResponse getInventoryById(Long id);
    InventoryResponse updateInventory(Long id, InventoryUpdateRequest request);
    void deactivateInventory(String sku);
    void stockIn(String sku, StockInRequest request);
    void stockOut(String sku, StockOutRequest request);
    List<InventoryResponse> getLowStockItems();
    List<InventoryResponse> getOutOfStockItems();
}
