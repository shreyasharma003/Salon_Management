package com.salon.order_service.controller;

import com.salon.order_service.dto.ApiResponse;
import com.salon.order_service.dto.OfferRequest;
import com.salon.order_service.dto.OfferResponse;
import com.salon.order_service.entity.Offer;
import com.salon.order_service.service.OfferService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<ApiResponse<OfferResponse>> createOffer(@Valid @RequestBody OfferRequest request) {
        Offer offer = Offer.builder()
                .billId(request.getBillId())
                .discountPercentage(request.getDiscountPercentage())
                .offerCode(request.getOfferCode())
                .description(request.getDescription())
                .validFrom(request.getValidFrom())
                .validTo(request.getValidTo())
                .status(request.getStatus())
                .build();

        OfferResponse response = offerService.createOffer(offer);

        ApiResponse<OfferResponse> apiResponse = ApiResponse.<OfferResponse>builder()
                .data(response)
                .message("Offer created successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/bill/{billId}")
    public ResponseEntity<ApiResponse<OfferResponse>> getOfferByBillId(@PathVariable Long billId) {
        OfferResponse response = offerService.getOfferByBillId(billId);

        ApiResponse<OfferResponse> apiResponse = ApiResponse.<OfferResponse>builder()
                .data(response)
                .message("Offer fetched successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OfferResponse>> getOfferById(@PathVariable Long id) {
        OfferResponse response = offerService.getOfferById(id);

        ApiResponse<OfferResponse> apiResponse = ApiResponse.<OfferResponse>builder()
                .data(response)
                .message("Offer fetched successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OfferResponse>>> getAllOffers() {
        List<OfferResponse> response = offerService.getAllOffers();

        ApiResponse<List<OfferResponse>> apiResponse = ApiResponse.<List<OfferResponse>>builder()
                .data(response)
                .message("All offers fetched successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
