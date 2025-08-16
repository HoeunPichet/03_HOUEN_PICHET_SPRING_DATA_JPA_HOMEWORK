package com.kshrd.spring_data_jpa_homework.controller;

import com.kshrd.spring_data_jpa_homework.model.dto.CustomerDto;
import com.kshrd.spring_data_jpa_homework.model.enums.CustomerProperty;
import com.kshrd.spring_data_jpa_homework.model.request.CustomerRequest;
import com.kshrd.spring_data_jpa_homework.model.response.ApiResponse;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import com.kshrd.spring_data_jpa_homework.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer")
public class CustomerController {
    private final CustomerService customerService;

    @Operation(
            summary = "Get paginated list of customers",
            description = "Retrieves all customers in a paginated format. You can specify the page number, page size, sorting property, and sort direction."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<List<CustomerDto>>>> getAllCustomers(
            @RequestParam(defaultValue = "1") @Valid @Min(value = 1, message = "Page must be greater than 0") Integer page,
            @RequestParam(defaultValue = "10") @Valid @Min(value = 1, message = "Size must be greater than 0") Integer size,
            @RequestParam CustomerProperty customerProperty,
            @RequestParam Sort.Direction direction
            ) {
        PaginatedResponse<List<CustomerDto>> customers = customerService.getAllCustomers(page, size, customerProperty, direction);
        ApiResponse<PaginatedResponse<List<CustomerDto>>> response = ApiResponse.<PaginatedResponse<List<CustomerDto>>>builder()
                .success(true)
                .message("Get all customers successfully!")
                .payload(customers)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get customer by ID",
            description = "Fetches detailed information about a single customer using the provided customer ID."
    )
    @GetMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<CustomerDto>> getCustomerById(@PathVariable("customer-id") Long id) {
        CustomerDto customer = customerService.getCustomerById(id);
        ApiResponse<CustomerDto> response = ApiResponse.<CustomerDto>builder()
                .success(true)
                .message("Get customer with ID " + id)
                .payload(customer)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create a new customer",
            description = """
            Accepts a valid
            <code>CustomerRequest</code>
            object to create a new customer record.
            """
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDto>> createCustomer(@RequestBody @Valid CustomerRequest request) {
        CustomerDto customer = customerService.createCustomer(request);
        ApiResponse<CustomerDto> response = ApiResponse.<CustomerDto>builder()
                .success(true)
                .message("Customer has been created successfully!")
                .status(HttpStatus.CREATED)
                .payload(customer)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{customer-id}")
    @Operation(
            summary = "Update existing customer",
            description = "Updates the details of an existing customer based on the provided customer ID and request body."
    )
    public ResponseEntity<ApiResponse<CustomerDto>> updateCustomer(@PathVariable("customer-id") Long id, @RequestBody @Valid CustomerRequest request) {
        CustomerDto customer = customerService.updateCustomer(id, request);
        ApiResponse<CustomerDto> response = ApiResponse.<CustomerDto>builder()
                .success(true)
                .message("Customer with ID " + id + " has been updated successfully!")
                .status(HttpStatus.OK)
                .payload(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customer-id}")
    @Operation(
            summary = "Delete customer by ID",
            description = "Removes a customer record from the system based on the provided customer ID."
    )
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable("customer-id") Long id) {
        customerService.deleteCustomer(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Customer with ID " + id + " has been deleted successfully!")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }
}
