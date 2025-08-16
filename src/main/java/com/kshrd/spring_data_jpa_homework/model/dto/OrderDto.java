package com.kshrd.spring_data_jpa_homework.model.dto;

import com.kshrd.spring_data_jpa_homework.model.entity.CustomerAccount;
import com.kshrd.spring_data_jpa_homework.model.entity.Order;
import com.kshrd.spring_data_jpa_homework.model.entity.Product;
import com.kshrd.spring_data_jpa_homework.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private CustomerDto customerResponse;
    private List<ProductDto> productResponse;

    public static OrderDto toResponse(Order order, CustomerAccount customerAccount, List<Product> products) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .customerResponse(CustomerDto.toResponse(customerAccount))
                .productResponse(
                        products.stream()
                                .map(ProductDto::toResponse)
                                .toList()
                )
                .build();
    }
}
