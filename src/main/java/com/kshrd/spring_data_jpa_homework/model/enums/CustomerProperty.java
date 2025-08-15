package com.kshrd.spring_data_jpa_homework.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerProperty {
    ID("customerId"),
    NAME("customer.name");

    private final String fieldName;
}