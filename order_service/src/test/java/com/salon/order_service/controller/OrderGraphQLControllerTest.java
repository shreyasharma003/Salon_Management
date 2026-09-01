package com.salon.order_service.controller;

import com.salon.order_service.dto.OrderRequest;
import com.salon.order_service.dto.OrderResponse;
import com.salon.order_service.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderGraphQLControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderGraphQLController orderGraphQLController;

    private OrderRequest orderRequest;
    private OrderResponse orderResponse;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequest();
        orderRequest.setCustomerId(3L);
        orderRequest.setArtistId(8L);
        orderRequest.setServiceId(1L);
        orderRequest.setQuantity(2);

        orderResponse = OrderResponse.builder()
                .id(100L)
                .quantity(2)
                .build();
    }

    @Test
    void testCreateOrder() {
        when(orderService.createOrder(orderRequest)).thenReturn(orderResponse);

        OrderResponse result = orderGraphQLController.createOrder(orderRequest);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        verify(orderService, times(1)).createOrder(orderRequest);
    }

    @Test
    void testGetOrder() {
        when(orderService.getOrderById(19L)).thenReturn(orderResponse);

        OrderResponse result = orderGraphQLController.order(19L);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        verify(orderService, times(1)).getOrderById(19L);
    }

    @Test
    void testGetAllOrders() {
        when(orderService.getAllOrders()).thenReturn(java.util.List.of(orderResponse));

        java.util.List<OrderResponse> result = orderGraphQLController.orders();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderService, times(1)).getAllOrders();
    }
}
