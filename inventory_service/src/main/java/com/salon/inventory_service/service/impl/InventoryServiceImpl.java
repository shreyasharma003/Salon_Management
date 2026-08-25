package com.salon.inventory_service.service.impl;

import com.salon.inventory_service.dto.eventDto.InventoryEvent;
import com.salon.inventory_service.dto.eventDto.InventoryEventType;
import com.salon.inventory_service.dto.inDto.InventoryRequest;
import com.salon.inventory_service.dto.inDto.InventoryUpdateRequest;
import com.salon.inventory_service.dto.inDto.StockOutRequest;
import com.salon.inventory_service.dto.outDto.InventoryResponse;
import com.salon.inventory_service.entity.Inventory;
import com.salon.inventory_service.entity.InventoryTransaction;
import com.salon.inventory_service.entity.enums.InventoryStatus;
import com.salon.inventory_service.exception.DuplicateSkuException;
import com.salon.inventory_service.exception.InsufficientStockException;
import com.salon.inventory_service.exception.InventoryNotFoundException;
import com.salon.inventory_service.kafka.InventoryEventProducer;
import com.salon.inventory_service.mapper.InventoryMapper;
import com.salon.inventory_service.repository.InventoryRepository;
import com.salon.inventory_service.repository.InventoryTransactionRepository;
import com.salon.inventory_service.service.InventoryService;
import com.salon.inventory_service.dto.inDto.StockInRequest;
import com.salon.inventory_service.entity.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final InventoryEventProducer inventoryEventProducer;

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {

        if (inventoryRepository.existsBySku(request.getSku())) {
            throw new DuplicateSkuException(
                    "Inventory with SKU '" + request.getSku() + "' already exists"
            );
        }

        Inventory inventory = inventoryMapper.toEntity(request);

        Inventory savedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(savedInventory);
    }

    @Override
    public List<InventoryResponse> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    public InventoryResponse getInventoryById(Long id) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found with id: " + id
                ));

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse updateInventory(
            Long id,
            InventoryUpdateRequest request) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found with id: " + id
                ));

        if (inventoryRepository.existsBySkuAndIdNot(
                request.getSku(), id)) {

            throw new DuplicateSkuException(
                    "Inventory with SKU '" + request.getSku() + "' already exists"
            );
        }

        inventory.setName(request.getName());
        inventory.setSku(request.getSku());
        inventory.setMinimumStock(request.getMinimumStock());
        inventory.setUnit(request.getUnit());
        inventory.setUnitPrice(request.getUnitPrice());

        Inventory updatedInventory = inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(updatedInventory);
    }

    @Override
    public void deactivateInventory(String sku) {

        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found with SKU: " + sku
                ));

        inventory.setStatus(InventoryStatus.INACTIVE);

        inventoryRepository.save(inventory);
    }

    @Override
    @Transactional
    public void stockIn(String sku, StockInRequest request) {

        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found with SKU: " + sku
                ));

        if (inventory.getStatus() == InventoryStatus.INACTIVE) {
            throw new IllegalStateException(
                    "Cannot add stock to an inactive inventory item"
            );
        }

        inventory.setQuantity(
                inventory.getQuantity() + request.getQuantity()
        );

        inventoryRepository.save(inventory);

        InventoryTransaction transaction = new InventoryTransaction();

        transaction.setInventory(inventory);
        transaction.setTransactionType(TransactionType.STOCK_IN);
        transaction.setQuantity(request.getQuantity());
        transaction.setReason(request.getReason());

        inventoryTransactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void stockOut(String sku, StockOutRequest request) {

        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new InventoryNotFoundException(
                        "Inventory not found with SKU: " + sku
                ));

        if (inventory.getStatus() == InventoryStatus.INACTIVE) {
            throw new IllegalStateException(
                    "Cannot remove stock from an inactive inventory item"
            );
        }

        if (inventory.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException(
                    "Insufficient stock. Available: "
                            + inventory.getQuantity()
                            + ", requested: "
                            + request.getQuantity()
            );
        }

        inventory.setQuantity(
                inventory.getQuantity() - request.getQuantity()
        );

        inventoryRepository.save(inventory);

        InventoryTransaction transaction = new InventoryTransaction();

        transaction.setInventory(inventory);
        transaction.setTransactionType(TransactionType.STOCK_OUT);
        transaction.setQuantity(request.getQuantity());
        transaction.setReason(request.getReason());

        inventoryTransactionRepository.save(transaction);

        InventoryEvent event = null;

        if(inventory.getQuantity()==0){

            event = new InventoryEvent(
                    InventoryEventType.INVENTORY_OUT_OF_STOCK,
                    inventory.getSku(),
                    inventory.getName(),
                    inventory.getQuantity(),
                    LocalDateTime.now()
            );
        }else if(inventory.getQuantity()<=inventory.getMinimumStock()){
            event = new InventoryEvent(
                    InventoryEventType.INVENTORY_LOW,
                    inventory.getSku(),
                    inventory.getName(),
                    inventory.getQuantity(),
                    LocalDateTime.now()
            );
        }

        if (event != null) {
            inventoryEventProducer.publishInventoryEvent(event);
        }
    }

    @Override
    public List<InventoryResponse> getLowStockItems() {

        return inventoryRepository.findLowStockItems(InventoryStatus.ACTIVE)
                .stream()
                .map(inventoryMapper::toResponse)
                .toList();
    }

    @Override
    public List<InventoryResponse> getOutOfStockItems() {

        return inventoryRepository
                .findByQuantityAndStatus(0, InventoryStatus.ACTIVE)
                .stream()
                .map(inventoryMapper::toResponse)
                .toList();
    }
}