package com.kshrd.spring_data_jpa_homework.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThrowFieldException extends RuntimeException {
    private String field;
    private String message;
}