package com.kshrd.spring_data_jpa_homework.repository;

import com.kshrd.spring_data_jpa_homework.model.composite.OrderItemId;
import com.kshrd.spring_data_jpa_homework.model.entity.OrderItem;
import com.kshrd.spring_data_jpa_homework.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    @Query("SELECT oi.product FROM OrderItem oi WHERE oi.order.orderId = :orderId")
    List<Product> findProductsByOrderId(@Param("orderId") Long orderId);
}
