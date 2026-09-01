package com.salon.order_service.service;

import com.salon.order_service.dto.OfferResponse;
import com.salon.order_service.entity.Offer;

import java.util.List;

public interface OfferService {

    OfferResponse createOffer(Offer offer);

    OfferResponse getOfferByBillId(Long billId);

    OfferResponse getOfferById(Long id);

    List<OfferResponse> getAllOffers();
}
