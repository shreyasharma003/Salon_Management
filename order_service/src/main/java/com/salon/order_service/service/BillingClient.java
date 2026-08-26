package com.salon.order_service.service;

import com.salon.order_service.dto.BillingRequest;
import com.salon.order_service.dto.BillingResponse;
import com.salon.order_service.entity.Billing;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "billing-service")
public interface BillingClient {

    @PostMapping("/billing")
    BillingResponse createBill(@RequestBody BillingRequest request);
}
