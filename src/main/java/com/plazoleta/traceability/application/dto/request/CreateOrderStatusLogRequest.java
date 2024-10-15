package com.plazoleta.traceability.application.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderStatusLogRequest {
    private Long orderId;
    private Long restaurantId;
    private Long customerId;
    private Long employeeId;
    private String status;
    private String description;
}
