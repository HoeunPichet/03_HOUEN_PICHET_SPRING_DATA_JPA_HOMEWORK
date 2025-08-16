package com.kshrd.spring_data_jpa_homework.model.request;

import com.kshrd.spring_data_jpa_homework.model.entity.CustomerAccount;
import com.kshrd.spring_data_jpa_homework.model.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @Schema(example = "name")
    @NotBlank(message = "Product name is required")
    @Size(min = 3, message = "Product name must be at least 3 characters")
    @Size(max = 255, message = "Product name must not exceed 255 characters")
    @Pattern(
            regexp = "^[a-zA-Z][\\da-zA-Z\\s]*$",
            message = "Product name must not start with a space and number"
    )
    private String name;

    @Schema(example = "0")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than zero")
    @DecimalMax(value = "99999999.99", message = "Unit price must not exceed 99999999.99")
    @Digits(integer = 8, fraction = 2, message = "Unit price must have at most 8 digits before the decimal point and 2 after")
    private BigDecimal unitPrice;

    @Schema(example = "description")
    private String description;

    public Product toEntity() {
        return Product.builder()
                .name(this.name)
                .unitPrice(this.unitPrice)
                .description(this.description)
                .orderItems(null)
                .build();
    }
}
