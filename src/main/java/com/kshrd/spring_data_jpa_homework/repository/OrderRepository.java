package com.kshrd.spring_data_jpa_homework.repository;

import com.kshrd.spring_data_jpa_homework.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCustomer_CustomerId(Long id, Pageable pageable);
}
