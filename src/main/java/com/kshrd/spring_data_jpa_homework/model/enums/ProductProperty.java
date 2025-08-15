package com.kshrd.spring_data_jpa_homework.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductProperty {
    ID("productId"),
    NAME("name");

    private final String fieldName;
}
