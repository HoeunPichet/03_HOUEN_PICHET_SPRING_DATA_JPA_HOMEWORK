package com.kshrd.spring_data_jpa_homework.repository;

import com.kshrd.spring_data_jpa_homework.model.composite.OrderItemId;
import com.kshrd.spring_data_jpa_homework.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
