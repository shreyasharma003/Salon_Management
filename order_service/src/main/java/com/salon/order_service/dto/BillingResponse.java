package com.salon.order_service.dto;

import com.salon.order_service.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponse {

    private Long id;

    private Long orderId;

    private Long customerId;

    private String customerName;

    private BigDecimal amount;

    private BigDecimal tax;

    private BigDecimal finalAmount;

    private PaymentStatus paymentStatus;

}
