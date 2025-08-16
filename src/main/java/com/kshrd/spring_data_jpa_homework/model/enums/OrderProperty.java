package com.kshrd.spring_data_jpa_homework.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderProperty {
    ID("orderId"),
    ORDER_DATE("orderDate"),
    TOTAL_AMOUNT("totalAmount");

    private final String fieldName;
}
