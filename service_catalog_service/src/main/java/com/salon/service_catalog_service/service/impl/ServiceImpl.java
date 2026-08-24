package com.salon.service_catalog_service.service.impl;

import com.salon.service_catalog_service.dto.inDto.ServiceRequestDto;
import com.salon.service_catalog_service.dto.outDto.ServiceResponseDto;
import com.salon.service_catalog_service.exception.DuplicateResourceException;
import com.salon.service_catalog_service.exception.ResourceNotFoundException;
import com.salon.service_catalog_service.mapper.ServiceMapper;
import com.salon.service_catalog_service.modal.ServiceCategory;
import com.salon.service_catalog_service.repository.ServiceCategoryRepository;
import com.salon.service_catalog_service.repository.ServiceRepository;
import com.salon.service_catalog_service.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceImpl implements Service {

    private final ServiceRepository serviceRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public ServiceResponseDto createService(ServiceRequestDto requestDto) {

        ServiceCategory category = serviceCategoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(()->
                    new ResourceNotFoundException(
                            "Service Category not found with id: "+requestDto.getCategoryId()
                    )
                );

        boolean serviceExists = serviceRepository.existsByNameIgnoreCaseAndCategoryId(
                requestDto.getName(),
                requestDto.getCategoryId()
        );

        if(serviceExists){
            throw new DuplicateResourceException(
                    "Service with name '" + requestDto.getName()
                            + "' already exists in this category"
            );
        }

        com.salon.service_catalog_service.modal.Service service = serviceMapper.toEntity(requestDto);

        service.setCategory(category);
        service.setActive(true);
        service.setCreatedAt(LocalDateTime.now());
        service.setUpdatedAt(LocalDateTime.now());

        com.salon.service_catalog_service.modal.Service savedService = serviceRepository.save(service);

        return serviceMapper.toDto(savedService);
    }

    @Override
    public ServiceResponseDto getServiceById(Long id) {

        com.salon.service_catalog_service.modal.Service service =
                serviceRepository.findById(id)
                        .orElseThrow(()->
                                new ResourceNotFoundException(
                                        "Service not found with id: "+id
                                )
                        );

        return serviceMapper.toDto(service);
    }

    @Override
    public Page<ServiceResponseDto> getAllServices(Pageable pageable) {

        return serviceRepository.findAll(pageable)
                .map(serviceMapper::toDto);
    }

    @Override
    public ServiceResponseDto updateService(Long id, ServiceRequestDto requestDto) {
        com.salon.service_catalog_service.modal.Service existingService =
                serviceRepository.findById(id)
                        .orElseThrow(()->
                                new ResourceNotFoundException(
                                        "Service not found with id: "+id
                                )
                        );

        ServiceCategory category = serviceCategoryRepository
                .findById(requestDto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Service category not found with id: "
                                        + requestDto.getCategoryId()
                        )
                );

        boolean duplicateService = serviceRepository
                .existsByNameIgnoreCaseAndCategoryIdAndIdNot(
                        requestDto.getName(),
                        requestDto.getCategoryId(),
                        id
                );

        if (duplicateService) {
            throw new DuplicateResourceException(
                    "Service with name '" + requestDto.getName()
                            + "' already exists in this category"
            );
        }

        existingService.setName(requestDto.getName());
        existingService.setDescription(requestDto.getDescription());
        existingService.setPrice(requestDto.getPrice());
        existingService.setCategory(category);

        com.salon.service_catalog_service.modal.Service updatedService =
                serviceRepository.save(existingService);

        return serviceMapper.toDto(updatedService);
    }

    @Override
    public ServiceResponseDto updateServiceStatus(Long id, Boolean active) {
        com.salon.service_catalog_service.modal.Service service =
                serviceRepository.findById(id)
                        .orElseThrow(()->
                                new ResourceNotFoundException(
                                        "Service not found with id: "+id
                                )
                        );

        service.setActive(active);

        com.salon.service_catalog_service.modal.Service updatedServic =
                serviceRepository.save(service);

        return serviceMapper.toDto(updatedServic);
    }

    @Override
    public void deleteService(Long id) {
        com.salon.service_catalog_service.modal.Service service =
                serviceRepository.findById(id)
                        .orElseThrow(()->
                                new ResourceNotFoundException(
                                        "Service not found with id: "+id
                                )
                        );

        serviceRepository.delete(service);
    }

    @Override
    public Page<ServiceResponseDto> searchServices(String keyword, Pageable pageable) {
        return serviceRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    keyword,
                        keyword,
                        pageable
                ).map(serviceMapper::toDto);
    }

    @Override
    public Page<ServiceResponseDto> getServicesByCategory(Long categoryId, Pageable pageable) {
        if(!serviceCategoryRepository.existsById(categoryId)){
            throw new ResourceNotFoundException(
                    "Service category not found with id: " + categoryId
            );
        }

        return serviceRepository.findByCategoryId(
                categoryId,
                pageable
        ).map(serviceMapper::toDto);
    }
}
