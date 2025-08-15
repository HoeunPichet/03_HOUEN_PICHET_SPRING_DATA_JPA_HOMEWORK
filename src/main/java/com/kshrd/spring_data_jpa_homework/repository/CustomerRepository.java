package com.kshrd.spring_data_jpa_homework.repository;

import com.kshrd.spring_data_jpa_homework.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
