package com.salon.order_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.kafka.shaded.com.google.protobuf.DescriptorProtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private String customerName;

    private Long artistId;
    private String artistName;

    private String serviceName;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
