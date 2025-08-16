package com.kshrd.spring_data_jpa_homework.controller;

import com.kshrd.spring_data_jpa_homework.model.dto.ProductDto;
import com.kshrd.spring_data_jpa_homework.model.enums.ProductProperty;
import com.kshrd.spring_data_jpa_homework.model.request.ProductRequest;
import com.kshrd.spring_data_jpa_homework.model.response.ApiResponse;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import com.kshrd.spring_data_jpa_homework.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product")
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Get paginated list of products",
            description = "Retrieves all products in a paginated format. You can specify the page number, page size, sorting property, and sort direction."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PaginatedResponse<List<ProductDto>>>> getAllProducts(
            @RequestParam(defaultValue = "1") @Valid @Min(value = 1, message = "Page must be greater than 0") Integer page,
            @RequestParam(defaultValue = "10") @Valid @Min(value = 1, message = "Size must be greater than 0") Integer size,
            @RequestParam ProductProperty productProperty,
            @RequestParam Sort.Direction direction
    ) {
        PaginatedResponse<List<ProductDto>> products = productService.getAllProducts(page, size, productProperty, direction);
        ApiResponse<PaginatedResponse<List<ProductDto>>> response = ApiResponse.<PaginatedResponse<List<ProductDto>>>builder()
                .success(true)
                .message("Get all products successfully!")
                .payload(products)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get product by ID",
            description = "Fetches detailed information about a single product using the provided product ID."
    )
    @GetMapping("/{product-id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable("product-id") Long id) {
        ProductDto product = productService.getProductById(id);
        ApiResponse<ProductDto> response = ApiResponse.<ProductDto>builder()
                .success(true)
                .message("Get product with ID " + id)
                .payload(product)
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Create a new product",
            description = """
            Accepts a valid
            <code>ProductRequest</code>
            object to create a new product record.
            """
    )
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody @Valid ProductRequest request) {
        ProductDto product = productService.createProduct(request);
        ApiResponse<ProductDto> response = ApiResponse.<ProductDto>builder()
                .success(true)
                .message("Product has been created successfully!")
                .status(HttpStatus.CREATED)
                .payload(product)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{product-id}")
    @Operation(
            summary = "Update existing product",
            description = "Updates the details of an existing product based on the provided product ID and request body."
    )
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable("product-id") Long id, @RequestBody @Valid ProductRequest request) {
        ProductDto product = productService.updateProduct(id, request);
        ApiResponse<ProductDto> response = ApiResponse.<ProductDto>builder()
                .success(true)
                .message("Product with ID " + id + " has been updated successfully!")
                .status(HttpStatus.OK)
                .payload(product)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{product-id}")
    @Operation(
            summary = "Delete product by ID",
            description = "Removes a product record from the system based on the provided product ID."
    )
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable("product-id") Long id) {
        productService.deleteProduct(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Product with ID " + id + " has been deleted successfully!")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.ok(response);
    }
}