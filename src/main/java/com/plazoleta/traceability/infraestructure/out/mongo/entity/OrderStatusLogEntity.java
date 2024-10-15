package com.plazoleta.traceability.infraestructure.out.mongo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "orderStatusLogs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusLogEntity {
    @Id
    private String id;
    private Long orderId;
    private Long restaurantId;
    private Long customerId;
    private Long employeeId;
    private String previousStatus;
    private String status;
    private String description;
    private LocalDateTime updatedAt;
}