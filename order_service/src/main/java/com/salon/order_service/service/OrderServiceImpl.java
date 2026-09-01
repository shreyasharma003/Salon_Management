package com.salon.order_service.service;

import com.salon.order_service.Mapper.OrderMapper;
import com.salon.order_service.dto.*;
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
    private final CustomerClient customerClient;
    private final ServiceCatalogClient serviceCatalogClient;
    private final ArtistClient artistClient;
    private final OrderProducerService orderProducerService;

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        // Get customer details
        CustomerResponse customer =
                customerClient.getCustomerById(
                        request.getCustomerId()
                );

        // Get artist details
        ArtistResponse artist =
                artistClient.getArtistById(
                        request.getArtistId()
                );

        // Get service details
        ServiceResponse service =
                serviceCatalogClient.getServiceById(
                        request.getServiceId()
                );

        // Calculate total using price from Service Catalog
        BigDecimal totalAmount =
                service.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()
                                )
                        );

        // Map request + Feign responses → Order entity
        Order order = orderMapper.toEntity(
                request,
                customer,
                artist,
                service
        );

        // Set fields handled by business logic
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.COMPLETED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Save order
        Order savedOrder =
                orderRepository.save(order);

        // Entity → Response DTO
        OrderResponse response = orderMapper.toResponse(savedOrder);

        // Publish Kafka Event
        if (savedOrder.getStatus() == OrderStatus.PENDING) {
            orderProducerService.sendOrderPendingEvent(response);
        } else {
            orderProducerService.sendOrderCreatedEvent(response);
        }

        return response;
    }


    // =========================
    // GET ORDER BY ID
    // =========================
    @Override
    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + id
                        )
                );

        return orderMapper.toResponse(order);
    }


    // =========================
    // GET ALL ORDERS
    // =========================
    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }


    // =========================
    // UPDATE ORDER
    // =========================
    @Override
    public OrderResponse updateOrder(
            Long id,
            OrderRequest request
    ) {

        // Find existing order
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + id
                        )
                );

        // Get latest customer details
        CustomerResponse customer =
                customerClient.getCustomerById(
                        request.getCustomerId()
                );

        // Get latest artist details
        ArtistResponse artist =
                artistClient.getArtistById(
                        request.getArtistId()
                );

        // Get latest service details
        ServiceResponse service =
                serviceCatalogClient.getServiceById(
                        request.getServiceId()
                );

        // Update IDs
        order.setCustomerId(customer.getId());
        order.setArtistId(artist.getId());

        // Update names
        order.setCustomerName(customer.getName());
        order.setArtistName(artist.getName());

        // Update service
        order.setServiceName(service.getName());

        // Update quantity
        order.setQuantity(request.getQuantity());

        // Get latest price from catalog
        BigDecimal price = service.getPrice();

        order.setPrice(price);

        // Recalculate total
        BigDecimal totalAmount =
                price.multiply(
                        BigDecimal.valueOf(
                                request.getQuantity()
                        )
                );

        order.setTotalAmount(totalAmount);

        order.setUpdatedAt(LocalDateTime.now());

        // Save
        Order savedOrder =
                orderRepository.save(order);

        OrderResponse response = orderMapper.toResponse(savedOrder);

        if (savedOrder.getStatus() == OrderStatus.CANCELLED) {
            orderProducerService.sendOrderCancelledEvent(response);
        }

        return response;
    }


    // =========================
    // DELETE ORDER
    // =========================
    @Override
    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + id
                        )
                );

        OrderResponse response = orderMapper.toResponse(order);

        orderRepository.delete(order);

        // Publish Cancelled Event
        orderProducerService.sendOrderCancelledEvent(response);
    }


}
