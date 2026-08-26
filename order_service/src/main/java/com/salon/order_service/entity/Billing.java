package com.salon.order_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name="billing")
@Data
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String serviceName;

    private Integer quantity;

    private BigDecimal totalAmount;

    private BigDecimal discount;

    private BigDecimal tax;

    private BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;;
}
