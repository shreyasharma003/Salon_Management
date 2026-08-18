package com.salon.customerservice.mapper;

import com.salon.customerservice.dto.inDto.CustomerRequest;
import com.salon.customerservice.dto.outDto.CustomerResponse;
import com.salon.customerservice.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerRequest request);

    CustomerResponse toResponse(Customer customer);
}
