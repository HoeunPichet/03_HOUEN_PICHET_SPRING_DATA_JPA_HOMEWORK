package com.kshrd.spring_data_jpa_homework.model.composite;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class OrderItemId implements Serializable {
    private Long productId;

    private Long orderId;
}
