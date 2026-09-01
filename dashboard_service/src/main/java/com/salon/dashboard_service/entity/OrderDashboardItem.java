package com.salon.dashboard_service.entity;

import com.salon.dashboard_service.dto.enums.OrderEventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_dashboard_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderDashboardItem {

    @Id
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderEventType eventType;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;
}