package com.kshrd.spring_data_jpa_homework.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private Long customerId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String phoneNumber;

    @OneToOne
    @MapsId
    @PrimaryKeyJoinColumn(name = "customer_id")
    private CustomerAccount customerAccount;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> order;
}
