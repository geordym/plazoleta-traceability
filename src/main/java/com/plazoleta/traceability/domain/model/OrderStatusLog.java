package com.plazoleta.traceability.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusLog {
    private String id;
    private Long restaurantId;
    private Long orderId;
    private Long customerId;
    private Long employeeId;
    private String previousStatus;
    private String status;
    private String description;
    private LocalDateTime updatedAt;
}
