package com.kshrd.spring_data_jpa_homework.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long productId;

    private Long quantity;
}
