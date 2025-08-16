package com.kshrd.spring_data_jpa_homework.service.impl;

import com.kshrd.spring_data_jpa_homework.exception.AppNotFoundException;
import com.kshrd.spring_data_jpa_homework.model.dto.ProductDto;
import com.kshrd.spring_data_jpa_homework.model.entity.Product;
import com.kshrd.spring_data_jpa_homework.model.enums.ProductProperty;
import com.kshrd.spring_data_jpa_homework.model.request.ProductRequest;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import com.kshrd.spring_data_jpa_homework.repository.ProductRepository;
import com.kshrd.spring_data_jpa_homework.service.ProductService;
import com.kshrd.spring_data_jpa_homework.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public PaginatedResponse<List<ProductDto>> getAllProducts(Integer page, Integer size, ProductProperty productProperty, Sort.Direction direction) {
        return new Pagination<>(productRepository).getPaginatedContent(
                page,
                size,
                productProperty.getFieldName(),
                direction,
                ProductDto::toResponse
        );
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Product not found"));

        return ProductDto.toResponse(product);
    }

    @Override
    public ProductDto createProduct(ProductRequest request) {
        Product product = request.toEntity();

        return ProductDto.toResponse(productRepository.save(product));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Product ID " + id + " not found"));
        product.setName(request.getName());
        product.setUnitPrice(request.getUnitPrice());
        product.setDescription(request.getDescription());

        return ProductDto.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppNotFoundException("Product ID " + id + " not found"));
        productRepository.delete(product);
    }
}
