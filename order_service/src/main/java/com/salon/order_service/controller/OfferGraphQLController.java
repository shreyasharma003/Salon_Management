package com.salon.order_service.controller;

import com.salon.order_service.dto.OfferRequest;
import com.salon.order_service.dto.OfferResponse;
import com.salon.order_service.entity.Offer;
import com.salon.order_service.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OfferGraphQLController {

    private final OfferService offerService;

    @QueryMapping
    public OfferResponse offer(@Argument Long id) {
        return offerService.getOfferById(id);
    }

    @QueryMapping
    public OfferResponse offerByBillId(@Argument Long billId) {
        return offerService.getOfferByBillId(billId);
    }

    @QueryMapping
    public List<OfferResponse> offers() {
        return offerService.getAllOffers();
    }

    @MutationMapping
    public OfferResponse createOffer(@Argument("input") OfferRequest input) {
        Offer offer = Offer.builder()
                .billId(input.getBillId())
                .discountPercentage(input.getDiscountPercentage())
                .offerCode(input.getOfferCode())
                .description(input.getDescription())
                .validFrom(input.getValidFrom())
                .validTo(input.getValidTo())
                .status(input.getStatus())
                .build();

        return offerService.createOffer(offer);
    }
}
