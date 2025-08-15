package com.kshrd.spring_data_jpa_homework.service.impl;

import com.kshrd.spring_data_jpa_homework.repository.ProductRepository;
import com.kshrd.spring_data_jpa_homework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
}
