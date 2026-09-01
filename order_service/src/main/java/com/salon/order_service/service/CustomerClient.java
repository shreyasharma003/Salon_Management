package com.salon.order_service.service;

import com.salon.order_service.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customerservice")
public interface CustomerClient {

    @GetMapping("/customer/{id}")
    CustomerResponse getCustomerById(
            @PathVariable("id") Long id
    );
}
