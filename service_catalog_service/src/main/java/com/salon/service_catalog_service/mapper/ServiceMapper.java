package com.salon.service_catalog_service.mapper;

import com.salon.service_catalog_service.dto.inDto.ServiceRequestDto;
import com.salon.service_catalog_service.dto.outDto.ServiceResponseDto;
import com.salon.service_catalog_service.modal.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    public Service toEntity(ServiceRequestDto serviceRequestDto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    public ServiceResponseDto toDto(Service service);

}
