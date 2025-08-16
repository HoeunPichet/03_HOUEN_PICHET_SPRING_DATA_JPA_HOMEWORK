package com.kshrd.spring_data_jpa_homework.service.impl;

import com.kshrd.spring_data_jpa_homework.exception.AppNotFoundException;
import com.kshrd.spring_data_jpa_homework.model.composite.OrderItemId;
import com.kshrd.spring_data_jpa_homework.model.dto.OrderDto;
import com.kshrd.spring_data_jpa_homework.model.dto.PaginationDto;
import com.kshrd.spring_data_jpa_homework.model.entity.CustomerAccount;
import com.kshrd.spring_data_jpa_homework.model.entity.Order;
import com.kshrd.spring_data_jpa_homework.model.entity.OrderItem;
import com.kshrd.spring_data_jpa_homework.model.entity.Product;
import com.kshrd.spring_data_jpa_homework.model.enums.OrderProperty;
import com.kshrd.spring_data_jpa_homework.model.enums.OrderStatus;
import com.kshrd.spring_data_jpa_homework.model.request.OrderRequest;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import com.kshrd.spring_data_jpa_homework.repository.CustomerAccountRepository;
import com.kshrd.spring_data_jpa_homework.repository.OrderItemRepository;
import com.kshrd.spring_data_jpa_homework.repository.OrderRepository;
import com.kshrd.spring_data_jpa_homework.repository.ProductRepository;
import com.kshrd.spring_data_jpa_homework.service.OrderService;
import com.kshrd.spring_data_jpa_homework.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CustomerAccountRepository customerAccountRepository;

    @Override
    public PaginatedResponse<List<OrderDto>> getAllOrders(Long id, Integer page, Integer size, OrderProperty orderProperty, Sort.Direction direction) {
        Sort sort = Sort.by(new Sort.Order(direction, orderProperty.getFieldName()).ignoreCase());
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Order> orderPage = orderRepository.findByCustomer_CustomerId(id, pageable);

        List<OrderDto> orderDtos = orderPage.getContent().stream()
                .map(order -> OrderDto.toResponse(order, order.getCustomer().getCustomerAccount(), orderItemRepository.findProductsByOrderId(order.getOrderId())))
                .toList();

        PaginationDto pagination = PaginationDto.builder()
                .totalElements(orderPage.getTotalElements())
                .pageSize(orderPage.getSize())
                .currentPage(orderPage.getNumber() + 1)
                .totalPages(orderPage.getTotalPages())
                .build();

        return new PaginatedResponse<>(orderDtos, pagination);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Order ID " + id + " not found"));
        CustomerAccount customerAccount = customerAccountRepository.findById(order.getCustomer().getCustomerId()).orElseThrow(() -> new AppNotFoundException("Customer not found"));
        List<Product> products = orderItemRepository.findProductsByOrderId(id);

        return OrderDto.toResponse(order, customerAccount, products);
    }

    @Override
    public OrderDto createOrder(Long id, List<OrderRequest> request) {
        CustomerAccount customerAccount = customerAccountRepository.findById(id)
                .orElseThrow(() -> new AppNotFoundException("Customer not found"));

        Set<Long> productIds = request.stream().map(OrderRequest::getProductId).collect(Collectors.toSet());

        if (productIds.size() != request.size()) {
            throw new AppNotFoundException("Duplicate product IDs are not allowed");
        }

        Map<Long, Product> productMap = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getProductId, p -> p));

        productIds.forEach(productId -> {
            if (!productMap.containsKey(productId)) {
                throw new AppNotFoundException("Product with ID " + productId + " not found");
            }
        });

        BigDecimal totalAmount = request.stream()
                .map(req -> {
                    Product product = productMap.get(req.getProductId());
                    return BigDecimal.valueOf(req.getQuantity()).multiply(product.getUnitPrice());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .customer(customerAccount.getCustomer())
                .totalAmount(totalAmount)
                .build();

        Order insertedOrder = orderRepository.save(order);
        request.forEach(req -> {
            Product product = productMap.get(req.getProductId());

            OrderItemId orderItemId = OrderItemId.builder()
                    .orderId(insertedOrder.getOrderId())
                    .productId(req.getProductId())
                    .build();

            OrderItem orderItem = OrderItem.builder()
                    .orderItemId(orderItemId)
                    .quantity(req.getQuantity())
                    .order(insertedOrder)
                    .product(product)
                    .build();

            orderItemRepository.save(orderItem);
        });

        return this.getOrderById(insertedOrder.getOrderId());
    }

    @Override
    public OrderDto updateOrderStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Order ID " + id + " not found"));
        order.setStatus(orderStatus);
        orderRepository.save(order);

        return this.getOrderById(id);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Order ID " + id + " not found"));
        orderRepository.delete(order);
    }
}
