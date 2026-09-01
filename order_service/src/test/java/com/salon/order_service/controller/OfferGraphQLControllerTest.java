package com.salon.order_service.controller;

import com.salon.order_service.dto.OfferRequest;
import com.salon.order_service.dto.OfferResponse;
import com.salon.order_service.entity.Offer;
import com.salon.order_service.entity.OfferStatus;
import com.salon.order_service.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferGraphQLControllerTest {

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferGraphQLController offerGraphQLController;

    private OfferRequest offerRequest;
    private OfferResponse offerResponse;

    @BeforeEach
    void setUp() {
        offerRequest = OfferRequest.builder()
                .billId(4L)
                .discountPercentage(new BigDecimal("10.0"))
                .offerCode("WELCOME10")
                .description("10% discount")
                .status(OfferStatus.ACTIVE)
                .build();

        offerResponse = OfferResponse.builder()
                .id(1L)
                .billId(4L)
                .discountPercentage(new BigDecimal("10.0"))
                .offerCode("WELCOME10")
                .description("10% discount")
                .status(OfferStatus.ACTIVE)
                .build();
    }

    @Test
    void testCreateOffer() {
        when(offerService.createOffer(any(Offer.class))).thenReturn(offerResponse);

        OfferResponse result = offerGraphQLController.createOffer(offerRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("WELCOME10", result.getOfferCode());
        verify(offerService, times(1)).createOffer(any(Offer.class));
    }

    @Test
    void testGetOfferById() {
        when(offerService.getOfferById(1L)).thenReturn(offerResponse);

        OfferResponse result = offerGraphQLController.offer(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(offerService, times(1)).getOfferById(1L);
    }

    @Test
    void testGetOfferByBillId() {
        when(offerService.getOfferByBillId(4L)).thenReturn(offerResponse);

        OfferResponse result = offerGraphQLController.offerByBillId(4L);

        assertNotNull(result);
        assertEquals(4L, result.getBillId());
        verify(offerService, times(1)).getOfferByBillId(4L);
    }

    @Test
    void testGetAllOffers() {
        when(offerService.getAllOffers()).thenReturn(List.of(offerResponse));

        List<OfferResponse> result = offerGraphQLController.offers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(offerService, times(1)).getAllOffers();
    }
}
