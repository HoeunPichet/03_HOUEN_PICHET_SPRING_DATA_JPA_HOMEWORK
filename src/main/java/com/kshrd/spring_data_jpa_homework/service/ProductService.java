package com.kshrd.spring_data_jpa_homework.service;

import com.kshrd.spring_data_jpa_homework.model.dto.ProductDto;
import com.kshrd.spring_data_jpa_homework.model.enums.ProductProperty;
import com.kshrd.spring_data_jpa_homework.model.request.ProductRequest;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    PaginatedResponse<List<ProductDto>> getAllProducts(@Valid @Min(value = 1, message = "Page must be greater than 0") Integer page, @Valid @Min(value = 1, message = "Size must be greater than 0") Integer size, ProductProperty productProperty, Sort.Direction direction);

    ProductDto getProductById(Long id);

    ProductDto createProduct(ProductRequest request);

    ProductDto updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
