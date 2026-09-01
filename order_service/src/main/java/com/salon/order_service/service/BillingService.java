package com.salon.order_service.service;

import com.salon.order_service.dto.BillingResponse;
import com.salon.order_service.entity.Billing;

public interface BillingService {

    BillingResponse createBill(Billing billing);

    BillingResponse getBillByOrderId(Long orderId);

    BillingResponse getBillById(Long id);

    java.util.List<BillingResponse> getAllBills();

}
