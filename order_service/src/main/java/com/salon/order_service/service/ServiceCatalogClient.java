package com.salon.order_service.service;

import com.salon.order_service.dto.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "service-catalog-service")
public interface ServiceCatalogClient {
    @GetMapping("/service/{id}")
    ServiceResponse getServiceById(
            @PathVariable("id") Long id
    );
}
