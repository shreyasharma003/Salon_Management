package com.salon.order_service.service;

import com.salon.order_service.Mapper.BillingMapper;
import com.salon.order_service.dto.BillingResponse;
import com.salon.order_service.entity.Billing;
import com.salon.order_service.entity.Order;
import com.salon.order_service.entity.PaymentStatus;
import com.salon.order_service.exception.ResourceNotFoundException;
import com.salon.order_service.repository.BillingRepository;
import com.salon.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService{

    private final BillingRepository billingRepository;
    private final BillingMapper billingMapper;
    private final OrderRepository orderRepository;

    @Override
    public BillingResponse createBill(Billing billing) {
        // Fetch order to get customer details and totalAmount
        Order order = orderRepository.findById(billing.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", billing.getOrderId()));
        
        BigDecimal totalAmount = order.getTotalAmount();
        
        // Calculate tax (18%)
        BigDecimal taxRate = new BigDecimal("0.18");
        BigDecimal tax = totalAmount.multiply(taxRate);
        
        // Calculate final amount
        BigDecimal finalAmount = totalAmount.add(tax);
        
        billing.setCustomerId(order.getCustomerId());
        billing.setCustomerName(order.getCustomerName());
        billing.setTotalAmount(totalAmount);
        billing.setTax(tax);
        billing.setFinalAmount(finalAmount);
        billing.setPaymentStatus(PaymentStatus.COMPLETED);
        
        Billing savedBilling = billingRepository.save(billing);
        
        return enrichResponse(savedBilling);
    }

    private BillingResponse enrichResponse(Billing billing) {
        BillingResponse response = billingMapper.toResponse(billing);
        if ((response.getCustomerId() == null || response.getCustomerName() == null) && billing.getOrderId() != null) {
            orderRepository.findById(billing.getOrderId()).ifPresent(order -> {
                if (response.getCustomerId() == null) {
                    response.setCustomerId(order.getCustomerId());
                }
                if (response.getCustomerName() == null) {
                    response.setCustomerName(order.getCustomerName());
                }
            });
        }
        return response;
    }

    @Override
    public BillingResponse getBillByOrderId(Long orderId) {
        Billing billing = billingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Billing", "orderId", orderId));
        return enrichResponse(billing);
    }

    @Override
    public BillingResponse getBillById(Long id) {
        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billing", "id", id));
        return enrichResponse(billing);
    }

    @Override
    public java.util.List<BillingResponse> getAllBills() {
        return billingRepository.findAll()
                .stream()
                .map(this::enrichResponse)
                .collect(java.util.stream.Collectors.toList());
    }
}
