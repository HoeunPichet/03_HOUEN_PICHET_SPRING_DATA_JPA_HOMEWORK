package com.kshrd.spring_data_jpa_homework.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @OneToOne
    @Column(name = "customer_id", nullable = false, updatable = false)
    private Long customerId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String phone_number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerAccount customerAccount;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Order order;
}
