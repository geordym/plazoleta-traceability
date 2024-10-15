package com.plazoleta.traceability.domain.spi;

import com.plazoleta.traceability.domain.model.OrderStatusLog;

import java.util.List;
import java.util.Optional;

public interface IOrderLogPersistencePort {

    OrderStatusLog saveOrderStatusLog(OrderStatusLog orderStatusLog);
    Optional<OrderStatusLog> findLastOrderStatusLogByOrderId(Long orderId);

    List<OrderStatusLog> findAllOrderStatusLogByOrderId(Long orderId);

    List<OrderStatusLog> findAllOrderStatusLogByCustomerId(Long customerId);


    List<OrderStatusLog> findAllOrderStatusLogByEmployeeId(Long employeeId);


    List<OrderStatusLog> findAllOrderStatusLogByRestaurantId(Long restaurantId);
    List<Long> findAllEmployeesByRestaurantId(Long restaurantId);

}
