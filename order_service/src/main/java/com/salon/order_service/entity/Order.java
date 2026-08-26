package com.salon.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.shaded.com.google.protobuf.DescriptorProtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long artistid;

    private Long customerid;

    private String serviceName;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
