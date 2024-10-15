package com.plazoleta.traceability.domain.api;

import com.plazoleta.traceability.domain.model.OrderStatusLog;
import com.plazoleta.traceability.domain.model.OrderWithLog;

import java.util.List;

public interface IOrderLogServicePort {


    void createLog(OrderStatusLog orderStatusLog);

    OrderWithLog getOrderLogByOrderId(Long orderId);

    List<OrderWithLog> getAllOrderLogByCustomer();

}
