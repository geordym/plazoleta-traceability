package com.plazoleta.traceability.application.handler;

import com.plazoleta.traceability.application.dto.request.CreateOrderStatusLogRequest;
import com.plazoleta.traceability.domain.model.OrderRanking;
import com.plazoleta.traceability.domain.model.OrderWithLog;

import java.util.List;

public interface IOrderStatusLogHandler {

    void createOrderStatusLog(CreateOrderStatusLogRequest createOrderStatusLogRequest);
    OrderWithLog getOrderLogByOrderId(Long orderId);
    List<OrderWithLog> getAllOrderLogByCustomer();

    OrderRanking getOrderRankingOfRestaurant(Long restaurantId);
}
