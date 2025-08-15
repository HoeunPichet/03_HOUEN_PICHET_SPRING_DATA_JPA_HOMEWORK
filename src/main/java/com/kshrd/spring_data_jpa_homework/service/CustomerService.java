package com.kshrd.spring_data_jpa_homework.service;

import com.kshrd.spring_data_jpa_homework.model.dto.CustomerDto;
import com.kshrd.spring_data_jpa_homework.model.request.CustomerRequest;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    CustomerDto createCustomer(CustomerRequest request);

    CustomerDto updateCustomer(Long id, CustomerRequest request);

    void deleteCustomer(Long id);
}
