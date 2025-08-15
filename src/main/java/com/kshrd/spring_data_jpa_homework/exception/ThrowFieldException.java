package com.kshrd.spring_data_jpa_homework.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ThrowFieldException extends RuntimeException {
    private String field;
    private String message;
}