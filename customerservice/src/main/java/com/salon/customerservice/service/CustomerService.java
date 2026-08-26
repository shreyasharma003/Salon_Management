package com.salon.customerservice.service;

import com.salon.customerservice.dto.inDto.CustomerRequest;
import com.salon.customerservice.dto.outDto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerById(long id);
    CustomerResponse getCustomerByEmail(String email);
    CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest);
    void deleteCustomer(Long id);
    long getCustomerCount();
}
