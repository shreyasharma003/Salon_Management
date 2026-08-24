package com.salon.service_catalog_service.controller;

import com.salon.service_catalog_service.dto.inDto.ServiceCategoryRequestDto;
import com.salon.service_catalog_service.dto.inDto.ServiceCategoryStatusUpdateRequestDto;
import com.salon.service_catalog_service.dto.outDto.ServiceCategoryResponseDto;
import com.salon.service_catalog_service.service.ServiceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private  final ServiceCategoryService serviceCategoryService;

    @PostMapping("/create")
    public ResponseEntity<ServiceCategoryResponseDto> createCategory(
            @Valid @RequestBody ServiceCategoryRequestDto requestDto
    ){
        ServiceCategoryResponseDto responseDto =
                serviceCategoryService.createCategory(requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCategoryResponseDto> getCategoryById(
            @PathVariable Long id
    ){
        ServiceCategoryResponseDto responseDto =
                serviceCategoryService.getCategoryById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ServiceCategoryResponseDto>> getAllCategory(Pageable pageable){
        Page<ServiceCategoryResponseDto> responseDtos =
                serviceCategoryService.getAllCategories(pageable);

        return  ResponseEntity.ok(responseDtos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServiceCategoryResponseDto> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody ServiceCategoryRequestDto requestDto
    ){
        ServiceCategoryResponseDto responseDto =
                serviceCategoryService.updateCategory(id, requestDto);

        return  ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/status")
    public  ResponseEntity<Void> updateCategoryStatus(
            @PathVariable Long id,
            @Valid @RequestBody ServiceCategoryStatusUpdateRequestDto requestDto
            ){
        serviceCategoryService.updateCategoryStatus(id,requestDto);
        return ResponseEntity.noContent().build();
    }
}
