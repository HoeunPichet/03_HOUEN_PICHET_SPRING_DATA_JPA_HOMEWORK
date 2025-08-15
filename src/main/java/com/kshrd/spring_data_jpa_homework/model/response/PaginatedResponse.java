package com.kshrd.spring_data_jpa_homework.model.response;

import com.kshrd.jpa_hibernate_homework.model.Pagination;
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
    private Pagination pagination;
}
