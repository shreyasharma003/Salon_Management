package com.salon.service_catalog_service.controller;

import com.salon.service_catalog_service.dto.inDto.ServiceRequestDto;
import com.salon.service_catalog_service.dto.inDto.ServiceStatusUpdateRequestDto;
import com.salon.service_catalog_service.dto.outDto.ServiceResponseDto;
import com.salon.service_catalog_service.service.Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {

    private final Service service;

    @PostMapping("/create")
    public ResponseEntity<ServiceResponseDto> createService(
            @Valid @RequestBody ServiceRequestDto serviceRequestDto
    ){
        ServiceResponseDto responseDto = service.createService(serviceRequestDto);
        return  ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> getServiceById(
            @PathVariable Long id
    ){
        ServiceResponseDto responseDto = service.getServiceById(id);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ServiceResponseDto>> getAllServices(
            Pageable pageable
    ){
        Page<ServiceResponseDto> responseDtos = service.getAllServices(pageable);

        return ResponseEntity.ok(responseDtos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequestDto serviceRequestDto
    ){
        ServiceResponseDto responseDto = service.updateService(id, serviceRequestDto);

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ServiceResponseDto> updateServiceStatus(
            @PathVariable Long id,
            @Valid @RequestBody ServiceStatusUpdateRequestDto requestDto
            ){
        ServiceResponseDto responseDto = service.updateServiceStatus(id,requestDto.getActive());

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(
            @PathVariable Long id
    ){
        service.deleteService(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ServiceResponseDto>> getAllServices(
            @RequestParam String keyword,
            Pageable pageable
    ){
        Page<ServiceResponseDto> responseDtos = service.searchServices(keyword, pageable);

        return  ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/category/{categoryId}")
    public  ResponseEntity<Page<ServiceResponseDto>> getAllServices(
            @PathVariable Long categoryId,
            Pageable pageable
    ){
        Page<ServiceResponseDto> responseDtos = service.getServicesByCategory(categoryId, pageable);

        return ResponseEntity.ok(responseDtos);
    }

}
