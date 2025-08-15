package com.kshrd.spring_data_jpa_homework.service;

import com.kshrd.spring_data_jpa_homework.model.dto.CustomerDto;
import com.kshrd.spring_data_jpa_homework.model.enums.CustomerProperty;
import com.kshrd.spring_data_jpa_homework.model.request.CustomerRequest;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CustomerService {
    PaginatedResponse<List<CustomerDto>> getAllCustomers(@Valid @Min(value = 1, message = "Page must be greater than 0") Integer page, @Valid @Min(value = 1, message = "Size must be greater than 0") Integer size, CustomerProperty customerProperty, Sort.Direction direction);

    CustomerDto getCustomerById(Long id);

    CustomerDto createCustomer(CustomerRequest request);

    CustomerDto updateCustomer(Long id, CustomerRequest request);

    void deleteCustomer(Long id);

}
