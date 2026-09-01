package com.salon.order_service.dto;

import com.salon.order_service.entity.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferResponse {

    private Long id;

    private Long billId;

    private BigDecimal discountPercentage;

    private String offerCode;

    private String description;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private OfferStatus status;
}
