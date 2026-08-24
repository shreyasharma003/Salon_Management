package com.salon.service_catalog_service.service;

import com.salon.service_catalog_service.dto.inDto.ServiceCategoryRequestDto;
import com.salon.service_catalog_service.dto.inDto.ServiceCategoryStatusUpdateRequestDto;
import com.salon.service_catalog_service.dto.outDto.ServiceCategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceCategoryService {
    ServiceCategoryResponseDto createCategory(ServiceCategoryRequestDto request);

    ServiceCategoryResponseDto getCategoryById(Long id);

    Page<ServiceCategoryResponseDto> getAllCategories(Pageable pageable);

    ServiceCategoryResponseDto updateCategory(
            Long id,
            ServiceCategoryRequestDto request
    );

    void updateCategoryStatus(
            Long id,
            ServiceCategoryStatusUpdateRequestDto requestDto);
}
