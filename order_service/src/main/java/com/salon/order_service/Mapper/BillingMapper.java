package com.salon.order_service.Mapper;

import com.salon.order_service.dto.BillingResponse;
import com.salon.order_service.entity.Billing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillingMapper {

    @Mapping(target = "amount", source = "totalAmount")
    BillingResponse toResponse(Billing billing);
}
