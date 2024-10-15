package com.plazoleta.traceability.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRanking {

    List<OrderCompletion> orderCompletionList;
    List<EmployeePerformance> employeePerformances;

}
