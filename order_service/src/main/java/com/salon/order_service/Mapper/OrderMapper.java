package com.salon.order_service.Mapper;

import com.salon.order_service.dto.OrderRequest;
import com.salon.order_service.dto.OrderResponse;
import com.salon.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
    public interface OrderMapper {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "totalAmount", ignore = true)
        @Mapping(target = "status", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        Order toEntity(OrderRequest request);

        OrderResponse toResponse(Order order);
    }

