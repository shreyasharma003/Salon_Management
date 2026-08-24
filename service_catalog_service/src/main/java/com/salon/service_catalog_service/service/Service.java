package com.salon.service_catalog_service.service;

import com.salon.service_catalog_service.dto.inDto.ServiceRequestDto;
import com.salon.service_catalog_service.dto.outDto.ServiceResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Service {

    ServiceResponseDto createService(ServiceRequestDto requestDto);

    ServiceResponseDto getServiceById(Long id);

    Page<ServiceResponseDto> getAllServices(Pageable pageable);

    ServiceResponseDto updateService(Long id, ServiceRequestDto requestDto);

    ServiceResponseDto updateServiceStatus(Long id, Boolean active);

    void deleteService(Long id);

    Page<ServiceResponseDto> searchServices(String keyword, Pageable pageable);

    Page<ServiceResponseDto> getServicesByCategory(Long categoryId, Pageable pageable);

}
