package com.plazoleta.traceability.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeePerformance {
    private Long employeeId;
    private Double averageCompletionTimeInSeconds;
}
