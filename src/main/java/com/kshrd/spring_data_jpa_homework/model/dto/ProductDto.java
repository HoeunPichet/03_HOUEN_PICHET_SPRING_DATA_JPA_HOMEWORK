package com.kshrd.spring_data_jpa_homework.model.dto;

import com.kshrd.spring_data_jpa_homework.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long productId;
    private String name;
    private BigDecimal unitPrice;
    private String description;

    public static ProductDto toResponse(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .unitPrice(product.getUnitPrice())
                .description(product.getDescription())
                .build();
    }
}
