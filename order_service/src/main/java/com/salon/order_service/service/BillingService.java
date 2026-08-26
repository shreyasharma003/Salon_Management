package com.salon.order_service.service;

import com.salon.order_service.entity.Billing;

public interface BillingService {

    Billing createBill(Billing billing);

    Billing getBillByOrderId(Long orderId);


}
