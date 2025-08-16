package com.kshrd.spring_data_jpa_homework.service.impl;

import com.kshrd.spring_data_jpa_homework.model.dto.OrderDto;
import com.kshrd.spring_data_jpa_homework.model.enums.OrderProperty;
import com.kshrd.spring_data_jpa_homework.model.request.OrderRequest;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import com.kshrd.spring_data_jpa_homework.repository.OrderItemRepository;
import com.kshrd.spring_data_jpa_homework.repository.OrderRepository;
import com.kshrd.spring_data_jpa_homework.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public PaginatedResponse<List<OrderDto>> getAllOrders(Long id, Integer page, Integer size, OrderProperty orderProperty, Sort.Direction direction) {
        return null;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return null;
    }

    @Override
    public OrderDto createOrder(Long id, OrderRequest request) {
        return null;
    }

    @Override
    public OrderDto updateOrderStatus(Long id, OrderProperty orderProperty) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }
}
