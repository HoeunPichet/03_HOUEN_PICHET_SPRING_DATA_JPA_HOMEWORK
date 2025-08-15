package com.kshrd.spring_data_jpa_homework.model.request;

import com.kshrd.spring_data_jpa_homework.model.entity.CustomerAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
     @Schema(example = "name")
     @NotBlank(message = "Customer name is required")
     @Size(min = 3, message = "Customer name must be at least 3 characters")
     @Size(max = 255, message = "Customer name must not exceed 255 characters")
     @Pattern(
             regexp = "^[a-zA-Z][a-zA-Z\\s]*$",
             message = "Customer name is allowed for only letters, and must not start with a space"
     )
     private String name;

     @Schema(example = "address")
     private String address;

     @Schema(example = "012345678")
     @NotBlank(message = "Phone number is required")
     @Pattern(
             regexp = "^0\\d{8,9}$",
             message = "Phone number is allowed only number amd must be 9 or 10 digits and start with zero"
     )
     private String phoneNumber;

     @Schema(example = "username")
     @NotBlank(message = "Username is required")
     @Size(min = 3, message = "Username must be at least 3 characters")
     @Size(max = 255, message = "Username must not exceed 255 characters")
     @Pattern(
             regexp = "^[a-zA-Z][a-zA-Z\\s]*$",
             message = "Username is allowed for only letters, and must not start with a space"
     )
     private String username;

     @Schema(example = "password")
     @NotBlank(message = "Password is required")
     @Size(min = 8, message = "Password must be at least 8 characters")
     @Size(max = 32, message = "Password must not exceed 32 characters")
     private String password;

     public CustomerAccount toEntity() {
          return CustomerAccount.builder()
                  .username(this.username)
                  .password(this.password)
                  .customer(null)
                  .build();
     }
}
