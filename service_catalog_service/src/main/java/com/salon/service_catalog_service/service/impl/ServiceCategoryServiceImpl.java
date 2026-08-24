package com.salon.service_catalog_service.service.impl;

import com.salon.service_catalog_service.dto.inDto.ServiceCategoryRequestDto;
import com.salon.service_catalog_service.dto.inDto.ServiceCategoryStatusUpdateRequestDto;
import com.salon.service_catalog_service.dto.outDto.ServiceCategoryResponseDto;
import com.salon.service_catalog_service.exception.ResourceAlreadyExistsException;
import com.salon.service_catalog_service.exception.ResourceNotFoundException;
import com.salon.service_catalog_service.mapper.ServiceCategoryMapper;
import com.salon.service_catalog_service.modal.ServiceCategory;
import com.salon.service_catalog_service.repository.ServiceCategoryRepository;
import com.salon.service_catalog_service.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

     private final ServiceCategoryMapper serviceCategoryMapper;
     private final ServiceCategoryRepository serviceCategoryRepository;

    @Override
    public ServiceCategoryResponseDto createCategory(ServiceCategoryRequestDto request) {
        if(serviceCategoryRepository.existsByNameIgnoreCase(request.getName().trim())){
            throw new ResourceAlreadyExistsException(
                    "Service Category with name " + request.getName() + " already exists"
            );
        }

        ServiceCategory category=serviceCategoryMapper.toEntity(request);
        category.setName(category.getName().trim());
        category.setActive(true);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        ServiceCategory savedCategory = serviceCategoryRepository.save(category);

        return serviceCategoryMapper.toDto(savedCategory);
    }

    @Override
    public ServiceCategoryResponseDto getCategoryById(Long id) {
        ServiceCategory category = serviceCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service Category not found with id :" + id)
                        );
        return serviceCategoryMapper.toDto(category);
    }

    @Override
    public Page<ServiceCategoryResponseDto> getAllCategories(Pageable pageable) {

        return serviceCategoryRepository
                .findAll(pageable)
                .map(serviceCategoryMapper::toDto);

    }

    @Override
    public ServiceCategoryResponseDto updateCategory(Long id, ServiceCategoryRequestDto request) {
        ServiceCategory category = serviceCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service Category not found with id :" + id)
                        );

        if(request.getName() == null && request.getDescription() == null){
            throw new IllegalArgumentException(
                    "At least one field is required for update"
            );
        }

        if(request.getName() != null && !request.getName().isBlank()){
            String newName = request.getName().trim();

            if(serviceCategoryRepository.existsByNameIgnoreCase(newName)){
                throw new ResourceAlreadyExistsException(
                        "Service Category with name " + newName + " already exists");
            }
            category.setName(newName);
        }
        if(request.getDescription() != null && !request.getDescription().isBlank()){
            category.setDescription(request.getDescription().trim());
        }
        category.setUpdatedAt(LocalDateTime.now());

        ServiceCategory updatedCategory = serviceCategoryRepository.save(category);
        return serviceCategoryMapper.toDto(updatedCategory);
    }

    @Override
    public void updateCategoryStatus(Long id,
                                     ServiceCategoryStatusUpdateRequestDto requestDto) {
            if(requestDto.getActive() == null){
                throw new IllegalArgumentException(
                        "Active status is required"
                );
            }

            ServiceCategory category = serviceCategoryRepository.findById(id)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Service Category not found with id :" + id
                            )
                    );

            if(Objects.equals(category.getActive(), requestDto.getActive())){
                return;
            }
            category.setActive(requestDto.getActive());
            category.setUpdatedAt(LocalDateTime.now());
            serviceCategoryRepository.save(category);
    }
}
