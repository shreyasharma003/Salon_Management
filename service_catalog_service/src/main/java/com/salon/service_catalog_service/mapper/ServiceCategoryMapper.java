package com.salon.service_catalog_service.mapper;

import com.salon.service_catalog_service.dto.inDto.ServiceCategoryRequestDto;
import com.salon.service_catalog_service.dto.outDto.ServiceCategoryResponseDto;
import com.salon.service_catalog_service.modal.ServiceCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceCategoryMapper {

    ServiceCategory toEntity(ServiceCategoryRequestDto request);
    ServiceCategoryResponseDto toDto(ServiceCategory category);

}
