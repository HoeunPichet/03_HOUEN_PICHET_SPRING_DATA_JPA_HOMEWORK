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
@Table(name = "customer_accounts")
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false, updatable = false)
    private Long customerId;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private Boolean isActive;

    @OneToOne(mappedBy = "customer_account", cascade = CascadeType.ALL, orphanRemoval = true)
    Customer customer;
}
