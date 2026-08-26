package com.salon.order_service.controller;

import com.salon.order_service.dto.ApiResponse;
import com.salon.order_service.dto.OrderRequest;
import com.salon.order_service.dto.OrderResponse;
import com.salon.order_service.entity.Order;
import com.salon.order_service.service.OrderService;
import feign.Response;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderservice;

    //create order
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request){

        OrderResponse response = orderservice.createOrder(request);

        ApiResponse<OrderResponse> apiResponse =
                ApiResponse.<OrderResponse>builder()
                .data(response)
                .message("Order created successfully")
                .success(true)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/orders/all")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> response = orderservice.getAllOrders();

        ApiResponse<List<OrderResponse>> apiResponse =
                ApiResponse.<List<OrderResponse>>builder()
                        .data(response)
                        .message("Order fetched successfully")
                        .success(true)
                        .build();
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long id){
        OrderResponse response = orderservice.getOrderById(id);

        ApiResponse<OrderResponse> apiResponse =
                ApiResponse.<OrderResponse>builder()
                        .data(response)
                        .message("Order fetched successfully")
                        .success(true)
                        .build();
        return ResponseEntity.ok(apiResponse);


    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequest request){

       OrderResponse response = orderservice.updateOrder(id, request);

       ApiResponse<OrderResponse> apiResponse =
                ApiResponse.<OrderResponse>builder()
                        .data(response)
                        .message("Order updated successfully")
                        .success(true)
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {

        orderservice.deleteOrder(id);

        ApiResponse<Void> apiResponse =
                ApiResponse.<Void>builder()
                        .message("Order deleted successfully")
                        .success(true)
                        .build();

        return ResponseEntity.ok(apiResponse);
    }


}
