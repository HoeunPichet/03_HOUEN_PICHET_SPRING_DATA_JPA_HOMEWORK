package com.kshrd.spring_data_jpa_homework.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum CustomerProperty {
    ID("customerId"),
    NAME("customer.name");

    private final String fieldName;
}