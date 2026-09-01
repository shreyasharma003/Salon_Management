package com.salon.order_service.controller;

import com.salon.order_service.dto.BillingRequest;
import com.salon.order_service.dto.BillingResponse;
import com.salon.order_service.entity.Billing;
import com.salon.order_service.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BillingGraphQLController {

    private final BillingService billingService;

    @QueryMapping
    public BillingResponse bill(@Argument Long id) {
        return billingService.getBillById(id);
    }

    @QueryMapping
    public BillingResponse billByOrderId(@Argument Long orderId) {
        return billingService.getBillByOrderId(orderId);
    }

    @QueryMapping
    public List<BillingResponse> bills() {
        return billingService.getAllBills();
    }

    @MutationMapping
    public BillingResponse createBill(@Argument("input") BillingRequest input) {
        Billing billing = Billing.builder()
                .orderId(input.getOrderId())
                .totalAmount(input.getAmount() != null ? input.getAmount() : input.getTotalAmount())
                .paymentStatus(input.getPaymentStatus())
                .build();

        return billingService.createBill(billing);
    }
}
