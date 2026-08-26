package com.salon.order_service.service;

import com.salon.order_service.Mapper.OrderMapper;
import com.salon.order_service.dto.OrderRequest;
import com.salon.order_service.dto.OrderResponse;
import com.salon.order_service.entity.Order;
import com.salon.order_service.entity.OrderStatus;
import com.salon.order_service.repository.OrderRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        // Convert OrderRequest DTO into Order entity
        Order order = orderMapper.toEntity(request);

        // Calculate total amount
        BigDecimal totalAmount = request.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));


        // Set business fields
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Convert Order entity back to OrderResponse
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        //Find order from database
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Convert Order entity back to OrderResponse
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        // Find existing order
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Update fields from request
        order.setArtistid(request.getArtistId());
        order.setCustomerid(request.getCustomerId());
        order.setServiceName(request.getServiceName());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());

        // Recalculate total amount
        BigDecimal totalAmount = request.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));
        order.setTotalAmount(totalAmount);

        // Update timestamp
        order.setUpdatedAt(LocalDateTime.now());

        // Save and return
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        // Find order to ensure it exists
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        // Delete the order
        orderRepository.delete(order);
    }

}
