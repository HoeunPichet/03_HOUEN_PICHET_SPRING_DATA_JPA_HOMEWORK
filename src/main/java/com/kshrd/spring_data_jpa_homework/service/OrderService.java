package com.kshrd.spring_data_jpa_homework.service;

import com.kshrd.spring_data_jpa_homework.model.dto.OrderDto;
import com.kshrd.spring_data_jpa_homework.model.enums.OrderProperty;
import com.kshrd.spring_data_jpa_homework.model.request.OrderRequest;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {
    PaginatedResponse<List<OrderDto>> getAllOrders(Long id, @Valid @Min(value = 1, message = "Page must be greater than 0") Integer page, @Valid @Min(value = 1, message = "Size must be greater than 0") Integer size, OrderProperty orderProperty, Sort.Direction direction);

    OrderDto getOrderById(Long id);

    OrderDto createOrder(Long id, @Valid List<OrderRequest> request);

    OrderDto updateOrderStatus(Long id, OrderProperty orderProperty);

    void deleteOrder(Long id);
}
