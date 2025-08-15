package com.kshrd.spring_data_jpa_homework.model.response;

import com.kshrd.spring_data_jpa_homework.model.dto.PaginationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private T items;
    private PaginationDto pagination;
}
