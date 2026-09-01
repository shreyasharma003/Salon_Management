package com.salon.order_service.controller;

import com.salon.order_service.dto.ApiResponse;
import com.salon.order_service.dto.BillingRequest;
import com.salon.order_service.dto.BillingResponse;
import com.salon.order_service.entity.Billing;
import com.salon.order_service.service.BillingService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BillingResponse>> createBill(@Valid @RequestBody BillingRequest request) {
        Billing billing = Billing.builder()
                .orderId(request.getOrderId())
                .build();

        BillingResponse response = billingService.createBill(billing);

        ApiResponse<BillingResponse> apiResponse = ApiResponse.<BillingResponse>builder()
                .data(response)
                .message("Bill created successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<BillingResponse>> getBillByOrderId(@PathVariable Long orderId) {
        BillingResponse response = billingService.getBillByOrderId(orderId);

        ApiResponse<BillingResponse> apiResponse = ApiResponse.<BillingResponse>builder()
                .data(response)
                .message("Bill fetched successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BillingResponse>> getBillById(@PathVariable Long id) {
        BillingResponse response = billingService.getBillById(id);

        ApiResponse<BillingResponse> apiResponse = ApiResponse.<BillingResponse>builder()
                .data(response)
                .message("Bill fetched successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BillingResponse>>> getAllBills() {
        List<BillingResponse> response = billingService.getAllBills();

        ApiResponse<List<BillingResponse>> apiResponse = ApiResponse.<List<BillingResponse>>builder()
                .data(response)
                .message("All bills fetched successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
