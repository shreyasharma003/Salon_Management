package com.salon.inventory_service.controller;

import com.salon.inventory_service.dto.inDto.InventoryRequest;
import com.salon.inventory_service.dto.inDto.InventoryUpdateRequest;
import com.salon.inventory_service.dto.inDto.StockInRequest;
import com.salon.inventory_service.dto.inDto.StockOutRequest;
import com.salon.inventory_service.dto.outDto.InventoryResponse;
import com.salon.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(
            @Valid @RequestBody InventoryRequest request) {

        InventoryResponse response = inventoryService.createInventory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventory() {

        return ResponseEntity.ok(
                inventoryService.getAllInventory()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                inventoryService.getInventoryById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryUpdateRequest request) {

        return ResponseEntity.ok(
                inventoryService.updateInventory(id, request)
        );
    }

    @PutMapping("/deactivate/{sku}")
    public ResponseEntity<Void> deactivateInventory(
            @PathVariable String sku) {

        inventoryService.deactivateInventory(sku);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sku}/stock-in")
    public ResponseEntity<Void> stockIn(
            @PathVariable String sku,
            @Valid @RequestBody StockInRequest request) {

        inventoryService.stockIn(sku, request);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sku}/stock-out")
    public ResponseEntity<Void> stockOut(
            @PathVariable String sku,
            @Valid @RequestBody StockOutRequest request) {

        inventoryService.stockOut(sku, request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponse>> getLowStockItems() {

        return ResponseEntity.ok(
                inventoryService.getLowStockItems()
        );
    }

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<InventoryResponse>> getOutOfStockItems() {

        return ResponseEntity.ok(
                inventoryService.getOutOfStockItems()
        );
    }
}