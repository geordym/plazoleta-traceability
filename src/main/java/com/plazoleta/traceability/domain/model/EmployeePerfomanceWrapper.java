package com.plazoleta.traceability.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeePerfomanceWrapper {
    private Long employeeId;
    private List<OrderWrapper> orders;
}
