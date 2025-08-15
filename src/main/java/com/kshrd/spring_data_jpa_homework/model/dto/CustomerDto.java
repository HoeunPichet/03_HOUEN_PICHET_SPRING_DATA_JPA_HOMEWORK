package com.kshrd.spring_data_jpa_homework.model.dto;

import com.kshrd.spring_data_jpa_homework.model.entity.Customer;
import com.kshrd.spring_data_jpa_homework.model.entity.CustomerAccount;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long customerId;
    private String name;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;
    private Boolean isActive;

    public CustomerDto toResponse(CustomerAccount customerAccount) {
        return CustomerDto.builder()
                .customerId(customerAccount.getCustomerId())
                .name(customerAccount.getCustomer().getName())
                .address(customerAccount.getCustomer().getAddress())
                .phoneNumber(customerAccount.getCustomer().getPhoneNumber())
                .username(customerAccount.getUsername())
                .password(customerAccount.getPassword())
                .isActive(customerAccount.getIsActive())
                .build();
    }
}
