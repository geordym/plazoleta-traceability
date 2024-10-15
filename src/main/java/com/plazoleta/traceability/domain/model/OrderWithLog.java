package com.plazoleta.traceability.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderWithLog {
    private Long orderId;
    private List<OrderStatusLog> logs;

    public OrderWithLog(Long orderId) {
        this.orderId = orderId;
    }
}
