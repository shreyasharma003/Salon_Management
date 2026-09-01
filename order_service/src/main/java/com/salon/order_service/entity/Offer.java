package com.salon.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long billId;

    private BigDecimal discountPercentage;

    private String offerCode;

    private String description;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OfferStatus status;
}
