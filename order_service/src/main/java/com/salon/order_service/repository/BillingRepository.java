package com.salon.order_service.repository;

import com.salon.order_service.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByOrderId(Long orderId);
}
