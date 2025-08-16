package com.kshrd.spring_data_jpa_homework.controller;


import com.kshrd.spring_data_jpa_homework.model.dto.OrderDto;
import com.kshrd.spring_data_jpa_homework.model.enums.OrderProperty;
import com.kshrd.spring_data_jpa_homework.model.request.OrderRequest;
import com.kshrd.spring_data_jpa_homework.model.response.ApiResponse;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import com.kshrd.spring_data_jpa_homework.service.OrderService;
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
@RequestMapping("/api/v1/orders")
@Tag(name = "Order")
public class OrderController {
    private final OrderService orderService;

    @Operation(
            summary = "Get paginated list of orders by customer",
            description = """
                    Retrieves a paginated list of orders for the specified customer. Supports page/size and sorting by
                    <code>OrderProperty</code>
                    with
                    <code>Sort.Direction</code>.
                    """
    )
    @GetMapping("/customers/{customer-id}")
    public ResponseEntity<ApiResponse<PaginatedResponse<List<OrderDto>>>> getAllOrders(
            @PathVariable("customer-id") Long id,
            @RequestParam(defaultValue = "1") @Valid @Min(value = 1, message = "Page must be greater than 0") Integer page,
            @RequestParam(defaultValue = "10") @Valid @Min(value = 1, message = "Size must be greater than 0") Integer size,
            @RequestParam OrderProperty orderProperty,
            @RequestParam Sort.Direction direction
    ) {
        PaginatedResponse<List<OrderDto>> orders = orderService.getAllOrders(id, page, size, orderProperty, direction);
        ApiResponse<PaginatedResponse<List<OrderDto>>> response = ApiResponse.<PaginatedResponse<List<OrderDto>>>builder()
                .success(true)
                .message("Get all orders successfully!")
                .payload(orders)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get order by ID",
            description = "Fetches detailed information about a single order using the provided order ID."
    )
    @GetMapping("/{order-id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable("order-id") Long id) {
        OrderDto order = orderService.getOrderById(id);
        ApiResponse<OrderDto> response = ApiResponse.<OrderDto>builder()
                .success(true)
                .message("Get order with ID " + id)
                .payload(order)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create new order(s) for customer",
            description = """
            Creates one new order for the given customer using a non-empty list of
            <code>OrderRequest</code>
            items (each item represents an order line). Returns the created order with calculated totals.
            """
    )
    @PostMapping("/customers/{customer-id}")
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(@PathVariable("customer-id") Long id, @RequestBody @Valid OrderRequest request) {
        OrderDto order = orderService.createOrder(id, request);
        ApiResponse<OrderDto> response = ApiResponse.<OrderDto>builder()
                .success(true)
                .message("Order has been created successfully!")
                .status(HttpStatus.CREATED)
                .payload(order)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{order-id}/status")
    @Operation(
            summary = "Update order status",
            description = """
                    Updates the status of an existing order using the provided order ID and
                    <code>OrderStatus</code> value
                    (e.g., <code>PENDING</code>, <code>PAID</code>, <code>SHIPPED</code>, <code>CANCELLED</code>).
                    """
    )
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderStatus(@PathVariable("order-id") Long id, @RequestParam OrderProperty orderProperty) {
        OrderDto order = orderService.updateOrderStatus(id, orderProperty);
        ApiResponse<OrderDto> response = ApiResponse.<OrderDto>builder()
                .success(true)
                .message("Order with ID " + id + " has been updated successfully!")
                .status(HttpStatus.OK)
                .payload(order)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{order-id}")
    @Operation(
            summary = "Delete order by ID",
            description = "Delete an order from the system using the specified order ID."
    )
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable("order-id") Long id) {
        orderService.deleteOrder(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Order with ID " + id + " has been deleted successfully!")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }
}