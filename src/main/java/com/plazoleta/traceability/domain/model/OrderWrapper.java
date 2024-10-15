package com.plazoleta.traceability.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderWrapper {
    private Long orderId;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private Double finishedInSeconds;
}
