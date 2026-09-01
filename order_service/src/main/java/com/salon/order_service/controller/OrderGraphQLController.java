package com.salon.order_service.controller;

import com.salon.order_service.dto.OrderRequest;
import com.salon.order_service.dto.OrderResponse;
import com.salon.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderGraphQLController {

    private final OrderService orderService;

    // GET single order
    @QueryMapping
    public OrderResponse order(@Argument Long id) {
        return orderService.getOrderById(id);
    }

    // GET all orders
    @QueryMapping
    public List<OrderResponse> orders() {
        return orderService.getAllOrders();
    }

    // CREATE order
    @MutationMapping
    public OrderResponse createOrder(@Argument("input") OrderRequest input) {
        return orderService.createOrder(input);
    }
}
