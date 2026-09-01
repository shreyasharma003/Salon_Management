package com.salon.order_service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
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
public class BillingRequest {

    private Long orderId;

    @JsonAlias({"totalAmount", "amount"})
    private BigDecimal amount;

    private PaymentStatus paymentStatus;

    public BigDecimal getTotalAmount() {
        return amount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.amount = totalAmount;
    }
}
