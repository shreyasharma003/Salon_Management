package com.salon.order_service.Mapper;

import com.salon.order_service.dto.*;
import com.salon.order_service.dto.ArtistResponse;
import com.salon.order_service.dto.CustomerResponse;
import com.salon.order_service.dto.ServiceResponse;
import com.salon.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "customerId", source = "request.customerId")
    @Mapping(target = "customerName", source = "customer.name")

    @Mapping(target = "artistId", source = "request.artistId")
    @Mapping(target = "artistName", source = "artist.name")

    @Mapping(target = "serviceName", source = "service.name")

    @Mapping(target = "quantity", source = "request.quantity")
    @Mapping(target = "price", source = "service.price")

    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    Order toEntity(
            OrderRequest request,
            CustomerResponse customer,
            ArtistResponse artist,
            ServiceResponse service
    );

    OrderResponse toResponse(Order order);
}