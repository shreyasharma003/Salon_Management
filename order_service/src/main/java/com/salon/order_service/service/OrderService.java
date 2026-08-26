package com.salon.order_service.service;

import com.salon.order_service.dto.OrderRequest;
import com.salon.order_service.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();

    OrderResponse updateOrder(Long id, OrderRequest request);

    void deleteOrder(Long id);

}
