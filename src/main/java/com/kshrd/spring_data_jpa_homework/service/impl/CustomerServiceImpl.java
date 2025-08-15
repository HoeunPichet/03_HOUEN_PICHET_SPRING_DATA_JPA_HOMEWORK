package com.kshrd.spring_data_jpa_homework.service.impl;

import com.kshrd.spring_data_jpa_homework.exception.AppNotFoundException;
import com.kshrd.spring_data_jpa_homework.model.dto.CustomerDto;
import com.kshrd.spring_data_jpa_homework.model.entity.Customer;
import com.kshrd.spring_data_jpa_homework.model.entity.CustomerAccount;
import com.kshrd.spring_data_jpa_homework.model.enums.CustomerProperty;
import com.kshrd.spring_data_jpa_homework.model.request.CustomerRequest;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import com.kshrd.spring_data_jpa_homework.repository.CustomerAccountRepository;
import com.kshrd.spring_data_jpa_homework.service.CustomerService;
import com.kshrd.spring_data_jpa_homework.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerAccountRepository customerAccountRepository;

    @Override
    public PaginatedResponse<List<CustomerDto>> getAllCustomers(Integer page, Integer size, CustomerProperty customerProperty, Sort.Direction direction) {
        Pagination<CustomerAccount, CustomerAccountRepository> pagination = new Pagination<>(customerAccountRepository);

        PaginatedResponse<List<CustomerAccount>> response =
                pagination.getAllWithPagination(page, size, customerProperty.getFieldName(), direction);

        List<CustomerDto> customerDtos = response.getItems().stream()
                .map(cus -> new CustomerDto().toResponse(cus))
                .toList();

        return new PaginatedResponse<>(customerDtos, response.getPagination());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        CustomerAccount customerAccount = customerAccountRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Customer not found"));

        return new CustomerDto().toResponse(customerAccount);
    }

    @Override
    public CustomerDto createCustomer(CustomerRequest request) {
        boolean existingAccount = customerAccountRepository.findByUsername(request.getUsername()).isPresent();
        if (existingAccount) {
            throw new AppNotFoundException("Username is already taken");
        }

        CustomerAccount customerAccount = request.toEntity();
        Customer customer = Customer.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .customerAccount(customerAccount)
                .build();

        customerAccount.setCustomer(customer);

        return new CustomerDto().toResponse(customerAccountRepository.save(customerAccount));
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerRequest request) {
        CustomerAccount customerAccount = customerAccountRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Customer ID " + id + " not found"));

        CustomerAccount existingUsername = customerAccountRepository.findByUsername(request.getUsername()).orElse(null);
        if (existingUsername != null) {
            if (!customerAccount.getUsername().equals(request.getUsername())) {
                throw new AppNotFoundException("Username is already taken");
            }
        }

        customerAccount.setUsername(request.getUsername());
        customerAccount.setPassword(request.getPassword());
        customerAccount.getCustomer().setName(request.getName());
        customerAccount.getCustomer().setAddress(request.getAddress());
        customerAccount.getCustomer().setPhoneNumber(request.getPhoneNumber());

        return new CustomerDto().toResponse(customerAccountRepository.save(customerAccount));
    }

    @Override
    public void deleteCustomer(Long id) {
        CustomerAccount customerAccount = customerAccountRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Customer ID " + id + " not found"));
        customerAccountRepository.delete(customerAccount);
    }
}
