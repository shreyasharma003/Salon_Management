package com.salon.order_service.service;

import com.salon.order_service.dto.OfferResponse;
import com.salon.order_service.entity.Offer;
import com.salon.order_service.entity.OfferStatus;
import com.salon.order_service.exception.ResourceNotFoundException;
import com.salon.order_service.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Override
    public OfferResponse createOffer(Offer offer) {
        if (offer.getStatus() == null) {
            offer.setStatus(OfferStatus.ACTIVE);
        }
        Offer savedOffer = offerRepository.save(offer);
        return mapToResponse(savedOffer);
    }

    @Override
    public OfferResponse getOfferByBillId(Long billId) {
        Offer offer = offerRepository.findByBillId(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer", "billId", billId));
        return mapToResponse(offer);
    }

    @Override
    public OfferResponse getOfferById(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer", "id", id));
        return mapToResponse(offer);
    }

    @Override
    public List<OfferResponse> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private OfferResponse mapToResponse(Offer offer) {
        return OfferResponse.builder()
                .id(offer.getId())
                .billId(offer.getBillId())
                .discountPercentage(offer.getDiscountPercentage())
                .offerCode(offer.getOfferCode())
                .description(offer.getDescription())
                .validFrom(offer.getValidFrom())
                .validTo(offer.getValidTo())
                .status(offer.getStatus())
                .build();
    }
}
