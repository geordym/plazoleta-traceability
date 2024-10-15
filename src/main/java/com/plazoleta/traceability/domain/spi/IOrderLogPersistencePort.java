package com.plazoleta.traceability.domain.spi;

import com.plazoleta.traceability.domain.model.OrderStatusLog;

import java.util.Optional;

public interface IOrderLogPersistencePort {

    OrderStatusLog saveOrderStatusLog(OrderStatusLog orderStatusLog);
    Optional<OrderStatusLog> findLastOrderStatusLogByOrderId(Long orderId);
}
