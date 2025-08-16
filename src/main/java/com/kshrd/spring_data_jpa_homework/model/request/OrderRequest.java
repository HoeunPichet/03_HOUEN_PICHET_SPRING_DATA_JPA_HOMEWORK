package com.kshrd.spring_data_jpa_homework.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @Schema(example = "1")
    @NotNull(message = "Quantity is required")
    @Min(value = 1L, message = "Quantity must be at least 1")
    @Max(value = 99999999L, message = "Quantity must have at most 8 digits")
    private Long quantity;
}
