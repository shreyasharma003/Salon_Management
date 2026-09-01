package com.salon.dashboard_service.entity;

import com.salon.dashboard_service.dto.enums.InventoryEventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_dashboard_items")
@Getter
@Setter
@NoArgsConstructor
public class InventoryDashboardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryEventType eventType;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;
}