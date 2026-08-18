package com.salon.customerservice.service.impl;

import com.salon.customerservice.dto.inDto.CustomerRequest;
import com.salon.customerservice.dto.outDto.CustomerResponse;
import com.salon.customerservice.entity.Customer;
import com.salon.customerservice.exception.ContactNumberAlreadyExistsException;
import com.salon.customerservice.exception.CustomerAlreadyExistsException;
import com.salon.customerservice.exception.CustomerNotFoundException;
import com.salon.customerservice.mapper.CustomerMapper;
import com.salon.customerservice.repository.CustomerRepository;
import com.salon.customerservice.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper
    ){
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomerAlreadyExistsException(
                    "Customer already exists with email: " + request.getEmail()
            );
        }

        if (customerRepository.findByContactNumber(request.getContactNumber()).isPresent()) {
            throw new ContactNumberAlreadyExistsException(
                    "Customer already exists with contact number: " + request.getContactNumber()
            );
        }

        Customer customer = customerMapper.toEntity(request);

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with email: " + email
                        )
                );

        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + id
                        )
                );

        existingCustomer.setName(request.getName());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setContactNumber(request.getContactNumber());
        existingCustomer.setGender(request.getGender());

        Customer updatedCustomer =
                customerRepository.save(existingCustomer);

        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + id
                        )
                );

        customerRepository.delete(customer);
    }


}
