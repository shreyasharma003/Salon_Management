package com.salon.inventory_service.repository;

import com.salon.inventory_service.entity.Inventory;
import com.salon.inventory_service.entity.enums.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySku(String sku);
    boolean existsBySkuAndIdNot(String sku, Long id);
    Optional<Inventory> findBySku(String sku);

    @Query("""
            SELECT i
            FROM Inventory i
            WHERE i.quantity <= i.minimumStock
            AND i.status = :status
            """
    )
    List<Inventory> findLowStockItems(
            @Param("status") InventoryStatus status
    );

    List<Inventory> findByQuantityAndStatus(
            Integer quantity,
            InventoryStatus status
    );

}